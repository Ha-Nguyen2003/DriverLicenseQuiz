<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="./view/header.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<main class="container py-5">
    <div class="card">
        <div class="card-body">
            <h5 class="card-title">Quiz Information</h5>
            <p class="card-text">Quiz Name: <strong>${quiz.quizName}</strong> </p>
        </div>
    </div>

    <h3 class="mt-4">Add New Question</h3>
    <div class="card mt-3">
        <div class="card-body">
            <form method="post" action="${pageContext.request.contextPath}/add-question">
                <input type="hidden" name="quizID" value="${quiz.quizID}">
                <div class="mb-3">
                    <label for="questionText" class="form-label">Question Text:</label>
                    <textarea class="form-control" id="questionText" name="questionText" required></textarea>
                </div>

                <h6 class="card-subtitle mb-2 text-muted"><strong>Answers:</strong></h6>
                <div class="mb-3">
                    <label for="answer1" class="form-label">Answer 1:</label>
                    <input type="text" class="form-control" id="answer1" name="answerTexts" required>
                    <div class="form-check">
                        <input class="form-check-input" type="radio" name="correctAnswer" value="1" checked>
                        <label class="form-check-label">Correct Answer</label>
                    </div>
                </div>
                <div class="mb-3">
                    <label for="answer1" class="form-label">Answer 2:</label>
                    <input type="text" class="form-control" id="answer2" name="answerTexts" required>
                    <div class="form-check">
                        <input class="form-check-input" type="radio" name="correctAnswer" value="1" checked>
                        <label class="form-check-label">Correct Answer</label>
                    </div>
                </div>
                <div class="mb-3">
                    <label for="answer1" class="form-label">Answer 3:</label>
                    <input type="text" class="form-control" id="answer3" name="answerTexts" required>
                    <div class="form-check">
                        <input class="form-check-input" type="radio" name="correctAnswer" value="1" checked>
                        <label class="form-check-label">Correct Answer</label>
                    </div>
                </div>
                <div class="mb-3">
                    <label for="answer1" class="form-label">Answer 4:</label>
                    <input type="text" class="form-control" id="answer4" name="answerTexts" required>
                    <div class="form-check">
                        <input class="form-check-input" type="radio" name="correctAnswer" value="1" checked>
                        <label class="form-check-label">Correct Answer</label>
                    </div>
                </div>

               

                <button type="submit" class="btn btn-primary">Add Question</button>
            </form>
        </div>
    </div>
</main>

<%@ include file="./view/footer.jsp"%>
