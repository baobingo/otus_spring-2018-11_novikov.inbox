package ru.otus.spring05.dao;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.EmptySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.spring05.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

@Repository
public class SimpleGenreDao implements GenreDao {

    private NamedParameterJdbcOperations statement;

    public SimpleGenreDao(NamedParameterJdbcOperations statement) {
        this.statement = statement;
    }

    @Override
    public int count() {
        return statement.queryForObject("SELECT count(*) FROM genre", EmptySqlParameterSource.INSTANCE, Integer.class);
    }

    @Override
    public void insert(Genre genre) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource namedParameters = new MapSqlParameterSource();
        ((MapSqlParameterSource)namedParameters).addValue("name", genre.getName());
        statement.update("INSERT INTO genre(name) VALUES(:name)", namedParameters, keyHolder);
        genre.setId((long)keyHolder.getKey());

    }

    @Override
    public void deleteById(long id) {
        final HashMap<String, Object> attr = new HashMap<>();
        attr.put("id", id);
        statement.update("DELETE FROM genre WHERE id=:id", attr);
    }

    @Override
    public List<Genre> getAll() {
        return statement.query("SELECT * FROM genre", EmptySqlParameterSource.INSTANCE, new GenreMapper());
    }

    @Override
    public Genre getByID(long id) {
        final HashMap<String, Object> attr = new HashMap<>();
        attr.put("id", id);
        return statement.queryForObject("SELECT * FROM genre WHERE id=:id LIMIT 1", attr, new GenreMapper());
    }

    public class GenreMapper implements RowMapper<Genre> {
        @Override
        public Genre mapRow(ResultSet resultSet, int i) throws SQLException {
            return new Genre(resultSet.getLong("id"), resultSet.getString("name"));
        }
    }

    @Override
    public Genre getByName(String name) {
        final HashMap<String, Object> attr = new HashMap<>();
        attr.put("name", name);
        try {
            return statement.queryForObject("SELECT * FROM genre WHERE name LIKE :name LIMIT 1", attr, new GenreMapper());
        }catch (DataAccessException e){
            return null;
        }
    }
}
