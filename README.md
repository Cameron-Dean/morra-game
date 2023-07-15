# Morra Game

This repository is my implementation of Assignment 2 in the course SOFTENG 281: Object-Oriented Programming at the University of Auckland in Semester One 2023.
The primary learning objective of this assignment was to apply object-oriented design patterns.

My code is located in the [src/main/java/nz/ac/auckland/se281/](src/main/java/nz/ac/auckland/se281) folder, with the exception of the Main.java, MessageCli.java, and Utils.java files, which were provided.


### Functionality

[Morra](https://en.wikipedia.org/wiki/Morra_(game)) is an old Italian hand game that dates back thousands of years to ancient Roman and Greek times.
The game is quite simple: it is played by two players.
Both players simultaneously show a number of fingers (between 1 and 5) and simultaneously call out a guess of the sum of fingers displayed by both players.
If a player’s guess is equal to the actual sum, this player scores a point.
If both players guess the actual sum, neither of them scores a point.
The game continues until one player reaches a predetermined score.

This version uses an “AI” to play against the player.
The difficulty level (implemented using the Factory design pattern) can be easy, medium, hard, or master, and controls the strategy of the “AI”.
The strategy used by the “AI” is implemented using the Strategy design pattern.

The simple command line morra game can:
- Start a new Morra game with a difficulty level (easy, medium, hard, or master) and a number of points to win
- Play a round of the game where the player must input a number of fingers and guess the sum
- Show the statistics of the current game (how many points the player and “AI” have, and how many more are needed to win)


### Marking

The marking scheme included marks for functionality, object-oriented design, and code style. This achieved 100% for all sections.
