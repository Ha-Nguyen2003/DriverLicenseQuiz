package dao;

import model.User;
import model.Quiz;
import model.Question;
import model.Answer;
import model.Result;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuizDAO extends DBContext {

    // Get quizzes by teacher ID
    public List<Quiz> getQuizzesByTeacherID(int teacherID) {
        List<Quiz> quizzes = new ArrayList<>();
        String sql = "SELECT * FROM Quizzes WHERE TeacherID = ?";
        try ( PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, teacherID);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Quiz quiz = new Quiz();
                quiz.setQuizID(resultSet.getInt("QuizID"));
                quiz.setTeacherID(resultSet.getInt("TeacherID"));
                quiz.setQuizName(resultSet.getString("QuizName"));
                quiz.setStatus(resultSet.getInt("status"));
                quizzes.add(quiz);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return quizzes;
    }

    // Get questions by quiz ID
    public List<Question> getQuestionsByQuizID(int quizID) {
        List<Question> questions = new ArrayList<>();
        String sql = "SELECT * FROM Questions WHERE QuizID = ?";
        try ( PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, quizID);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Question question = new Question();
                question.setQuestionID(resultSet.getInt("QuestionID"));
                question.setQuizID(resultSet.getInt("QuizID"));
                question.setQuestionText(resultSet.getString("QuestionText"));
                questions.add(question);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return questions;
    }

    // Get answers by question ID
    public List<Answer> getAnswersByQuestionID(int questionID) {
        List<Answer> answers = new ArrayList<>();
        String sql = "SELECT * FROM Answers WHERE QuestionID = ?";
        try ( PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, questionID);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Answer answer = new Answer();
                answer.setAnswerID(resultSet.getInt("AnswerID"));
                answer.setQuestionID(resultSet.getInt("QuestionID"));
                answer.setAnswerText(resultSet.getString("AnswerText"));
                answer.setCorrect(resultSet.getBoolean("IsCorrect"));
                answers.add(answer);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return answers;
    }

    // Get results by user ID
    public List<Result> getResultsByUserID(int userID) {
        List<Result> results = new ArrayList<>();
        String sql = "SELECT * FROM Results WHERE UserID = ?";

        try ( PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userID);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Result result = new Result();
                result.setId(resultSet.getInt("ResultID"));
                result.setUserId(resultSet.getInt("UserID"));
                result.setQuizId(resultSet.getInt("QuizID"));
                result.setScore(resultSet.getInt("Score"));
                results.add(result);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return results;
    }

    public Quiz getQuizByID(int quizID) {
        UserDAO userDAO = new UserDAO();
        String query = "SELECT * FROM Quizzes WHERE QuizID = ?";
        try ( PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, quizID);
            try ( ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Quiz quiz = new Quiz();
                    quiz.setQuizID(resultSet.getInt("QuizID"));
                    quiz.setTeacherID(resultSet.getInt("TeacherID"));
                    quiz.setQuizName(resultSet.getString("QuizName"));
                    quiz.setTeacherName(userDAO.getUserByID(resultSet.getInt("TeacherID")).getName());
                    return quiz;
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }

        return null; // Return null if no quiz is found
    }

    public Question getQuesionByID(int questionID) {
        String query = "SELECT * FROM Questions WHERE QuestionID = ?";
        try ( PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, questionID);

            try ( ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Question question = new Question();
                    question.setQuestionID(resultSet.getInt("QuestionID"));
                    question.setQuizID(resultSet.getInt("QuizID"));
                    question.setQuestionText(resultSet.getString("QuestionText"));

                    return question;
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }

        return null; // Return null if no quiz is found
    }

    public static void main(String[] args) {
        QuizDAO aO = new QuizDAO();
        List<Quiz> list = aO.getQuizzesByTeacher("E",1,1);
        for (Quiz quiz : list) {
            System.out.println(quiz);
        }
    }

    public List<Question> getQuestionsByQuizIDWithAnswers(int quizID) {
        List<Question> questions = new ArrayList<>();

        String query = "SELECT q.QuestionID, q.QuestionText, a.AnswerID, a.AnswerText, a.IsCorrect "
                + "FROM Questions q "
                + "JOIN Answers a ON q.QuestionID = a.QuestionID "
                + "WHERE q.QuizID = ?";

        try ( PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, quizID);

            try ( ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int questionID = resultSet.getInt("QuestionID");
                    String questionText = resultSet.getString("QuestionText");

                    Question question = findQuestionById(questions, questionID);
                    if (question == null) {
                        question = new Question(questionID, questionText);
                        questions.add(question);
                    }

                    int answerID = resultSet.getInt("AnswerID");
                    String answerText = resultSet.getString("AnswerText");
                    boolean isCorrect = resultSet.getBoolean("IsCorrect");

                    Answer answer = new Answer(answerID, answerText, isCorrect);
                    question.addAnswer(answer);
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }

        return questions;
    }

    private Question findQuestionById(List<Question> questions, int questionID) {
        for (Question question : questions) {
            if (question.getQuestionID() == questionID) {
                return question;
            }
        }
        return null;
    }

    public void editQuiz(int quizID, String quizName) {
        try {
            // Update quiz in the database
            String sql = "UPDATE Quizzes SET QuizName = ? WHERE QuizID = ?";
            try ( PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, quizName);
                ps.setInt(2, quizID);
                ps.executeUpdate();
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public void addQuiz(int teacherID, String quizName) {

        PreparedStatement stmt = null;

        try {

            String query = "INSERT INTO quizzes (TeacherID, QuizName) VALUES (?, ?)";
            stmt = connection.prepareStatement(query);
            stmt.setInt(1, teacherID);
            stmt.setString(2, quizName);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void toggleQuizStatus(int quizID, int newStatus) {
        PreparedStatement pstmt = null;

        try {
            String sql = "UPDATE quizzes SET status = ? WHERE quizID = ?";
            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, newStatus);
            pstmt.setInt(2, quizID);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void editQuestionText(int questionID, String questionText) {
        try {
            // Update question text in the database
            String sql = "UPDATE Questions SET QuestionText = ? WHERE QuestionID = ?";
            try ( PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, questionText);
                ps.setInt(2, questionID);
                ps.executeUpdate();
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public void updateCorrectAnswer(int questionID, int correctAnswerID) {
        try {
            // Update correct answer in the database
            String sql = "UPDATE Answers SET IsCorrect = CASE WHEN AnswerID = ? THEN 1 ELSE 0 END WHERE QuestionID = ?";
            try ( PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1, correctAnswerID);
                ps.setInt(2, questionID);
                ps.executeUpdate();
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public void editAnswerText(int answerID, String answerText) {
        try {
            String sql = "UPDATE Answers SET AnswerText = ? WHERE AnswerID = ?";
            try ( PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, answerText);
                ps.setInt(2, answerID);
                ps.executeUpdate();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void addQuestion(Question question) {
        try {
            // Insert the question into the database
            String insertQuestionSQL = "INSERT INTO Questions (QuizID, QuestionText) VALUES (?, ?)";
            try ( PreparedStatement questionStatement = connection.prepareStatement(insertQuestionSQL, Statement.RETURN_GENERATED_KEYS)) {
                questionStatement.setInt(1, question.getQuizID());
                questionStatement.setString(2, question.getQuestionText());
                questionStatement.executeUpdate();

                // Retrieve the generated question ID
                ResultSet questionGeneratedKeys = questionStatement.getGeneratedKeys();
                int questionID = -1;
                if (questionGeneratedKeys.next()) {
                    questionID = questionGeneratedKeys.getInt(1);
                }

                // Insert answers into the database with the generated question ID
                String insertAnswerSQL = "INSERT INTO Answers (QuestionID, AnswerText, IsCorrect) VALUES (?, ?, ?)";
                try ( PreparedStatement answerStatement = connection.prepareStatement(insertAnswerSQL)) {
                    for (Answer answer : question.getAnswers()) {
                        answerStatement.setInt(1, questionID);
                        answerStatement.setString(2, answer.getAnswerText());
                        answerStatement.setBoolean(3, answer.isCorrect());
                        answerStatement.addBatch();
                    }
                    answerStatement.executeBatch();
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private static final int PAGE_SIZE = 10; // Adjust the page size as needed

    public List<Quiz> getQuizzes(String search, int page) {
        int offset = (page - 1) * PAGE_SIZE;
        String query = "SELECT * FROM (SELECT *, ROW_NUMBER() OVER (ORDER BY quizID) AS RowNum FROM Quizzes WHERE quizName LIKE ? and Status  =1 ) AS QuizWithRowNum "
                + "WHERE RowNum BETWEEN ? AND ?";

        try ( PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, "%" + search + "%");
            preparedStatement.setInt(2, offset + 1);  // Start RowNum
            preparedStatement.setInt(3, offset + PAGE_SIZE);  // End RowNum
            UserDAO userDAO = new UserDAO();

            ResultSet resultSet = preparedStatement.executeQuery();
            List<Quiz> quizzes = new ArrayList<>();
            while (resultSet.next()) {
                Quiz quiz = new Quiz();
                quiz.setQuizID(resultSet.getInt("quizID"));
                quiz.setQuizName(resultSet.getString("quizName"));
                quiz.setTeacherName(userDAO.getUserByID(resultSet.getInt("TeacherID")).getName());
                quizzes.add(quiz);
            }
            return quizzes;
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
    
      public List<Quiz> getQuizzesByTeacher(String search, int page, int teacherID) {
        int offset = (page - 1) * PAGE_SIZE;
        String query = "SELECT * FROM (SELECT *, ROW_NUMBER() OVER (ORDER BY quizID) AS RowNum FROM Quizzes WHERE quizName LIKE ? and Status  = 1 and TeacherID =? ) AS QuizWithRowNum "
                + "WHERE RowNum BETWEEN ? AND ?";

        try ( PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, "%" + search + "%");
            preparedStatement.setInt(2, teacherID);  
            preparedStatement.setInt(3, offset + 1);  // Start RowNum
            preparedStatement.setInt(4, offset + PAGE_SIZE);  // End RowNum
            UserDAO userDAO = new UserDAO();

            ResultSet resultSet = preparedStatement.executeQuery();
            List<Quiz> quizzes = new ArrayList<>();
            while (resultSet.next()) {
                Quiz quiz = new Quiz();
                quiz.setQuizID(resultSet.getInt("quizID"));
                quiz.setQuizName(resultSet.getString("quizName"));
                quiz.setTeacherName(userDAO.getUserByID(resultSet.getInt("TeacherID")).getName());
                quizzes.add(quiz);
            }
            return quizzes;
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public int calculateTotalPages(String search) {
        // Implement logic to calculate total pages based on search
        // Use PreparedStatement to prevent SQL injection
        String query = "SELECT COUNT(*) AS total FROM Quizzes WHERE quizName LIKE ?";
        try ( PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, "%" + search + "%");

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                int totalQuizzes = resultSet.getInt("total");
                return (int) Math.ceil((double) totalQuizzes / PAGE_SIZE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Quiz getQuizDetails(int quizID) {
        Quiz quiz = new Quiz();
        try {
            // Get quiz information
            String quizQuery = "SELECT * FROM Quizzes WHERE quizID = ?";
            try ( PreparedStatement quizStatement = connection.prepareStatement(quizQuery)) {
                quizStatement.setInt(1, quizID);
                ResultSet quizResultSet = quizStatement.executeQuery();

                if (quizResultSet.next()) {
                    quiz.setQuizID(quizResultSet.getInt("quizID"));
                    quiz.setQuizName(quizResultSet.getString("quizName"));
                    // Set other quiz properties as needed
                }
            }

            // Get questions and answers for the quiz
            String questionQuery = "SELECT * FROM Questions WHERE quizID = ?";
            try ( PreparedStatement questionStatement = connection.prepareStatement(questionQuery)) {
                questionStatement.setInt(1, quizID);
                ResultSet questionResultSet = questionStatement.executeQuery();

                while (questionResultSet.next()) {
                    Question question = new Question();
                    question.setQuestionID(questionResultSet.getInt("questionID"));
                    question.setQuestionText(questionResultSet.getString("questionText"));

                    // Get answers for the question
                    String answerQuery = "SELECT * FROM Answers WHERE questionID = ?";
                    try ( PreparedStatement answerStatement = connection.prepareStatement(answerQuery)) {
                        answerStatement.setInt(1, question.getQuestionID());
                        ResultSet answerResultSet = answerStatement.executeQuery();

                        List<Answer> answers = new ArrayList<>();
                        while (answerResultSet.next()) {
                            Answer answer = new Answer();
                            answer.setAnswerID(answerResultSet.getInt("answerID"));
                            answer.setAnswerText(answerResultSet.getString("answerText"));
                            answer.setCorrect(answerResultSet.getBoolean("isCorrect"));
                            answers.add(answer);
                        }

                        question.setAnswers(answers);
                    }

                    quiz.addQuestion(question);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return quiz;
    }

}
