<%@ include file="header.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Pre-Payment</title>
</head>
<body>
    <h1>Bidding Ended!</h1>

    <h2>${item.name}</h2>
    <p><strong>Description:</strong> ${item.description}</p>
    <p><strong>Shipping Price:</strong> <fmt:formatNumber value="${item.shippingPrice}" type="currency" /></p>
    <p><strong>Winning Price:</strong> <fmt:formatNumber value="${item.currentPrice}" type="currency" /></p>
    <p>
        <strong>Highest Bidder:</strong>
        <c:choose>
            <c:when test="${highestBidder != null}">
                ${highestBidder.username}
            </c:when>
            <c:otherwise>
                
            </c:otherwise>
        </c:choose>
    </p>

    <!-- Error message for non-winners -->
    <c:if test="${not empty error}">
        <p style="color: red;">${error}</p>
    </c:if>

    <!-- Pay Now form (always visible) -->
    <form action="/payNow" method="post">
        <input type="hidden" name="itemId" value="${item.id}">
        <label>
            <input type="radio" name="shippingOption" value="normal" checked>
            Normal Shipping - <fmt:formatNumber value="${item.shippingPrice}" type="currency" />
        </label>
        <br>
        <label>
            <input type="radio" name="shippingOption" value="expedited">
            Expedited Shipping - <fmt:formatNumber value="${item.expeditedShippingCost}" type="currency" />
        </label>
        <br>
        <button type="submit">Pay Now</button>
    </form>
</body>
</html>
