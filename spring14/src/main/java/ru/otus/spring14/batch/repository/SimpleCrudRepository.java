package ru.otus.spring14.batch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.otus.spring14.batch.domain.BookSQL;

@Repository
public interface SimpleCrudRepository extends JpaRepository<BookSQL, Long> {
}
