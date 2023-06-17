# GUIPokerGoBoom

# Part 2

## A. Member Contributions

No | ID         | Name                 | Task descriptions | Contribution %
-- | ---------- | -------------------- | ----------------- | --------------
1  | 1211101699 | Low Kai Yan          |                   |  100
2  | 1211102398 | Nicholas Tiow Kai Bo |                   |  100                 
3  | 1211101452 | Wong Jui Hong        |                   |  100                
4  | 1211102080 | Wong Wei Ping        |                   |  100                 


## B. Part 1 Feature Completion (Latest)

Mark Y for Complete, P for Partial done, N for Not implemented.

No | Feature                                                                         | Completed (Y/P/N)
-- | ------------------------------------------------------------------------------- | -----------------
1  | All cards should be faced up to facilitate checking.                            |  Y
2  | Start a new game with randomized 52 cards.                                      |  Y
3  | The first card in the deck is the first lead card and is placed at the center.  |  Y
4  | The first lead card determines the first player.                                |  Y
5  | Deal 7 cards to each of the 4 players.                                          |  Y
6  | All players must follow the suit or rank of the lead card.                      |  Y
7  | The highest-rank card with the same suit as the lead card wins the trick.       |  Y
8  | The winner of a trick leads the next card.                                      |  Y



## C. Part 2 Feature Completion

Mark Y for Complete, P for Partial done, N for Not implemented.

No | Feature                                                                                                                               | Completed (Y/P/N)
-- | ------------------------------------------------------------------------------------------------------------------------------------- | -----------------
1  | If a player cannot follow suit or rank, the player must draw from the deck until a card can be played.                                |  Y                                                       
2  | When the remaining deck is exhausted and the player cannot play, the player does not play in the trick.                               |  Y
3  | Finish a round of game correctly. Display the score of each player.                                                                   |  Y
4  | Can exit and save the game (use file or database).                                                                                    |  Y
5  | Can resume the game. The state of the game is restored when resuming a game (use file or database).                                   |  Y
6  | Reset the game. All scores become zero. Round and trick number restart from 1.                                                        |  Y
7  | Support GUI playing mode (cards should be faced up or down as in the real game). The GUI can be in JavaFX, Swing, Spring, or Android. |  Y
8  | Keep the console output to facilitate checking. The data in console output and the GUI must tally.                                    |  Y

## Use Appropriate Data Structures
1) Array or List [Lots of data store in List] -> [GoBoomGame.java]
2) Set [Hashset store saved file directory] -> [FileListView.java]
3) Map [Store the position for player played card] -> [GoBoomGame.java]

## D. Link to Part 2 GitHub Repo

[LINK TO PART 2 POKER GOBOOM GAME GUI](https://github.com/Low0000/GUIPokerGoBoom)


## Description
GUIPokerGoBoom is a desktop Poker game developed using Java. It provides an interactive and enjoyable gaming experience.

## Requirements
To compile and run this game, you will need:
- Editor: Visual Studio Code
- Compiler: OpenJDK version "17.0.6" (LTS) - Zulu17

You can download the Zulu17 compiler [here](https://www.azul.com/downloads/?version=java-17-lts&architecture=x86-64-bit&package=jdk-fx#zulu).
1. **Screenshot VSCODE**: JAVAFX JDK MOVE ALL THE .jar file in ./lib/ into the VSCODE Referenced Library
![Screenshot_8](https://github.com/Low0000/GUIPokerGoBoom/assets/123613860/4509f7eb-ff8d-4f29-94d5-7a4a96ada978)

## Game Preview
1. **Screenshot 1**: The Main Page Of The Game.
   ![Screenshot 1](https://github.com/Low0000/GUIPokerGoBoom/assets/123613860/6cf5ab63-b45b-487a-b6de-0fffabd37403)

2. **Screenshot 2**: Start a new game OR load progress of saved game.
   ![Screenshot 2](https://github.com/Low0000/GUIPokerGoBoom/assets/123613860/f085c920-265a-404f-9893-581a60d9cd1a)

3. **Screenshot 3**: List Game Progress That Had Saved Before. Able To Restore / Delete Saved Game / start A New Game / EXIT
   ![Screenshot 3](https://github.com/Low0000/GUIPokerGoBoom/assets/123613860/8c10c2ed-b0d4-481f-a81e-278df7997129)

4. **Screenshot 4**: Game Play Display
   ![Screenshot 4](https://github.com/Low0000/GUIPokerGoBoom/assets/123613860/3ca14caa-ce9c-4dd2-946f-6be0eb7c830c)

5. **Screenshot 5**: Player Choose Card To Play
   ![Screenshot 5](https://github.com/Low0000/GUIPokerGoBoom/assets/123613860/1e381aac-ef7b-4b1b-ba12-db98a526ba8a)

6. **Screenshot 6**: View Card On Hand For Each Players
   ![Screenshot 6](https://github.com/Low0000/GUIPokerGoBoom/assets/123613860/5063af97-b8ef-48c1-8b08-35793d275a03)

7. **Screenshot 7**: Save Game
   ![Screenshot 7](https://github.com/Low0000/GUIPokerGoBoom/assets/123613860/2df10fcf-eb32-479f-bfc3-d6a40b2c4c73)

## How to Use
1. Download and install the Zulu17 compiler from the provided link.
2. Open the project in Visual Studio Code.
3. Add all the .jar file in ./lib/ directory into the VSCode Referenced Library
4. Compile the Java code using the Zulu17 compiler.
5. Compile Command: ***javac GoBoomGame.java***
6. Run the compiled code to start the game.
7. Run compiled code command: ***java GoBoomGame***
8. Follow the on-screen instructions to play the game.

Enjoy playing Poker Go Boom!

Note: Make sure to have Java installed on your system and all the necessary requirements have been fulfilled before running the game.
