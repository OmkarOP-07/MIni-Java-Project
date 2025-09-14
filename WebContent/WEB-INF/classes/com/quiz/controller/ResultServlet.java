package com.quiz.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.sql.*;
import com.quiz.util.DBUtil;

public class ResultServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        int qid = Integer.parseInt(req.getParameter("qid"));
        String userAnswer = req.getParameter("answer");

        try (Connection con = DBUtil.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT correct_option FROM questions WHERE id=?")) {

            ps.setInt(1, qid);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String correctAnswer = rs.getString("correct_option");

                out.println("<html><head><title>Result</title></head><body>");
                if (userAnswer != null && userAnswer.equalsIgnoreCase(correctAnswer)) {
                    out.println("<h2>✅ Correct Answer!</h2>");
                } else {
                    out.println("<h2>❌ Wrong Answer. Correct was: " + correctAnswer + "</h2>");
                }
                out.println("<a href='quiz'>Next Question</a>");
                out.println("</body></html>");
            }

        } catch (Exception e) {
            e.printStackTrace(out);
        }
    }
}
