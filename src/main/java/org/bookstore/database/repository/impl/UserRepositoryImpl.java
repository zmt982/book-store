package org.bookstore.database.repository.impl;

import org.bookstore.database.entity.User;
import org.bookstore.database.repository.UserRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private final SessionFactory sessionFactory;

    public UserRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Optional<List<User>> findAll() {
        try (Session session = sessionFactory.openSession()) {
            Query<User> query = session.createQuery("from User", User.class);
            List<User> resultList = query.getResultList();
            return Optional.ofNullable(resultList);
        }
    }

    @Override
    public Optional<User> findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return Optional.ofNullable(session.get(User.class, id));
        }
    }

    @Override
    public Optional<User> save(User user) {
        try (Session session = sessionFactory.openSession()) {
            Long id = (Long) session.save(user);
            user.setId(id);
            return Optional.ofNullable(user);
        }
    }

    @Override
    public Optional<User> updateById(Long id, User user) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            User userToUpdate = session.get(User.class, id);
            userToUpdate.setUsername(user.getUsername());
            session.update(userToUpdate);
            transaction.commit();
            return Optional.ofNullable(userToUpdate);
        }
    }

    @Override
    public void deleteById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            User userToDelete = session.get(User.class, id);
            session.delete(userToDelete);
            transaction.commit();
        }
    }

}