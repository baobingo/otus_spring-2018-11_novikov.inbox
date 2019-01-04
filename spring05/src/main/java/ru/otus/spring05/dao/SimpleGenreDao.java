package ru.otus.spring05.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.EmptySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
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
        final HashMap<String, Object> attr = new HashMap<>();
        attr.put("id", genre.getId());
        attr.put("name", genre.getName());
        statement.update("INSERT INTO genre VALUES(:id, :name)", attr);

    }

    @Override
    public void deleteById(int id) {
        final HashMap<String, Object> attr = new HashMap<>();
        attr.put("id", id);
        statement.update("DELETE FROM genre WHERE id=:id", attr);
    }

    @Override
    public List<Genre> getAll() {
        return statement.query("SELECT * FROM genre", EmptySqlParameterSource.INSTANCE, new GenreMapper());
    }

    @Override
    public Genre getByID(int id) {
        final HashMap<String, Object> attr = new HashMap<>();
        attr.put("id", id);
        return statement.queryForObject("SELECT * FROM genre WHERE id=:id", attr, new GenreMapper());
    }

    public class GenreMapper implements RowMapper<Genre> {
        @Override
        public Genre mapRow(ResultSet resultSet, int i) throws SQLException {
            return new Genre(resultSet.getInt("id"), resultSet.getString("name"));
        }
    }
}
