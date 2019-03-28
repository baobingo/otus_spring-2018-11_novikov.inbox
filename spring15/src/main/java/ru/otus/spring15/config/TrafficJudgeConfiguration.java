package ru.otus.spring15.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.expression.common.LiteralExpression;
import org.springframework.integration.annotation.*;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.dsl.*;
import org.springframework.integration.mongodb.inbound.MongoDbMessageSource;
import org.springframework.integration.mongodb.outbound.MongoDbStoringMessageHandler;
import org.springframework.integration.scheduling.PollerMetadata;
import org.springframework.messaging.MessageHandler;
import ru.otus.spring15.domain.Penalty;
import ru.otus.spring15.domain.Vehicle;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

@Configuration
@ComponentScan("ru.otus.spring15")
@IntegrationComponentScan("ru.otus.spring15")
@EnableIntegration
public class TrafficJudgeConfiguration {

    /*
    * Канал в который пушим проезжающие машины
    * */
    @Bean
    public QueueChannel aimChannel() {
        return MessageChannels.queue(100).get();
    }

    @Bean
    public PublishSubscribeChannel postboxChannel() {
        return MessageChannels.publishSubscribe().get();
    }

    /*
    * Создаем флоу, берем из очереди пропускаем через логгер, пишем зафиксированные машины в монгу
    * */
    @Bean
    public IntegrationFlow aimFlow(MongoTemplate mongoTemplate) {
        return IntegrationFlows
                .from("aimChannel")
                .handle("trafficJudgeService", "logVehicle")
                .handle(mongoOutboundAdapter(mongoTemplate))
                .get();
    }
    /*
    * Дефолтный поллер нужен очереди
    * */
    @Bean (name = PollerMetadata.DEFAULT_POLLER )
    public PollerMetadata poller () {
        return Pollers.fixedRate(3000).maxMessagesPerPoll(5).get() ;
    }

    /*
    * писака в монгу, коллекция венчиль
    * */
    @Bean
    public MessageHandler mongoOutboundAdapter(MongoTemplate mongoTemplate) {
        MongoDbStoringMessageHandler mongoHandler = new MongoDbStoringMessageHandler(mongoTemplate);
        mongoHandler.setCollectionNameExpression(new LiteralExpression("vehicle"));
        return mongoHandler;
    }

    /*
    * создаем флоу, поллит из монги зафиксированные машины, чекает их, фильтрует по превышению скорости,
    * трансформирует в штрафы, назначает штраф, пишет в монгу
    * */

    @Bean
    public IntegrationFlow judgmentFlow(MongoTemplate mongoTemplate) {
        return IntegrationFlows.from(mongoMessageSource(mongoTemplate), c -> c.poller(Pollers.fixedDelay(1, TimeUnit.SECONDS)))
                .handle("trafficJudgeService", "updateChecked")
                .filter(Vehicle.class, vehicle -> vehicle.getSpeed()>vehicle.getSpeedLimit())
                .<Vehicle, Penalty>transform(vehicle ->
                {
                    int excess = vehicle.getSpeed() - vehicle.getSpeedLimit();
                    if(excess < 20) {
                        return new Penalty(vehicle, BigDecimal.valueOf(100));
                    }else{
                        return new Penalty(vehicle, BigDecimal.valueOf(500));
                    }
                })
                .handle("trafficJudgeService", "logPenalty")
                .handle(mongoOutboundAdapterPenalty(mongoTemplate))
                .get();
    }

    /*
    * Читака из монги, коллекция венчиль, достаем по одной записи
    * */
    @Bean
    @Autowired
    public MessageSource<Object> mongoMessageSource(MongoTemplate mongoTemplate) {
        MongoDbMessageSource messageSource = new MongoDbMessageSource(mongoTemplate.getMongoDbFactory(), new LiteralExpression("{'checked' : false}"));
        messageSource.setExpectSingleResult(true);
        messageSource.setEntityClass(Vehicle.class);

        messageSource.setCollectionNameExpression(new LiteralExpression("vehicle"));

        return messageSource;
    }

    /*
    * Писака в монгу, коллеция пенальти
    * */

    @Bean
    public MessageHandler mongoOutboundAdapterPenalty(MongoTemplate mongoTemplate) {
        MongoDbStoringMessageHandler mongoHandler = new MongoDbStoringMessageHandler(mongoTemplate);
        mongoHandler.setCollectionNameExpression(new LiteralExpression("penalty"));
        return mongoHandler;
    }

    /*
    * Читака из монги, коллекция панельти, достаем по одной записи
    * */
    @Bean
    @Autowired
    public MessageSource<Object> mongoMessageSourcePenalty(MongoTemplate mongoTemplate) {
        MongoDbMessageSource messageSource = new MongoDbMessageSource(mongoTemplate.getMongoDbFactory(), new LiteralExpression("{'paid' : false}"));
        messageSource.setExpectSingleResult(true);
        messageSource.setEntityClass(Penalty.class);
        messageSource.setCollectionNameExpression(new LiteralExpression("penalty"));

        return messageSource;
    }

    /*
    * создаем флоу оплаты, читаем-трансформирует-записываем
    * */
    @Bean
    public IntegrationFlow paidFlow(MongoTemplate mongoTemplate) {
        return IntegrationFlows.from(mongoMessageSourcePenalty(mongoTemplate), c -> c.poller(Pollers.fixedDelay(1, TimeUnit.SECONDS).maxMessagesPerPoll(5)))
                .handle("trafficJudgeService", "logPaid")
                .channel("postboxChannel")
                .get();
    }

    @Bean
    public IntegrationFlow endFlow() {
        return IntegrationFlows.from("postboxChannel")
                .handle("trafficJudgeService", "logMail")
                .get();
    }
}
