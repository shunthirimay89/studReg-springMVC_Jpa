<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<c:import url="/WEB-INF/views/header.jsp">
    <c:param name="title" value="Student Detail"/>
</c:import>
<div class="main_contents">
    <div id="sub_content">
        <div style="color: red">${fail}</div>
        <form:form action="/studentDetail" method="post" enctype="multipart/form-data" modelAttribute="student">
            <h2 class="col-md-6 offset-md-2 mb-5 mt-4">Student Details</h2>
            <form:input type="hidden" id="hideid" class="form-control" name="hideid" path="id"/>

            <div class="row mb-4">
                <div class="col-md-2"></div>
                <label for="studname" class="col-md-2 col-form-label">Name</label>
                <div class="col-md-4">
                    <form:input type="text" class="form-control" id="studname" path="name"/>
                </div>
            </div>
            <div class="row mb-4">
                <div class="col-md-2"></div>
                <label for="dob" class="col-md-2 col-form-label">DOB</label>
                <div class="col-md-4">
                    <form:input type="date" class="form-control" id="dob" path="dob"/>
                </div>
            </div>
            <fieldset class="row mb-4">
                <div class="col-md-2"></div>
                <legend class="col-form-label col-md-2 pt-0">Gender</legend>
                <div class="col-md-4">
                    <div class="form-check-inline">
                        <form:radiobutton class="form-check-input" id="option1"
                                          path="gender"/>
                        <label class="form-check-label" for="option1">
                            Male
                        </label>
                    </div>
                    <div class="form-check-inline">
                        <form:radiobutton class="form-check-input" id="option2" path="gender"/>
                        <label class="form-check-label" for="option2">
                            Female
                        </label>
                    </div>

                </div>
            </fieldset>

            <div class="row mb-4">
                <div class="col-md-2"></div>
                <label for="phone" class="col-md-2 col-form-label">Phone</label>
                <div class="col-md-4">
                    <form:input type="text" class="form-control" id="phone" path="phone"/>
                </div>
            </div>
            <div class="row mb-4">
                <div class="col-md-2"></div>
                <label for="education" class="col-md-2 col-form-label">Education</label>
                <div class="col-md-4">
                    <form:select class="form-select" aria-label="Education" id="education" path="education">
                        <form:option
                                value="Bachelor of Information Technology">Bachelor of Information Technology</form:option>
                        <form:option value="Diploma in IT">Diploma in IT</form:option>
                        <form:option value="Beachelor of Computer Science">Bachelor of Computer Science</form:option>

                    </form:select>
                </div>
            </div>
            <fieldset class="row mb-4">
                <div class="col-md-2"></div>
                <legend class="col-form-label col-md-2 pt-0">Attend</legend>
                <div class="col-md-4">

                        <%-- Combine courses from course table and student's selected courses --%>
                    <c:set var="allCourses" value="${courses}"/>
                    <c:forEach items="${student.courses}" var="selectedCourse">
                        <c:if test="${not fn:contains(allCourses, selectedCourse)}">
                            <c:set var="allCourses" value="${allCourses.addAll(selectedCourse)}"/>
                        </c:if>
                    </c:forEach>

                        <%-- Display checkboxes for all courses --%>
                    <c:forEach items="${courses}" var="course">
                        <div class="form-check-inline col-md-2">
                            <input type="checkbox" class="form-check-input" name="courseIds" id="gridRadios1"
                                   value="${course.id}"
                                ${fn:contains(student.courses, course) ? 'checked' : ''} />
                            <label class="form-check-label" for="gridRadios1">${course.courseName}</label>
                        </div>
                    </c:forEach>

                </div>
            </fieldset>

            <div class="row mb-4">
                <div class="col-md-2"></div>
                <label for="name" class="col-md-2 col-form-label">Photo</label>
                <div class="col-md-4">
                    <form:input type="file" class="form-control" id="name" path="file"/>
                </div>
            </div>
            <form:input type="hidden" class="form-control" path="image"/>

            <div class="row mb-4">
                <div class="col-md-4"></div>

                <div class="col-md-4">
                    <button type="submit" class="btn btn-secondary">
                        <span>Update</span>
                    </button>
                </div>
                <div class="col-md-4">
                    <!-- Add confirmation dialog for the delete button -->
                    <button type="button" class="btn btn-danger" onclick="confirmDelete()">
                        Delete
                    </button>
                </div>
            </div>
        </form:form>
    </div>
</div>
<jsp:include page="/WEB-INF/views/footer.jsp"/>

<script>
    function confirmDelete() {
        var confirmation = confirm("Are you sure you want to delete?");
        if (confirmation) {
            // If the user confirms, redirect to the delete URL
            location.href = '/studentDelete?id=${student.id}';
        }
    }
</script>
