<%--
  Created by IntelliJ IDEA.
  User: Asus
  Date: 7/3/2023
  Time: 7:45 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <link rel="stylesheet" href="/assets/css/style.css" type="text/css">
    <title> Student Registration Login </title>

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
</head>
<body class="login-page-body">

<div class="login-page">

    <div class="form">
        <div class="login">
            <div class="login-header">
                <h1>Welcome!</h1>
            </div>
            <!-- Add an ID to the messages container -->
            <div id="messagesContainer">
                <div style="color: green">${Reg}</div>
                <div style="color: red">${loginFail}</div>
                <div style="color: red">${password}</div>
                <div style="color: red">${banMessage}</div>
            </div>

        </div>
        <form:form class="login-form" action="/" method="post" modelAttribute="user">
            <form:input type="text" placeholder="User Email" path="email"/>
            <form:input type="password" placeholder="Password" path="password"/>
            <button type="submit"> login</button>
            <p class="message">Not registered? <a href="/userReg">Create an account</a></p>
        </form:form>
    </div>
</div>
</body>
</html>
