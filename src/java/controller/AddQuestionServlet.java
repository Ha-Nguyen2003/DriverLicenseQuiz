/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller;

import dao.QuizDAO;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import model.Answer;
import model.Question;

/**
 *
 * @author ADMIN
 */
@WebServlet(name = "AddQuestionServlet", urlPatterns = {"/add-question"})
public class AddQuestionServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Retrieve parameters from the form
        int quizID = Integer.parseInt(request.getParameter("quizID"));
        String questionText = request.getParameter("questionText");

        // Retrieve answers and correct answer
        String[] answerTexts = request.getParameterValues("answerTexts");
        int correctAnswerIndex = Integer.parseInt(request.getParameter("correctAnswer"));

        // Create a new question
        Question question = new Question();
        question.setQuizID(quizID);
        question.setQuestionText(questionText);

        // Create answers for the question
        List<Answer> answers = new ArrayList<>();
        for (int i = 0; i < answerTexts.length; i++) {
            Answer answer = new Answer();
            answer.setAnswerText(answerTexts[i]);
            answer.setCorrect(i == correctAnswerIndex - 1); // Index starts from 0
            answers.add(answer);
        }

        question.setAnswers(answers);

        // Add the question to the database
        QuizDAO quizDAO = new QuizDAO();
        quizDAO.addQuestion(question);

        // Redirect back to view quiz details page
        response.sendRedirect(request.getContextPath() + "/view-quiz-details?quizID=" + quizID);
    }
}
