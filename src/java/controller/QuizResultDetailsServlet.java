package controller;

import dao.QuizDAO;
import dao.ResultDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import model.Result;
import model.User;

@WebServlet(name = "QuizResultDetailsServlet", urlPatterns = {"/quiz-result-details"})
public class QuizResultDetailsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User account = (User) session.getAttribute("account");
        if (account != null && account.getRole().equalsIgnoreCase("teacher")) {
            int quizID = Integer.parseInt(request.getParameter("quizID"));
            String searchTerm = request.getParameter("search");
            String sortBy = request.getParameter("sort");

            ResultDAO resultDAO = new ResultDAO();
            List<Result> results = resultDAO.getResultsByQuiz(quizID, searchTerm, sortBy);

            QuizDAO quizDAO = new QuizDAO();
            String quizName = quizDAO.getQuizByID(quizID).getQuizName();

            request.setAttribute("result", results);
            request.setAttribute("quizName", quizName);
            request.getRequestDispatcher("result-details.jsp").forward(request, response);
        } else {
            response.sendRedirect("login");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}