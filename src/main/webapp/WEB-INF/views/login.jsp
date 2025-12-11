<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>User Login</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 40px; }
        .form-group { margin-bottom: 15px; }
        label { display: block; margin-bottom: 5px; }
        input[type="text"], input[type="password"] {
            padding: 8px; width: 300px; border: 1px solid #ddd;
        }
        button { padding: 10px 20px; background: #007bff; color: white; border: none; cursor: pointer; }
        .error { color: red; margin-bottom: 15px; }
        .success { color: green; margin-bottom: 15px; }
    </style>
</head>
<body>
    <h1>User Login</h1>

    <c:if test="${not empty error}">
        <div class="error">${error}</div>
    </c:if>

    <c:if test="${not empty success}">
        <div class="success">${success}</div>
    </c:if>

    <form action="${pageContext.request.contextPath}/api/login" method="post">
        <div class="form-group">
            <label for="username">Username:</label>
            <input type="text"
                   id="username"
                   name="username"
                   required
                   minlength="3"
                   maxlength="50"
                   value="${param.username}">
        </div>

        <div class="form-group">
            <label for="password">Password:</label>
            <input type="password"
                   id="password"
                   name="password"
                   required
                   minlength="6"
                   maxlength="50">
        </div>

        <button type="submit">Login</button>
    </form>
</body>
</html>
