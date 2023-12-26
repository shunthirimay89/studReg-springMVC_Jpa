<%@ page import="java.util.List" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:import url="/WEB-INF/views/header.jsp">
    <c:param name="title" value="User Details"/>
</c:import>

<div class="main_contents">
    <div id="sub_content">
        <div id="messagesContainer">
            <div style="color: green">${updSuccess}</div>
            <div style="color: green">${success}</div>
            <div style="color: red">${disable}</div>
            <div style="color: green">${delSuccess}</div>
        </div>
        <form:form class="row g-3 mt-3 ms-2" action="/userSearch" method="post" modelAttribute="user">
            <div class="col-auto">
                <label for="staticEmail2" class="visually-hidden">User Id</label>
                <form:input type="text" class="form-control" id="staticEmail2" placeholder="User ID" path="id"/>
            </div>
            <div class="col-auto">
                <label for="inputPassword2" class="visually-hidden">User Name</label>
                <form:input type="text" class="form-control" id="inputPassword2" placeholder="User Name" path="name"/>
            </div>

            <div class="col-auto">
                <button type="submit" class="btn btn-primary ">
                    Search
                </button>
            </div>

            <div class="col-auto">
                <button type="button" class="btn btn-secondary " onclick="location.href = '/userReg'">
                    Add
                </button>
            </div>

            <div class="col-auto">
                <button type="button" class="btn btn-danger " onclick="location.href = '/userView'">Reset</button>

                <!--model-->

            </div>
        </form:form>

        <table class="table table-striped table-hover" id="stduentTable">
            <thead>
            <tr>
                <th scope="col">No</th>
                <th scope="col">User ID</th>
                <th scope="col">User Name</th>
                <th scope="col">Email</th>
                <c:if test="${not empty sessionScope.admin}">
                    <th scope="col">Details</th>
                </c:if>


            </tr>
            </thead>
            <tbody>
            <c:forEach items="${users}" var="user" varStatus="loop">
                <tr>
                    <td>${loop.index+1}</td>
                    <td>${user.id}</td>
                    <td>${user.name}</td>
                    <td>${user.email}</td>
                    <c:if test="${not empty sessionScope.admin}">
                        <td>
                            <button type="button" class="btn btn-danger mb-3" onclick="confirmDelete('${user.id}')">
                                Delete
                            </button>
                        </td>
                    </c:if>

                    <script>
                        function confirmDelete(userId) {
                            // Display a confirmation dialog
                            var confirmed = confirm("Are you sure you want to delete this user?");

                            // If the user clicks "OK" in the confirmation dialog, proceed with deletion
                            if (confirmed) {
                                // Redirect to the delete URL
                                window.location.href = '/userDelete?id=' + userId;
                            }
                        }
                    </script>


                </tr>
            </c:forEach>


            </tbody>
        </table>

        <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">Student Deletion</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">

                        <h5 style="color: rgb(127, 209, 131);">Are you sure want to delete !</h5>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-success col-md-2" data-bs-dismiss="modal">Ok</button>

                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
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

