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

public class ProfileServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DatabaseConnect databaseConnect = new DatabaseConnect();
        PrintWriter out = resp.getWriter();
        try {
            ResultSet resultSet = databaseConnect.getProfileTable();
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<style>");
            out.println("form input { margin-right: 10px}");
            out.println("table, th, td { border: 1px solid;}");
            out.println("td, th { padding: 20px; } ");
            out.println("</style>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Profiles</h1>");
            out.println("<br>");
            out.println("<h2>Filter</h2>");
            out.println("<br>");
            out.println("<form>");
            out.println("<input type=text name=filter>");
            out.println("<input type=submit>");
            out.println("</form>");
            out.println("<h2>Add new</h2>");
            out.println("<br>");
            out.println("<form>");
            out.println("<input type=text name=add>");
            out.println("<input type=submit>");
            out.println("</form>");
            out.println("<br>");
            out.println("<table>");
            out.println("<tr>");
            out.println("<th>ID</th>");
            out.println("<th>Profiles</th>");
            out.println("</tr>");
            String filterTextField = req.getParameter("filter");
            String addTextField = req.getParameter("add");
            if (addTextField != null) {
                databaseConnect.addElementToTable(addTextField);
                resultSet = databaseConnect.getProfileTable();
            }
            if (filterTextField != null) {
                while (resultSet.next()) {
                    String id = resultSet.getString("profile_id");
                    String name = resultSet.getString("name");
                    if (name.contains(filterTextField)) {
                        out.println("<tr>");
                        out.println("<td>" + id + "</td>");
                        out.println("<td>" + name + "</td>");
                        out.println("</tr>");
                    }
                }
            }
            else {
                while (resultSet.next()) {
                    String id = resultSet.getString("profile_id");
                    String name = resultSet.getString("name");
                    out.println("<tr>");
                    out.println("<td>" + id + "</td>");
                    out.println("<td>" + name + "</td>");
                    out.println("</tr>");
                }
            }
            out.println("</table>");
            out.println("<br>");
            out.println("</body>");
            out.println("</html>");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
