<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Register for WorkNest</title>
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/style.css"/>">
</head>
<body>
    <div class="container">
        <h1 class="text-center">Register for WorkNest</h1>
        <form:form action="register" method="post" modelAttribute="user">
            <label for="username">Username:</label>
            <form:input path="username" id="username" required="true"/><br/><br/>
            <label for="password">Password:</label>
            <form:password path="password" id="password" required="true"/><br/><br/>
            <label for="email">Email:</label>
            <form:input path="email" id="email"/><br/><br/>
            <label for="role">Role:</label>
            <form:select path="role">
                <form:option value="USER" label="User"/>
                <form:option value="ADMIN" label="Admin"/>
            </form:select><br/><br/>
            <input type="submit" value="Register"/>
        </form:form>
        <p class="text-center margin-top-large">Already have an account? <a href="<c:url value="/login"/>">Login here</a></p>
    </div>
</body>
</html>