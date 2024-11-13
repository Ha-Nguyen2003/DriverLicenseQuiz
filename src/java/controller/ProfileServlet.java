package controller;

import dao.UserDAO;
import model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {
    
    private UserDAO userDAO;
    
    @Override
    public void init() throws ServletException {
        super.init();
        userDAO = new UserDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("account");
        
        if (user != null) {
            request.setAttribute("user", user);
            request.getRequestDispatcher("/profile.jsp").forward(request, response);
        } else {
            response.sendRedirect("login");
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("account");
        
        if (user != null) {
            String action = request.getParameter("action");
            
            if ("updateProfile".equals(action)) {
                updateProfile(request, response, user);
            } else if ("changePassword".equals(action)) {
                changePassword(request, response, user);
            } else {
                response.sendRedirect("profile");
            }
        } else {
            response.sendRedirect("login");
        }
    }
    
    private void updateProfile(HttpServletRequest request, HttpServletResponse response, User user)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        
        user.setName(name);
        user.setEmail(email);
        
        boolean updated = userDAO.updateUser2(user);
        
        if (updated) {
            request.setAttribute("message", "Profile updated successfully!");
        } else {
            request.setAttribute("error", "Failed to update profile. Please try again.");
        }
        
        request.setAttribute("user", user);
        request.getRequestDispatcher("/profile.jsp").forward(request, response);
    }
    
    private void changePassword(HttpServletRequest request, HttpServletResponse response, User user)
            throws ServletException, IOException {
        String currentPassword = request.getParameter("currentPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");
        
        if (!userDAO.validateUser(user.getEmail(), currentPassword)) {
            request.setAttribute("passwordError", "Current password is incorrect.");
        } else if (!newPassword.equals(confirmPassword)) {
            request.setAttribute("passwordError", "New passwords do not match.");
        } else {
            boolean updated = userDAO.updatePassword(user.getId(), newPassword);
            if (updated) {
                request.setAttribute("passwordMessage", "Password changed successfully!");
            } else {
                request.setAttribute("passwordError", "Failed to change password. Please try again.");
            }
        }
        
        request.setAttribute("user", user);
        request.getRequestDispatcher("/profile.jsp").forward(request, response);
    }
}