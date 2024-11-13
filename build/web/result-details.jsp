<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="./view/header.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<main class="container py-5">
    <h2>Quiz Results for <span style="color: red">${quizName}</span></h2>

    <!-- Search and Sort Form -->
    <form action="quiz-result-details" method="GET" class="mb-4">
        <input type="hidden" name="quizID" value="${param.quizID}">
        <div class="row g-3 align-items-center">
            <div class="col-auto">
                <input type="text" class="form-control" id="search" name="search" placeholder="Search by student name" value="${param.search}">
            </div>
            <div class="col-auto">
                <select class="form-select" id="sort" name="sort">
                    <option value="">Sort by</option>
                    <option value="score_asc" ${param.sort == 'score_asc' ? 'selected' : ''}>Score (Low to High)</option>
                    <option value="score_desc" ${param.sort == 'score_desc' ? 'selected' : ''}>Score (High to Low)</option>
                    <option value="time_asc" ${param.sort == 'time_asc' ? 'selected' : ''}>Time (Oldest First)</option>
                    <option value="time_desc" ${param.sort == 'time_desc' ? 'selected' : ''}>Time (Newest First)</option>
                </select>
            </div>
            <div class="col-auto">
                <button type="submit" class="btn btn-primary">Search</button>
            </div>
        </div>
    </form>

    <table class="table mt-4">
        <thead>
            <tr>
                <th scope="col">Student</th>
                <th scope="col">Score</th>
                <th scope="col">Time</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="result" items="${result}">
                <tr>
                    <td>${result.studentName}</td>
                    <td>${result.score}</td>
                    <td>${result.date}</td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</main>

<%@ include file="./view/footer.jsp"%>