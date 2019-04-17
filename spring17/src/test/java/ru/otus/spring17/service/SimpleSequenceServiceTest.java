package ru.otus.spring17.service;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.FixedHostPortGenericContainer;
import org.testcontainers.containers.GenericContainer;
import ru.otus.spring17.domain.Sequences;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataMongoTest
@Import(SimpleSequenceService.class)
class SimpleSequenceServiceTest {

    @Autowired
    private SequenceService sequenceService;
    @Autowired
    private MongoTemplate mongoTemplate;


    public static GenericContainer mongo = new FixedHostPortGenericContainer("mongo:3.4.20-xenial").withFixedExposedPort(52300, 27017);

    @BeforeAll
    static void run(){
        mongo.start();
    }

    @AfterAll
    static void stop(){
        mongo.stop();
    }

    @BeforeEach
    void dropCollection(){
        mongoTemplate.dropCollection(Sequences.class);
    }

    @Test
    void getNextSequence() {
        assertEquals(1, sequenceService.getNextSequence());
        sequenceService.getNextSequence();
        sequenceService.getNextSequence();
        assertEquals(4, sequenceService.getNextSequence());
    }

    @Test
    void getCustomSequence() {
        assertEquals("customSequences", sequenceService.getCustomSequence());
    }
}