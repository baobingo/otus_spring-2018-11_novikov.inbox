package ru.otus.spring06.repo;

import org.springframework.stereotype.Repository;
import ru.otus.spring06.domain.Genre;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.List;

@SuppressWarnings("JpaQlInspection")
@Repository
public class SimpleGenreRepo implements GenreRepo {

    @PersistenceContext
    private EntityManager em;

    @Override
    public int count() {
        return em.createQuery("select count(g) from Genre g", Long.class).getSingleResult().intValue();
    }

    @Override
    @Transactional
    public void insert(Genre genre) {
        em.persist(genre);
    }

    @Override
    @Transactional
    public void deleteById(long id){
        Query query = em.createQuery("delete from Genre g where g.id=:id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public List<Genre> getAll() {
        TypedQuery<Genre> typedQuery = em.createQuery("select g from Genre g", Genre.class);
        return typedQuery.getResultList();
    }

    @Override
    public Genre getByID(long id) {
        TypedQuery<Genre> typedQuery = em.createQuery("select g from Genre g where g.id=:id", Genre.class);
        typedQuery.setParameter("id", id);
        return typedQuery.getSingleResult();
    }

    @Override
    public Genre getByName(String name) {
        try {
            TypedQuery<Genre> typedQuery = em.createQuery("select g from Genre g where g.name like :name", Genre.class);
            typedQuery.setParameter("name", name);
            return typedQuery.getSingleResult();
        }catch (NoResultException e){
            return null;
        }
    }

    @Override
    @Transactional
    public void insertOrId(Genre genre) {
        try {
            TypedQuery<Genre> typedQuery = em.createQuery("select g from Genre g where g.name like :name", Genre.class);
            typedQuery.setParameter("name", genre.getName());
            Genre persistGenre = typedQuery.getSingleResult();
            genre.setId(persistGenre.getId());
        }catch (NoResultException e){
            em.persist(genre);
        }
    }
}
