import java.util.*;

public class GoBoomGame {
    private List<Card> deck;
    private List<Card> centerDeck;
    private List<Player> players;
    private List<Card> centerCards;
    private int currentPlayerIndex;
    private int trickNumber = 1;
    private int largestCardValue = -1;
    private Card leadCard;

    public GoBoomGame() {
        initializeGame();
    }

    // ----------------------------------------------------- Initialize Game Methods -----------------------------------------------------

    private void initializeGame() {
        // Create a new deck of 52 cards
        // deck.clear();
        trickNumber = 1;
        largestCardValue = -1;

        deck = createDeck();

        // Shuffle the deck
        Collections.shuffle(deck);

        // Initialize the players
        players = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            players.add(new Player("Player" + (i + 1)));
        }

        // Initialize the center cards list
        centerCards = new ArrayList<>();
        centerDeck = new ArrayList<>();

        // Deal cards to each player
        dealCards();

        currentPlayerIndex = determineFirstPlayerIndex();
    }

    // ----------------------------------------------------- Play Game Methods -----------------------------------------------------

    public void playGame() {
        // Print Card Shortcuts
        System.out.println("c = club");
        System.out.println("d = diamond");
        System.out.println("h = heart");
        System.out.println("s = spade");

        Scanner scanner = new Scanner(System.in);

        int turn = 0;
        int winnerIndex = -1;
        while (!isGameOver()) {
            // Display current state of the game
            displayGameState();


            // Check if the player hand is empty
            boolean checkWin = false;
            for(Player player: players) {
                if(player.getHand().size() == 0) {
                    checkWin = true;
                }
            }

            if(checkWin) {
                turn = 4;
            } else {
                // System.out.println(leadCard.toString() + " is the lead card.");
                System.out.print("> ");
                String input = scanner.nextLine();

                if(!input.equals("s") && !input.equals("d") && !input.equals("x")) {
                    if(input.length() <= 1) {
                        System.out.println("Invalid input. Please enter a valid input.");
                        continue;
                    }
                }

                Boolean found = false;
                while(!found && !input.equals("d") && !input.equals("x") && !input.equals("s")) {
                    boolean isValidCard = false;

                    for (Card card : players.get(currentPlayerIndex).getHand()) {
                        if (card.toString().equals(input)) {
                            isValidCard = true;
                            break;  
                        }
                    }
                    
                    // Check whether the trick is the first trick cause the lead card is open by system
                    if(trickNumber == 1) {
                        if(input.charAt(0) != leadCard.getSuit().toString().charAt(0) && input.charAt(1) != leadCard.getRank().toString().charAt(0)) {
                            isValidCard = false;
                        }
                    } 
                    
                    if(centerCards.size() > 0 && trickNumber != 1) {
                        leadCard = centerCards.get(0);
                        if(input.charAt(0) != leadCard.getSuit().toString().charAt(0) && input.charAt(1) != leadCard.getRank().toString().charAt(0)) {
                            isValidCard = false;
                        }
                    }

                    if (!isValidCard) {
                        System.out.println("Invalid input. Please enter a valid input.");

                        System.out.print("> ");
                        input = scanner.nextLine();
                        while(input.length() <= 1 && (!input.equals("s") && !input.equals("d") && !input.equals("x"))) {
                            System.out.println("Invalid input. Please enter a valid input.");
                            System.out.print("> ");
                            input = scanner.nextLine();
                        }
                    } else {
                        found = true;
                    }
                }


                Player currentPlayer = players.get(currentPlayerIndex);

                if(input.equals("d")) {
                    if(centerDeck.size() == 0) {
                        System.out.println("No cards left in the deck. Skipping turn.");
                        currentPlayerIndex = (currentPlayerIndex + 1) % 4;
                        turn++;

                        //If none of the players play any card in that round. It will show not follow to the game rule
                        if (centerCards.size() == 0 && turn == 4) {
                            turn = 0;
                            System.out.println("ATTENTION: Please play according to the rules. Continue playing.");
                            continue;
                        }
                    } else {
                        currentPlayer.addToHand(centerDeck.get(0));
                        centerDeck.remove(0);
                    }

                } else if(input.equals("s")) {
                    System.out.print("\033[H\033[2J"); // Clear the console
                    GoBoomGame game = new GoBoomGame();
                    game.playGame();

                } else if(input.equals("x")) {
                    System.exit(0);
                } else {
                    turn++;

                    Card playedCard = currentPlayer.playCard(leadCard, input);

                    currentPlayer.removeFromHand(playedCard);

                    // Add the played card to the center cards
                    centerCards.add(playedCard);

                    winnerIndex = checkLargerCard(playedCard, winnerIndex);

                    // Determine the next player
                    currentPlayerIndex = (currentPlayerIndex + 1) % 4;
                }
            }
            // Check if a trick has ended
            if (turn == 4) {
                turn = 0;

                if(winnerIndex != -1) {
                    System.out.println("\n*** Player" + (winnerIndex + 1) + " wins Trick #" + trickNumber + "! ***");
                }
                
                // Clear the center cards
                centerCards.clear();
                
                // Set the next player as the winner of the trick
                currentPlayerIndex = winnerIndex;
                
                // Increment the trick number
                trickNumber++;

                if(checkWin) {
                    for(Player player: players) {
                        int sum = player.getScore();
                        List<Card> calculateScore = player.getHand();
                        for(Card card: calculateScore) {
                            sum += card.getRank().getValue();
                        }
                        player.setScore(sum);
                    }
                    // Restart game but players remain
                    nextGame();
                }
                winnerIndex = -1;
                largestCardValue = -1;
            }
        }
        // Display the final results
        displayResults();
    }

    

    // ----------------------------------------------------- Display Methods -----------------------------------------------------

    private void displayGameState() {
        System.out.println("\n\nTrick #" + trickNumber);
    
        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            List<Card> hand = player.getHand();
            System.out.print("Player" + (i + 1) + ": ");

            System.out.println(hand.toString());
        }
    
        System.out.print("Center: ");
        System.out.print("[ ");
        if (trickNumber == 1) {
            System.out.print(leadCard.toString() + " ");
        }
        for(Card card: centerCards) {
            System.out.print(card.toString() + " ");
        }
        System.out.println("]");

        System.out.print("Deck: ");
        System.out.println(centerDeck.toString());


        System.out.println("Score: " + getScoresAsString());
        System.out.println("Turn: " + getCurrentPlayer().getName());
    }

    private void displayResults() {
        // Display the final results of the game, including players' scores

        int smallestIndex = -1;

        int smallestScore = Integer.MAX_VALUE;

        System.out.println("Score:");
        for(Player playerCheck: players) {
            System.out.println("Player" + (players.indexOf(playerCheck) + 1) + ": " + playerCheck.getScore());
        }

        for(Player player: players) {
            if(player.getScore() < smallestScore) {
                smallestScore = player.getScore();
                smallestIndex = players.indexOf(player);
            }
        }
        System.out.println("********************************************************************");
        System.out.println("Player" + (smallestIndex + 1) + " wins the game!");
    }

    // ----------------------------------------------------- Get Methods -----------------------------------------------------

    private String getScoresAsString() {
        StringBuilder scores = new StringBuilder();
        for (Player player : players) {
            scores.append(player.getName()).append(" = ").append(player.getScore()).append(" | ");
        }
        scores.setLength(scores.length() - 3); // Remove the last " | "
        return scores.toString();
    }
    

    private Player getCurrentPlayer() {
        // Return the current player
        return players.get(currentPlayerIndex);
    }

    // ----------------------------------------------------- Checking Methods -----------------------------------------------------

    private int checkLargerCard(Card card, int winnerIndex) {
        // Check if the card is larger than the current largest card
        if(card.getRank().getValue() > largestCardValue) {
            largestCardValue = card.getRank().getValue();
            return currentPlayerIndex;
        } else {
            return winnerIndex;
        }
    }

    private void nextGame() {
        // Reset the game state for the next game
        trickNumber = 1;
        largestCardValue = -1;
        
        // Create a new deck of 52 cards
        deck = createDeck();

        // Shuffle the deck
        Collections.shuffle(deck);

        // clear cards in hand
        for (Player player: players) {
            player.getHand().clear();
        }

        // Initialize the center cards list
        centerCards = new ArrayList<>();
        centerDeck = new ArrayList<>();

        // Deal cards to each player
        dealCards();

        currentPlayerIndex = determineFirstPlayerIndex();
    }

    private boolean isGameOver() {
        // Check if any player has reached the 100 score or if the deck is empty
        for(Player player: players) {
            if(player.getScore() >= 100) {
                return true;
            }
        }
        return false;
    }

    // ----------------------------------------------------- Automate Methods -----------------------------------------------------

    private List<Card> createDeck() {
        List<Card> deck = new ArrayList<>();
        // Add cards to the deck
        for(Suit suit: Suit.values()) {
            for(Rank rank: Rank.values()) {
                deck.add(new Card(suit, rank));
            }
        }
        return deck;
    }

    private void dealCards() {
        int numPlayers = players.size();
        int cardsPerPlayer = 7;
    
        // Shuffle the deck
        Collections.shuffle(deck);
        System.out.println("Shuffled Deck: " + deck.toString());
        
        // Initialize the lead card
        leadCard = deck.get(0);
    
        // Distribute cards to each player
        int currentCardIndex = 1;

        for (int i = 0; i < cardsPerPlayer; i++) {
            for (int j = 0; j < numPlayers; j++) {
                Player player = players.get(j);
                Card card = deck.get(currentCardIndex);
                player.addToHand(card);
                currentCardIndex++;
            }
        }

        
        // Add the remaining cards to the center cards list
        for(int i = currentCardIndex; i < deck.size(); i++) {
            centerDeck.add(deck.get(i));
        }
    }
    

    private int determineFirstPlayerIndex() {
        Card leadcard = leadCard; // Replace with the actual lead card
    
        // Determine the index of the first player based on the lead card
        if (leadcard.getRank() == Rank.ACE || leadcard.getRank() == Rank.FIVE ||
        leadcard.getRank() == Rank.NINE || leadcard.getRank() == Rank.KING) {
            return 0; // Player 1
        } else if (leadcard.getRank() == Rank.TWO || leadcard.getRank() == Rank.SIX ||
            leadcard.getRank() == Rank.TEN) {
            return 1; // Player 2
        } else if (leadcard.getRank() == Rank.THREE || leadcard.getRank() == Rank.SEVEN ||
            leadcard.getRank() == Rank.JACK) {
            return 2; // Player 3
        } else if (leadcard.getRank() == Rank.FOUR || leadcard.getRank() == Rank.EIGHT ||
            leadcard.getRank() == Rank.QUEEN) {
            return 3; // Player 4
        }
    
        return 0; // Default to player 1 if no matching rank is found
    }

    public static void main(String[] args) {
        // Create a new GoBoomGame instance and start the game
        GoBoomGame game = new GoBoomGame();
        game.playGame();
    }
}
