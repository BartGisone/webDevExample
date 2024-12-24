<%@ include file="header.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>Sign Up</title>
</head>
<body>
    <h1>Sign Up</h1>

    <c:if test="${not empty error}">
        <p style="color: red;">${error}</p>
    </c:if>

    <form action="/signup" method="post">
        <label for="username">Username:</label>
        <input type="text" id="username" name="username" required><br><br>

        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required><br><br>

        <label for="firstName">First Name:</label>
        <input type="text" id="firstName" name="firstName" required><br><br>

        <label for="lastName">Last Name:</label>
        <input type="text" id="lastName" name="lastName" required><br><br>

        <h3>Address</h3>
        <label for="streetAddress">Street Address:</label>
        <input type="text" id="streetAddress" name="streetAddress" required><br><br>

        <label for="streetNumber">Street Number:</label>
        <input type="text" id="streetNumber" name="streetNumber" required><br><br>

        <label for="postalCode">Postal Code:</label>
        <input type="text" id="postalCode" name="postalCode" required><br><br>

        <label for="city">City:</label>
        <input type="text" id="city" name="city" required><br><br>

        <label for="province">Province:</label>
        <input type="text" id="province" name="province" required><br><br>

        <label for="country">Country:</label>
        <input type="text" id="country" name="country" required><br><br>

        <button type="submit">Register</button>
    </form>
</body>
</html>
