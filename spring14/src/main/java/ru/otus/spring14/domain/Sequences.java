package ru.otus.spring14.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "customSequences")
public class Sequences {
    @Id
    private String id;
    private int seq;

    public int getSeq() {
        return seq;
    }
}