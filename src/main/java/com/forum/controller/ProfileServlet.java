package com.forum.controller;

import com.forum.dao.DBConnection;
import com.forum.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;

@WebServlet(name = "ProfileServlet", urlPatterns = {"/profile", "/profile/update"})
public class ProfileServlet extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getSession().getAttribute("user") == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        request.getRequestDispatcher("/WEB-INF/views/profile.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        String username = request.getParameter("username");
        String full_name = request.getParameter("full_name");
        String email = request.getParameter("email");
        String bio = request.getParameter("bio");
        
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement("UPDATE users SET username = ?, full_name = ?, email = ?, bio = ? WHERE id = ?")) {
            ps.setString(1, username);
            ps.setString(2, full_name);
            ps.setString(3, email);
            ps.setString(4, bio);
            ps.setLong(5, user.getId());
            if (ps.executeUpdate() > 0) {
                user.setUsername(username);
                user.setFull_name(full_name);
                user.setEmail(email);
                user.setBio(bio);
                session.setAttribute("user", user);
                request.setAttribute("success", "Profil mis à jour avec succès");
            } else {
                request.setAttribute("error", "Erreur lors de la mise à jour");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("error", "Erreur base de données");
        }
        request.getRequestDispatcher("/WEB-INF/views/profile.jsp").forward(request, response);
    }
}
