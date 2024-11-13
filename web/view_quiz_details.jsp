<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="./view/header.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:choose>
    <c:when test="${not empty questions}">
        <main class="container py-5">
            <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#addQuestionModal">
                Add New Question
            </button>
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title">Quiz Information</h5>
                    <p class="card-text">Quiz Name: <strong>${quiz.quizName}</strong> </p>
                </div>
            </div>

            <h3 class="mt-4">Questions and Answers</h3>
            <c:forEach var="question" items="${questions}">
                <div class="card mt-3">
                    <div class="card-body">
                        <button style="float: right" type="button" class="btn btn-primary mt-2" data-bs-toggle="modal" data-bs-target="#editQuestionModal${question.questionID}">
                            Edit Question
                        </button>
                        <p class="card-text">Question Text: <strong>${question.questionText}</strong></p>

                        <h6 class="card-subtitle mb-2 text-muted"><strong>Answers:</strong></h6>
                        <c:forEach var="answer" items="${question.answers}">
                            <p class="card-text">
                                ${answer.answerText} 
                                (Correct: 
                                <c:choose>
                                    <c:when test="${answer.correct}">
                                        <i class="fas fa-check text-success"></i> <!-- Use Font Awesome checkmark icon -->
                                    </c:when>
                                    <c:otherwise>
                                        <i class="fas fa-times text-danger"></i> <!-- Use Font Awesome times icon -->
                                    </c:otherwise>
                                </c:choose>
                                )
                            </p>
                        </c:forEach>
                    </div>
                </div>
            </c:forEach>
        </main>


        <!-- Move this outside the loop to avoid ID conflicts -->
        <c:forEach var="question" items="${questions}">
            <div class="modal fade" id="editQuestionModal${question.questionID}" tabindex="-1" aria-labelledby="editQuestionModalLabel${question.questionID}" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="editQuestionModalLabel${question.questionID}">Edit Question</h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <!-- Edit form for the question -->
                            <form method="post" action="${pageContext.request.contextPath}/edit-question">
                                <input type="hidden" name="questionID" value="${question.questionID}">
                                <input type="hidden" name="quizID" value="${quiz.quizID}">
                                <div class="mb-3">
                                    <label for="editQuestionText${question.questionID}" class="form-label">Question Text:</label>
                                    <textarea class="form-control" id="editQuestionText${question.questionID}" name="questionText">${question.questionText}</textarea>
                                </div>
                                <!-- Loop through answers for this question -->
                                <c:forEach var="answer" items="${question.answers}">
                                    <div class="mb-3">
                                        <label for="editAnswerText${answer.answerID}" class="form-label">Answer Text:</label>
                                        <input type="hidden" class="form-control"  name="answerIDs" value="${answer.answerID}">
                                        <input type="text" class="form-control" id="editAnswerText${answer.answerID}" name="answerTexts" value="${answer.answerText}">
                                        <div class="form-check">
                                            <input class="form-check-input" type="radio" name="correctAnswer" value="${answer.answerID}" <c:if test="${answer.correct}">checked</c:if>>
                                                <label class="form-check-label">Correct Answer</label>
                                            </div>
                                        </div>
                                </c:forEach>
                                <button type="submit" class="btn btn-primary">Save Changes</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>

    </c:when>
    <c:otherwise>
        <!-- Handle the case where there are no questions -->
        <main class="container py-5">
            <p>No questions available for this quiz.</p>
            <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#addQuestionModal">
                Add New Question
            </button>
        </main>
    </c:otherwise>
</c:choose>
<div class="modal fade" id="addQuestionModal" tabindex="-1" aria-labelledby="addQuestionModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="addQuestionModalLabel">Add New Question</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <!-- Add form elements for adding a new question -->
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
                        <label for="answer2" class="form-label">Answer 2:</label>
                        <input type="text" class="form-control" id="answer2" name="answerTexts" required>
                        <div class="form-check">
                            <input class="form-check-input" type="radio" name="correctAnswer" value="2">
                            <label class="form-check-label">Correct Answer</label>
                        </div>
                    </div>
                    <div class="mb-3">
                        <label for="answer3" class="form-label">Answer 3:</label>
                        <input type="text" class="form-control" id="answer3" name="answerTexts" required>
                        <div class="form-check">
                            <input class="form-check-input" type="radio" name="correctAnswer" value="3">
                            <label class="form-check-label">Correct Answer</label>
                        </div>
                    </div>
                    <div class="mb-3">
                        <label for="answer4" class="form-label">Answer 4:</label>
                        <input type="text" class="form-control" id="answer4" name="answerTexts" required>
                        <div class="form-check">
                            <input class="form-check-input" type="radio" name="correctAnswer" value="4">
                            <label class="form-check-label">Correct Answer</label>
                        </div>
                    </div>
                    <button type="submit" class="btn btn-primary">Add Question</button>
                </form>
            </div>
        </div>
    </div>
</div>
<%@ include file="./view/footer.jsp"%>
