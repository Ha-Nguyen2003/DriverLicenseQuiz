<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="./view/header.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<main class="container py-5">
    <h2>Quizzes</h2>

    <!-- Search form -->
    <form method="get" action="${pageContext.request.contextPath}/quizzes-result">
        <div class="mb-3">
            <label for="search" class="form-label">Search:</label>
            <input type="text" class="form-control" id="search" name="search" value="${param.search}">
        </div>
        <button type="submit" class="btn btn-primary">Search</button>
    </form>

    <!-- Quiz list -->
    <ul class="list-group mt-3">
        <c:forEach var="quiz" items="${quizzes}">
            <li class="list-group-item">
                <a href="${pageContext.request.contextPath}/quiz-result-details?quizID=${quiz.quizID}">
                    ${quiz.quizName}
                </a>
            </li>
        </c:forEach>
    </ul>

    <!-- Pagination -->
    <nav aria-label="Page navigation">
        <ul class="pagination mt-3">
            <c:forEach var="pageNumber" begin="1" end="${totalPages}">
                <li class="page-item ${page eq pageNumber ? 'active' : ''}">
                    <a class="page-link" href="${pageContext.request.contextPath}/student-quiz-list?page=${pageNumber}&search=${param.search}">
                        ${pageNumber}
                    </a>
                </li>
            </c:forEach>
        </ul>
    </nav>
</main>

<%@ include file="./view/footer.jsp"%>
