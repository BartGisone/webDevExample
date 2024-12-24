<%@ include file="header.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Receipt</title>
    <style>
        .container {
            display: flex;
            justify-content: flex-start;
            align-items: flex-start;
            margin: 20px;
        }

        .section {
            flex: 0 1 auto; /* Prevent sections from stretching unevenly */
            margin: 0;
            padding: 0;
        }

        .receipt-section {
            margin-right: 20px; /* Space between Receipt and Shipping Details */
        }
    </style>
</head>
<body>
    <div class="container">
        <!-- Receipt Section -->
        <div class="section receipt-section">
            <h1>Receipt</h1>
            <p><strong>First Name:</strong> ${user.firstName}</p>
            <p><strong>Last Name:</strong> ${user.lastName}</p>
            <p><strong>Street:</strong> ${user.streetAddress}</p>
            <p><strong>Number:</strong> ${user.streetNumber}</p>
            <p><strong>City:</strong> ${user.city}</p>
            <p><strong>Province:</strong> ${user.province}</p>
            <p><strong>Country:</strong> ${user.country}</p>
            <p><strong>Postal Code:</strong> ${user.postalCode}</p>
            <p><strong>Total Paid:</strong> <fmt:formatNumber value="${payment.amountPaid}" type="currency" /></p>
            <p><strong>Item ID:</strong> ${item.id}</p>
        </div>

        <!-- Shipping Details Section -->
        <div class="section">
            <h1>Shipping Details</h1>
            <p>The item will be shipped in <strong>${item.shippingTime} days</strong>.</p>
            <div style="margin-top: 10px;">
                <form action="/search" method="get">
                    <button type="submit" style="padding: 10px 20px; font-size: 16px;">Back to Main Page</button>
                </form>
            </div>
        </div>
    </div>
</body>
</html>
