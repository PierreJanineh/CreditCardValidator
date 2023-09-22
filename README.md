# 🎴 CreditCardValidator

## 📌 Overview

The `CreditCardValidator` is a classic college JAVA project designed to validate credit card numbers. It can distinguish between fake and valid credit card numbers. The project utilizes both server and client sides to perform various functions and manipulations on user input.

## 🌟 Features

- **Luhn Validation**: Validates each credit card number using the Luhn algorithm to ensure it's a legitimate number.
  
- **Save Checked Cards**: After validation, the system can save the checked credit card numbers for future reference.
  
- **Export to TXT**: Provides functionality to export the validated credit card numbers to a `.txt` file for external use or backup.

## 🛠 Components

### 🖥 CCValidation_Client (Client Side)

The `CCValidation_Client` is the client-side component of the `CreditCardValidator` project. It is responsible for:

- **User Interface**: Provides an intuitive interface for users to input credit card numbers for validation.
  
- **Sending Requests**: Sends credit card numbers to the server for validation and receives validation results.
  
- **Displaying Results**: Showcases the validation results to the user, indicating whether a credit card number is valid or not.

### 🌐 CCValidation_Server (Server Side)

The `CCValidation_Server` is the server-side component of the `CreditCardValidator` project. It handles:

- **Receiving Requests**: Accepts credit card numbers sent from the client for validation.
  
- **Processing**: Uses the Luhn algorithm and other validation methods to determine the legitimacy of the credit card number.
  
- **Storing Data**: Has the capability to store validated credit card numbers for future reference.
  
- **Sending Responses**: Sends back the validation results to the client.

## 🚀 Getting Started

Follow these steps to set up and run the `CreditCardValidator` project on your local machine:

### 📋 Prerequisites

- Ensure you have Java Development Kit (JDK) installed. If not, download and install it from the [official Oracle website](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html).
  
- A Java Integrated Development Environment (IDE) like [IntelliJ IDEA](https://www.jetbrains.com/idea/) or [Eclipse](https://www.eclipse.org/downloads/).

### 🛠 Setup

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/PierreJanineh/CreditCardValidator.git
   ```
2. Navigate to the Project Directory:
    ```bash
    cd CreditCardValidator
    ```
3. Open the Project:
    Open the project using your preferred Java IDE.

### 🖥 Running the Server
1. Navigate to the CCValidation_Server directory in your IDE.
2. Locate the main server class (usually containing a main method) and run it. This will start the server and it should be waiting for client connections.

### 🖱 Running the Client
1. Navigate to the CCValidation_Client directory in your IDE.
2. Locate the main client class (usually containing a main method) and run it.
3. The client interface should appear, allowing you to input credit card numbers for validation.

### 🧪 Testing
1. Input a credit card number into the client interface and submit it for validation.
2. The server should process the request and send back a validation result, which will be displayed on the client interface.

## 🤝 Contributions
Feel free to fork the project, submit pull requests, or report any issues you encounter.
