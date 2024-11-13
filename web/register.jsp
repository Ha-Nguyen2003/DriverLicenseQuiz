<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>Driver License Quiz</title>

        <!-- Bootstrap CSS -->
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    </head>

    <body class="bg-light">
        <section class="vh-100">
            <div class="container py-5 h-100">
                <div class="row d-flex align-items-center justify-content-center h-100">
                    <div class="col-md-8 col-lg-7 col-xl-6">
                        <img src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-login-form/draw2.svg"
                             class="img-fluid" alt="Phone image">
                    </div>
                    <div class="col-md-7 col-lg-5 col-xl-5 offset-xl-1">
                       
                        <form action="register" method="post">
                            <!-- Email input -->
                            <div class="form-outline mb-4">
                                <label class="form-label" >Name</label>
                                <input type="text" name="name" class="form-control form-control-lg" />
                            </div>
                            <div class="form-outline mb-4">
                                <label class="form-label" >Email address</label>
                                <input type="email" name="email" class="form-control form-control-lg" />

                            </div>
                            <!-- Password input -->
                            <div class="form-outline mb-4">
                                <label class="form-label" for="form1Example23">Password</label>
                                <input type="password"  name="password" class="form-control form-control-lg" />
                            </div>
                            <div class="form-outline mb-4">
                                <label class="form-label" for="form1Example23">Password</label>
                                <input type="password"  name="re-password" class="form-control form-control-lg" />
                            </div>
                            <!-- Display error message if login fails -->
                            <c:if test="${not empty requestScope.error}">
                                <div class="alert alert-danger" role="alert">
                                    ${error}
                                </div>
                            </c:if>


                            <!-- Submit button -->


                            <div class="d-flex justify-content-around align-items-center mb-4">
                                <button type="submit" class="btn btn-primary btn-lg btn-block">Sign in</button>

                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </section>

        <!-- Bootstrap JS and dependencies -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
    </body>

</html>
