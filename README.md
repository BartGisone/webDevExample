

# Overview  
The HB Auction System is a web application designed to facilitate online auctions, supporting both Forward and Dutch auction types. The system allows users to browse items, place bids, and complete purchases using integrated payment functionality. It is built using **Spring Boot**, **JPA**, **MySQL**, and **JSP** and includes features like auction expiration and real-time updates.  

# Prerequisites  
Before running the application, ensure you have the following software installed:  
- **Java 17+**  
- **Maven 3.8+**  
- **MySQL Server**  
- **Lombok Plugin** (ensure it's enabled in your IDE if required)  
  - Download here: [Lombok Download](https://projectlombok.org/download)  

# Setup Instructions  
1. **Database Configuration**  
   - Set up a MySQL database of your choice, ours is configured to a database named: `HB_DB`. You can create it using the following SQL command:  
    
     CREATE DATABASE HB_DB;
    
   - Update your MySQL credentials in the `src/main/resources/application.properties` file:  

     spring.datasource.username= "your_username"  
     spring.datasource.password= "your_password"  

   - The JPA API will automatically create and modify the necessary tables in the database.  

2. **Optional: Populate the Items Table**  
   - You may populate the `item` table with initial data using the following SQL query as an example:  

INSERT INTO item (
    name,
    description,
    starting_price,
    current_price,
    auction_type,
    shipping_price,
    expedited_shipping_cost,
    auction_end_time,
    shipping_time
)
VALUES (
    'Headphones - Forward Auction', -- Item Name
    'Wireless headphones with noise-cancellation.', -- Description
    50.00, -- Starting Price
    75.00, -- Current Price
    'Forward', -- Auction Type
    5.00, -- Normal Shipping Price
    10.00, -- Expedited Shipping Cost
    NOW() + INTERVAL 15 MINUTE, -- Auction End Time (15 minutes from now)
    2 -- Shipping Time in Days
);
    
    

3. **Build and Run the Application**  
   - Navigate to the project directory, compile and run WebappApplication.java to begin the SpringBoot Project or use the following commands:

     mvn clean install  
     mvn spring-boot:run  
    

4. **Access the Application**  
   - Open your browser and navigate to: http://localhost:8080/

# Application Features  
- **User Authentication:** Sign up and log in functionality.  
- **Auction Browsing:** Search for items by keyword.  
- **Real-Time Updates:** Remaining time, highest bid, and current price are updated dynamically.  
- **Auction Types:** Forward and Dutch auctions with corresponding bidding or "Buy Now" options.  
- **Payment Integration:** Secure payment processing with itemized receipts.  
- **Auction Expiration:** Automatic expiration handling for Forward auctions.  

# Key Notes  
- The default server port is **8080**. Update `server.port` in `application.properties` if needed.  
- Perform `Maven Clean` and `Maven Install` if build issues occur.  
- Ensure your MySQL server is running and accessible.

# Troubleshooting  
- **Database Connection Issues:** Verify MySQL credentials in `application.properties` and ensure the database is running.  
- **Lombok Plugin Errors:** Ensure the Lombok plugin is installed and enabled in your IDE.   
