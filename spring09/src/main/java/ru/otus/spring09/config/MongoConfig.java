package ru.otus.spring09.config;

import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "ru.otus.spring09.repository")
public class MongoConfig extends AbstractMongoConfiguration {

    private String dbName;
    private String dbHost;

    public MongoConfig(@Value("${spring.data.mongodb.database}") String dbName, @Value("${spring.data.mongodb.host}") String dbHost) {
        this.dbName = dbName;
        this.dbHost = dbHost;
    }

    @Override
    @Bean
    public MongoClient mongoClient() {
        return new MongoClient(dbHost);
    }

    @Override
    protected String getDatabaseName() {
        return dbName;
    }
}