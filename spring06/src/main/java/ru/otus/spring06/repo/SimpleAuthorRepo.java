package ru.otus.spring06.repo;

import org.springframework.stereotype.Repository;
import ru.otus.spring06.domain.Author;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.List;

@SuppressWarnings("JpaQlInspection")
@Repository
public class SimpleAuthorRepo implements AuthorRepo {
    
    @PersistenceContext
    private EntityManager em;

    @Override
    public int count() {
        return em.createQuery("select count(g) from Author g", Long.class).getSingleResult().intValue();
    }

    @Override
    @Transactional
    public void insert(Author author) {
        em.persist(author);
    }

    @Override
    @Transactional
    public void deleteById(long id){
        Query query = em.createQuery("delete from Author g where g.id=:id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public List<Author> getAll() {
        TypedQuery<Author> typedQuery = em.createQuery("select g from Author g", Author.class);
        return typedQuery.getResultList();
    }

    @Override
    public Author getByID(long id) {
        TypedQuery<Author> typedQuery = em.createQuery("select g from Author g where g.id=:id", Author.class);
        typedQuery.setParameter("id", id);
        return typedQuery.getSingleResult();
    }

    @Override
    public Author getByName(String name) {
        try{
        TypedQuery<Author> typedQuery = em.createQuery("select g from Author g where g.name like :name", Author.class);
        typedQuery.setParameter("name", name);
        return typedQuery.getSingleResult();
        }catch (NoResultException e){
            return null;
        }
    }

    @Override
    @Transactional
    public void insertOrId(Author author) {
        try {
            TypedQuery<Author> typedQuery = em.createQuery("select g from Author g where g.name like :name", Author.class);
            typedQuery.setParameter("name", author.getName());
            Author persistAuthor = typedQuery.getSingleResult();
            author.setId(persistAuthor.getId());
        }catch (NoResultException e){
            em.persist(author);
        }
    }
}
