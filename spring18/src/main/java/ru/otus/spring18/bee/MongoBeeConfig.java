package ru.otus.spring18.bee;

import com.github.mongobee.Mongobee;
import com.mongodb.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.otus.spring18.config.ConfigProperties;

@Configuration
public class MongoBeeConfig {

    private MongoClient mongo;
    private MongoTemplate mongoTemplate;
    private ConfigProperties configProperties;

    public MongoBeeConfig(MongoClient mongo, MongoTemplate mongoTemplate, ConfigProperties configProperties) {
        this.mongo = mongo;
        this.mongoTemplate = mongoTemplate;
        this.configProperties = configProperties;
    }

    @Bean
    public Mongobee mongobee(final Environment environment) {
        Mongobee runner = new Mongobee(mongo);
        runner.setDbName(configProperties.getDatabase());
        runner.setChangeLogsScanPackage("ru.otus.spring18.bee.changelog");
        runner.setMongoTemplate(mongoTemplate);
        runner.setSpringEnvironment(environment);
        return runner;
    }
}
