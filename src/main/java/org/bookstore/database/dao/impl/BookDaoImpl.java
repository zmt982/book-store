package org.bookstore.database.dao.impl;

import com.sun.jdi.PathSearchingVirtualMachine;
import org.bookstore.database.dao.BookDao;
import org.bookstore.database.entity.Book;
import org.bookstore.database.util.ConnectionPool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookDaoImpl implements BookDao {
    private final ConnectionPool connectionPool;

    public BookDaoImpl() {
        this.connectionPool = ConnectionPool.getConnectionPool();
    }

    @Override
    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM books");
                ResultSet resultSet = preparedStatement.executeQuery()
        ) {
            while (resultSet.next()) {
                Book book = new Book();
                book.setId(resultSet.getLong("id"));
                book.setName(resultSet.getString("name"));
                book.setAuthor(resultSet.getString("author"));
                book.setUserId(resultSet.getLong("user_id"));
                books.add(book);
            }
            connectionPool.returnConnection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return books;
    }

    @Override
    public Book findById(Long id) {
        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM books WHERE id = ?")
        ) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Book book = new Book();
                book.setId(resultSet.getLong("id"));
                book.setName((resultSet.getString("name")));
                book.setAuthor(resultSet.getString("author"));
                book.setUserId(resultSet.getLong("user_id"));
                return book;
            }
            resultSet.close();
            connectionPool.returnConnection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        throw new RuntimeException("No book foind with id = " + id);
    }

    @Override
    public void save(Book book) {
        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO books (author, name, user_id) VALUES (?, ?, ?)")
        ) {
            preparedStatement.setString(1, book.getAuthor());
            preparedStatement.setString(2, book.getName());
            preparedStatement.setLong(3, book.getUserId());
            preparedStatement.executeUpdate();
            connectionPool.returnConnection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Book updateById(Long id, Book book) {
        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("UPDATE books SET author = ?, name = ? WHERE id = ?")
        ) {
            preparedStatement.setString(1, book.getAuthor());
            preparedStatement.setString(2, book.getName());
            preparedStatement.setLong(3, id);
            preparedStatement.executeUpdate();
            connectionPool.returnConnection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        book.setId(id);
        return book;
    }

    @Override
    public void deleteById(Long id) {
        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM books WHERE id = ?")
        ) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
            connectionPool.returnConnection(connection);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}