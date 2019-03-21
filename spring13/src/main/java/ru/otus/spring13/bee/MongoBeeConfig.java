package ru.otus.spring13.bee;

import com.github.mongobee.Mongobee;
import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class MongoBeeConfig {

    private MongoClient mongo;
    private MongoTemplate mongoTemplate;
    private String dbName;

    public MongoBeeConfig(MongoClient mongo, MongoTemplate mongoTemplate, @Value("${spring.data.mongodb.database}") String dbName) {
        this.mongo = mongo;
        this.mongoTemplate = mongoTemplate;
        this.dbName = dbName;
    }

    @Bean
    public Mongobee mongobee(final Environment environment) {
        Mongobee runner = new Mongobee(mongo);
        runner.setDbName(dbName);
        runner.setChangeLogsScanPackage("ru.otus.spring13.bee.changelog");
        runner.setMongoTemplate(mongoTemplate);
        runner.setSpringEnvironment(environment);
        return runner;
    }
}
