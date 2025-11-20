package com.test;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.GenericServlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebServlet;

@WebServlet("/ViewStudentServlet")
public class ViewStudentServlet extends GenericServlet {
    private static final long serialVersionUID = 1L;

    private static final String URL = "jdbc:mysql://localhost:3306/studentdb";
    private static final String USER = "root";
    private static final String PASS = "Pavilion@123";

    @Override
    public void service(ServletRequest request, ServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter pw = response.getWriter();

        String sidStr = request.getParameter("sid");
        int sid;

        // Validate ID
        try {
            sid = Integer.parseInt(sidStr);
        } catch (NumberFormatException e) {
            pw.println("<h3 style='color:red'>Student ID must be a number.</h3>");
            pw.println("<a href='view.html'>Go Back</a>");
            return;
        }

        String sql = "SELECT sid, sname, mname, fname, age, gmail, phone, location FROM student WHERE sid = ?";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection con = DriverManager.getConnection(URL, USER, PASS);
                 PreparedStatement ps = con.prepareStatement(sql)) {

                ps.setInt(1, sid);

                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {

                        pw.println("<h2>Student Details</h2>");
                        pw.println("<table border='1' cellpadding='10'>");

                        pw.println("<tr><th>Student ID</th><td>" + rs.getInt("sid") + "</td></tr>");
                        pw.println("<tr><th>Name</th><td>" + rs.getString("sname") + "</td></tr>");
                        pw.println("<tr><th>Mother Name</th><td>" + rs.getString("mname") + "</td></tr>");
                        pw.println("<tr><th>Father Name</th><td>" + rs.getString("fname") + "</td></tr>");
                        pw.println("<tr><th>Age</th><td>" + rs.getInt("age") + "</td></tr>");
                        pw.println("<tr><th>Email</th><td>" + rs.getString("gmail") + "</td></tr>");
                        pw.println("<tr><th>Phone</th><td>" + rs.getString("phone") + "</td></tr>");
                        pw.println("<tr><th>Location</th><td>" + rs.getString("location") + "</td></tr>");

                        pw.println("</table>");

                    } else {
                        pw.println("<h3 style='color:red'>No student found with ID: " + sid + "</h3>");
                    }
                }
            }

        } catch (Exception e) {
            pw.println("<h3 style='color:red'>Error: " + e.getMessage() + "</h3>");
        }

        pw.println("<p><a href='index.html'>Back to Home</a></p>");
    }
}
