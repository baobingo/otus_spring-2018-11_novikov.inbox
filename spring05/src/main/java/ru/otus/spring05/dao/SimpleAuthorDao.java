package ru.otus.spring05.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.EmptySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.spring05.domain.Author;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

@Repository
public class SimpleAuthorDao implements AuthorDao {

    private NamedParameterJdbcOperations statement;

    public SimpleAuthorDao(NamedParameterJdbcOperations statement) {
        this.statement = statement;
    }

    @Override
    public int count() {
        return statement.queryForObject("SELECT count(*) FROM author", EmptySqlParameterSource.INSTANCE, Integer.class);
    }

    @Override
    public void insert(Author author) {
        final HashMap<String, Object> attr = new HashMap<>();
        attr.put("id", author.getId());
        attr.put("name", author.getName());
        statement.update("INSERT INTO author VALUES(:id, :name)", attr);

    }

    @Override
    public void deleteById(int id) {
        final HashMap<String, Object> attr = new HashMap<>();
        attr.put("id", id);
        statement.update("DELETE FROM author WHERE id=:id", attr);
    }

    @Override
    public List<Author> getAll() {
        return statement.query("SELECT * FROM author", EmptySqlParameterSource.INSTANCE, new AuthorMapper());
    }

    @Override
    public Author getByID(int id) {
        final HashMap<String, Object> attr = new HashMap<>();
        attr.put("id", id);
        return statement.queryForObject("SELECT * FROM author WHERE id=:id", attr, new AuthorMapper());
    }

    public class AuthorMapper implements RowMapper<Author> {
        @Override
        public Author mapRow(ResultSet resultSet, int i) throws SQLException {
            return new Author(resultSet.getInt("id"), resultSet.getString("name"));
        }
    }
}
