package com.test;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/DeleteStudentServlet")
public class DeleteStudentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private static final String URL = "jdbc:mysql://localhost:3306/studentdb";
    private static final String USER = "root";
    private static final String PASS = "Pavilion@123";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter pw = response.getWriter();

        // Read Student ID from form
        String sidStr = request.getParameter("sid");
        int sid;

        // Validate ID
        try {
            sid = Integer.parseInt(sidStr);
        } catch (NumberFormatException e) {
            pw.println("<h3 style='color:red'>Student ID must be a number.</h3>");
            pw.println("<a href='delete.html'>Go Back</a>");
            return;
        }

        String sql = "DELETE FROM student WHERE sid = ?";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection con = DriverManager.getConnection(URL, USER, PASS);
                 PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setInt(1, sid);

                int rows = ps.executeUpdate();

                if (rows > 0) {
                    pw.println("<h2 style='color:green'>Student deleted successfully.</h2>");
                } else {
                    pw.println("<h2 style='color:red'>No student found with ID: " + sid + "</h2>");
                }

                pw.println("<center><a href='index.html'>Back to Home</a></center>");
            }

        } catch (Exception e) {
            pw.println("<h3 style='color:red'>Error: " + e.getMessage() + "</h3>");
        }
    }
}
