<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title> Driver License Quiz</title>
        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
        <style>
            body {
                min-height: 100vh;
                display: flex;
                flex-direction: column;
            }

            main {
                flex: 1;
            }
        </style>
    </head>

    <body class="bg-light d-flex flex-column">
        <header>
            <!-- Navigation bar for teacher -->
            <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
                <div class="container-fluid">
                    <c:if test="${account.role eq 'student'}">
                        <a class="navbar-brand" href="student-quiz-list">Driver License Quiz</a>
                    </c:if>
                    <c:if test="${account.role eq 'teacher'}">
                        <a class="navbar-brand" href="home.jsp">Driver License Quiz</a>
                    </c:if>
                    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav"
                            aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                        <span class="navbar-toggler-icon"></span>
                    </button>

                    <div class="collapse navbar-collapse justify-content-end" id="navbarNav">
                        <ul class="navbar-nav">
                            <c:if test="${account.role eq 'student'}">
                                <li class="nav-item">
                                    <a class="nav-link" href="student-quiz-list">Home</a>
                                </li>

                            </c:if>
                            <c:if test="${account.role eq 'teacher'}">
                                <li class="nav-item">
                                    <a class="nav-link" href="home.jsp">Home</a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link" href="quiz-management">Quiz Management</a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link" href="quizzes-result">Results</a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link" href="manage-account">Manage Account</a>
                                </li>
                            </c:if>
                            <li class="nav-item">
                                <a class="nav-link" href="profile">Profile</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="logout">Logout</a>
                            </li>
                        </ul>
                    </div>
                </div>
            </nav>
        </header>
