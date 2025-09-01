<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <title><c:if test="${user.id == null}">Add New User</c:if><c:if test="${user.id != null}">Edit User</c:if></title>
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/style.css"/>">
</head>
<body>
    <div class="container">
        <div class="header-bar">
            <h1><c:if test="${user.id == null}">Add New User</c:if><c:if test="${user.id != null}">Edit User</c:if></h1>
            <a href="<c:url value="/admin/dashboard"/>" class="button secondary">Back to Dashboard</a>
        </div>
        
        <form:form action="/admin/users/save" method="post" modelAttribute="user">
            <form:hidden path="id"/>
            <label for="username">Username:</label>
            <form:input path="username" id="username"/><br/><br/>
            <label for="password">Password:</label>
            <form:password path="password" id="password"/><br/><br/>
            <label for="email">Email:</label>
            <form:input path="email" id="email"/><br/><br/>
            <label for="role">Role:</label>
            <form:select path="role">
                <form:option value="USER" label="User"/>
                <form:option value="ADMIN" label="Admin"/>
            </form:select><br/><br/>
            <input type="submit" value="Save User"/>
        </form:form>
    </div>
</body>
</html>