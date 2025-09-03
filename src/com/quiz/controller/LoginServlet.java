package com.quiz.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.sql.*;
import com.quiz.util.DBUtil;

public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM users WHERE username=? AND password=?")) {

            ps.setString(1, username);
            ps.setString(2, password); // ⚠️ For real apps, use hashing

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                HttpSession session = req.getSession();
                session.setAttribute("username", username);
                resp.sendRedirect("quiz");
            } else {
                out.println("<h3>Invalid username or password</h3>");
                out.println("<a href='index.html'>Try Again</a>");
            }

        } catch (Exception e) {
            e.printStackTrace(out);
        }
    }
}
