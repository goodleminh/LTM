package com.example.controller;

import com.example.util.DBConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/login")
public class LoginController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Chuyển hướng đến trang login.jsp khi nhận yêu cầu GET
        req.getRequestDispatcher("login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        HttpSession session = req.getSession();

        // Kiểm tra dữ liệu đầu vào đơn giản
        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
            req.setAttribute("error", "Username and password must not be empty");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
            return;
        }

        // Kết nối DB và kiểm tra
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT role FROM users WHERE username = ? AND password = ?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, username);
                ps.setString(2, password);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        String role = rs.getString("role");
                        session.setAttribute("username", username);
                        session.setAttribute("role", role);

                        // Chuyển hướng theo role
                        if ("admin".equals(role)) {
                            resp.sendRedirect("admin.jsp");
                        } else {
                            resp.sendRedirect("home.jsp");
                        }
                        return;
                    } else {
                        req.setAttribute("error", "Invalid username or password");
                        req.getRequestDispatcher("login.jsp").forward(req, resp);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            req.setAttribute("error", "Database error: " + e.getMessage());
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        }
    }
}
 