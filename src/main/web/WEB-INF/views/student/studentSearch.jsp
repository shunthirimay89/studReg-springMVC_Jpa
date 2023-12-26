<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ page import="java.util.List" %>
<c:import url="/WEB-INF/views/header.jsp">
    <c:param name="title" value="Student Search"/>
</c:import>

<div class="main_contents">

    <div id="sub_content">
        <div id="successMessage" style="color:green;">${updSuccess}</div>
        <form:form class="row g-3 mt-3 ms-2" action="/studentSearch" method="post" modelAttribute="student">
            <div class="col-auto">
                <label for="studid" class="visually-hidden">studentID</label>
                <form:input type="text" class="form-control" id="studid" placeholder="Student ID" path="id"/>
            </div>
            <div class="col-auto">
                <label for="studname" class="visually-hidden">studentName</label>
                <form:input type="text" class="form-control" id="studname" placeholder="Student Name" path="name"/>
            </div>
            <div class="col-auto">
                <label for="studcoures" class="visually-hidden">Course</label>
                <input type="text" class="form-control" id="studcoures" placeholder="Course" name="courseName"/>
            </div>
            <div class="col-auto">
                <button type="submit" class="btn btn-primary mb-3">Search</button>
                <!--model-->

            </div>
            <div class="col-auto">
                <button type="reset" class="btn btn-danger mb-3" onclick="location.href='/studentView'">Rest</button>
                <!--model-->

            </div>
        </form:form>
        <div>
            <table class="table table-striped" id="stduentTable">
                <thead>
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">Student ID</th>
                    <th scope="col">Name</th>

                    <th scope="col">Courses</th>
                    <th scope="col">Photo</th>
                    <th scope="col">Details</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${studentList}" var="stud" varStatus="loop">
                    <tr>
                        <th scope="row">${loop.index+1}</th>
                        <td>${stud.id}</td>
                        <td>${stud.name}</td>

                        <!-- Iterate through courses and display without brackets -->
                        <td>
                            <c:forEach items="${stud.courses}" var="course" varStatus="courseLoop">
                                ${fn:trim(course.courseName)}<c:if test="${!courseLoop.last}">, </c:if>
                            </c:forEach>
                        </td>

                        <td><img src="../assets/images/${stud.image}" alt="no photo" width="60px" height="60px"></td>
                        <td>
                        <td>
                            <a href="/studentDetail?id=${stud.id}">
                                <button type="submit" class="btn btn-secondary mb-2">See More</button>
                            </a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
<jsp:include page="/WEB-INF/views/footer.jsp"/>
<script>
    // Function to hide the success message after 1.5 seconds
    function hideSuccessMessage() {
        var successMessage = document.getElementById("successMessage");
        if (successMessage) {
            successMessage.style.display = "none";
        }
    }

    // Set timeout to hide the success message after 1.5 seconds
    setTimeout(hideSuccessMessage, 2000);
</script>
