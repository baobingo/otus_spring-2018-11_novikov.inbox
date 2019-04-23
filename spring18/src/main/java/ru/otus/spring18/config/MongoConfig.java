package ru.otus.spring18.config;

import com.mongodb.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "ru.otus.spring18.repository")
public class MongoConfig extends AbstractMongoConfiguration {

    private ConfigProperties configProperties;

    public MongoConfig(ConfigProperties configProperties) {
        this.configProperties = configProperties;
    }

    @Override
    @Bean
    public MongoClient mongoClient() {
        return new MongoClient(configProperties.getHost(), configProperties.getPort());
    }

    @Override
    protected String getDatabaseName() {
        return configProperties.getDatabase();
    }
}