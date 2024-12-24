<%@ include file="header.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>Sign In</title>
</head>
<body>
    <h1>Sign In</h1>

    <c:if test="${not empty success}">
        <p style="color: green;">${success}</p>
    </c:if>

    <c:if test="${not empty error}">
        <p style="color: red;">${error}</p>
    </c:if>

    <form action="/login" method="post">
        <label for="username">Username:</label>
        <input type="text" id="username" name="username" required><br><br>

        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required><br><br>

        <button type="submit">Login</button>
    </form>
</body>
</html>
