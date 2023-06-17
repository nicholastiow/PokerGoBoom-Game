import java.util.List;
import java.util.Map;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CardGridWindow extends Stage {

    private List<Player> players;
    private List<Card> centerDeck;
    private int currentPlayer;
    private Map<Integer, Card> centerCards;
    private List<Card> cardRealIndex;
    private Card leadCard;
    private int trickNumber;

    MusicPlayer musicPlayer = new MusicPlayer();

    AudioAsset aa = new AudioAsset();


    public CardGridWindow(List<Player> players, List<Card> centerDeck, Map<Integer, Card> centerCards, List<Card> cardRealIndex, Card leadCard, int currentPlayer, int trickNumber) {
        this.players = players;
        this.centerDeck = centerDeck;
        this.centerCards = centerCards;
        this.cardRealIndex = cardRealIndex;
        this.leadCard = leadCard;
        this.currentPlayer = currentPlayer;
        this.trickNumber = trickNumber;

        
        setTitle(players.get(currentPlayer).getName() + "'s turn"); // Set the stage title


        // Create a FlowPane to display the card images
        FlowPane flowPane = new FlowPane();
        flowPane.setPadding(new Insets(10));
        flowPane.setHgap(10);
        flowPane.setVgap(10);    
        flowPane.setPrefHeight(420);
        flowPane.setPrefWidth(720);


        Image backImage = new Image("./images/back4.jpg");
        BackgroundImage background = new BackgroundImage(backImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        flowPane.setBackground(new Background(background));
        
        setOnCloseRequest(event -> {
            event.consume();
            createAlert("Invalid", null, "Please select a card! And the window will close automatically!").showAndWait();
        });

        HBox topRow = new HBox();

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(new VBox(topRow, flowPane));
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        // Add the card images to the flow pane
        refreshContent(topRow,flowPane, cardRealIndex);

        // Create a Scene with the FlowPane and set it on the Stage
        setResizable(false);
        Scene cardGridScene = new Scene(scrollPane);
        setScene(cardGridScene);
        
    }

    private void refreshContent(HBox topRow,FlowPane flowPane, List<Card> cardRealIndex) {
        
        flowPane.getChildren().clear(); // Clear the existing content
        
        StackPane deckCardPane = createCardPane("./images/back.png",0.0); 
        Label label = new Label("Deck: \n" + Integer.toString(centerDeck.size()) + " cards left"); 
        label.setStyle("-fx-font-weight: bold; -fx-font-size: 22; -fx-text-fill: white;");

        topRow.getChildren().addAll(deckCardPane, label);

        deckCardPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                // Handle deck card click event
                if(centerDeck.size() == 0) {
                    createAlert("Invalid Card", null, "No card in the deck!").showAndWait();
                } else {
                    Player player = players.get(currentPlayer);
                    List<Card> hand = player.getHand();    
                    hand.add(centerDeck.get(0));
                    centerDeck.remove(0);
                }
                
                refreshContent(topRow ,flowPane, cardRealIndex); // Refresh the content
            }
        });

        if(centerDeck.size() == 0) {   
            
            Button skipButton = new Button("Skip Turn!");
            skipButton.setStyle("-fx-font-size: 14pt; -fx-font-weight: bold; -fx-background-color: white; -fx-text-fill: black;"); 
            skipButton.setPrefWidth(160);
            skipButton.setPrefHeight(70);
            skipButton.setWrapText(true);
            skipButton.setPadding(new Insets(10));

            skipButton.setOnMouseEntered(event -> {
                musicPlayer.setFile(aa.hover);
                musicPlayer.play(aa.hover);
                skipButton.setStyle("-fx-background-color: #ff0000; -fx-text-fill: white; -fx-font-size: 14pt; -fx-font-weight: bold;");
            });

            skipButton.setOnMouseExited(event -> {
                skipButton.setStyle("-fx-background-color: white; -fx-text-fill: black; -fx-font-size: 14pt; -fx-font-weight: bold;");
            });

            flowPane.getChildren().addAll(deckCardPane, label,skipButton);

            //Click on skip button
            skipButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    // Handle deck card click event

                    //Create a new blank Card with rank "blank" and suit ("",0)
                    Card blankCard = new Card(Suit.BLANK, Rank.BLANK);
                    cardRealIndex.set(currentPlayer, blankCard);
                    centerCards.put(currentPlayer, blankCard);
                    close();
                }
            });
            
        } else {
            deckCardPane.setVisible(true);
            label.setVisible(true);
            flowPane.getChildren().addAll(deckCardPane, label);
        }


        // Add the player's hand cards below the top row
        Player player = players.get(currentPlayer);
        List<Card> hand = player.getHand();
    
        for (int i = 0; i < hand.size(); i++) {
            StackPane cardPane;
            if(trickNumber != 1 && centerCards.size() == 0) {
                cardPane = createCardPane(hand.get(i).toString(), 0.0);
                setCardBehaviour(cardPane, "yellow");

            } else if(hand.get(i).getRank() != leadCard.getRank() && hand.get(i).getSuit() != leadCard.getSuit()) {
                cardPane = createCardPane(hand.get(i).toString(), -0.5);
                setCardBehaviour(cardPane, "red");

            } else {
                cardPane = createCardPane(hand.get(i).toString(), 0.0);
                setCardBehaviour(cardPane, "yellow");
            }
            
            String cardName = hand.get(i).toString();

            

            cardPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    // Handle card click event
                    
                    Card card = player.playCard(cardName);


                    if(trickNumber != 1 && centerCards.size() == 0) {
                        cardRealIndex.set(currentPlayer, card);
                        flowPane.getChildren().remove(cardPane);
                        centerCards.put(currentPlayer, card);
                        player.removeFromHand(card);                        
                        // Close this window
                        close();
                    } else {
                        
                        if(card.getRank() != leadCard.getRank() && card.getSuit() != leadCard.getSuit()) {
                            musicPlayer.setFile(aa.error);
                            musicPlayer.play(aa.error);
                            createAlert("Invalid Card", null, "Only accept same rank or same suit!").showAndWait();
                        } else {
                            musicPlayer.setFile(aa.flip);
                            musicPlayer.play(aa.flip);
                            cardRealIndex.set(currentPlayer, card);
                            flowPane.getChildren().remove(cardPane);
                            centerCards.put(currentPlayer, card);
                            player.removeFromHand(card);
                            // Close this window
                            close();
                        }
                    }

                }
            });
            flowPane.getChildren().add(cardPane);
        }
    }

    private void setCardBehaviour(StackPane card, String color) {
        card.setOnMouseEntered(event -> {
            musicPlayer.setFile(aa.tab);
            musicPlayer.play(aa.tab);
            card.setStyle("-fx-border-color: "+ color + "; -fx-border-width: 3; -fx-border-radius: 5; -fx-border-style: segments(10, 15, 15, 15)  line-cap round;");
        });
        card.setOnMouseExited(event -> {
            card.setStyle("-fx-border-color: transparent; -fx-border-width: 3; -fx-border-radius: 5; -fx-border-style: segments(10, 15, 15, 15)  line-cap round;");
        });
    }
    

    private StackPane createCardPane(String imagePath, Double brightness) {
        ImageView imageView = new ImageView(new Image(imagePath));
        imageView.setFitWidth(80); // Set the desired card width
        imageView.setFitHeight(110);

        ColorAdjust colorAdjust = new ColorAdjust();
        colorAdjust.setBrightness(brightness);

        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        imageView.setCache(true);
        imageView.setEffect(colorAdjust);

        StackPane cardPane = new StackPane(imageView);
        cardPane.setPrefSize(90, 120);
        return cardPane;
    }

    private Alert createAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        return alert;
    }

    
}
