/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.QuizDAO;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.Quiz;
import model.User;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "QuizManagementServlet", urlPatterns = {"/quiz-management"})
public class QuizManagementServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve teacherID from the session
        HttpSession session = request.getSession();
        User account = (User) session.getAttribute("account");
        if (account != null && account.getRole().equalsIgnoreCase("teacher")) {
            int teacherID = account.getId();

            // Fetch quizzes for the specific teacher
            QuizDAO quizDAO = new QuizDAO();
            List<Quiz> quizzes = quizDAO.getQuizzesByTeacherID(teacherID);

            request.setAttribute("quizzes", quizzes);
            request.getRequestDispatcher("quiz_management.jsp").forward(request, response);
        } else {
            response.sendRedirect("login");
        }

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("editQuiz".equals(action)) {
            // Edit Quiz logic
            int quizID = Integer.parseInt(request.getParameter("quizID"));
            String quizName = request.getParameter("quizName");

            QuizDAO quizDAO = new QuizDAO();
            quizDAO.editQuiz(quizID, quizName);

            // Redirect back to quiz management page
            response.sendRedirect(request.getContextPath() + "/quiz-management");
        } else if ("addQuiz".equals(action)) {
            // Add Quiz logic
            String quizName = request.getParameter("quizName");

            HttpSession session = request.getSession();
            User account = (User) session.getAttribute("account");

            if (account != null && account.getRole().equalsIgnoreCase("teacher")) {
                int teacherID = account.getId();

                QuizDAO quizDAO = new QuizDAO();
                quizDAO.addQuiz(teacherID, quizName);

                // Redirect back to quiz management page
                response.sendRedirect(request.getContextPath() + "/quiz-management");
            } else {
                response.sendRedirect("login");
            }
        } else if ("toggleStatus".equals(action)) {
            // Toggle Status logic
            int quizID = Integer.parseInt(request.getParameter("quizID"));
            int currentStatus = Integer.parseInt(request.getParameter("currentStatus"));

            QuizDAO quizDAO = new QuizDAO();
            // Toggle the status (1 to 0, 0 to 1)
            int newStatus = (currentStatus == 1) ? 0 : 1;
            quizDAO.toggleQuizStatus(quizID, newStatus);

            // Respond with a success message or any other data if needed
            response.getWriter().write("Status toggled successfully");
        }
    }
}
