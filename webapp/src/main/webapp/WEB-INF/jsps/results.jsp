<%@ include file="header.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Search Results</title>
</head>
<body>
    <h1>Search Results</h1>

    <c:if test="${not empty items}">
        <form action="/bid" method="post">
            <table border="1">
                <thead>
                    <tr>
                        <th>Item Name</th>
                        <th>Current Price</th>
                        <th>Auction Type</th>
                        <th>Remaining Time</th>
                        <th>Select</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="item" items="${items}">
                        <tr>
                            <td>${item.name}</td>
                            <td><fmt:formatNumber value="${item.currentPrice}" type="currency" /></td>
                            <td>${item.auctionType}</td>
                            <td>${item.remainingTime}</td>
                            <td>
                                <input type="radio" name="itemId" value="${item.id}" required>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
            <button type="submit">Bid</button>
        </form>
    </c:if>

    <c:if test="${empty items}">
        <p>No items found for the given keyword.</p>
    </c:if>
</body>
</html>
