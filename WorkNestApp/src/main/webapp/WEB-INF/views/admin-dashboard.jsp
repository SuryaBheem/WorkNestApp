<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <title>Admin Dashboard</title>
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/style.css"/>">
</head>
<body>
    <div class="container">
        <div class="header-bar">
            <h1>Admin Dashboard</h1>
            <div>
                <a href="<c:url value="/logout"/>" class="button secondary">Logout</a>
            </div>
        </div>
        <div class="section">
            <h2>Manage Users</h2>
            <div class="section-actions">
                <a href="<c:url value="/admin/users/add"/>" class="button">Add New User</a>
            </div>
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Username</th>
                        <th>Email</th>
                        <th>Role</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${users}" var="user">
                        <tr>
                            <td><c:out value="${user.id}"/></td>
                            <td><c:out value="${user.username}"/></td>
                            <td><c:out value="${user.email}"/></td>
                            <td><c:out value="${user.role}"/></td>
                            <td>
                                <a href="<c:url value="/admin/users/edit/${user.id}"/>">Edit</a> |
                                <a href="<c:url value="/admin/users/delete/${user.id}"/>" onclick="return confirm('Are you sure you want to delete this user?');">Delete</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="section">
            <h2>Manage Tasks</h2>
            <div class="section-actions">
                <a href="<c:url value="/admin/tasks/add"/>" class="button">Add New Task</a>
            </div>
            <table>
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Task Name</th>
                        <th>Assigned To</th>
                        <th>Created By</th>
                        <th>Due Date</th>
                        <th>Status</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${tasks}" var="task">
                        <tr>
                            <td><c:out value="${task.id}"/></td>
                            <td><c:out value="${task.title}"/></td>
                            <td><c:out value="${task.assignedUser.username}"/></td>
                            <td><c:out value="${task.createdBy.username}"/></td>
                            <td><c:out value="${task.dueDate}"/></td>
                            <td><c:out value="${task.status}"/></td>
                            <td>
                                <a href="<c:url value="/admin/tasks/edit/${task.id}"/>">Edit</a> |
                                <a href="<c:url value="/admin/tasks/delete/${task.id}"/>" onclick="return confirm('Are you sure you want to delete this task?');">Delete</a> |
                                <a href="<c:url value="/admin/tasks/details/${task.id}"/>">Details</a>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</body>
</html>