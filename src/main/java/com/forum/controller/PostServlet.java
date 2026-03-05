package com.forum.controller;

import com.forum.dao.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import com.forum.model.User;

@WebServlet(name = "PostServlet", urlPatterns = {"/post/new", "/post/edit", "/post/delete"})
public class PostServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String path = request.getServletPath();

        if ("/post/new".equals(path)) {
            handleCreatePost(request, response);
        } else if ("/post/edit".equals(path)) {
            handleEditPost(request, response);
        } else if ("/post/delete".equals(path)) {
            handleDeletePost(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/home");
        }
    }

    private void handleCreatePost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
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

    private void handleEditPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        long postId = Long.parseLong(request.getParameter("postId"));
        long topicId = Long.parseLong(request.getParameter("topicId"));
        String content = request.getParameter("content");

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement("UPDATE posts SET content = ? WHERE id = ? AND user_id = ?")) {
            ps.setString(1, content);
            ps.setLong(2, postId);
            ps.setLong(3, user.getId());
            ps.executeUpdate();
            response.sendRedirect(request.getContextPath() + "/topic?id=" + topicId);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
    }

    private void handleDeletePost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        long postId = Long.parseLong(request.getParameter("postId"));
        long topicId = Long.parseLong(request.getParameter("topicId"));

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement("DELETE FROM posts WHERE id = ? AND user_id = ?")) {
            ps.setLong(1, postId);
            ps.setLong(2, user.getId());
            ps.executeUpdate();
            response.sendRedirect(request.getContextPath() + "/topic?id=" + topicId);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
    }
}
