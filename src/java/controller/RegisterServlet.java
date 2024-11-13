package controller;

import dao.UserDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.User;

@WebServlet(name = "RegisterServlet", urlPatterns = {"/register"})
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("register.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String re_password = request.getParameter("re-password");

        UserDAO userDAO = new UserDAO();
        try {
            boolean isExist = userDAO.isEmailAlreadyInUse(email);
            if (isExist) {
                request.setAttribute("error", "Email already existed");
                RequestDispatcher dispatcher = request.getRequestDispatcher("register.jsp");
                dispatcher.forward(request, response);
                return;
            }
        } catch (SQLException ex) {
            Logger.getLogger(RegisterServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (password.equals(re_password)) {
            // Set the default status to null or a specific value based on your requirements
            Boolean defaultStatus = null; // or true if you want new users to be active by default
            User u = new User(name, email, password, "student", defaultStatus);
            boolean isRegister = userDAO.registerUser(u);
            if (isRegister) {
                response.sendRedirect("login?msg=Register successfully!");
                return;
            }
        } else {
            request.setAttribute("error", "Password do not match!");
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("register.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}