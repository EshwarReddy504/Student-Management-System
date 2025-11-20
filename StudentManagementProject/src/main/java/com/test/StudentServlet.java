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

@WebServlet("/StudentServlet")
public class StudentServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // DB config — change if needed
    private static final String URL = "jdbc:mysql://localhost:3306/studentdb";
    private static final String USER = "root";
    private static final String PASS = "Pavilion@123";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter pw = response.getWriter()) {

            // Read params (names must match your form)
            String sidStr   = request.getParameter("sid");
            String sname    = request.getParameter("sname");
            String mname    = request.getParameter("mname");
            String fname    = request.getParameter("fname");
            String ageStr   = request.getParameter("age");
            String gmail    = request.getParameter("gmail");
            String phone    = request.getParameter("phone");
            String location = request.getParameter("location");

            // Basic validation + parsing
            int sid;
            int age;
            try {
                sid = Integer.parseInt(sidStr);   // ID must be a number
                age = Integer.parseInt(ageStr);   // Age must be a number
            } catch (NumberFormatException nfe) {
                pw.println("<h3 style='color:red'>Student ID and Age must be numbers.</h3>");
                pw.println("<a href='index.html'>Go Back</a>");
                return;
            }

            // Proper INSERT with column list + VALUES
            String sql = "INSERT INTO student VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            Class.forName("com.mysql.cj.jdbc.Driver");
            try (Connection con = DriverManager.getConnection(URL, USER, PASS);
                 PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setInt(1, sid);
                ps.setString(2, sname);
                ps.setString(3, mname);
                ps.setString(4, fname);
                ps.setInt(5, age);
                ps.setString(6, gmail);
                ps.setString(7, phone);
                ps.setString(8, location);

                int rows = ps.executeUpdate();
                if (rows > 0) {
                    pw.println("<h2 style='color:green'>1 student registered successfully.</h2>");
                } else {
                    pw.println("<h2 style='color:red'>Student not registered.</h2>");
                }
                pw.println("<center><a href='index.html'>Go Back</a></center>");
            }
        } catch (Exception e) {
            // Show a readable error (and log full stack in server logs)
            response.getWriter().println("<h3 style='color:red'>Error: " + e.getMessage() + "</h3>");
        }
    }
}
