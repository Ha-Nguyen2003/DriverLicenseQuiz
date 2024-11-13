package controller;

import dao.QuizDAO;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "EditQuestionServlet", urlPatterns = {"/edit-question"})
public class EditQuestionServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Retrieve parameters from the form
        int questionID = Integer.parseInt(request.getParameter("questionID"));
        String questionText = request.getParameter("questionText");
        int correctAnswerID = Integer.parseInt(request.getParameter("correctAnswer"));

        // Update question text
        QuizDAO quizDAO = new QuizDAO();
        quizDAO.editQuestionText(questionID, questionText);

        // Update correct answer
        quizDAO.updateCorrectAnswer(questionID, correctAnswerID);

        // Update answer content
        String[] answerIDs = request.getParameterValues("answerIDs");
        String[] answerTexts = request.getParameterValues("answerTexts");

        if (answerIDs != null && answerTexts != null) {
            for (int i = 0; i < answerIDs.length; i++) {
                int answerID = Integer.parseInt(answerIDs[i]);
                String answerText = answerTexts[i];

                quizDAO.editAnswerText(answerID, answerText);
            }
        }
        // Redirect back to view quiz details page
        int quizID = Integer.parseInt(request.getParameter("quizID"));
        response.sendRedirect(request.getContextPath() + "/view-quiz-details?quizID=" + quizID);
    }
}
