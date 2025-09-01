<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Task Details</title>
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/style.css"/>">
</head>
<body>
    <div class="container">
        <div class="header-bar">
            <h1>Task: <c:out value="${task.title}"/></h1>
            <a href="<c:url value="${user.role == 'ADMIN' ? '/admin/dashboard' : '/user/dashboard'}"/>" class="button secondary">Back to Dashboard</a>
        </div>
        
        <div class="section">
            <h2>Task Details</h2>
            <p><strong>Description:</strong> <c:out value="${task.description}"/></p>
            <p><strong>Status:</strong> <c:out value="${task.status}"/></p>
            <p><strong>Assigned to:</strong> <c:out value="${task.assignedUser.username}"/></p>
            <p><strong>Created by:</strong> <c:out value="${task.createdBy.username}"/></p>
            <p><strong>Start Date:</strong> <c:out value="${task.startDate}"/></p>
            <p><strong>Due Date:</strong> <c:out value="${task.dueDate}"/></p>
        </div>
        
        <div class="section comment-section">
            <h2>Comments</h2>
            <c:if test="${empty comments}">
                <p>No comments yet.</p>
            </c:if>
            <c:forEach items="${comments}" var="comment">
                <div class="comment">
                    <p><strong><c:out value="${comment.commenter.username}"/></strong> on <c:out value="${comment.commentDate}"/>:</p>
                    <p><c:out value="${comment.commentText}"/></p>
                </div>
            </c:forEach>
        </div>
        
        <div class="section">
            <h2>Add a Comment</h2>
            <form:form action="/add-comment" method="post" modelAttribute="comment">
                <form:hidden path="task.id" value="${task.id}"/>
                <label for="commentText">Your Comment:</label><br/>
                <form:textarea path="commentText" id="commentText" rows="4" cols="50"/><br/>
                <input type="submit" value="Submit Comment"/>
            </form:form>
        </div>
    </div>
</body>
</html>