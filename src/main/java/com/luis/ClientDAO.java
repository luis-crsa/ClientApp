package com.luis;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ClientDAO {
    
    private Connection con;

    public ClientDAO() {
        con = Database.getInstance().getConnection();
    }

    public void insert(Client client) throws SQLException {
        String sql = "INSERT INTO client (name, email, phone, cpf, birth_date, monthly_income, registration_date) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, client.getName());
            stmt.setString(2, client.getEmail());
            stmt.setString(3, client.getPhone());
            stmt.setString(4, client.getCpf());
            stmt.setDate(5, java.sql.Date.valueOf(client.getBirthDate()));

            if (client.getMonthlyIncome() != null) {
                stmt.setDouble(6, client.getMonthlyIncome());
            } else {
                stmt.setNull(6, java.sql.Types.DOUBLE);
            }

            stmt.setDate(7, java.sql.Date.valueOf(client.getRegistrationDate()));

            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    client.setId(generatedKeys.getInt(1));
                }
            }
        }
    }

    public List<Client> findAll() throws SQLException {
        List<Client> clients = new ArrayList<>();
        String query = """
        SELECT id, name, email, phone, cpf, birth_date, monthly_income, registration_date 
        FROM client
        """;

        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Client client = new Client();
                client.setId(rs.getInt("id"));
                client.setName(rs.getString("name"));
                client.setEmail(rs.getString("email"));
                client.setPhone(rs.getString("phone"));
                client.setCpf(rs.getString("cpf"));
                client.setBirthDate(rs.getObject("birth_date", LocalDate.class));
                client.setMonthlyIncome(rs.getDouble("monthly_income"));
                client.setRegistrationDate(rs.getObject("registration_date", LocalDate.class));
                clients.add(client);
            }
        }

        return clients;
    }

    public Client findById(Integer id) throws SQLException {
        String query = """
        SELECT id, name, email, phone, cpf, birth_date, monthly_income, registration_date 
        FROM client WHERE id = ?
        """;

        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Client client = new Client();
                    client.setId(rs.getInt("id"));
                    client.setName(rs.getString("name"));
                    client.setEmail(rs.getString("email"));
                    client.setPhone(rs.getString("phone"));
                    client.setCpf(rs.getString("cpf"));
                    client.setBirthDate(rs.getObject("birth_date", LocalDate.class));
                    client.setMonthlyIncome(rs.getDouble("monthly_income"));
                    client.setRegistrationDate(rs.getObject("registration_date", LocalDate.class));
                    return client;
                }
            }
        }

        return null;
    }

    public void update(Client client) throws SQLException {
        String sql = """
        UPDATE client SET 
            name = ?, email = ?, phone = ?, cpf = ?, birth_date = ?, monthly_income = ?, registration_date = ?
        WHERE id = ?
        """;

        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, client.getName());
            stmt.setString(2, client.getEmail());
            stmt.setString(3, client.getPhone());
            stmt.setString(4, client.getCpf());
            stmt.setDate(5, Date.valueOf(client.getBirthDate()));

            if (client.getMonthlyIncome() != null) {
                stmt.setDouble(6, client.getMonthlyIncome());
            } else {
                stmt.setNull(6, java.sql.Types.DOUBLE);
            }

            stmt.setDate(7, Date.valueOf(client.getRegistrationDate()));
            stmt.setInt(8, client.getId());

            stmt.executeUpdate();
        }
    }

    public void delete(Integer id) throws SQLException {
        String sql = "DELETE FROM client WHERE id = ?";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }
}
