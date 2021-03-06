# Reward-points-system
A system to manage a bonus point rewards on any purchase by customer.

## About

Our users have points in their accounts. Users only see a single balance in their accounts. But for reporting purposes we actually track their points per payer/partner. 

There are two rules for determining what points to "spend" first:

1) We want the oldest points to be spent first (oldest based on transaction timestamp, not the order they’re received)

2) We want no payer's points to go negative.


## Prerequisite Technologies

**Programming Language:** [Java](https://en.wikipedia.org/wiki/Java_(programming_language))

**Framework:** [Spring Boot](https://www.tutorialspoint.com/spring_boot/spring_boot_introduction.htm)

**Database:** [H2](https://en.wikipedia.org/wiki/H2_(DBMS))


**Tools:** IntelliJ, Swagger

## Links to download the Tools

[Java](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)

[IntelliJ](https://www.jetbrains.com/idea/download/#section=windows)


## How to run the project

Step 1: Setting up the tools
* Download Java and IntelliJ, Links are given above.

* [Set up java](https://www.youtube.com/watch?v=1ZbHHLobt8A) in local machine. [Set up java in IntelliJ](https://www.youtube.com/watch?v=L7IZ6Ckujbw). 

Step 2: Setting up the project
* Using "-git clone" clone the project into your local machine.

* Open IntelliJ. On top click on File->Open. Select the pom.xml in the project folder and select add as project. This will add project into your IntelliJ IDE.
 
* Run the project. 

Note: Generally the project starts at localhost:8080  but in case it does not, go to your console and see last second line it will show the port at which your porject is running.

Step 3: Testing the project
* To check the end point services [Swagger UI](http://localhost:8080/swagger-ui/). Go to fetch-rewards-controller and test all the API's.

## Sample Data
### Transactions
T1:{
    "payer": "DANNON",
    "points": 300,
    "timestamp": "2020-10-31T10:00:00Z"
}

T2:{
    "payer": "DANNON",
    "points": -200,
    "timestamp": "2020-10-31T15:00:00Z"
}

T3:{
    "payer": "UNILEVER",
    "points": 200,
    "timestamp": "2020-10-31T11:00:00Z"
}

T4:{
    "payer": "DANNON",
    "points": 1000,
    "timestamp": "2020-11-02T14:00:00Z"
}

T5:{
    "payer": "MILLER COORS",
    "points": 10000,
    "timestamp": "2020-11-01T14:00:00Z"
}
