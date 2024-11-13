<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="./view/header.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<main class="container py-5">
    <h2>Take Quiz</h2>

    <form method="post" action="${pageContext.request.contextPath}/take-quiz">
        <input type="hidden" name="quizID" value="${quiz.quizID}">
        
        <c:forEach var="question" items="${quiz.questions}">
            <div class="card mt-3">
                <div class="card-body">
                    <p class="card-text">Question: <strong>${question.questionText}</strong></p>
                    
                    <h6 class="card-subtitle mb-2 text-muted"><strong>Answers:</strong></h6>
                    <c:forEach var="answer" items="${question.answers}">
                        <div class="mb-3">
                            <input type="radio" name="question_${question.questionID}" value="${answer.answerID}" required>
                            ${answer.answerText}
                        </div>
                    </c:forEach>
                </div>
            </div>
        </c:forEach>

        <button type="submit" class="btn btn-primary mt-3">Submit Quiz</button>
    </form>
</main>

<%@ include file="./view/footer.jsp"%>
