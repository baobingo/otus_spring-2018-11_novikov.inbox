package ru.otus.spring05.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.EmptySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
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
    private AuthorDao authorDao;
    private GenreDao genreDao;

    public SimpleBookDao(NamedParameterJdbcOperations statement, AuthorDao authorDao, GenreDao genreDao) {
        this.statement = statement;
        this.authorDao = authorDao;
        this.genreDao = genreDao;
    }

    @Override
    public int count() {
        return statement.queryForObject("SELECT count(*) FROM book", EmptySqlParameterSource.INSTANCE, Integer.class);
    }

    @Override
    public void insert(Book book) {
        final HashMap<String, Object> attr = new HashMap<>();
        attr.put("id", book.getId());
        attr.put("name", book.getName());
        attr.put("authorid", book.getAuthor().getId());
        attr.put("genreid", book.getGenre().getId());
        statement.update("INSERT INTO book(id, name, authorid, genreid) VALUES(:id, :name, :authorid, :genreid)", attr);
    }

    @Override
    public void deleteById(int id) {
        final HashMap<String, Object> attr = new HashMap<>();
        attr.put("id", id);
        statement.update("DELETE FROM book WHERE id=:id", attr);
    }

    @Override
    public List<Book> getAll() {
        return statement.query("SELECT * FROM book", EmptySqlParameterSource.INSTANCE, new BookMapper());
    }

    @Override
    public Book getByID(int id) {
        final HashMap<String, Object> attr = new HashMap<>();
        attr.put("id", id);
        return statement.queryForObject("SELECT * FROM book WHERE id=:id", attr, new BookMapper());
    }

    @Override
    public List<Book> getByAuthor(Author author) {
        final HashMap<String, Object> attr = new HashMap<>();
        attr.put("id", author.getId());
        return statement.query("SELECT b.* FROM author a JOIN book b ON a.id = b.authorid WHERE a.id = :id", attr, new BookMapper());
    }

    @Override
    public List<Book> getByGenre(Genre genre) {
        final HashMap<String, Object> attr = new HashMap<>();
        attr.put("id", genre.getId());
        return statement.query("SELECT b.* FROM genre g JOIN book b ON g.id = b.genreid WHERE g.id = :id", attr, new BookMapper());
    }

    public class BookMapper implements RowMapper<Book> {
        @Override
        public Book mapRow(ResultSet resultSet, int i) throws SQLException {
            return new Book(resultSet.getInt("id"), resultSet.getString("name"), authorDao.getByID(Integer.parseInt(resultSet.getString("authorid"))) , genreDao.getByID(Integer.parseInt(resultSet.getString("genreid"))));
        }
    }
}
