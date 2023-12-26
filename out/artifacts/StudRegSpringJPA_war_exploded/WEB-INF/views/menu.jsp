<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="header.jsp">
    <c:param name="title" value="Menu"/>
</c:import>
<div class="main-contents">

    <div id="contents">
        <!-- Add an ID to the success message -->
        <div id="successMessage" style="color: green">${loginSuccess}</div>

        <h3>Welcome Back...! <br><br>
            Enjoy this project and try your best in your own!</h3>
    </div>
</div>

<jsp:include page="footer.jsp"/>

<!-- Add this script for hiding the success message after 1.5 seconds -->
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

