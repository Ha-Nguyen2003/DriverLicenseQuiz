<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Account Management</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" 
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
    <style>
        .switch {
            position: relative;
            display: inline-block;
            width: 60px;
            height: 34px;
        }
        .switch input {
            opacity: 0;
            width: 0;
            height: 0;
        }
        .slider {
            position: absolute;
            cursor: pointer;
            top: 0;
            left: 0;
            right: 0;
            bottom: 0;
            background-color: #ccc;
            transition: .4s;
            border-radius: 34px;
        }
        .slider:before {
            position: absolute;
            content: "";
            height: 26px;
            width: 26px;
            left: 4px;
            bottom: 4px;
            background-color: white;
            transition: .4s;
            border-radius: 50%;
        }
        input:checked + .slider {
            background-color: #2196F3;
        }
        input:checked + .slider:before {
            transform: translateX(26px);
        }
    </style>
</head>
<body>

<%@ include file="./view/header.jsp"%>

<main class="container py-5">
    <h1 class="mb-4">Account Management</h1>
    <form action="manage-account" method="get" class="mb-4">
        <div class="input-group">
            <input type="text" class="form-control" name="searchTerm" placeholder="Search by name or email" value="${param.searchTerm}">
            <button class="btn btn-primary" type="submit">
                <i class="fas fa-search"></i> Search
            </button>
        </div>
    </form>
    <table class="table table-striped table-hover">
        <thead class="table-dark">
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Email</th>
                <th>Role</th>
                <th>Status</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${users}" var="user">
                <tr>
                    <td>${user.id}</td>
                    <td>${user.name}</td>
                    <td>${user.email}</td>
                    <td>${user.role}</td>
                    <td>
                        <label class="switch">
                            <input type="checkbox" class="status-toggle" data-id="${user.id}" ${user.status ? 'checked' : ''}>
                            <span class="slider"></span>
                        </label>
                    </td>
                    <td>
                        <button class="btn btn-sm btn-primary edit-user" data-id="${user.id}">
                            <i class="fas fa-edit"></i> Edit
                        </button>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
    
    <a href="add-user" class="btn btn-success mt-3">
        <i class="fas fa-plus"></i> Add New User
    </a>
</main>

<!-- Edit User Modal -->
<div class="modal fade" id="editUserModal" tabindex="-1" aria-labelledby="editUserModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="editUserModalLabel">Edit User</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form id="editUserForm">
                    <input type="hidden" id="editUserId" name="id">
                    <div class="mb-3">
                        <label for="editName" class="form-label">Name</label>
                        <input type="text" class="form-control" id="editName" name="name" required>
                    </div>
                    <div class="mb-3">
                        <label for="editEmail" class="form-label">Email</label>
                        <input type="email" class="form-control" id="editEmail" name="email" required>
                    </div>
                    <div class="mb-3">
                        <label for="editRole" class="form-label">Role</label>
                        <select class="form-select" id="editRole" name="role" required>
                            <option value="student">Student</option>
                            <option value="teacher">Teacher</option>
                        </select>
                    </div>
                    <div class="mb-3 form-check">
                        <input type="checkbox" class="form-check-input" id="editStatus" name="status">
                        <label class="form-check-label" for="editStatus">Active</label>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary" id="saveUserChanges">Save changes</button>
            </div>
        </div>
    </div>
</div>

<%@ include file="./view/footer.jsp"%>

<!-- Bootstrap Bundle with Popper -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" 
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
<!-- jQuery -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js" 
        integrity="sha384-vtXRMe3mGCbOeY7l30aIg8H9p3GdeSe4IFlP6G8JMa7o7lXvnz3GFKzPxzJdPfGK" crossorigin="anonymous"></script>

<script>
$(document).ready(function() {
    // Handle edit button click
    $('.edit-user').click(function() {
        var userId = $(this).data('id');
        // Fetch user data using AJAX
        $.ajax({
            url: 'manage-account?action=get&id=' + userId,
            method: 'GET',
            success: function(response) {
                // Populate modal with user data
                $('#editUserId').val(response.id);
                $('#editName').val(response.name);
                $('#editEmail').val(response.email);
                $('#editRole').val(response.role);
                $('#editStatus').prop('checked', response.status);
                // Show modal
                $('#editUserModal').modal('show');
            },
            error: function(xhr, status, error) {
                alert('Error fetching user data');
            }
        });
    });

    // Handle save changes button click
    $('#saveUserChanges').click(function() {
        var userData = {
            id: $('#editUserId').val(),
            name: $('#editName').val(),
            email: $('#editEmail').val(),
            role: $('#editRole').val(),
            status: $('#editStatus').is(':checked')
        };

        // Send update request using AJAX
        $.ajax({
            url: 'manage-account?action=update',
            method: 'POST',
            data: userData,
            success: function(response) {
                if (response.success) {
                    // Close modal
                    $('#editUserModal').modal('hide');
                    // Refresh page to show updated data
                    location.reload();
                } else {
                    alert('Failed to update user data');
                }
            },
            error: function(xhr, status, error) {
                alert('Error updating user data');
            }
        });
    });

    // Handle status toggle
    $('.status-toggle').change(function() {
        var userId = $(this).data('id');
        var newStatus = this.checked;
        
        $.ajax({
            url: 'manage-account?action=update',
            method: 'POST',
            data: {
                id: userId,
                status: newStatus
            },
            success: function(response) {
                if (!response.success) {
                    alert('Failed to update status');
                    // Revert the toggle if update failed
                    $(this).prop('checked', !newStatus);
                }
            },
            error: function(xhr, status, error) {
                alert('Error updating user status');
                // Revert the toggle on error
                $(this).prop('checked', !newStatus);
            }
        });
    });

    
});
</script>

</body>
</html>