package ru.otus.spring14.security;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import org.apache.commons.io.IOUtils;
import org.bson.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.io.IOException;

import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.*;

@SpringBootTest
@DirtiesContext(classMode= DirtiesContext.ClassMode.AFTER_CLASS)
class SimpleSecurityConfigurationTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    MongoClient mongoClient;

    private WebTestClient rest;

    @BeforeEach
    void setUp() {
        this.rest = WebTestClient
                .bindToApplicationContext(this.applicationContext)
                .apply(springSecurity())
                .configureClient()
                .build();
    }

    @Test
    public void unauthorizedUser(){
        rest.get().uri("/api/books").exchange().expectStatus().isUnauthorized();
    }

    @Test
    @WithMockUser(roles = "USER")
    public void userRoleGET(){
        rest.get().uri("/api/books").exchange().expectStatus().isOk();
    }

    @Test
    @WithMockUser(roles = "USER")
    public void userRoleDELETE(){
        rest.delete().uri("/api/books/1").exchange().expectStatus().isForbidden();
    }

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void adminRoleDELETE(){
        /*
            Грузанем сначала ACL коллекцию.
         */
        ClassLoader classLoader = getClass().getClassLoader();
        try {
            MongoCollection mongoCollection = mongoClient.getDatabase("777_books_test").getCollection("ACL");
            Document document = Document.parse(IOUtils.toString(classLoader.getResourceAsStream("ACL.json")));
            mongoCollection.insertOne(document);

            rest.delete().uri("/api/books/1").exchange().expectStatus().isOk();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}