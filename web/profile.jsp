<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="./view/header.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<main class="container py-5">
    <h2>User Profile</h2>
    
    <c:if test="${not empty message}">
        <div class="alert alert-success" role="alert">
            ${message}
        </div>
    </c:if>
    
    <c:if test="${not empty error}">
        <div class="alert alert-danger" role="alert">
            ${error}
        </div>
    </c:if>
    
    <div class="row">
        <div class="col-md-6">
            <h3>Update Profile</h3>
            <form action="profile" method="post">
                <input type="hidden" name="action" value="updateProfile">
                <div class="mb-3">
                    <label for="name" class="form-label">Name</label>
                    <input type="text" class="form-control" id="name" name="name" value="${user.name}" required>
                </div>
                <div class="mb-3">
                    <label for="email" class="form-label">Email</label>
                    <input type="email" class="form-control" id="email" name="email" value="${user.email}" required>
                </div>
                <div class="mb-3">
                    <label for="role" class="form-label">Role</label>
                    <input type="text" class="form-control" id="role" value="${user.role}" readonly>
                </div>
                <button type="submit" class="btn btn-primary">Update Profile</button>
            </form>
        </div>
        
        <div class="col-md-6">
            <h3>Change Password</h3>
            <form action="profile" method="post">
                <input type="hidden" name="action" value="changePassword">
                <c:if test="${not empty passwordMessage}">
                    <div class="alert alert-success" role="alert">
                        ${passwordMessage}
                    </div>
                </c:if>
                <c:if test="${not empty passwordError}">
                    <div class="alert alert-danger" role="alert">
                        ${passwordError}
                    </div>
                </c:if>
                <div class="mb-3">
                    <label for="currentPassword" class="form-label">Current Password</label>
                    <input type="password" class="form-control" id="currentPassword" name="currentPassword" required>
                </div>
                <div class="mb-3">
                    <label for="newPassword" class="form-label">New Password</label>
                    <input type="password" class="form-control" id="newPassword" name="newPassword" required>
                </div>
                <div class="mb-3">
                    <label for="confirmPassword" class="form-label">Confirm New Password</label>
                    <input type="password" class="form-control" id="confirmPassword" name="confirmPassword" required>
                </div>
                <button type="submit" class="btn btn-primary">Change Password</button>
            </form>
        </div>
    </div>
</main>

<%@ include file="./view/footer.jsp"%>