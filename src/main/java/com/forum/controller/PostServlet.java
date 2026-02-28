package com.forum.controller;

import com.forum.dao.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import com.forum.model.User;

@WebServlet(name = "PostServlet", urlPatterns = {"/post/new"})
public class PostServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String content = request.getParameter("content");
        long topicId = Long.parseLong(request.getParameter("topicId"));

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement("INSERT INTO posts (content, user_id, topic_id) VALUES (?, ?, ?)")) {
            ps.setString(1, content);
            ps.setLong(2, user.getId());
            ps.setLong(3, topicId);
            ps.executeUpdate();
            response.sendRedirect(request.getContextPath() + "/topic?id=" + topicId);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
    }
}
