# cardverification

A Java/Springboot-based REST API for testing carn numbers and number of hits on the URL
  
  ## Requirements
  
  For building and running the application you need:
  
  - [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
  - [Maven 3](https://maven.apache.org)
  - [IntelliJ IDE](https://www.jetbrains.com/idea/download/#section=windows) or any other Java based IDE
  - Install Lombok in your IDE 


## Getting Started

1. **Clone the application**

	```bash
	git clone https://github.com/victor-onu/cardverification.git
	cd cardverification
	```

2. **Run the app**

	There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `com.victor.cardverification.CardVerificationApplication` class from your IDE.
    
    Alternatively you can run the following command

	```bash
	mvn spring-boot:run
	```

	The server will start on port 8080.


## Testing the endpoints on Postman

# Get
**Retrieves the card information if available by specifiying the card number - (We used 45717361 as example below)**

	localhost:8080/card-scheme/verify/45717361

# Get
**Retrieves the number of hits on the url above and details of card number**

	localhost:8080/card-scheme/stats?start=1&limit=3


## Built With

  - Java
  - Springboot
  
## Testing the app on browser

   - Use this [link](https://verifycard.herokuapp.com/card-scheme/verify/45717361) to test a card. You can change the card number in the URL
   - Use this [link](https://verifycard.herokuapp.com/card-scheme/stats?start=0&limit=1) to check the number of hits and the details. You can change the start and limit valuesmin your URL

