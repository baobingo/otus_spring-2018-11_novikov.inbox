package ru.otus.spring15.repository;

import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring15.domain.Penalty;

import java.util.Optional;

public interface PenaltyRepository extends MongoRepository<Penalty, String> {
    @Override
    <S extends Penalty> Optional<S> findOne(Example<S> example);
}
