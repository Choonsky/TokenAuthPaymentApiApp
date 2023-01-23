# Token-based authorization and payment API application

## Table of contents

* [General info](#general-info)
* [Technologies](#technologies)
* [Setup](#setup)

## General info

This application accepts login attempts, gives a web token and verifies it while making payments.

## Technologies

Project is created with:

* Java 17 (Oracle OpenJDK)
* Maven 3.8.1
* H2 DB 2.1.214
* Spring Boot 3.0.1
* Spring 6.0.3
* Spring Security 6.0.1
* Java JWT: JSON Web Token for Java and Android (io.jsonwebtoken) 0.11.5
* Spring Web 
* Log4J
* JUnit 
* Joda money 1.0.3
* Brute Force prevention (AuthenticationFailureListener, 
  AuthenticationSuccessEventListener and LoginAttemptService)

## Setup

To test this project locally:

* Clone this project using "git clone https://github.com/Choonsky/TokenAuthPaymentApiApp"
* Navigate to the program folder ("cd TokenAuthPaymentApiApp")
* Run a Spring Boot app using "mvn spring-boot:run"

* Open "http://localhost:8080/api/test" in your browser to see that service is working. You can as well send test 
  request with Postman or anything, POST and GET supported. 
* Take test JSON file at static/test.json for all these requests.
* Send test credentials (user:user1, password:password1, they are in test JSON file) to 
  "http://localhost:8080/api/login" endpoint to get an error, because users DB is empty yet.
* Send signup request to http://localhost:8080/api/signup with the same credentials.
* Now send right credentials to "http://localhost:8080/api/login" to get a token (you can see it in as a Cookie)
* Send request to "http://localhost:8080/api/payment" endpoint including token to get payment done (you will see a 
  balance, initial amount is $8, each payment is $1.1)
* Send some more payment requests till the funds is over.
* Send the same credentials (user:user1, password:password1) to "http://localhost:8080/api/login" endpoint again 
  from another IP to get next token (first token will still be actual till expire time, which is 30 minutes by 
  default). Multisession is made by including original IP address (X-Forwarded-For header) in JWT claims.
* Send request to "http://localhost:8080/api/logout" endpoint including token to make this token invalid (test it with 
  requesting "http://localhost:8080/api/payment" with this token again)
* Additionally, if you send wrong credentials 10 times from the same IP address, this IP will be blocked for 24 hours.
  (brute force attack prevention)
