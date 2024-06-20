package org.jogo;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager
{

    private static final String DATABASE_URL = "jdbc:sqlite:jogo.db";
    private static final String SCHEMA_FILE = "/schema.sql";
    private Connection conn;

    public DatabaseManager()
    {
        connect();
        createTables();
    }

    private void connect()
    {
        try
        {
            conn = DriverManager.getConnection(DATABASE_URL);
            System.out.println("Conectado ao banco de dados SQLite.");
        } catch (SQLException e)
        {
            System.err.println("Erro ao conectar ao banco de dados SQLite: " + e.getMessage());
        }
    }

    private void createTables()
    {
        try {
            executeSqlFile(SCHEMA_FILE);
            System.out.println("Tabelas criadas no banco de dados.");
        } catch (IOException | SQLException e) {
            System.err.println("Erro ao criar tabelas: " + e.getMessage());
        }
    }

    private void executeSqlFile(String filePath) throws IOException, SQLException
    {
        StringBuilder sql = new StringBuilder();
        try (InputStream is = getClass().getResourceAsStream(filePath);
             BufferedReader br = new BufferedReader(new InputStreamReader(is)))
        {
            String line;
            while ((line = br.readLine()) != null) {
                sql.append(line);
                if (line.trim().endsWith(";")) {
                    try (Statement stmt = conn.createStatement()) {
                        stmt.execute(sql.toString());
                        sql.setLength(0);
                    }
                }
            }
        }
    }

    public Connection getConnection()
    {
        return conn;
    }

    public void closeConnection()
    {
        try {
            if (conn != null) {
                conn.close();
                System.out.println("Conexão com banco de dados fechada.");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao fechar conexão com banco de dados: " + e.getMessage());
        }
    }
}