# Token-based authorization and payment API application

## Table of contents

* [General info](#general-info)
* [Technologies](#technologies)
* [Setup](#setup)

## General info

This application accepts login attempts, gives a web token and verifies it while making payments.

## Technologies

Project is created with:

* Java 19 (Oracle OpenJDK)
* Maven 3.8.1
* H2 DB 2.1.214
* Spring Boot 3.0.1
* Spring 6.0.3
* Spring Security 6.0.1
* JWT 0.9.1
* Spring Web 
* Log4J
* JUnit
* Joda money

## Setup

To test this project locally:

* Clone this project using "git clone https://github.com/Choonsky/TokenAuthPaymentApiApp"
* Navigate to the program folder ("cd TokenAuthPaymentApiApp")
* Run a Spring Boot app using "mvn spring-boot:run"

* Open "http://localhost:8080/api/test" in your browser to see that service is working
* Send test credentials (user:user1, password:password1) to "http://localhost:8080/api/login" endpoint to get a token
* Send wrong credentials to "http://localhost:8080/api/login" endpoint to get an error
* Try to send right username (user1) with wrong password more than 3 times to get blocked for a minute (brute force 
  attack prevention)
* Send request to "http://localhost:8080/api/payment" endpoint including token to get payment dane (you will see a 
  balance, initial amount is $8, each payment is $1.1)
* Send test credentials (user:user1, password:password1) to "http://localhost:8080/api/login" endpoint again to get next 
  token (first token will still be actual till expire time, which is 30 minutes by default)
* Send request to "http://localhost:8080/api/logout" endpoint including token to make this token invalid (test it with 
  requesting "http://localhost:8080/api/payment" with this token again)
* Additionally, you can make new user by sending request to http://localhost:8080/api/signup!