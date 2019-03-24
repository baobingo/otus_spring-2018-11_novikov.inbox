package ru.otus.spring14.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.spring14.domain.Sequences;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataMongoTest
@Import(SimpleSequenceService.class)
class SimpleSequenceServiceTest {

    @Autowired
    private SequenceService sequenceService;

    @Autowired
    private MongoTemplate mongoTemplate;

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