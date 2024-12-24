<%@ include file="header.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>Search Items</title>
</head>
<body>
    <h1>Item Search</h1>
    <form action="/results" method="get">
        <label for="keyword">Enter keyword:</label>
        <input type="text" id="keyword" name="keyword" required>
        <button type="submit">Search</button>
    </form>
</body>
</html>
