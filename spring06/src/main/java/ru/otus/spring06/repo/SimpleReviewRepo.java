package ru.otus.spring06.repo;

import org.springframework.stereotype.Repository;
import ru.otus.spring06.domain.Book;
import ru.otus.spring06.domain.Review;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@SuppressWarnings("JpaQlInspection")
@Repository
public class SimpleReviewRepo implements ReviewRepo {

    @PersistenceContext
    private EntityManager em;

    @Override
    public int count() {
        return em.createQuery("select count(r) from Review r", Long.class).getSingleResult().intValue();
    }

    @Override
    @Transactional
    public void insert(Review review) {
        em.persist(review);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        Query query = em.createQuery("delete from Review r where r.id=:id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public List<Review> getByBook(Book book) {
        TypedQuery<Review> typedQuery = em.createQuery("select r from Review r where book=:book", Review.class);
        typedQuery.setParameter("book", book);
        return typedQuery.getResultList();
    }
}
