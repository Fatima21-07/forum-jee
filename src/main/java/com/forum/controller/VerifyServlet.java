package com.forum.controller;

import com.forum.dao.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet("/verify")
public class VerifyServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String token = request.getParameter("token");
        if (token != null) {
            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement ps = conn.prepareStatement("UPDATE users SET is_active = TRUE, validation_token = NULL WHERE validation_token = ?")) {
                ps.setString(1, token);
                if (ps.executeUpdate() > 0) {
                    request.setAttribute("success", "Votre compte a été activé avec succès ! Vous pouvez maintenant vous connecter.");
                } else {
                    request.setAttribute("error", "Lien d'activation invalide ou déjà utilisé.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
    }
}
