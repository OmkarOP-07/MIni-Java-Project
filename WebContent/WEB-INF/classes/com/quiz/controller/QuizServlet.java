package com.quiz.controller;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.sql.*;
import com.quiz.util.DBUtil;

public class QuizServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        // Get first question from DB
        try (Connection con = DBUtil.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM questions LIMIT 1")) {

            if (rs.next()) {
                int qid = rs.getInt("id");

                out.println("<html><head><title>Quiz</title></head><body>");
                out.println("<form action='result' method='post'>");
                out.println("<h2>" + rs.getString("question_text") + "</h2>");
                out.println("<input type='hidden' name='qid' value='" + qid + "'/>");

                out.println("<input type='radio' name='answer' value='A'> " + rs.getString("option_a") + "<br>");
                out.println("<input type='radio' name='answer' value='B'> " + rs.getString("option_b") + "<br>");
                out.println("<input type='radio' name='answer' value='C'> " + rs.getString("option_c") + "<br>");
                out.println("<input type='radio' name='answer' value='D'> " + rs.getString("option_d") + "<br><br>");

                out.println("<input type='submit' value='Submit Answer'/>");
                out.println("</form>");
                out.println("</body></html>");
            }

        } catch (Exception e) {
            e.printStackTrace(out);
        }
    }
}
