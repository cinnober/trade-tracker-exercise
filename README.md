# trade-tracker-exercise

This is an assignment designed to test how much a candidate knows about code quality, object oriented principles, coding best practices, design patterns and written communication skills. The output of this assignment would be the base for evaluation and will be followed by a technical interview if qualified.

Project Specification:
1.	Design a REST API endpoint to store Trades (create a Trade object) in the system. The trade must contain quantity, price, buyer, seller, date and trade time (Consider buyer and seller as numeric ids. The existing 'User' object can also be used as a member, and the user Ids as member Ids, in-order to do that make sure users are added in the system.).
2.	Design a REST API endpoint to view trades for a member. The list should not include more than 10 items and also, the list should not include trades older than 3 days.
3.	Design any relevant code to ensure that the requirements in the above points are met.
4.	Assuming you have millions of trades every day, try to come up with the most efficient database schema in terms of storage space, latency and throughput. Write in-line comments to justify the design decisions taken.
5.	Write some unit and integration tests.

Instructions:
	Download the project from 
	Technical requirements for the project are java 1.8+, maven 3+, Dropwizard 0.9.2 (http://dropwizard.codahale.com/) , database h2 1.4.187
	Steps to build and install
o		Download and place the 'TradeTracker' directory to your project location.
o		From terminal (can also be referred as command prompt.) go to the TradeTracker project root directory.
o		'mvn eclipse:eclipse' for eclipse ide (find similar commands for other ide if required.)
o		Import the project into eclipse to see the source code.
o		'mvn package' (build and deploy)
o	'java -jar target/tradeTracker-0.0.1-SNAPSHOT.jar db migrate tradetracker.yml' (create/migrate database)
o	'java -jar target/tradeTracker-0.0.1-SNAPSHOT.jar server tradetracker.yml' (start server)
o	Open another terminal and go to the TradeTracker project root directory.
