package ru.otus.spring08.service;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import ru.otus.spring08.domain.Sequences;


@Service
public class SimpleSequenceService implements SequenceService{

    @Value("${spring.data.mongodb.customseq}")
    private String customSequence;

    @Autowired private MongoTemplate mongo;

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
