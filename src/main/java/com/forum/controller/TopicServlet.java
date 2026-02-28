package com.forum.controller;

import com.forum.dao.DBConnection;
import com.forum.model.Category;
import com.forum.model.Post;
import com.forum.model.Topic;
import com.forum.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "TopicServlet", urlPatterns = {"/topics", "/topic", "/topic/new"})
public class TopicServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String path = request.getServletPath();
        if ("/topics".equals(path)) {
            listTopics(request, response);
        } else if ("/topic".equals(path)) {
            viewTopic(request, response);
        } else if ("/topic/new".equals(path)) {
            showNewTopicForm(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String path = request.getServletPath();
        if ("/topic/new".equals(path)) {
            handleCreateTopic(request, response);
        }
    }

    private void listTopics(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int categoryId = Integer.parseInt(request.getParameter("categoryId"));
        
        try (Connection conn = DBConnection.getConnection()) {
            // Get category
            PreparedStatement cp = conn.prepareStatement("SELECT * FROM categories WHERE id = ?");
            cp.setInt(1, categoryId);
            ResultSet rsCat = cp.executeQuery();
            if (rsCat.next()) {
                Category category = new Category(rsCat.getInt("id"), rsCat.getString("name"), rsCat.getString("description"));
                request.setAttribute("category", category);

                // Get topics
                List<Topic> topics = new ArrayList<>();
                PreparedStatement tp = conn.prepareStatement(
                    "SELECT t.*, u.username, c.name as category_name, (SELECT COUNT(*) FROM posts WHERE topic_id = t.id) as post_count " +
                    "FROM topics t JOIN users u ON t.user_id = u.id JOIN categories c ON t.category_id = c.id " +
                    "WHERE t.category_id = ? ORDER BY t.created_at DESC"
                );
                tp.setInt(1, categoryId);
                ResultSet rsTop = tp.executeQuery();
                while (rsTop.next()) {
                    topics.add(new Topic(
                        rsTop.getLong("id"), rsTop.getInt("category_id"), rsTop.getLong("user_id"), 
                        rsTop.getString("title"), rsTop.getString("content"), rsTop.getTimestamp("created_at"),
                        rsTop.getString("username"), rsTop.getString("category_name"), rsTop.getInt("post_count")
                    ));
                }
                request.setAttribute("topics", topics);
                request.getRequestDispatcher("/WEB-INF/views/topics.jsp").forward(request, response);
            } else {
                response.sendRedirect("home");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
    }

    private void viewTopic(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        long topicId = Long.parseLong(request.getParameter("id"));
        
        try (Connection conn = DBConnection.getConnection()) {
            // Get topic
            PreparedStatement tp = conn.prepareStatement(
                "SELECT t.*, u.username, c.name as category_name, (SELECT COUNT(*) FROM posts WHERE topic_id = t.id) as post_count " +
                "FROM topics t JOIN users u ON t.user_id = u.id JOIN categories c ON t.category_id = c.id WHERE t.id = ?"
            );
            tp.setLong(1, topicId);
            ResultSet rsTop = tp.executeQuery();
            if (rsTop.next()) {
                Topic topic = new Topic(
                    rsTop.getLong("id"), rsTop.getInt("category_id"), rsTop.getLong("user_id"), 
                    rsTop.getString("title"), rsTop.getString("content"), rsTop.getTimestamp("created_at"),
                    rsTop.getString("username"), rsTop.getString("category_name"), rsTop.getInt("post_count")
                );
                request.setAttribute("topic", topic);

                // Get posts
                List<Post> posts = new ArrayList<>();
                PreparedStatement mp = conn.prepareStatement(
                    "SELECT p.*, u.username FROM posts p JOIN users u ON p.user_id = u.id " +
                    "WHERE p.topic_id = ? ORDER BY p.created_at ASC"
                );
                mp.setLong(1, topicId);
                ResultSet rsPost = mp.executeQuery();
                while (rsPost.next()) {
                    posts.add(new Post(
                        rsPost.getLong("id"), rsPost.getLong("topic_id"), rsPost.getLong("user_id"),
                        rsPost.getString("content"), rsPost.getTimestamp("created_at"),
                        rsPost.getString("username")
                    ));
                }
                request.setAttribute("posts", posts);
                request.getRequestDispatcher("/WEB-INF/views/topic_view.jsp").forward(request, response);
            } else {
                response.sendRedirect("home");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
    }

    private void showNewTopicForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        request.setAttribute("categoryId", request.getParameter("categoryId"));
        request.getRequestDispatcher("/WEB-INF/views/topic_new.jsp").forward(request, response);
    }

    private void handleCreateTopic(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String title = request.getParameter("title");
        String content = request.getParameter("content");
        int categoryId = Integer.parseInt(request.getParameter("categoryId"));

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement("INSERT INTO topics (title, content, user_id, category_id) VALUES (?, ?, ?, ?)")) {
            ps.setString(1, title);
            ps.setString(2, content);
            ps.setLong(3, user.getId());
            ps.setInt(4, categoryId);
            if (ps.executeUpdate() > 0) {
                response.sendRedirect(request.getContextPath() + "/topics?categoryId=" + categoryId);
            } else {
                request.setAttribute("error", "Erreur lors de la création du sujet");
                request.setAttribute("categoryId", categoryId);
                request.getRequestDispatcher("/WEB-INF/views/topic_new.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
    }
}
