package org.bookstore.database.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {
    private static ConnectionPool connectionPool;
    private static final Lock LOCK = new ReentrantLock();
    private static final String URL = "jdbc:postgresql://localhost:16111/postgres";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "postgres";
    private static final int MAX_CONNECTIONS = 10;
    private final BlockingQueue<Connection> blockingQueue = new ArrayBlockingQueue<>(MAX_CONNECTIONS);

    private ConnectionPool() {
        try {
            Class.forName("org.postgresql.Driver");
            for (int i = 0; i < MAX_CONNECTIONS; i++) {
                blockingQueue.put(createConnection());
            }
        } catch (SQLException | InterruptedException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    //это синглтон
    public static ConnectionPool getConnectionPool() {
        LOCK.lock();
        if (connectionPool == null) {
            connectionPool = new ConnectionPool();
        }
        LOCK.unlock();
        return connectionPool;
    }

    private Connection createConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    public synchronized Connection getConnection() {
        try {
            return blockingQueue.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized void returnConnection(Connection con) {
        try {
            blockingQueue.put(con);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}