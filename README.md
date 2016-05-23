# trade-tracker-exercise #

This assignment is to find out the candidates knowledge about code quality, object oriented principles, coding best practices, design patterns and written communication skills. The output of this assignment would be the base for evaluation and will be followed by a technical interview if qualified.

### Project Specification:
- Design a REST API endpoint to store Trades (create a Trade object) in the system. The trade must contain quantity, price, buyer, seller, date and trade time (Consider buyer and seller as numeric ids. The existing 'User' object can also be used as a member, and the user Ids as member Ids, in-order to do that make sure users are added in the system.).
- Design a REST API endpoint to view trades for a member. The list should not include more than 10 items and also, the list should not include trades older than 3 days.
- Design any relevant code to ensure that the requirements in the above points are met.
- Assuming you have millions of trades every day, try to come up with the most efficient database schema in terms of storage space, latency and throughput. Write in-line comments to justify the design decisions taken.
- Write some unit and integration tests.

### Instructions:
- Download this project.
- Technical requirements for the project are java 1.8+, maven 3+, Dropwizard 0.9.2 (http://dropwizard.codahale.com/) , database h2       1.4.187
- Steps to build and install
    - Download and place the 'TradeTracker' directory to your project location.
    - From terminal (can also be referred as command prompt.) go to the TradeTracker project root directory.
    - 'mvn eclipse:eclipse' for eclipse ide (find similar commands for other ide if required.)
    - Import the project into eclipse to see the source code.
    - 'mvn package' (build and deploy)
    - 'java -jar target/tradeTracker-0.0.1-SNAPSHOT.jar db migrate tradetracker.yml' (create/migrate database)
    - 'java -jar target/tradeTracker-0.0.1-SNAPSHOT.jar server tradetracker.yml' (start server)
    - Open another terminal and go to the TradeTracker project root directory.
    - 'java -cp target/tradeTracker-0.0.1-SNAPSHOT.jar com.cinnober.tradetracker.LoadData' (To load initial test data into the system. With current configuration the load Data script sets the business date for the system.)
    - From web browser (preferably chrome) type 'http://localhost:8080/globalproperties'. This provides information about the current business date of the system.

