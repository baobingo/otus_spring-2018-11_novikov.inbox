package ru.otus.spring10.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import ru.otus.spring10.domain.Sequences;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;


@Service
public class SimpleSequenceService implements SequenceService{

    private String customSequence;

    private MongoTemplate mongo;

    public SimpleSequenceService(@Value("${spring.data.mongodb.customseq}") String customSequence, MongoTemplate mongo) {
        this.customSequence = customSequence;
        this.mongo = mongo;
    }

    public int getNextSequence()
    {
        Sequences counter = mongo.findAndModify(
                query(where("_id").is(customSequence)),
                new Update().inc("seq",1),
                options().returnNew(true).upsert(true),
                Sequences.class);
        return counter.getSeq();
    }

    public String getCustomSequence() {
        return customSequence;
    }
}
