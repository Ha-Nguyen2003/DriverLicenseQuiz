package controller;

import dao.UserDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;
import java.io.IOException;
import java.util.List;

@WebServlet("/manage-account")
public class ManageAccount extends HttpServlet {
    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        
        if ("edit".equals(action)) {
            int userId = Integer.parseInt(request.getParameter("id"));
            User user = userDAO.getUserByID(userId);
            if (user != null) {
                request.setAttribute("user", user);
                request.getRequestDispatcher("/edit-user.jsp").forward(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "User not found");
            }
        } else if ("add".equals(action)) {
            request.getRequestDispatcher("/add-user.jsp").forward(request, response);
        } else {
            // Handle search and listing
            String searchTerm = request.getParameter("searchTerm");
            List<User> users;
            if (searchTerm != null && !searchTerm.trim().isEmpty()) {
                users = userDAO.searchUsers(searchTerm);
            } else {
                users = userDAO.findAll();
            }
            request.setAttribute("users", users);
            request.getRequestDispatcher("/account-list.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        
        if ("toggleStatus".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            Boolean newStatus = Boolean.valueOf(request.getParameter("status"));
            User user = userDAO.getUserByID(id);
            if (user != null) {
                user.setStatus(newStatus);
                userDAO.updateUser(user);
            }
            response.sendRedirect("manage-account");
        } else if ("update".equals(action)) {
            // Handle user update
            int id = Integer.parseInt(request.getParameter("id"));
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String role = request.getParameter("role");
            Boolean status = Boolean.valueOf(request.getParameter("status"));

            User user = new User(id, name, email, null, role, status);
            boolean success = userDAO.updateUser(user);
            
            if (success) {
                response.sendRedirect("manage-account");
            } else {
                request.setAttribute("error", "Failed to update user");
                request.getRequestDispatcher("/edit-user.jsp").forward(request, response);
            }
        } else if ("add".equals(action)) {
            // Handle adding new user
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String role = request.getParameter("role");
            Boolean status = Boolean.valueOf(request.getParameter("status"));

            User newUser = new User(name, email, password, role, status);
            boolean success = userDAO.registerUser(newUser);
            
            if (success) {
                response.sendRedirect("manage-account");
            } else {
                request.setAttribute("error", "Failed to add new user");
                request.getRequestDispatcher("/add-user.jsp").forward(request, response);
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid action");
        }
    }
}