# ATM Simulator

## Description

The current project was given as a technical test and is part of the evaluation process for the interviewing candidates of Excite Holidays.

## Build and Run

If Maven is installed use:  

`./mvn spring-boot:run`

If Maven is not installed use (needs Internet Connection for Maven Wrapper to download): 

`./mvnw spring-boot:run`

## Frameworks

The simulator uses Spring Framework for the dependency management as well as Spring Boot for a fast bootstrap of the application.

## Design pattern

The current simulation makes use of a behavioral design pattern known as Chain of Responsibility. 
Dispensers of 10$, 20$ and 50$ notes are all linked to each other (like a single linked list). 
Each withdraw request is delegated to the top-level dispenser (50$) which tries to fulfill it as much as possible. 
The remaining cash amount which cannot be withdrawn using 50$ is then delegated to the next dispenser in the chain (20$) and so on. 

The simulator tries to fulfill in a permutative way. 
This means it will go back and forth between the dispensers and try all possible permutations of notes which satisfy the request 
until it finds the most appropriate combination of notes (the combination with the fewest notes possible).

## Command Line Interface

When the simulator runs, it prompts an initialization action for the ATM initial cash. User can enter the number of 
10$, 20$ and 50$ notes as input.

After initialization is completed, the user can start "sending" withdrawal requests to the atm.

Press 'q' or 'quit' or 'exit' at any time to exit the application.
