<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:import url="/WEB-INF/views/header.jsp">
    <c:param name="title" value="User Registration"/>
</c:import>
<div class="main_contents">
    <div id="sub_content">
        <div id="messagesContainer">
            <div style="color: red">${bsError}</div>
            <div style="color: red">${error}</div>
            <div style="color: red">${existError}</div>
        </div>

        <form:form action="/userReg" method="post" modelAttribute="user">

            <h2 class="col-md-6 offset-md-2 mb-5 mt-4">User Registration</h2>

            <div class="row mb-4">
                <div class="col-md-2"></div>
                <label for="role" class="col-md-2 col-form-label">User Id</label>
                <div class="col-md-4">
                    <form:input type="text" class="form-control" id="id" path="id" readonly="true"/>
                </div>
            </div>

            <div class="row mb-4">
                <div class="col-md-2"></div>
                <label for="name" class="col-md-2 col-form-label">User Name</label>
                <div class="col-md-4">
                    <form:input type="text" class="form-control" id="name" path="name"/>
                    <form:errors path="name" cssStyle="color : red "/>
                </div>
            </div>
            <div class="row mb-4">
                <div class="col-md-2"></div>
                <label for="email" class="col-md-2 col-form-label">Email</label>
                <div class="col-md-4">
                    <form:input type="email" class="form-control" id="email" path="email"/>
                    <form:errors path="email" cssStyle="color : red "/>
                </div>
            </div>
            <div class="row mb-4">
                <div class="col-md-2"></div>
                <label for="Password" class="col-md-2 col-form-label">Passowrd</label>
                <div class="col-md-4">
                    <form:input type="password" class="form-control" id="password" path="password"/>
                    <form:errors path="password" cssStyle="color : red "/>
                </div>
            </div>
            <div class="row mb-4">
                <div class="col-md-2"></div>
                <label for="confirmPassword" class="col-md-2 col-form-label">Confirm Password</label>
                <div class="col-md-4">
                    <input type="password" class="form-control" id="confirmPassword" name="confirmPassword"
                           oninput="validatePassword()"/>
                    <div id="error-message" class="text-danger"></div>

                </div>
            </div>

            <div class="row mb-4">
                <div class="col-md-2"></div>
                <label for="role" class="col-md-2 col-form-label">User Role</label>
                <div class="col-md-4">
                    <form:select class="form-select" aria-label="Education" id="role" path="role">
                        <form:option value="Admin">Admin</form:option>
                        <form:option value="User">User</form:option>
                    </form:select>
                </div>
            </div>

            <div class="row mb-4">
                <div class="col-md-4"></div>
                <div class="col-md-6">
                    <button type="submit" class="btn btn-secondary col-md-2" data-bs-toggle="modal"
                            data-bs-target="#exampleModal">Add
                    </button>
                    <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel"
                         aria-hidden="true">
                        <div class="modal-dialog modal-dialog-centered">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="exampleModalLabel">Student Registration</h5>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal"
                                            aria-label="Close"></button>
                                </div>
                                <div class="modal-body">

                                    <h5 style="color: rgb(127, 209, 131);">Registered Succesfully !</h5>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-success col-md-2" data-bs-dismiss="modal">Ok
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </form:form>
    </div>
</div>
<script>
    function validatePassword() {
        var passwordField = document.getElementById('password');  // Corrected ID
        var confirmPasswordField = document.getElementById('confirmPassword');
        var errorMessageElement = document.getElementById('error-message');

        if (confirmPasswordField.value !== passwordField.value) {
            // Display an error message within the input field
            confirmPasswordField.setCustomValidity('Passwords do not match!');
            errorMessageElement.innerHTML = 'Passwords do not match!';
        } else {
            // Clear the error message if passwords match
            confirmPasswordField.setCustomValidity('');
            errorMessageElement.innerHTML = '';
        }
    }

    document.addEventListener('DOMContentLoaded', function () {
        var form = document.querySelector('form');

        form.addEventListener('submit', function (event) {
            var passwordField = document.getElementById('password');  // Corrected ID
            var confirmPasswordField = document.getElementById('confirmPassword');
            var errorMessageElement = document.getElementById('error-message');

            if (confirmPasswordField.value !== passwordField.value) {
                // Prevent the form from submitting if passwords do not match
                event.preventDefault();
                // Display an error message
                confirmPasswordField.setCustomValidity('Passwords do not match!');
                errorMessageElement.innerHTML = 'Passwords do not match!';
            }
        });
    });
</script>
<jsp:include page="/WEB-INF/views/footer.jsp"/>
<!-- Add this script for displaying the message for 1.5 seconds -->
<script>
    // Function to hide all messages after 1.5 seconds
    function hideMessages() {
        var messagesContainer = document.getElementById("messagesContainer");
        if (messagesContainer) {
            messagesContainer.style.display = "none";
        }
    }

    // Set timeout to hide messages after 1.5 seconds
    setTimeout(hideMessages, 2000);
</script>
