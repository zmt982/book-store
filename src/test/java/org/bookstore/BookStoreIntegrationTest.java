package org.bookstore;

import org.bookstore.database.entity.Book;
import org.bookstore.database.entity.User;
import org.bookstore.database.repository.BookRepository;
import org.bookstore.database.repository.UserRepository;
import org.bookstore.database.repository.impl.BookRepositoryImpl;
import org.bookstore.database.repository.impl.UserRepositoryImpl;
import org.bookstore.service.BookService;
import org.bookstore.service.UserService;
import org.bookstore.service.impl.BookServiceImpl;
import org.bookstore.service.impl.UserServiceImpl;
import org.bookstore.service.mapper.BookMapper;
import org.bookstore.service.mapper.UserMapper;
import org.bookstore.service.mapper.impl.BookMapperImpl;
import org.bookstore.service.mapper.impl.UserMapperImpl;
import org.bookstore.service.model.BookDto;
import org.bookstore.service.model.UserDto;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Collections;
import java.util.List;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
public class BookStoreIntegrationTest {
    private static final PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres");

    private static BookRepository bookRepository;
    private static UserRepository userRepository;
    private static BookMapper bookMapper;
    private static UserMapper userMapper;
    private static BookService bookService;
    private static UserService userService;

    @BeforeAll
    public static void beforeAll() {
        postgreSQLContainer.start();
        Properties properties = new Properties();
        properties.put("hibernate.connection.url", postgreSQLContainer.getJdbcUrl());
        properties.put("hibernate.connection.driver_class", postgreSQLContainer.getDriverClassName());
        properties.put("hibernate.connection.username", postgreSQLContainer.getUsername());
        properties.put("hibernate.connection.password", postgreSQLContainer.getPassword());
        properties.put("show_sql", true);
        properties.put("format_sql", true);
        properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        properties.put("hibernate.hbm2ddl.auto", "create-drop");
        Configuration configuration = new Configuration();
        configuration.addProperties(properties);
        configuration.addAnnotatedClass(Book.class);
        configuration.addAnnotatedClass(User.class);
//        configuration.configure();
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        bookRepository = new BookRepositoryImpl(sessionFactory);
        userRepository = new UserRepositoryImpl(sessionFactory);
        bookMapper = new BookMapperImpl();
        userMapper = new UserMapperImpl();
        bookService = new BookServiceImpl(bookRepository, bookMapper);
        userService = new UserServiceImpl(userRepository, userMapper);
    }

    @Test
    void bookStoreIntegrationTest() {
        UserDto userToAdd = new UserDto();
        userToAdd.setUsername("User to add");
        UserDto addedUser = userService.add(userToAdd);

        assertEquals(userToAdd, addedUser);

        List<UserDto> all = userService.getAll();
        assertEquals(Collections.singletonList(userToAdd), all);

        UserDto userGetById = userService.getById(1L);
        assertEquals(userToAdd, userGetById);

        UserDto userUpdateById = userService.updateById(1L, userToAdd);
        assertEquals(userToAdd, userUpdateById);

        userService.deleteById(1L);

        BookDto bookToAdd = new BookDto();
        bookToAdd.setAuthor("Author to add");
        bookToAdd.setName("Name to add");
        BookDto addedBook = bookService.add(bookToAdd);

        assertEquals(bookToAdd, addedBook);

        List<BookDto> allBooks = bookService.getAll();
        assertEquals(Collections.singletonList(bookToAdd), allBooks);

        BookDto bookGetById = bookService.getById(1L);
        assertEquals(bookToAdd, bookGetById);

        BookDto bookUpdateById = bookService.updateById(1L, bookToAdd);
        assertEquals(bookToAdd, bookUpdateById);

        bookService.deleteById(1L);
    }
}
