package ru.otus.spring08.bee;

import com.github.mongobee.Mongobee;
import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class MongoBeeConfig {

    @Autowired
    private MongoClient mongo;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Value("${spring.data.mongodb.database}")
    private String dbName;

    @Bean
    public Mongobee mongobee() {
        Mongobee runner = new Mongobee(mongo);
        runner.setDbName(dbName);
        runner.setChangeLogsScanPackage("ru.otus.spring08.bee.changelog");
        runner.setMongoTemplate(mongoTemplate);
        return runner;
    }
}
