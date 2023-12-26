<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:import url="/WEB-INF/views/header.jsp">
    <c:param name="title" value="Course Registration"/>
</c:import>
<div class="main_contents">
    <div id="sub_content">
        <div id="messagesContainer">
            <div style="color:red">${error}</div>
            <div style="color:green">${success}</div>
        </div>
        <form:form action="/courseReg" method="post" modelAttribute="course">
            <h2 class="col-md-6 offset-md-2 mb-5 mt-4">Course Registration</h2>
            <div class="row mb-4">
                <div class="col-md-2"></div>
                <label for="id" class="col-md-2 col-form-label"> ID</label>
                <div class="col-md-4">
                    <form:input type="text" class="form-control" id="id" path="id" readonly="true"></form:input>
                </div>
            </div>

            <div class="row mb-4">
                <div class="col-md-2"></div>
                <label for="name" class="col-md-2 col-form-label">Name</label>
                <div class="col-md-4">
                    <form:input type="text" class="form-control" id="name" placeholder="eg. PHP ,Andriod"
                                path="courseName"/>
                    <form:errors path="courseName" cssStyle="color : red "/>
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
                                    <h5 class="modal-title" id="exampleModalLabel">Course Registration</h5>
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

<script type="text/javascript">
    function validateForm() {
        var courseName = document.getElementById('name').value;

        // Check if the courseName is empty
        if (courseName.trim() === '') {
            alert('Course name must not be empty.');
            return false;
        }

        return true;
    }
</script>
<jsp:include page="/WEB-INF/views/footer.jsp"/>
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
