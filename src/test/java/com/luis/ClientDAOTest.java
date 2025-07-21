package com.luis;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ClientDAOTest {

    private Connection connection;
    private ClientDAO dao;

    @BeforeEach
    void setup() throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite::memory:");

        Statement stmt = connection.createStatement();
        stmt.executeUpdate("""
            CREATE TABLE client (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT NOT NULL,
                email TEXT,
                phone TEXT,
                cpf TEXT NOT NULL,
                birth_date DATE NOT NULL,
                monthly_income NUMERIC,
                registration_date DATE NOT NULL
            );
        """);

        dao = new ClientDAO(connection);
    }

    @AfterEach
    void tearDown() throws SQLException {
        connection.close();
    }
    
    @Test
    public void testInsertClient() throws SQLException {
        Client client = new Client(null, "João", "joao@example.com", "(82) 99545-4045", "460.076.666-03", LocalDate.parse("1995-05-09"), null, LocalDate.now());

        dao.insert(client);
        Client saved = dao.findById(client.getId());

        assertNotNull(saved);
        assertEquals(client.getName(), saved.getName());
    }
}
