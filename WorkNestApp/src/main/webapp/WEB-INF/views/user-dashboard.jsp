<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>User Dashboard</title>
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/style.css"/>">
</head>
<body>
    <div class="container">
        <div class="header-bar">
            <h1>Welcome, <c:out value="${user.username}"/>!</h1>
            <div>
                <a href="<c:url value="/logout"/>" class="button secondary">Logout</a>
            </div>
        </div>
        <div class="section">
            <h2>Your Assigned Tasks</h2>
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Task Name</th>
                        <th>Status</th>
                        <th>Due Date</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:choose>
                        <c:when test="${empty tasks}">
                            <tr><td colspan="5" class="text-center">No tasks assigned to you.</td></tr>
                        </c:when>
                        <c:otherwise>
                            <c:forEach items="${tasks}" var="task">
                                <tr>
                                    <td><c:out value="${task.id}"/></td>
                                    <td><c:out value="${task.title}"/></td>
                                    <td><c:out value="${task.status}"/></td>
                                    <td><c:out value="${task.dueDate}"/></td>
                                    <td>
                                        <a href="<c:url value="/user/tasks/update-status?taskId=${task.id}&newStatus=IN_PROGRESS"/>" class="button button-small">Start</a>
                                        <a href="<c:url value="/user/tasks/update-status?taskId=${task.id}&newStatus=COMPLETED"/>" class="button button-small">Complete</a>
                                        <a href="<c:url value="/user/task/${task.id}/details"/>" class="button button-small secondary">Details & Comments</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                </tbody>
            </table>
        </div>
    </div>
</body>
</html>