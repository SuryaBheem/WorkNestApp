<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login to WorkNest</title>
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/style.css"/>">
</head>
<body>
    <div class="container">
        <h1 class="text-center">WorkNest Login</h1>
        <form:form action="login" method="post" modelAttribute="user">
            <label for="username">Username:</label>
            <form:input path="username" id="username"/><br/><br/>
            <label for="password">Password:</label>
            <form:password path="password" id="password"/><br/><br/>
            <input type="submit" value="Login"/>
        </form:form>
        <p class="text-center margin-top-large">Don't have an account? <a href="<c:url value="/register"/>">Register here</a></p>
    </div>
</body>
</html>