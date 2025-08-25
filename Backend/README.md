# Treading Platform Application

## Project Overview

The Treading Platform Application is a comprehensive trading application designed for users to engage in various trading operations. The primary goal of the application is to provide a seamless and user-friendly interface for buying and selling assets, managing wallets, and tracking trading history. It caters to both novice and experienced traders, offering tools to facilitate informed trading decisions.

## Core Features

- **Trading Operations**: Users can execute buy and sell orders for various assets, allowing them to participate in the market actively.
- **Wallet Management**: The application provides users with a secure wallet to manage their assets, view balances, and track transaction history.
- **Order Management**: Users can create, view, and cancel orders, giving them control over their trading activities.
- **Asset Tracking**: The application lists available trading assets along with their current market prices, enabling users to make informed decisions.
- **User Management**: The application supports user registration, authentication, and profile management, ensuring a personalized experience.

## Architecture

The architecture of the Treading Platform Application follows a layered approach, which includes:

1. **Presentation Layer**: This layer consists of various controllers (e.g., `AuthController`, `UserController`, etc.) that handle incoming HTTP requests and return appropriate responses. It acts as the interface between the client and the application.

2. **Service Layer**: The service layer contains business logic and interacts with the data access layer. It is responsible for processing requests from the controllers and returning the results.

3. **Data Access Layer**: This layer consists of repository classes that handle database operations. It abstracts the data access logic and provides a clean interface for the service layer.

4. **Domain Layer**: The domain layer includes model classes that represent the application's data structure. These classes are used throughout the application to manage data.

5. **Configuration Layer**: The application configuration is managed through classes like `AppConfig`, which define security settings, CORS configurations, and other application-wide settings.

## API Endpoints

The application exposes several RESTful API endpoints for different operations:

- **POST /buy**: Allows users to purchase assets. This endpoint processes buy requests and updates the user's wallet accordingly. 
  - **Request Body**: 
    ```json
    {
      "assetId": "string",
      "quantity": "number"
    }
    ```
  - **Response**: 
    ```json
    {
      "message": "Purchase successful",
      "transactionId": "string"
    }
    ```

- **POST /sell**: Enables users to sell their assets. It handles sell requests and adjusts the user's asset holdings.
  - **Request Body**: 
    ```json
    {
      "assetId": "string",
      "quantity": "number"
    }
    ```
  - **Response**: 
    ```json
    {
      "message": "Sale successful",
      "transactionId": "string"
    }
    ```

- **GET /wallet**: Provides information about the user's wallet, including balances and transaction history.
  - **Response**: 
    ```json
    {
      "balance": "number",
      "transactions": [
        {
          "transactionId": "string",
          "type": "string",
          "amount": "number",
          "date": "string"
        }
      ]
    }
    ```

- **GET /orders**: Manages user orders, allowing them to view, create, and cancel orders.
  - **Response**: 
    ```json
    {
      "orders": [
        {
          "orderId": "string",
          "status": "string",
          "assetId": "string",
          "quantity": "number"
        }
      ]
    }
    ```

- **GET /assets**: Lists available trading assets and their current market prices.
  - **Response**: 
    ```json
    {
      "assets": [
        {
          "assetId": "string",
          "name": "string",
          "currentPrice": "number"
        }
      ]
    }
    ```

- **POST /user**: Handles user-related operations, such as registration, authentication, and profile management.
  - **Request Body**: 
    ```json
    {
      "email": "string",
      "password": "string"
    }
    ```
  - **Response**: 
    ```json
    {
      "message": "User registered successfully",
      "userId": "string"
    }
    ```

## User Roles and Permissions

The application supports different user roles, including:

- **Trader**: Can perform trading operations, manage their wallet, and view their transaction history.
- **Admin**: Has access to manage users, view all transactions, and oversee the trading platform's operations.

## Technologies Used

- **Spring Boot**: The core framework for building the application, providing features like dependency injection and auto-configuration.
- **Spring Security**: Used for securing the application and managing user authentication and authorization.
- **JWT (JSON Web Tokens)**: Employed for secure token-based authentication.
- **MySQL**: The database used for storing user data, transaction history, and asset information.
- **Maven**: The build tool used for managing project dependencies and building the application.

## Future Enhancements

- **Real-time Market Data**: Integrate real-time market data feeds to provide users with up-to-date information on asset prices.
- **Advanced Trading Features**: Implement features like stop-loss orders, limit orders, and trading analytics.
- **Mobile Application**: Develop a mobile version of the application to enhance accessibility for users on the go.

This project serves as a solid foundation for building a secure and scalable trading platform, leveraging the best practices in modern web application development.
