package org.bookstore.database.repository.impl;

import org.bookstore.database.entity.Book;
import org.bookstore.database.repository.BookRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class BookRepositoryImpl implements BookRepository {
    private final SessionFactory sessionFactory;

    public BookRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Optional<List<Book>> findAll() {
        try (Session session = sessionFactory.openSession()) {
            Query<Book> query = session.createQuery("from Book", Book.class);
            List<Book> resultList = query.getResultList();
            return Optional.ofNullable(resultList);
        }
    }

    @Override
    public Optional<Book> findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.get(Book.class, id));
        }
    }

    @Override
    public Optional<Book> save(Book book) {
        try (Session session = sessionFactory.openSession()) {
            Long id = (Long) session.save(book);
            book.setId(id);
            return Optional.ofNullable(book);
        }
    }

    @Override
    public Optional<Book> updateById(Long id, Book book) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Book bookToUpdate = session.get(Book.class, id);
            bookToUpdate.setAuthor(book.getAuthor());
            bookToUpdate.setName(book.getName());
            session.update(bookToUpdate);
            transaction.commit();
            return Optional.ofNullable(bookToUpdate);
        }
    }

    @Override
    public void deleteById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Book deleteBook = session.get(Book.class, id);
            session.delete(deleteBook);
            transaction.commit();
        }
    }

}