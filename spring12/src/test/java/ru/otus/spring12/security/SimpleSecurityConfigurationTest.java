package ru.otus.spring12.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.*;

@SpringBootTest
@DirtiesContext(classMode= DirtiesContext.ClassMode.AFTER_CLASS)
class SimpleSecurityConfigurationTest {

    @Autowired
    private ApplicationContext applicationContext;

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
    @WithMockUser(roles = "ADMIN")
    public void adminRoleDELETE(){
        rest.delete().uri("/api/books/1").exchange().expectStatus().isOk();
    }
}