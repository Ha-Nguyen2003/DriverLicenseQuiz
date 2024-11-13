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
import model.Question;
import model.Quiz;
import model.User;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "ViewQuizDetailsServlet", urlPatterns = {"/view-quiz-details"})
public class ViewQuizDetailsServlet extends HttpServlet {

    @Override

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve quizID from the request parameter
        HttpSession session = request.getSession();
        User account = (User) session.getAttribute("account");
        if (account != null) {
            int quizID = Integer.parseInt(request.getParameter("quizID"));

            // Fetch quiz details and questions with answers
            QuizDAO quizDAO = new QuizDAO();
            Quiz quiz = quizDAO.getQuizByID(quizID);
            List<Question> questions = quizDAO.getQuestionsByQuizIDWithAnswers(quizID);

            request.setAttribute("quiz", quiz);
            request.setAttribute("questions", questions);
            request.getRequestDispatcher("view_quiz_details.jsp").forward(request, response);

        }else{
            response.sendRedirect("login");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }
}
