/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.QuizDAO;
import jakarta.servlet.RequestDispatcher;
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
@WebServlet(name = "QuizzesResultServlet", urlPatterns = {"/quizzes-result"})
public class QuizzesResultServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User account = (User) session.getAttribute("account");
        if (account != null) {
            int page = 1;
            String search = "";

            // Get page and search parameters from the request
            String pageParam = request.getParameter("page");
            String searchParam = request.getParameter("search");

            // Check if page parameter is provided
            if (pageParam != null && !pageParam.isEmpty()) {
                try {
                    page = Integer.parseInt(pageParam);
                } catch (NumberFormatException e) {
                    
                }
            }

            // Check if search parameter is provided
            if (searchParam != null) {
                search = searchParam;
            }
            QuizDAO dao = new QuizDAO();
            List<Quiz> quizzes = dao.getQuizzesByTeacher(search, page, account.getId());
            int totalPages = dao.calculateTotalPages(search);

            // Set attributes for JSP
            request.setAttribute("quizzes", quizzes);
            request.setAttribute("totalPages", totalPages);
            request.setAttribute("currentPage", page);
            request.setAttribute("search", search);

            // Forward to JSP
            RequestDispatcher dispatcher = request.getRequestDispatcher("/quizzes_result.jsp");
            dispatcher.forward(request, response);
        } else {
            response.sendRedirect("login");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

}
