package com.test;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/DisplayAllStudents")
public class DisplayAllStudentsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // TODO: change credentials if yours differ
    private static final String URL = "jdbc:mysql://localhost:3306/studentdb";
    private static final String USER = "root";
    private static final String PASS = "Pavilion@123";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();

        // Simple HTML head + table styles
        out.println("<!DOCTYPE html>");
        out.println("<html><head><meta charset='UTF-8'><title>All Students</title>");
        out.println("<style>");
        out.println("body{font-family:Arial,sans-serif;background:#eef2f3;margin:0;padding:24px;}");
        out.println(".card{max-width:1000px;margin:0 auto;background:#fff;padding:22px;border-radius:14px;box-shadow:0 5px 18px rgba(0,0,0,.2)}");
        out.println("h2{margin:0 0 14px;color:#333;text-align:center}");
        out.println("table{width:100%;border-collapse:collapse;margin-top:10px}");
        out.println("th,td{border:1px solid #777;padding:8px;text-align:left;font-size:14px}");
        out.println(".btn{display:inline-block;margin-top:12px;padding:10px 14px;background:#4a90e2;color:#fff;text-decoration:none;border-radius:6px}");
        out.println(".btn:hover{background:#357ac8}");
        out.println("</style></head><body><div class='card'>");
        out.println("<h2>All Students</h2>");

        Connection con = null;
        Statement st = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(URL, USER, PASS);

            // Use the real table name you created. If unsure, SELECT * and read by index.
            // Safer if your column names differ (avoids “Unknown column”).
            st = con.createStatement();
            rs = st.executeQuery("SELECT * FROM student"); // <-- table name must exist

            // Print header once
            out.println("<table>");
            out.println("<tr>"
                    + "<th>#</th>"
                    + "<th>Student ID</th>"
                    + "<th>Student Name</th>"
                    + "<th>Mother Name</th>"
                    + "<th>Father Name</th>"
                    + "<th>Age</th>"
                    + "<th>Gmail</th>"
                    + "<th>Phone</th>"
                    + "<th>Location</th>"
                    + "</tr>");

            int rowNo = 0;
            ResultSetMetaData md = rs.getMetaData();
            int colCount = md.getColumnCount();

            while (rs.next()) {
                rowNo++;

                // Read by index to avoid column-name mismatches
                // Adjust the order below if your table column order differs.
                // If your table has fewer columns, guard with ternaries.
                String c1 = colCount >= 1 ? safe(rs.getString(1)) : "";
                String c2 = colCount >= 2 ? safe(rs.getString(2)) : "";
                String c3 = colCount >= 3 ? safe(rs.getString(3)) : "";
                String c4 = colCount >= 4 ? safe(rs.getString(4)) : "";
                String c5 = colCount >= 5 ? safe(rs.getString(5)) : "";
                String c6 = colCount >= 6 ? safe(rs.getString(6)) : "";
                String c7 = colCount >= 7 ? safe(rs.getString(7)) : "";
                String c8 = colCount >= 8 ? safe(rs.getString(8)) : "";
                String c9 = colCount >= 9 ? safe(rs.getString(9)) : "";

                // Map them to intended fields (common schema):
                // 1:id, 2:sname, 3:mname, 4:fname, 5:age, 6:gmail, 7:phone, 8:location
                out.println("<tr>");
                out.println("<td>" + rowNo + "</td>");
                out.println("<td>" + c1 + "</td>"); // Student ID
                out.println("<td>" + c2 + "</td>"); // Student Name
                out.println("<td>" + c3 + "</td>"); // Mother Name
                out.println("<td>" + c4 + "</td>"); // Father Name
                out.println("<td>" + c5 + "</td>"); // Age
                out.println("<td>" + c6 + "</td>"); // Gmail
                out.println("<td>" + c7 + "</td>"); // Phone
                out.println("<td>" + c8 + "</td>"); // Location
                out.println("</tr>");
            }

            out.println("</table>");
            out.println("<a class='btn' href='index.html'>Back to Home</a>");

        } catch (Exception e) {
            // Show the error to help you debug quickly
            out.println("<pre style='color:red;white-space:pre-wrap;background:#fff;border:1px solid #ccc;padding:12px;border-radius:8px;'>");
            e.printStackTrace(out);
            out.println("</pre>");
            out.println("<a class='btn' href='index.html'>Back to Home</a>");
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception ignore) {}
            try { if (st != null) st.close(); } catch (Exception ignore) {}
            try { if (con != null) con.close(); } catch (Exception ignore) {}
        }

        out.println("</div></body></html>");
    }

    private String safe(String s) {
        if (s == null) return "";
        return s.replace("&","&amp;").replace("<","&lt;").replace(">","&gt;");
    }
}
