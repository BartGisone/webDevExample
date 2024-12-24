<%@ include file="header.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Forward Auction Bidding</title>
    <script>
        
        function updateCurrentPrice(itemId) {
            fetch(`/getCurrentPrice?itemId=` + itemId)
                .then(response => response.json())
                .then(data => {
                    const currentPriceElement = document.getElementById('currentPrice');
                    if (data.currentPrice !== undefined) {
                        currentPriceElement.innerText = "$" + parseFloat(data.currentPrice).toLocaleString("en-US", {minimumFractionDigits: 2});
                    } else {
                        currentPriceElement.innerText = 'N/A';
                    }
                })
                .catch(error => console.error('Error fetching current price:', error));
        }

       
        function updateHighestBidder(itemId) {
            fetch(`/getHighestBidder?itemId=` + itemId)
                .then(response => response.json())
                .then(data => {
                    const highestBidderElement = document.getElementById('highestBidder');
                    highestBidderElement.innerText = data.highestBidder ? data.highestBidder : '';
                })
                .catch(error => console.error('Error fetching highest bidder:', error));
        }

        
        function updateRemainingTime(itemId) {
            fetch(`/getRemainingTime?itemId=` + itemId)
                .then(response => response.json())
                .then(data => {
                    const remainingTimeElement = document.getElementById('remainingTime');
                    if (data.remainingTime === "Expired") {
                        window.location.href = `/prePayment?itemId=` + itemId;
                    } else {
                        remainingTimeElement.innerText = data.remainingTime;
                    }
                })
                .catch(error => console.error('Error fetching remaining time:', error));
        }

        
        function pollAuctionUpdates() {
            const itemId = document.getElementById('itemId').value;
            setInterval(() => {
                updateCurrentPrice(itemId);
                updateHighestBidder(itemId);
                updateRemainingTime(itemId);
            }, 1000);
        }

        
        window.addEventListener('DOMContentLoaded', pollAuctionUpdates);
    </script>
</head>
<body>
    <h1>Forward Auction Bidding</h1>

    <h2>${item.name}</h2>
    <p><strong>Description:</strong> ${item.description}</p>
    <p><strong>Shipping Price:</strong> <fmt:formatNumber value="${item.shippingPrice}" type="currency" /></p>
    <p><strong>Current Price:</strong> <span id="currentPrice"><fmt:formatNumber value="${item.currentPrice}" type="currency" /></span></p>
    <p><strong>Remaining Time:</strong> <span id="remainingTime">${item.remainingTime}</span></p>
    <p>
        <strong>Highest Bidder:</strong> <span id="highestBidder">
            <c:choose>
                <c:when test="${highestBidder != null}">
                    ${highestBidder.username}
                </c:when>
                <c:otherwise>
                 
                </c:otherwise>
            </c:choose>
        </span>
    </p>

    <c:if test="${not empty error}">
        <p style="color: red;">${error}</p>
    </c:if>

    <c:if test="${not empty success}">
        <p style="color: green;">${success}</p>
    </c:if>

    <!-- Hidden field to store itemId -->
    <input type="hidden" id="itemId" value="${item.id}">

    <form action="/placeBid" method="post">
        <input type="hidden" name="itemId" value="${item.id}">
        <label for="bidAmount">Enter Your Bid:</label>
        <input type="number" id="bidAmount" name="bidAmount" step="0.01" required>
        <button type="submit">Place Bid</button>
    </form>
</body>
</html>
