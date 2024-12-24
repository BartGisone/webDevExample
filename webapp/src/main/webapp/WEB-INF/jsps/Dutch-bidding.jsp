<%@ include file="header.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Dutch Auction</title>
</head>
<body>
    <h1>Dutch Auction</h1>

    <h2>${item.name}</h2>
    <p><strong>Description:</strong> ${item.description}</p>
	<p><strong>Shipping Price:</strong> <fmt:formatNumber value="${item.shippingTime}" type="currency" /></p>
	<p><strong>Current Price:</strong> <fmt:formatNumber value="${item.currentPrice}" type="currency" /></p>

    <form action="/buyNow" method="post">
        <input type="hidden" name="itemId" value="${item.id}">
        <button type="submit">Buy Now</button>
    </form>
</body>
</html>
