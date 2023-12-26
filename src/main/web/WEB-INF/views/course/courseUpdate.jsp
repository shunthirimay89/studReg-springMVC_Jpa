<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:import url="/WEB-INF/views/header.jsp">
    <c:param name="title" value="User Detail"/>
</c:import>

<div class="main_contents">
    <div style="color:red">${updError}</div>
    <div id="sub_content">
        <form:form action="/courseUpdate" method="post" modelAttribute="course">

            <h2 class="col-md-6 offset-md-2 mb-5 mt-4">Course Update</h2>


            <div class="col-md-4">
                <form:input type="hidden" class="form-control" id="id" path="id"></form:input>
            </div>


            <div class="row mb-4">
                <div class="col-md-2"></div>
                <label for="id" class="col-md-2 col-form-label">Course Name</label>
                <div class="col-md-4">
                    <form:input type="text" class="form-control" id="name" path="courseName"></form:input>
                </div>
            </div>

            <div class="row mb-4">
                <div class="col-md-4"></div>

                <div class="col-md-6">

                    <button type="submit" class="btn btn-success col-md-2" data-bs-toggle="modal"
                            data-bs-target="#exampleModal">Update
                    </button>
                    <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel"
                         aria-hidden="true">
                        <div class="modal-dialog modal-dialog-centered">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title" id="exampleModalLabel">User Update</h5>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal"
                                            aria-label="Close"></button>
                                </div>
                                <div class="modal-body">

                                    <h5 style="color: rgb(127, 209, 131);">Succesfully Updated !</h5>
                                </div>
                                <div class="modal-footer">
                                    <button type="button" class="btn btn-success col-md-2" data-bs-dismiss="modal">Ok
                                    </button>

                                </div>
                            </div>
                        </div>
                    </div>
                    <button type="button" class="btn btn-secondary col-md-2 " onclick='location.href = "/user_view"'>
                        Back
                    </button>
                </div>
            </div>
        </form:form>

    </div>
</div>
<jsp:include page="/WEB-INF/views/footer.jsp"/>
