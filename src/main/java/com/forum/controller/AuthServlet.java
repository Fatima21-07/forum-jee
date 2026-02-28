package com.forum.controller;

import com.forum.dao.DBConnection;
import com.forum.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;

@WebServlet(name = "AuthServlet", urlPatterns = {"/login", "/register", "/logout"})
public class AuthServlet extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String path = request.getServletPath();
        if ("/logout".equals(path)) {
            request.getSession().invalidate();
            response.sendRedirect("login");
        } else if ("/register".equals(path)) {
            request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String path = request.getServletPath();
        if ("/login".equals(path)) {
            handleLogin(request, response);
        } else if ("/register".equals(path)) {
            handleRegister(request, response);
        }
    }

    private void handleLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM users WHERE username = ? AND password_hash = ?")) {
            ps.setString(1, username);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    if (!rs.getBoolean("is_active")) {
                        request.setAttribute("error", "Votre compte n'est pas encore activé. Veuillez vérifier vos emails.");
                        request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
                        return;
                    }
                    User user = new User(
                        rs.getLong("id"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("password_hash"),
                        rs.getString("full_name"),
                        rs.getString("bio"),
                        rs.getTimestamp("created_at")
                    );
                    request.getSession().setAttribute("user", user);
                    response.sendRedirect("home");
                } else {
                    request.setAttribute("error", "Nom d'utilisateur ou mot de passe incorrect");
                    request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
    }

    private void handleRegister(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String token = java.util.UUID.randomUUID().toString();

        try (Connection conn = DBConnection.getConnection()) {
            // Check if user exists
            PreparedStatement checkPs = conn.prepareStatement("SELECT id FROM users WHERE username = ? OR email = ?");
            checkPs.setString(1, username);
            checkPs.setString(2, email);
            if (checkPs.executeQuery().next()) {
                request.setAttribute("error", "Nom d'utilisateur ou email déjà pris");
                request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
                return;
            }

            // Save user
            PreparedStatement ps = conn.prepareStatement("INSERT INTO users (username, password_hash, email, is_active, validation_token) VALUES (?, ?, ?, ?, ?)");
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, email);
            ps.setBoolean(4, false);
            ps.setString(5, token);
            
            if (ps.executeUpdate() > 0) {
                // Simulation d'envoi d'email
                String activationLink = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/verify?token=" + token;
                System.out.println("----- EMAIL SIMULATION -----");
                System.out.println("To: " + email);
                System.out.println("Subject: Validation de votre compte Forum CDL");
                System.out.println("Link: " + activationLink);
                System.out.println("----------------------------");
                
                request.setAttribute("success", "Inscription réussie ! Veuillez cliquer sur le lien envoyé par email (voir console serveur) pour activer votre compte.");
                request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
            } else {
                request.setAttribute("error", "Erreur lors de l'inscription");
                request.getRequestDispatcher("/WEB-INF/views/register.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
    }
}
