package com.luis;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ClientDAOTest {
    
    @Test
    public void testInsertClient() throws SQLException {
        Client client = new Client(null, "João", "joao@example.com", "(82) 99545-4045", "460.076.666-03", LocalDate.parse("1995-05-09"), null, LocalDate.now());
        ClientDAO dao = new ClientDAO();

        dao.insert(client);
        Client saved = dao.findById(client.getId());

        assertNotNull(saved);
        assertEquals("João", saved.getName());
    }
}
