package com.luis;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ClientDAO {
    
    private Connection con;

    public ClientDAO() {
        con = Database.getInstance().getConnection();
    }

    public void insert(Client client) throws SQLException {
        String sql = "INSERT INTO client (name, email, phone, cpf, birth_date, monthly_income, registration_date, active) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

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
            stmt.setBoolean(8, client.isActive());

            stmt.executeUpdate();
        }
    }
}
