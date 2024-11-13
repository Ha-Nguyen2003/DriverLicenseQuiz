<%-- 
    Document   : quiz_management
    Created on : Feb 28, 2024, 7:43:52 PM
    Author     : ADMIN
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ include file="./view/header.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<main class="container py-5">
    <h2>Quiz Management</h2>
    <button type="button" class="btn btn-success" data-bs-toggle="modal" data-bs-target="#addQuizModal">
        Add New Quiz
    </button>

    <table class="table">
        <thead>
            <tr>
                <th>Quiz ID</th>
                <th>Quiz Name</th>
                <th>Status</th>
                <th>Action</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="quiz" items="${quizzes}">
                <tr>

                    <td>${quiz.quizID}</td>
                    <td> <a href="view-quiz-details?quizID=${quiz.quizID}">
                            ${quiz.quizName}
                        </a>
                    </td>
                    <td>
                        <c:choose>
                            <c:when test="${quiz.status == 1}">
                                <span style="color: green;">Active</span>
                            </c:when>
                            <c:otherwise>
                                <span style="color: red;">Inactive</span>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#editQuizModal"
                                data-quiz-id="${quiz.quizID}" data-quiz-name="${quiz.quizName}">
                            Edit
                        </button>
                        <button type="button" class="btn btn-primary status-button"
                                onclick="toggleStatus(${quiz.quizID}, ${quiz.status})"
                                style="${quiz.status == 1 ? 'background-color: green; color: white;' : 'background-color: red; color: white;'}">
                            ${quiz.status == 1 ? 'Deactivate' : 'Activate'}
                        </button>
                    </td>

                </tr>
            </c:forEach>
        </tbody>
    </table>
    <div class="modal fade" id="editQuizModal" tabindex="-1" aria-labelledby="editQuizModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="editQuizModalLabel">Edit Quiz</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <!-- Add form elements for editing quiz -->
                    <form method="post" action="${pageContext.request.contextPath}/quiz-management">
                        <input type="hidden" name="action" value="editQuiz">
                        <input type="hidden" name="quizID" id="editQuizID">
                        <div class="mb-3">
                            <label for="editQuizName" class="form-label">Quiz Name:</label>
                            <input type="text" class="form-control" id="editQuizName" name="quizName">
                        </div>
                        <button type="submit" class="btn btn-primary">Save Changes</button>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <!-- Modal for adding a new quiz -->
    <div class="modal fade" id="addQuizModal" tabindex="-1" aria-labelledby="addQuizModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="addQuizModalLabel">Add New Quiz</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <!-- Add form elements for adding a new quiz -->
                    <form method="post" action="${pageContext.request.contextPath}/quiz-management">
                        <input type="hidden" name="action" value="addQuiz">
                        <div class="mb-3">
                            <label for="newQuizName" class="form-label">Quiz Name:</label>
                            <input type="text" class="form-control" id="newQuizName" name="quizName">
                        </div>
                        <button type="submit" class="btn btn-success">Add Quiz</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</main>
<!-- Include jQuery -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

<!-- Include Bootstrap JS and Popper.js -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>

<!-- Your existing scripts -->
<script>
            // JavaScript to handle modal data
            $('#editQuizModal').on('show.bs.modal', function (event) {
                var button = $(event.relatedTarget);
                var quizID = button.data('quiz-id');
                var quizName = button.data('quiz-name');
                var modal = $(this);
                modal.find('#editQuizID').val(quizID);
                modal.find('#editQuizName').val(quizName);
            });
            function toggleStatus(quizID, currentStatus) {
                // Confirm status change using a JavaScript confirm dialog
                var confirmationMessage = "Are you sure you want to " + (currentStatus == 1 ? "deactivate" : "activate") + " this quiz?";
                var confirmed = confirm(confirmationMessage);

                if (confirmed) {
                    // User confirmed, update the status
                    $.ajax({
                        type: 'POST',
                        url: "${pageContext.request.contextPath}/quiz-management",
                        data: {
                            action: "toggleStatus",
                            quizID: quizID,
                            currentStatus: currentStatus,
                            confirmed: true
                        },
                        success: function (data) {
                            // Handle the response from the server
                            console.log(data);

                            // Update the button text immediately
                            var buttonText = (currentStatus == 1 ? 'Activate' : 'Deactivate');
                            $('.status-button[data-quiz-id=' + quizID + ']').text(buttonText);

                            // Reload the page after a short delay (e.g., 1000 milliseconds)
                            setTimeout(function () {
                                location.reload();
                            }, 200);
                        },
                        error: function (error) {
                            console.error('Error updating status:', error);
                        }
                    });
                }
            }

</script>



<%@ include file="./view/footer.jsp"%>
