package com.example.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;

@WebServlet("/download")
public class DownloadController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        // Lấy tên file từ tham số request
        String filename = request.getParameter("filename");
        if (filename == null || filename.isEmpty()) {
            response.getWriter().println("Tên file không hợp lệ!");
            return;
        }

        // Sử dụng đường dẫn  đến thư mục uploads
        String uploadPath = System.getProperty("user.dir") + File.separator + "uploads";
        File file = new File(uploadPath, filename);

        // Kiểm tra file có tồn tại không
        if (!file.exists()) {
            response.getWriter().println("File không tồn tại tại đường dẫn: " + file.getAbsolutePath());
            return;
        }

        // Thiết lập header để kích hoạt hộp thoại "Save As"
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");

        // Gửi file về client
        try (FileInputStream fis = new FileInputStream(file);
             OutputStream os = response.getOutputStream()) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            response.getWriter().println("Lỗi khi tải file: " + e.getMessage());
        }
    }
}