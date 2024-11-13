<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="./view/header.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<main class="container py-5">
    <h2>Quiz Result</h2>

    <div class="card mt-4">
        <div class="card-body">
            <h5 class="card-title">Quiz Information</h5>
            <p class="card-text">Quiz Name: <strong>${quiz.quizName}</strong></p>
            <p class="card-text">Created By: <strong>${quiz.teacherName}</strong></p>
        </div>
    </div>

    <h3 class="mt-4">Your Result</h3>
    <p class="card-text">Attempted date: <strong>${result.date}</strong></p>
    <p class="card-text">Score: <strong>${result.score}/100</strong></p>

    <h4 class="mt-4">Detailed Review</h4>
    <c:forEach var="question" items="${quiz.questions}" varStatus="questionStatus">
        <div class="card mb-3">
            <div class="card-header">
                Question ${questionStatus.index + 1}: ${question.questionText}
            </div>
            <div class="card-body">
                <c:forEach var="answer" items="${question.answers}">
                    <div class="form-check">
                        <input class="form-check-input" type="radio" disabled
                               ${userAnswers[questionStatus.index].answerID == answer.answerID ? 'checked' : ''}>
                        <label class="form-check-label 
                               ${answer.correct ? 'text-success' : ''} 
                               ${userAnswers[questionStatus.index].answerID == answer.answerID && !answer.correct ? 'text-danger' : ''}">
                            ${answer.answerText}
                            <c:if test="${userAnswers[questionStatus.index].answerID == answer.answerID && answer.correct}">
                                <i class="fas fa-check text-success"></i>
                            </c:if>
                            <c:if test="${userAnswers[questionStatus.index].answerID == answer.answerID && !answer.correct}">
                                <i class="fas fa-times text-danger"></i>
                            </c:if>
                            <c:if test="${userAnswers[questionStatus.index].answerID != answer.answerID && answer.correct}">
                                <i class="fas fa-check text-success"></i> (Correct Answer)
                            </c:if>
                        </label>
                    </div>
                </c:forEach>
            </div>
        </div>
    </c:forEach>

    <a href="student-quiz-list" class="btn btn-primary btn-lg btn-block mt-4">Back to Home</a>
</main>

<%@ include file="./view/footer.jsp"%>