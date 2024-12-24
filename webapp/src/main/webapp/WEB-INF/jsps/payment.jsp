<%@ include file="header.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Payment</title>
</head>
<body>
    <h1>Payment Details:</h1>

    <c:if test="${not empty error}">
        <p style="color: red;">${error}</p>
    </c:if>

    <c:if test="${empty error}">
        <div style="display: flex; gap: 40px; align-items: flex-start;">
            <div>
                <h3>Winning Bidder:</h3>
                <p><strong>First Name:</strong> ${user.firstName}</p>
                <p><strong>Last Name:</strong> ${user.lastName}</p>
                <p><strong>Street:</strong> ${user.streetAddress}</p>
                <p><strong>Number:</strong> ${user.streetNumber}</p>
                <p><strong>City:</strong> ${user.city}</p>
                <p><strong>Province:</strong> ${user.province}</p>
                <p><strong>Country:</strong> ${user.country}</p>
                <p><strong>Postal Code:</strong> ${user.postalCode}</p>
                <p><strong>Total Cost:</strong> <fmt:formatNumber value="${totalPrice}" type="currency" /></p>
            </div>

            <div style="margin-top: 10px;">
                <h3>Credit Card Information:</h3>
                <form action="submitPayment" method="post">
                    <input type="hidden" name="itemId" value="${item.id}">
                    <label for="cardNumber">Card Number:</label>
                    <input type="text" id="cardNumber" name="cardNumber" required><br><br>

                    <label for="cardName">Name on Card:</label>
                    <input type="text" id="cardName" name="cardHolderName" required><br><br>

                    <label for="expiryDate">Expiry Date:</label>
                    <input type="month" id="expiryDate" name="expiryDate" required><br><br>

                    <label for="securityCode">Security Code:</label>
                    <input type="text" id="securityCode" name="securityCode" required><br><br>

                    <button type="submit">Submit Payment</button>
                </form>
            </div>
        </div>
    </c:if>
</body>
</html>
