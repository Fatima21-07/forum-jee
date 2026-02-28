package com.forum.controller;

import com.forum.dao.DBConnection;
import com.forum.model.Topic;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "SearchServlet", urlPatterns = {"/search"})
public class SearchServlet extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        
        String query = request.getParameter("query");
        if (query == null || query.trim().isEmpty()) {
            response.sendRedirect("home");
            return;
        }

        List<Topic> results = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT t.*, u.username, c.name as category_name, (SELECT COUNT(*) FROM posts WHERE topic_id = t.id) as post_count " +
                         "FROM topics t JOIN users u ON t.user_id = u.id JOIN categories c ON t.category_id = c.id " +
                         "WHERE t.title LIKE ? OR t.content LIKE ? " +
                         "ORDER BY t.created_at DESC";
            
            PreparedStatement ps = conn.prepareStatement(sql);
            String searchPattern = "%" + query + "%";
            ps.setString(1, searchPattern);
            ps.setString(2, searchPattern);
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                results.add(new Topic(
                    rs.getLong("id"), rs.getInt("category_id"), rs.getLong("user_id"), 
                    rs.getString("title"), rs.getString("content"), rs.getTimestamp("created_at"),
                    rs.getString("username"), rs.getString("category_name"), rs.getInt("post_count")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException(e);
        }

        request.setAttribute("query", query);
        request.setAttribute("results", results);
        request.getRequestDispatcher("/WEB-INF/views/search_results.jsp").forward(request, response);
    }
}
