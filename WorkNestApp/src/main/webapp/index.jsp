<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Welcome to WorkNestApp</title>
    <link rel="stylesheet" href="<c:url value='/css/style.css'/>">
    <style>
        .container {
            text-align: center;
        }
    </style>
</head>
<body>
    <div class="container">
        <h2>Welcome to WorkNestApp</h2>
        <p>A simple and effective task management system.</p>
        <p>
            <a href="<c:url value='/login'/>" class="button">Login</a>
            <a href="<c:url value='/register'/>" class="button">Register</a>
        </p>
    </div>
</body>
</html>