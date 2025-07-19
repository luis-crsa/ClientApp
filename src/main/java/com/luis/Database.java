package com.luis;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    
    private static Database INSTANCE;
    
    private Connection connection;

    public Database() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:database.db");
        } catch (SQLException e) {
            System.err.println("Houve um problema ao criar o arquivo do banco!");
        }
    }
    
    public static Database getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Database();
        }
        
        return INSTANCE;
    }

    public Connection getConnection() {
        return connection;
    }
    
    public void closeConnection() {
        try {
            this.connection.close();
        } catch (SQLException e) {
            System.err.println("Houve um problema ao fechar a conexão do banco!");
        }
    }
}
