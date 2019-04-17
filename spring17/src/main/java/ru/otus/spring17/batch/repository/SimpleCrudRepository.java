package ru.otus.spring17.batch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.otus.spring17.batch.domain.BookSQL;

@Repository
public interface SimpleCrudRepository extends JpaRepository<BookSQL, Long> {
}
