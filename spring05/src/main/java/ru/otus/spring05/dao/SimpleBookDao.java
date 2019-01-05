package ru.otus.spring05.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.EmptySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.spring05.domain.Author;
import ru.otus.spring05.domain.Book;
import ru.otus.spring05.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

@Repository
public class SimpleBookDao implements BookDao{

    private NamedParameterJdbcOperations statement;

    public SimpleBookDao(NamedParameterJdbcOperations statement) {
        this.statement = statement;
    }

    @Override
    public int count() {
        return statement.queryForObject("SELECT count(*) FROM book", EmptySqlParameterSource.INSTANCE, Integer.class);
    }

    @Override
    public void insert(Book book) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource namedParameters = new MapSqlParameterSource();
        ((MapSqlParameterSource)namedParameters).addValue("name", book.getName());
        ((MapSqlParameterSource)namedParameters).addValue("authorid", book.getAuthor().getId());
        ((MapSqlParameterSource)namedParameters).addValue("genreid", book.getGenre().getId());

        statement.update("INSERT INTO book(name, authorid, genreid) VALUES(:name, :authorid, :genreid)", namedParameters, keyHolder);
        book.setId((long)keyHolder.getKey());
    }

    @Override
    public void deleteById(long id) {
        final HashMap<String, Object> attr = new HashMap<>();
        attr.put("id", id);
        statement.update("DELETE FROM book WHERE id=:id", attr);
    }

    @Override
    public List<Book> getAll() {
        return statement.query("SELECT b.id, b.name, a.id aid, a.name aname, g.id gid, g.name gname FROM book b INNER JOIN author a ON a.id = b.authorid INNER JOIN genre g ON g.id = b.genreid", EmptySqlParameterSource.INSTANCE, new BookMapper());
    }

    @Override
    public Book getByID(long id) {
        final HashMap<String, Object> attr = new HashMap<>();
        attr.put("id", id);
        return statement.queryForObject("SELECT b.id, b.name, a.id aid, a.name aname, g.id gid, g.name gname FROM book b INNER JOIN author a ON a.id = b.authorid INNER JOIN genre g ON g.id = b.genreid WHERE b.id=:id LIMIT 1", attr, new BookMapper());
    }

    @Override
    public List<Book> getByAuthor(Author author) {
        final HashMap<String, Object> attr = new HashMap<>();
        attr.put("id", author.getId());
        return statement.query("SELECT b.id, b.name, a.id aid, a.name aname, g.id gid, g.name gname FROM author a INNER JOIN book b ON a.id = b.authorid INNER JOIN genre g ON g.id = b.genreid WHERE a.id = :id ", attr, new BookMapper());
    }

    @Override
    public List<Book> getByGenre(Genre genre) {
        final HashMap<String, Object> attr = new HashMap<>();
        attr.put("id", genre.getId());
        return statement.query("SELECT b.id, b.name, a.id aid, a.name aname, g.id gid, g.name gname FROM genre g INNER JOIN book b ON g.id = b.genreid INNER JOIN author a ON a.id = b.authorid WHERE g.id = :id ", attr, new BookMapper());
    }

    public class BookMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            return new Book(resultSet.getLong("id"), resultSet.getString("name"), new Author(resultSet.getLong("aid"), resultSet.getString("aname")),new Genre(resultSet.getLong("gid"), resultSet.getString("gname")));
        }
    }
}
