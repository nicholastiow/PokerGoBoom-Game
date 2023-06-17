# Part 2

## A. Member Contributions

No | ID         | Name                 | Task descriptions  | Contribution %
-- | ---------- | -------------------- | ------------------ | --------------
1  | 1211101699 | Low Kai Yan          | Feature no. 7 & 8  | 100%
2  | 1211102398 | Nicholas Tiow Kai Bo | Feature no. 3 & 4  | 100%
3  | 1211101452 | Wong Jui Hong        | Feature no. 5 & 6  | 100%
4  | 1211102080 | Wong Wei Ping        | Feature no. 1 & 2  | 100%


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

https://github.com/nicholastiow/TCP1201_TT8L_G7_Assignment

