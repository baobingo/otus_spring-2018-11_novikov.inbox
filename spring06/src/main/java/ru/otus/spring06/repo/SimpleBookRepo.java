package ru.otus.spring06.repo;

import org.springframework.stereotype.Repository;
import ru.otus.spring06.domain.Author;
import ru.otus.spring06.domain.Book;
import ru.otus.spring06.domain.Genre;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@SuppressWarnings("JpaQlInspection")
@Repository
public class SimpleBookRepo implements BookRepo {

    @PersistenceContext
    private EntityManager em;

    @Override
    public int count() {
        return em.createQuery("select count(b) from Book b", Long.class).getSingleResult().intValue();
    }

    @Override
    @Transactional
    public void insert(Book book) {
        em.persist(book);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        Query query = em.createQuery("delete from Book b where b.id=:id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public List<Book> getAll() {
        TypedQuery<Book> typedQuery = em.createQuery("select b from Book b", Book.class);
        return typedQuery.getResultList();
    }

    @Override
    public Book getByID(long id) {
        TypedQuery<Book> typedQuery = em.createQuery("select b from Book b where b.id=:id", Book.class);
        typedQuery.setParameter("id", id);
        return typedQuery.getSingleResult();
    }

    @Override
    public List<Book> getByAuthor(Author author) {
        TypedQuery<Book> typedQuery = em.createQuery("select b from Book b where b.author=:author", Book.class);
        typedQuery.setParameter("author", author);
        return typedQuery.getResultList();
    }

    @Override
    public List<Book> getByGenre(Genre genre) {
        TypedQuery<Book> typedQuery = em.createQuery("select b from Book b where b.genre=:genre", Book.class);
        typedQuery.setParameter("genre", genre);
        return typedQuery.getResultList();
    }
}
