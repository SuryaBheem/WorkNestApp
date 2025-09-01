<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title><c:if test="${task.id == null}">Add New Task</c:if><c:if test="${task.id != null}">Edit Task</c:if></title>
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/style.css"/>">
</head>
<body>
    <div class="container">
        <div class="header-bar">
            <h1><c:if test="${task.id == null}">Add New Task</c:if><c:if test="${task.id != null}">Edit Task</c:if></h1>
            <a href="<c:url value="/admin/dashboard"/>" class="button secondary">Back to Dashboard</a>
        </div>
        
        <form:form action="/admin/tasks/save" method="post" modelAttribute="task">
            <form:hidden path="id"/>
            <label for="title">Task Name:</label>
            <form:input path="title" id="title"/><br/><br/>
            <label for="description">Description:</label>
            <form:textarea path="description" id="description"/><br/><br/>
            <label for="startDate">Start Date:</label>
            <form:input type="date" path="startDate" id="startDate"/><br/><br/>
            <label for="dueDate">Due Date:</label>
            <form:input type="date" path="dueDate" id="dueDate"/><br/><br/>
            <label for="assignedUser">Assigned to:</label>
            <form:select path="assignedUser.id">
                <c:forEach items="${users}" var="user">
                    <form:option value="${user.id}" label="${user.username}"/>
                </c:forEach>
            </form:select><br/><br/>
            <input type="submit" value="Save Task"/>
        </form:form>
    </div>
</body>
</html>