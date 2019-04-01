package ru.otus.spring16.config;

import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "ru.otus.spring16.repository")
public class MongoConfig extends AbstractMongoConfiguration {

    private String dbName;
    private String dbHost;
    private String dbPort;

    public MongoConfig(@Value("${spring.data.mongodb.database}") String dbName, @Value("${spring.data.mongodb.host}") String dbHost, @Value("${spring.data.mongodb.port}") String dbPort) {
        this.dbName = dbName;
        this.dbHost = dbHost;
        this.dbPort = dbPort;
    }

    @Override
    @Bean
    public MongoClient mongoClient() {
        return new MongoClient(dbHost, Integer.parseInt(dbPort));
    }

    @Override
    protected String getDatabaseName() {
        return dbName;
    }
}