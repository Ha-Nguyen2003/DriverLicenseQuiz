package controller;

import dao.QuizDAO;
import dao.ResultDAO;
import model.Quiz;
import model.Question;
import model.Answer;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import model.User;

@WebServlet(name = "TakeQuizServlet", urlPatterns = {"/take-quiz"})
public class TakeQuizServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User account = (User) session.getAttribute("account");
        if (account != null) {
            int quizID = Integer.parseInt(request.getParameter("quizID"));
            ResultDAO resultDAO = new ResultDAO();
            boolean isUserTakenQuiz = resultDAO.ísUserTakenQuiz(account.getId(), quizID);
            if (isUserTakenQuiz) {
                response.sendRedirect("already-taken-quiz?quizID=" + quizID + "&userID=" + account.getId());
                return;
            }
            QuizDAO quizDAO = new QuizDAO();
            Quiz quiz = quizDAO.getQuizDetails(quizID);

            // Set attributes to be accessed in the JSP page
            request.setAttribute("quiz", quiz);

            // Forward the request to the JSP page for taking the quiz
            request.getRequestDispatcher("take-quiz.jsp").forward(request, response);
        } else {
            response.sendRedirect("login");
        }

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User account = (User) session.getAttribute("account");
        if (account != null) {
            int quizID = Integer.parseInt(request.getParameter("quizID"));

            QuizDAO quizDAO = new QuizDAO();
            Quiz quiz = quizDAO.getQuizDetails(quizID);

            // Calculate the user's score based on the submitted answers
            float score = calculateScore(request, quiz);
            request.setAttribute("quiz", quiz);
            request.setAttribute("score", score);

            ResultDAO resultDAO = new ResultDAO();
            resultDAO.saveQuizResult(account.getId(), quizID, score);
            // Forward the request to the JSP page for displaying the result
            request.getRequestDispatcher("/quiz-result.jsp").forward(request, response);
        } else {
            response.sendRedirect("login");
        }

    }

    private float calculateScore(HttpServletRequest request, Quiz quiz) {
        int totalQuestions = quiz.getQuestions().size();
        int correctAnswers = 0;

        // Iterate over each question in the quiz
        for (Question question : quiz.getQuestions()) {
            // Retrieve the user's selected answer ID for the current question

            String answerParamName = "question_" + question.getQuestionID();
            int selectedAnswerID = Integer.parseInt(request.getParameter(answerParamName));

            // Find the correct answer for the current question
            Answer correctAnswer = findCorrectAnswer(question);

            // Check if the selected answer ID matches the correct answer ID
            if (selectedAnswerID == correctAnswer.getAnswerID()) {
                // Increment the count of correct answers
                correctAnswers++;
            }
            for (Answer answer : question.getAnswers()) {
                answer.setSelected(answer.getAnswerID() == selectedAnswerID);
            }

        }

        // Calculate the percentage score
        float percentageScore = ((float) correctAnswers / totalQuestions) * 100;

        return percentageScore;
    }

    private Answer findCorrectAnswer(Question question) {
        // Iterate over answers for the question and find the correct one
        for (Answer answer : question.getAnswers()) {
            if (answer.isCorrect()) {
                return answer;
            }
        }
        return null; // Return null or handle the case when a correct answer is not found
    }
}
