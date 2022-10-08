# Rewards Calculator by Raja Vontela
## _Rewards formula: each $ >50 and <=100 will get 1 point & each $ >100 will get 2 points._

This is a REST implementation using Spring.
Tools used:
- Build tool: Maven
- IDE: STS 4
- Spring Boot
- H2 In-Memory DB

## Steps to load and run the application in IDE

- Clone this repo into you preferred IDE
- Run the cloned application as Spring Boot App.

## Testing the working of the application

- Once the app is up and running, open Postman or any preferred REST api testing tool. If nothing is available use browser
- Do a GET on URL: http://localhost:8991/rewardTotal/customer/1/last3Months, this should result in a successful response with reward points.
- Do a GET on URL: http://localhost:8991/rewardTotal/customer/4/last3Months, this should result in a zero response as this customer has no transactions.
- Do a GET on URL: http://localhost:8991/rewardTotal/customer/8/last3Months, this should result in a zero response even though this customer has transactions but not within last 3 months.
- Do a GET on URL: http://localhost:8991/rewardTotal/customer/21/last3Months, this should result in a error response as the customer does not exist.
- Do a GET on URL: http://localhost:8991/rewardTotal/customer/10/last3Months, this should result in a success response but only include transaction within last 3 months.
- To explore date from the H2 database please use this URL: http://localhost:8991/console, JDBC URL: jdbc:h2:mem:testdb, username: sa, password: password.

## Other facts

- Logging is done to inform about what is being processed behind the scenes.
- Application level error handling is applied.
- Java 8 features were used.
