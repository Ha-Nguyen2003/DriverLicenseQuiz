<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="./view/header.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<main class="container py-5">
    <h2>Quiz Result</h2>

    <div class="card mt-4">
        <div class="card-body">
            <h5 class="card-title">Quiz Information</h5>
            <p class="card-text">Quiz Name: <strong>${quiz.quizName}</strong></p>
            <!-- Add other quiz details as needed -->
        </div>
    </div>

    <h3 class="mt-4">Your Result</h3>

    <div class="card mt-3">
        <div class="card-body">
            <h5 class="card-title">Score: ${score}%</h5>
            <!-- Display other result details, feedback, etc. -->

            <h6 class="card-subtitle mb-2 text-muted">Questions and Answers</h6>
            <c:forEach var="question" items="${quiz.questions}">
                <div class="card mt-3">
                    <div class="card-body">
                        <p class="card-text">Question: ${question.questionText}</p>
                        <ul class="list-group">
                            <c:forEach var="answer" items="${question.answers}">
                                <li class="list-group-item ${answer.selected ? 'list-group-item-info' : ''} ${answer.correct ? 'list-group-item-success' : 'list-group-item-danger'}">
                                    ${answer.answerText}
                                    <c:if test="${answer.correct}">
                                        <span class="badge bg-success">Correct</span>
                                    </c:if>
                                    <c:if test="${answer.selected}">
                                        <span class="badge bg-info">Your Choice</span>
                                    </c:if>
                                </li>
                            </c:forEach>
                        </ul>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
</main>

<%@ include file="./view/footer.jsp"%>
