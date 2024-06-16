# Risk


## Description
Risk is a popular boardgame with the goal to conquer the whole world. During a university-project, we implemented a video game version of Risk. We aimed to keep all essential rules of the game while making some adjustments to the rulebook. This way we adapted to the special requirements of video games and improved the UX. The game can be played as an online multiplayer game (as long as all payers are connected to the same network) or as an offline game with AI players (with various difficulty levels).

## Installation and usage
The Project is built with Java 19. The project uses Maven for dependency and build management. Notable dependencise required for the application to run properly are JavaFx by openjfx, JUnit, and Mockito.
After installing Maven, run 'mvn install' in your Command Line to assemble the JAR file. When completed, you can execute the JAR file in the 'target' folder.

## Structure

### ai package

The ai package includes classes for generating and executing AI moves, as well as classes for evaluating the game state und determining the best moves to make.

### database package

The database package contains the code for storing and retrieving game data. This includes classes for reading and writing game data to an SQL file, as well as interacting with the database. The database is used to track and store player accounts and statistics.

### game package

The game package contains the code for managing the game state. This includes classes for initializing the game, executing game actions, and determining when the game is over.

### gamePhases package

The gamePhases package contains the code for the five game phases and their sub-phases. The phases are initially claiming countries, exchanging cards, placing troops, attacking, and moving troops. Each phase has a class assigned to it, which is responsible for managing the actions that can be taken during that ühase. Phases 2-5 occur in each round whereas phase 1 only occurs at the very beginning of the game

### gui package

The gui package contains the controllers for the graphical user interface. This includes classes for displaying the game board, the game state, and handling user inputs.

### network package

The network package contains the code for multiplayer games via the network. This includes classes for sending and receiving messages over the network, and classes for managing multiple players in a networke game.

## Authors and acknowledgment
As this was a group-project, I don't take credit for all of the code. My main topics were the UX/UI Design of the game. Since the original repository has been deleted and I had to manually insert our code into this repository, I couldn't preserve the actual contributions.  
The other authors are:
- Hannes Neumann
- Leonard Küch ([@leonardkuech](https://github.com/leonardkuech))
- Lukas Greiner
- Florian Bauer ([@floribau])
- Valentin Stoll

## License
All images used in the project are free of copyright and royalty. The images as well as the project as a whole may not be used or sold in any commercial way.
