package ru.otus.spring06.repo;

import org.springframework.stereotype.Repository;
import ru.otus.spring06.domain.Author;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("JpaQlInspection")
@Repository
public class SimpleAuthorRepo implements AuthorRepo {
    
    @PersistenceContext
    private EntityManager em;

    @Override
    public int count() {
        return em.createQuery("select count(a) from Author a", Long.class).getSingleResult().intValue();
    }

    @Override
    @Transactional
    public void insert(Author author) {
        em.persist(author);
    }

    @Override
    @Transactional
    public void deleteById(long id){
        Query query = em.createQuery("delete from Author a where a.id=:id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public List<Author> getAll() {
        TypedQuery<Author> typedQuery = em.createQuery("select a from Author a", Author.class);
        return typedQuery.getResultList();
    }

    @Override
    public Optional<Author> getByID(long id) {
        try {
            TypedQuery<Author> typedQuery = em.createQuery("select a from Author a where a.id=:id", Author.class);
            typedQuery.setParameter("id", id);
            return Optional.of(typedQuery.getSingleResult());
        }catch (NoResultException e){
            return Optional.empty();
        }
    }

    @Override
    public Optional<Author> getByName(String name) {
        try {
            TypedQuery<Author> typedQuery = em.createQuery("select a from Author a where a.name like :name", Author.class);
            typedQuery.setParameter("name", name);
            return Optional.of(typedQuery.getSingleResult());
        }catch(NoResultException e){
            return Optional.empty();
        }

    }

    @Override
    @Transactional
    public void insertOrId(Author author) {
        try {
            TypedQuery<Author> typedQuery = em.createQuery("select a from Author a where a.name like :name", Author.class);
            typedQuery.setParameter("name", author.getName());
            Author persistAuthor = typedQuery.getSingleResult();
            author.setId(persistAuthor.getId());
        }catch (NoResultException e){
            em.persist(author);
        }
    }
}
