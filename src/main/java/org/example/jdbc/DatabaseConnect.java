package org.example.jdbc;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.Properties;

public class DatabaseConnect {

    private Connection con = null;

    public DatabaseConnect() {
        getConnection();
    }
    public void getConnection() {
        Properties properties = new Properties();
        Path currentRelativePath = Paths.get("");
        String s = currentRelativePath.toAbsolutePath().toString();
        System.out.println("Current absolute path is: " + s);
        try(InputStream input = getClass().getClassLoader().getResourceAsStream("db.properties")) {
            properties.load(input);
        } catch(IOException e) {
            e.printStackTrace();
        }

        String url = properties.getProperty("db.url");
        String username = properties.getProperty("db.username");
        String password = properties.getProperty("db.password");

        loadConnection(url,username,password);

    }

    private void loadConnection(String url, String username, String password) {
        try {
            con = DriverManager.getConnection(url, username, password);
            System.out.println("Connection was loaded!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet getProfileTable() throws SQLException {
        Statement statement = con.createStatement();
        String query = "SELECT * FROM profile";
        return statement.executeQuery(query);
    }

    public ResultSet getFilteredProfileTable(String filter) throws SQLException {
        String sql = "SELECT * FROM profile WHERE (?)";
        PreparedStatement preparedStatement = con.prepareStatement(sql);
        preparedStatement.setString(1,filter);
        return preparedStatement.executeQuery();
    }

    public void addElementToTable(String value) throws SQLException {
        String sql = "INSERT INTO PROFILE (name) VALUES (?)";
        PreparedStatement prepareStatement = con.prepareStatement(sql);
        prepareStatement.setString(1,value);
        prepareStatement.execute();
    }

    public ResultSet getTables() throws SQLException {
        Statement statement = con.createStatement();
        String query = "show tables";
        return statement.executeQuery(query);
    }

    public void closeConnection() {
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
