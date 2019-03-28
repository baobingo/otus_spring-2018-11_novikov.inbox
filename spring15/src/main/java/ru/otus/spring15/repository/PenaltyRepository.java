package ru.otus.spring15.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring15.domain.Penalty;


public interface PenaltyRepository extends MongoRepository<Penalty, String> {
}
