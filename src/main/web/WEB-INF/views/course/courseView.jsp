<%@ page import="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:import url="/WEB-INF/views/header.jsp">
    <c:param name="title" value="User Details"/>
</c:import>

<div class="main_contents">
    <div id="sub_content">
        <div id="messagesContainer">
            <div style="color:green">${success}</div>
            <div style="color:green">${updSuccess}</div>
        </div>
        <form:form class="row g-3 mt-3 ms-2" action="/courseSearch" method="post" modelAttribute="course">
            <div class="col-auto">
                <label for="staticEmail2" class="visually-hidden">Course Id</label>
                <form:input type="text" class="form-control" id="staticEmail2" placeholder="Course ID" path="id"/>
            </div>
            <div class="col-auto">
                <label for="inputPassword2" class="visually-hidden">User Name</label>
                <form:input type="text" class="form-control" id="inputPassword2" placeholder="Course Name"
                            path="courseName"/>
            </div>


            <!--model-->
            <div class="col-auto">
                <button type="submit" class="btn btn-primary ">
                    Search
                </button>
            </div>

            <div class="col-auto">
                <button type="button" class="btn btn-secondary " onclick="location.href = '/courseReg'">
                    Add
                </button>
            </div>


            <div class="col-auto">
                <button type="button" class="btn btn-danger " onclick="location.href = '/courseView'">Reset</button>

                <!--model-->
            </div>
        </form:form>

        <table class="table table-striped table-hover" id="stduentTable">
            <thead>
            <tr>
                <th scope="col">No</th>
                <th scope="col">Course ID</th>
                <th scope="col">Course Name</th>
                <th scope="col">Details</th>

            </tr>
            </thead>
            <tbody>
            <c:forEach items="${courseList}" var="course" varStatus="loop">
                <tr>
                    <td>${loop.index + 1 }</td>
                    <td>${course.id}</td>
                    <td>${course.courseName}</td>
                    <td>
                        <button type="button" class="btn btn-success">
                            <a href="/courseUpdate?id=${course.id}" style="color: #d6f5e3; text-decoration: none;">Update</a>
                        </button>
                    </td>
                    <td>
                        <button type="button" class="btn btn-danger mb-3" onclick="confirmDelete('${course.id}')">
                            Delete
                        </button>
                    </td>
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
                        <button type="submit" class="btn btn-success col-md-2" data-bs-dismiss="modal">Ok</button>

                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<jsp:include page="/WEB-INF/views/footer.jsp"/>

<script>
    function confirmDelete(courseId) {
        // Set the course ID in the modal form action
        document.getElementById('exampleModal').querySelector('.btn-success').addEventListener('click', function () {
            location.href = '/courseDelete?id=' + courseId;
        });

        // Show the confirmation modal
        var myModal = new bootstrap.Modal(document.getElementById('exampleModal'));
        myModal.show();
    }
</script>
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
