package controller;

import dao.QuizDAO;
import dao.ResultDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Quiz;
import model.Result;
import model.Question;
import model.Answer;
import java.util.List;
import java.util.Map;

@WebServlet(name = "AlreadyTakenQuizServlet", urlPatterns = { "/already-taken-quiz" })
public class AlreadyTakenQuizServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int quizID = Integer.parseInt(request.getParameter("quizID"));
        int userID = Integer.parseInt(request.getParameter("userID"));

        QuizDAO quizDAO = new QuizDAO();
        Quiz quiz = quizDAO.getQuizDetails(quizID); // Lấy chi tiết bài kiểm tra bao gồm cả câu hỏi và đáp án

        ResultDAO resultDAO = new ResultDAO();
        Map<String, Object> detailedResult = resultDAO.getDetailedResultByUserAndQuiz(userID, quizID);
        Result result = (Result) detailedResult.get("result");
        List<Map<String, Object>> userAnswers = (List<Map<String, Object>>) detailedResult.get("userAnswers");

        request.setAttribute("result", result);
        request.setAttribute("userAnswers", userAnswers);

        request.setAttribute("result", result);
        request.setAttribute("quiz", quiz);
        request.getRequestDispatcher("already-taken-quiz.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }
}