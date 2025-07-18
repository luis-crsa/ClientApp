package com.luis;

import java.sql.*;

public class Main {
    public static void main(String[] args) {
        try {

            Connection con = Database.getInstance().getConnection();
            Statement statement = con.createStatement();

            String sql = FileUtils.loadTextFile("src/main/resources/schema.sql");
            statement.executeUpdate(sql);

            // Insert de teste
            String insertSql = "INSERT INTO client (name, email, phone, cpf, birth_date, monthly_income, registration_date, active) " +
                    "VALUES ('Maria Silva', 'maria@example.com', '99999-9999', '12345678900', '2600-05-15', 3500.00, '2025-07-18', 1)";
            statement.executeUpdate(insertSql);
            
            ResultSet rs = statement.executeQuery("select * from client");
            while(rs.next())
            {
                System.out.println("name = " + rs.getString("name"));
                System.out.println("id = " + rs.getInt("id"));
            }
        }
        catch(Exception e)
        {
            e.printStackTrace(System.err);
        }
    }
}