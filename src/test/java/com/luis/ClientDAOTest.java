package com.luis;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    public void testFindById() throws SQLException {
        Client client = new Client(null, "Maria", "maria@example.com", "99999-9999", "123.456.789-00", LocalDate.of(1990, 1, 1), 5000.0, LocalDate.now());
        dao.insert(client);

        Client saved = dao.findById(client.getId());

        assertNotNull(saved);
        assertEquals("Maria", saved.getName());
        assertEquals("maria@example.com", saved.getEmail());
    }

    @Test
    public void testFindAll() throws SQLException {
        Client c1 = new Client(null, "João", "joao@example.com", "123", "111.111.111-11", LocalDate.now(), 2000.0, LocalDate.now());
        Client c2 = new Client(null, "Ana", "ana@example.com", "456", "222.222.222-22", LocalDate.now(), 3000.0, LocalDate.now());

        dao.insert(c1);
        dao.insert(c2);

        List<Client> clients = dao.findAll();

        assertEquals(2, clients.size());
    }

    @Test
    public void testUpdateClient() throws SQLException {
        Client client = new Client(null, "Pedro", "pedro@example.com", "111", "333.333.333-33", LocalDate.now(), 4000.0, LocalDate.now());
        dao.insert(client);

        client.setName("Pedro Silva");
        client.setEmail("pedro.silva@example.com");
        dao.update(client);

        Client updated = dao.findById(client.getId());
        assertEquals("Pedro Silva", updated.getName());
        assertEquals("pedro.silva@example.com", updated.getEmail());
    }

    @Test
    public void testDeleteClient() throws SQLException {
        Client client = new Client(null, "Carlos", "carlos@example.com", "999", "444.444.444-44", LocalDate.now(), 2500.0, LocalDate.now());
        dao.insert(client);

        dao.delete(client.getId());
        Client deleted = dao.findById(client.getId());

        assertNull(deleted);
    }
}
