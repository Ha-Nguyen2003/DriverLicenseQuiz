/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Answer;
import model.Result;

/**
 *
 * @author ADMIN
 */
public class ResultDAO extends DBContext {

    public void saveQuizResult(int userID, int quizID, float score) {
        try {
            String insertResultSQL = "INSERT INTO Results (UserID, QuizID, Score) VALUES (?, ?, ?)";
            try ( PreparedStatement resultStatement = connection.prepareStatement(insertResultSQL)) {
                resultStatement.setInt(1, userID);
                resultStatement.setInt(2, quizID);
                resultStatement.setFloat(3, score);
                resultStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean ísUserTakenQuiz(int userID, int quizId) {
        try {
            String query = "SELECT * FROM Results WHERE UserID = ? AND QuizID = ?";
            try ( PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, userID);
                preparedStatement.setInt(2, quizId);

                try ( ResultSet resultSet = preparedStatement.executeQuery()) {
                    return resultSet.next(); // If result set has at least one row, user is valid
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Result getResultByUserAndQuiz(int userID, int quizID) {
        try {
            String query = "SELECT * FROM Results WHERE userID = ? AND quizID = ?";
            try ( PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, userID);
                preparedStatement.setInt(2, quizID);
                UserDAO userDAO = new UserDAO();
                QuizDAO quizDAO = new QuizDAO();

                try ( ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        return new Result(
                                resultSet.getInt("ResultID"),
                                resultSet.getInt("UserID"),
                                resultSet.getInt("QuizID"),
                                resultSet.getFloat("Score"),
                                resultSet.getDate("CreatedAt"),
                                userDAO.getUserByID(resultSet.getInt("userID")).getName(),
                                quizDAO.getQuizByID(resultSet.getInt("QuizID")).getQuizName()
                        );
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Result> getResultsByQuiz(int quizID) {
        List<Result> results = new ArrayList<>();

        String query = "select * from Results where QuizID =?";

        try ( PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, quizID);
            UserDAO userDAO = new UserDAO();
            QuizDAO quizDAO = new QuizDAO();
            try ( ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Result result = new Result();
                    result.setId(resultSet.getInt("ResultID"));
                    result.setUserId(resultSet.getInt("UserID"));
                    result.setQuizId(resultSet.getInt("QuizID"));
                    result.setScore(resultSet.getInt("Score"));
                    result.setDate(resultSet.getDate("CreatedAt"));
                    result.setStudentName(userDAO.getUserByID(resultSet.getInt("UserID")).getName());
                    result.setQuizName(quizDAO.getQuizByID(resultSet.getInt("QuizID")).getQuizName());
                    results.add(result);
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }

        return results;
    }

    public List<Result> getResultsByQuiz(int quizID, String searchTerm, String sortBy) {
        List<Result> results = new ArrayList<>();

        StringBuilder queryBuilder = new StringBuilder("SELECT * FROM Results r JOIN Users u ON r.UserID = u.UserID WHERE QuizID = ?");

        if (searchTerm != null && !searchTerm.isEmpty()) {
            queryBuilder.append(" AND u.Name LIKE ?");
        }

        if (sortBy != null && !sortBy.isEmpty()) {
            switch (sortBy) {
                case "score_asc":
                    queryBuilder.append(" ORDER BY r.Score ASC");
                    break;
                case "score_desc":
                    queryBuilder.append(" ORDER BY r.Score DESC");
                    break;
                case "time_asc":
                    queryBuilder.append(" ORDER BY r.CreatedAt ASC");
                    break;
                case "time_desc":
                    queryBuilder.append(" ORDER BY r.CreatedAt DESC");
                    break;
            }
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(queryBuilder.toString())) {
            preparedStatement.setInt(1, quizID);
            if (searchTerm != null && !searchTerm.isEmpty()) {
                preparedStatement.setString(2, "%" + searchTerm + "%");
            }

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Result result = new Result();
                    result.setId(resultSet.getInt("ResultID"));
                    result.setUserId(resultSet.getInt("UserID"));
                    result.setQuizId(resultSet.getInt("QuizID"));
                    result.setScore(resultSet.getInt("Score"));
                    result.setDate(resultSet.getDate("CreatedAt"));
                    result.setStudentName(resultSet.getString("Name")); // Lấy tên từ bảng Users
                    result.setQuizName(new QuizDAO().getQuizByID(resultSet.getInt("QuizID")).getQuizName());
                    results.add(result);
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }

        return results;
    }

    public Map<String, Object> getDetailedResultByUserAndQuiz(int userID, int quizID) {
        Map<String, Object> detailedResult = new HashMap<>();
        try {
            // First, get the basic result information
            String resultQuery = "SELECT r.*, u.Name as StudentName, q.QuizName " +
                                 "FROM Results r " +
                                 "JOIN Users u ON r.UserID = u.UserID " +
                                 "JOIN Quizzes q ON r.QuizID = q.QuizID " +
                                 "WHERE r.UserID = ? AND r.QuizID = ?";
            
            try (PreparedStatement pstmt = connection.prepareStatement(resultQuery)) {
                pstmt.setInt(1, userID);
                pstmt.setInt(2, quizID);
                
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        Result result = new Result(
                            rs.getInt("ResultID"),
                            rs.getInt("UserID"),
                            rs.getInt("QuizID"),
                            rs.getFloat("Score"),
                            rs.getDate("CreatedAt"),
                            rs.getString("StudentName"),
                            rs.getString("QuizName")
                        );
                        detailedResult.put("result", result);
                    }
                }
            }

            // If we found a result, get the detailed answer information
            if (detailedResult.containsKey("result")) {
                String answerQuery = "SELECT ua.*, q.QuestionText, a.AnswerText, a.IsCorrect " +
                                     "FROM UserAnswers ua " +
                                     "JOIN Questions q ON ua.QuestionID = q.QuestionID " +
                                     "JOIN Answers a ON ua.AnswerID = a.AnswerID " +
                                     "WHERE ua.ResultID = ?";
                
                try (PreparedStatement pstmt = connection.prepareStatement(answerQuery)) {
                    Result result = (Result) detailedResult.get("result");
                    pstmt.setInt(1, result.getId());
                    
                    try (ResultSet rs = pstmt.executeQuery()) {
                        List<Map<String, Object>> userAnswers = new ArrayList<>();
                        while (rs.next()) {
                            Map<String, Object> answerDetail = new HashMap<>();
                            answerDetail.put("questionID", rs.getInt("QuestionID"));
                            answerDetail.put("questionText", rs.getString("QuestionText"));
                            answerDetail.put("answerID", rs.getInt("AnswerID"));
                            answerDetail.put("answerText", rs.getString("AnswerText"));
                            answerDetail.put("isCorrect", rs.getBoolean("IsCorrect"));
                            userAnswers.add(answerDetail);
                        }
                        detailedResult.put("userAnswers", userAnswers);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Consider logging the error or throwing a custom exception
        }
        return detailedResult;
    }

    public static void main(String[] args) {
        ResultDAO aO = new ResultDAO();
        List<Result> l = aO.getResultsByQuiz(1);
        for (Result r : l) {
            System.out.println(r);
        }
    }
}
