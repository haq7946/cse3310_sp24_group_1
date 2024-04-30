# TicTacToe

```bash
% export JAVA_HOME=/usr/lib/jvm/java-11-openjdk-11.0.18.0.9-0.3.ea.el8.x86_64
% mvn clean
% mvn compile
% mvn package
% mvn exec:java -Dexec.mainClass=uta.cse3310.App
```

TESTING INFORMATION: 
REVISED 04/30/2024 

The whole game test performs a test on the game in its entirety. 
The test can be invoked without setting the TEST_GRID environmental variable. 

If the environmental variable is not set the test only performs basic functionality of the lobby and game mechanics, as well as messaging between players. This includes Lobby Chat and Game Chat. Additionally, it performs color checking and basic information on the room size/room functionality. 

There are two main tests 

1.TEST_GRID=2 

The first test can be invoked by setting the TEST_GRID variable to 2 (export TEST_GRID=2) --Can be set like this in the command line (Linux).

The first test ensures that the game is working properly. Players can join the lobby, create room, join game room, send chat messages, and play a game. 
It simulates two players playing a game in the same room. The players select words that are oriented all ways as per the requirement. (Horizontal, Vertical Up, Vertical Down, Diagonal Up, Diagonal Down). The test finishes by checking the exit conditions and the game winner. 


2.TEST_GRID=3 

The second test can be invoked by setting the TEST_GRID variable to 3 (export TEST_GRID=3) --Can be set like this in the command line (Linux).

The second test ensures that the game can support multiple users playing at the same time in multiple rooms. This test is like the first test except two games are being played at the same time. (3 players join room 1) (2 players join room 2). 


Information on deployment:

https://www.programonaut.com/how-to-deploy-a-git-repository-to-a-server-using-github-actions/
