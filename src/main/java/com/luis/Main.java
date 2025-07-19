package com.luis;

import java.sql.*;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        try {

            Connection con = Database.getInstance().getConnection();
            Statement statement = con.createStatement();

            String sql = FileUtils.loadTextFile("src/main/resources/schema.sql");
            statement.executeUpdate(sql);

            // Insert de teste
            ClientDAO dao = new ClientDAO();
            Client client = new Client(
                    null,
                    "Maria Silva",
                    "maria@example.com",
                    "99999-9999",
                    "12345678900",
                    LocalDate.parse("1995-05-15"),
                    3500.0,
                    LocalDate.now()
            );
            dao.insert(client);
            
            // Delete de teste
            dao.delete(1);
            
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