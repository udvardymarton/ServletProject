package org.example;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.jdbc.DatabaseConnect;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ServletDB extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DatabaseConnect databaseConnect = new DatabaseConnect();
        PrintWriter out = resp.getWriter();
        try {
            ResultSet resultSet = databaseConnect.getTables();
            while (resultSet.next()) {
                String name = resultSet.getString("Tables_in_reddit_clone");
                out.println(name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
