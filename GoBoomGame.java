import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;

public class GoBoomGame extends Application {
    private List<Card> deck;
    private List<Card> centerDeck;
    private List<Player> players;
    private List<String> colors;

    private int currentPlayerIndex;
    private int trickNumber;
    private int round;
    public int seconds = 0;
    private String selectedFile;

    private Card leadCard;
    private Map<Integer, Card> centerCards;

    private List<Card> cardRealIndex;
    private List<StackPane> cardPaneList;

    private boolean isOpen;

    private Timeline timelineCounter;

    MusicPlayer musicPlayer = new MusicPlayer();
    AudioAsset aa = new AudioAsset();

    MediaPlayer mediaPlayer1;

    // ------------------------------------------------------ Start Method ------------------------------------------------------
    public void start(Stage primaryStage) {

        MainPage mainPage = new MainPage();
        mainPage.showAndWait();

        initializeGame();

        PokerCardShuffleAnimation pokerCardShuffleAnimation = new PokerCardShuffleAnimation(leadCard);
        pokerCardShuffleAnimation.showAndWait();

        if(!pokerCardShuffleAnimation.getSelectedFile().equals("")) {

            String fileName = pokerCardShuffleAnimation.getSelectedFile();
            selectedFile = fileName;
            DataHandle dataHandle = new DataHandle();
            deck = dataHandle.readCardFromFile(fileName, 1);
            centerDeck = dataHandle.readCardFromFile(fileName, 2);
            players = dataHandle.readPlayerFromFile(fileName,4);
            colors = dataHandle.readColorFromFile(fileName, 16);
            currentPlayerIndex = dataHandle.readIntFromFile(fileName, 17);
            trickNumber = dataHandle.readIntFromFile(fileName, 18);
            round = dataHandle.readIntFromFile(fileName, 19);
            seconds = dataHandle.readIntFromFile(fileName, 20);
            leadCard = dataHandle.readLeadCardFromFile(fileName, 21);
            centerCards = dataHandle.readMapFromFile(fileName, 22);
            cardRealIndex = dataHandle.readCardFromFile(fileName, 23);
            // isOpen = false;
            refreshContent();
        }


        displayGameState();
        mediaPlayer1 = new MediaPlayer(new Media(new File("./music/game.wav").toURI().toString()));
        mediaPlayer1.setVolume(0.2);
        mediaPlayer1.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer1.play();

        // Create the root container
        BorderPane root = new BorderPane();

        Image backgroundImage = new Image("./images/background.jpg");

        BackgroundSize backgroundSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, false,
                false);
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, backgroundSize);

        root.setBackground(new Background(background));
        root.setPadding(new Insets(20));

        
        PauseTransition pause = new PauseTransition(Duration.seconds(2));

        pause.setOnFinished(event -> {
            checkPlayerTurn();
        });
        pause.play();

        // Check the draw / play card window is in use
        isOpen = false;

        cardPaneList.get(0).setOnMouseClicked(event -> {
            if (colors.get(0).equals("yellow") ) {
                if (!isOpen) {
                    isOpen = true;
                    CardGridWindow cardGridWindow = new CardGridWindow(players, centerDeck, centerCards, cardRealIndex,
                            leadCard, 0, trickNumber);
                    // cardGridWindow.setAlwaysOnTop(true);

                    cardGridWindow.showAndWait();
                    if (cardGridWindow.isShowing() == false) {
                        isOpen = false;
                        displayGameState();
                        colors.set(0, "white");
                        colors.set(1, "yellow");
                        refreshContent();
                        checkPlayerTurn();
                    }
                }

            } else {
                ViewCardWindow viewCard = new ViewCardWindow(players.get(0).getHand(), 1);
                // viewCard.setAlwaysOnTop(true);
                viewCard.show();

                // alertInfo().showAndWait();
            }
            refreshContent();
        });

        cardPaneList.get(1).setOnMouseClicked(event -> {
            if (colors.get(1).equals("yellow")) {
                if (!isOpen) {
                    isOpen = true;
                    CardGridWindow cardGridWindow = new CardGridWindow(players, centerDeck, centerCards, cardRealIndex,
                            leadCard, 1, trickNumber);
                    // cardGridWindow.setAlwaysOnTop(true);

                    cardGridWindow.showAndWait();
                    if (cardGridWindow.isShowing() == false) {
                        isOpen = false;
                        displayGameState();
                        colors.set(1, "white");
                        colors.set(2, "yellow");
                        refreshContent();
                        checkPlayerTurn();
                    }
                }

            } else {
                ViewCardWindow viewCard = new ViewCardWindow(players.get(1).getHand(), 2);
                // viewCard.setAlwaysOnTop(true);
                viewCard.showAndWait();
            }
            
            refreshContent();
        });

        cardPaneList.get(2).setOnMouseClicked(event -> {
            
            if (colors.get(2).equals("yellow")) {
                if (!isOpen) {
                    isOpen = true;
                    CardGridWindow cardGridWindow = new CardGridWindow(players, centerDeck, centerCards, cardRealIndex,
                            leadCard, 2, trickNumber);
                    // cardGridWindow.setAlwaysOnTop(true);
                            cardGridWindow.showAndWait();
                    if (cardGridWindow.isShowing() == false) {
                        isOpen = false;
                        displayGameState();
                        colors.set(2, "white");
                        colors.set(3, "yellow");
                        refreshContent();
                        checkPlayerTurn();
                    }
                }
            } else {
                ViewCardWindow viewCard = new ViewCardWindow(players.get(2).getHand(), 3);
                // viewCard.setAlwaysOnTop(true);
                viewCard.showAndWait();
            }
            refreshContent();
        });

        cardPaneList.get(3).setOnMouseClicked(event -> {;
            if (colors.get(3).equals("yellow")) {
                if (!isOpen) {
                    CardGridWindow cardGridWindow = new CardGridWindow(players, centerDeck, centerCards, cardRealIndex,
                            leadCard, 3, trickNumber);
                    isOpen = true;
                    // cardGridWindow.setAlwaysOnTop(true);
                    cardGridWindow.showAndWait();
                    if (cardGridWindow.isShowing() == false) {
                        isOpen = false;
                        displayGameState();
                        colors.set(3, "white");
                        colors.set(0, "yellow");
                        refreshContent();
                        checkPlayerTurn();
                    }
                }
            } else {
                ViewCardWindow viewCard = new ViewCardWindow(players.get(3).getHand(), 4);
                // viewCard.setAlwaysOnTop(true);
                viewCard.showAndWait();
            }
            refreshContent();
            
        });

        for (int i = 0; i < 4; i++) {
            setCardPaneBehavior(cardPaneList.get(i));
        }

        // Set the stack panes to the corners of the screen
        root.setBottom(cardPaneList.get(3));
        root.setTop(cardPaneList.get(1));
        root.setLeft(cardPaneList.get(0));
        // root.setTop(stackPane);
        root.setRight(cardPaneList.get(2));
        root.setCenter(cardPaneList.get(4));

        // BorderPane pane = new BorderPane();
        ImageView restartIcon = createIconImageView("./images/restart.png", 60, 60);
        ImageView soundIcon = createIconImageView("./images/unmute.png", 70, 70);
        ImageView helpIcon = createIconImageView("./images/help.png", 70, 70);
        ImageView saveIcon = createIconImageView("./images/save.png", 140, 140);

        setIconBehaviour(restartIcon, () -> {
            System.out.println("Clicked on restart");
            mediaPlayer1.stop();
            timelineCounter.stop();

            Stage stage = (Stage) cardPaneList.get(0).getScene().getWindow();
            stage.close();
            start(new Stage());
        });

        BooleanProperty isChange = new SimpleBooleanProperty(false);

        setIconBehaviour(soundIcon, () -> {
            System.out.println("Clicked on unmute");
            if (isChange.get()) {
                soundIcon.setImage(new Image("./images/unmute.png"));
                isChange.set(false);
                mediaPlayer1.setVolume(0.2);
            } else {
                soundIcon.setImage(new Image("./images/mute.png"));
                isChange.set(true);
                mediaPlayer1.setVolume(0.0);
            }
        });

        setIconBehaviour(helpIcon, () -> {
            System.out.println("Clicked on help");
            GameManual gameManual = new GameManual();
            gameManual.showAndWait();
        });

        setIconBehaviour(saveIcon, () -> {
            System.out.println("Clicked on save");
            TextInputDialog inputDialog = new TextInputDialog();
            inputDialog.setTitle("Save Game");
            inputDialog.setHeaderText("Enter the file name to save");
            inputDialog.setContentText("File name: ");
            inputDialog.getEditor().setPromptText("XXXXX.txt");
            //Set default value to the inputdialog

            String extractedFileName = "";
            if(selectedFile == null) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmm");
                Date now = new Date();
                extractedFileName = dateFormat.format(now);
            } else {
                File newFile = new File(selectedFile);
                extractedFileName = newFile.getName();
            }

            

            inputDialog.getEditor().setText(extractedFileName);
            

            Optional<String> result = inputDialog.showAndWait();

            result.ifPresent(
                    fileName -> {
                        DataHandle dataWriter = new DataHandle();
                        dataWriter.readDataFolder();
                        HashSet<String> fileSet = dataWriter.getSavedFileSet();
                        if (!fileName.isEmpty()) {
                            if(!fileName.endsWith(".txt")) {
                                fileName += ".txt";
                            } else {
                                fileName = result.get();
                            }
                            
                            fileName = ".\\data\\" + fileName;
                            System.out.println();
                            for(String file : fileSet) {
                                System.out.println(file);
                            }

                            if(fileSet.contains(fileName)) {
                                // Delete contents in file
                                try {
                                    FileWriter fileWriter = new FileWriter(fileName);
                                    fileWriter.write("");
                                    fileWriter.close();
                                    createAlertPane("Success", "File saved successfully!").showAndWait();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                createAlertPane("Success", "New File saved successfully!").showAndWait();
                            }
                            
                            dataWriter.writeCardsToFile(deck, fileName);
                            dataWriter.writeCardsToFile(centerDeck, fileName);
                            dataWriter.writePlayerToFile(players, fileName);
                            dataWriter.writeStringToFile(colors, fileName);
                            dataWriter.writeIntToFile(currentPlayerIndex, fileName);
                            dataWriter.writeIntToFile(trickNumber, fileName);
                            dataWriter.writeIntToFile(round, fileName);
                            dataWriter.writeIntToFile(seconds, fileName);
                            dataWriter.writeLeadCard(leadCard, fileName);
                            dataWriter.writeMapToFile(centerCards, fileName);
                            dataWriter.writeCardsToFile(cardRealIndex, fileName);

                            // createAlertPane("Success", "File saved successfully!").showAndWait();
                        } else {
                            createAlertPane("Empty", "No file name entered!").showAndWait();
                        }
                    });
        });

        // BorderPane.setAlignment(settingImageView, Pos.CENTER_RIGHT);
        Label timerLabel = new Label("Timer: 00:00:00");
        timerLabel.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 20px;");
        timerLabel.setAlignment(Pos.CENTER_RIGHT);

        // Create hbox2 for timer label
        HBox hbox2 = new HBox(timerLabel);
        hbox2.setAlignment(Pos.CENTER_RIGHT);

        // Set the timeline for the timer
        timelineCounter = new Timeline(
                new KeyFrame(Duration.seconds(1), event -> {
                    seconds++;
                    int hour = seconds / 3600;
                    int minute = (seconds % 3600) / 60;
                    int second = seconds % 60;
                    String time = String.format("%02d:%02d:%02d", hour, minute, second);
                    timerLabel.setText("Timer: " + time);
                }));
        timelineCounter.setCycleCount(Animation.INDEFINITE);
        timelineCounter.play();

        // Create the HBox
        HBox hBox = new HBox(soundIcon, restartIcon,helpIcon ,saveIcon, hbox2);
        hBox.setStyle("-fx-background-color: rgba(36,113,33,255);");
        hBox.setPadding(new Insets(10));
        HBox.setHgrow(hbox2, Priority.ALWAYS);

        // Set spacing between buttons
        VBox vBox = new VBox();
        // vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(hBox, root);

        VBox.setVgrow(root, Priority.ALWAYS);


        // Create the scene and set it on the stage
        Scene scene = new Scene(vBox); // Set the desired window size

        primaryStage.setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0);
        });

        
        primaryStage.setMaximized(true);
        primaryStage.setTitle("GoBoom Game");
        primaryStage.setScene(scene);
        Image icon = new Image("./images/icon.jpg");
        primaryStage.getIcons().add(icon);
        primaryStage.show();
    }

    // ------------------------------------------------------ Icon Clicked Method ------------------------------------------------------

    private void setIconBehaviour(ImageView icon, Runnable onClick) {
        icon.setOnMouseEntered(event -> {
            icon.setOpacity(0.5);
        });
        icon.setOnMouseExited(event -> {
            icon.setOpacity(1);
        });
        icon.setOnMouseClicked(event -> {
            onClick.run();
        });
    }

    private void setCardPaneBehavior(StackPane cardPane) {
        cardPane.setOnMouseEntered(event -> {
            cardPane.setOpacity(0.5);
        });
        cardPane.setOnMouseExited(event -> {
            cardPane.setOpacity(1);
        });
    }

    // ------------------------------------------------------ Alert Methods ------------------------------------------------------
    
    private Alert roundAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Round " + round);
        
        
        String winText = "";
        int smallestScore = 1000;
        int winnerIndex = 0;
        for (Player player : players) {
            if (player.getScore() < smallestScore) {
                winnerIndex = players.indexOf(player);
                smallestScore = player.getScore();
            }
            winText += player.getName() + ": " + player.getScore() + "\n";
        }
        alert.setHeaderText("Round " + round + " finished! " + "Player" + (winnerIndex + 1) + " lead this round!");
        alert.setContentText(winText);


        return alert;
    }
    
    
    private Alert gameFinishAlert(List<Player> players) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("Game Over");
        alert.setHeaderText("Score");
        String winText = "";
        int smallestScore = 1000;
        int winnerIndex = 0;
        for (Player player : players) {
            if (player.getScore() < smallestScore) {
                winnerIndex = players.indexOf(player);
                smallestScore = player.getScore();
            }
            winText += player.getName() + ": " + player.getScore() + "\n";
        }
        winText += "\nCongratulation! Player " + (winnerIndex + 1) + " win!";
        alert.setContentText(winText);
        return alert;
    }

    private void alertWin(int winnerIndex) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        if (winnerIndex == 1) {
            musicPlayer.setFile(aa.win1);
            musicPlayer.play(aa.win1);
        } else if (winnerIndex == 2) {
            musicPlayer.setFile(aa.win2);
            musicPlayer.play(aa.win2);
        } else if (winnerIndex == 3) {
            musicPlayer.setFile(aa.win3);
            musicPlayer.play(aa.win3);
        } else if (winnerIndex == 4) {
            musicPlayer.setFile(aa.win4);
            musicPlayer.play(aa.win4);
        }

        alert.setTitle("Winner Of Trick");
        alert.setHeaderText("Trick" + (trickNumber) + ": " + "Player " + winnerIndex + " win!");
        alert.setContentText("Starting Trick " + (trickNumber + 1) + "...Automatic close in 5 seconds");
        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
        alertStage.setOnCloseRequest(event -> {
            event.consume(); // Consume the close request
        });

        // // Disable all button types
        // for (Object buttonType : alert.getButtonTypes().toArray()) {
        //     alert.getButtonTypes().remove(buttonType);
        // }

        Thread closeAfterDelay = new Thread(() -> {
            try {
                Thread.sleep(6000);
                Platform.runLater(alert::close);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        closeAfterDelay.setDaemon(true);
        closeAfterDelay.start();
        alert.showAndWait();

    }

    private Alert createAlertPane(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        return alert;
    }

    private void checkPlayerTurn() {
        if (colors.get(0) == "yellow") {
            musicPlayer.setFile(aa.player1);
            musicPlayer.play(aa.player1);
        } else if (colors.get(1) == "yellow") {
            musicPlayer.setFile(aa.player2);
            musicPlayer.play(aa.player2);
        } else if (colors.get(2) == "yellow") {
            musicPlayer.setFile(aa.player3);
            musicPlayer.play(aa.player3);
        } else if (colors.get(3) == "yellow") {
            musicPlayer.setFile(aa.player4);
            musicPlayer.play(aa.player4);
        }
    }

    // ------------------------------------------------------ Refresh Methods ------------------------------------------------------
    private void refreshContent() {
        // Update the card panes

        updateCardPane(
                cardPaneList.get(0), cardRealIndex.get(0).toString(), players.get(0).getName() + "\n" + "Score: "
                        + players.get(0).getScore() + "\nCardLeft: " + players.get(0).getHand().size(),
                colors.get(0), true);
        updateCardPane(
                cardPaneList.get(1), cardRealIndex.get(1).toString(), players.get(1).getName() + "\n" + "Score: "
                        + players.get(1).getScore() + "\nCardLeft: " + players.get(1).getHand().size(),
                colors.get(1), false);
        updateCardPane(
                cardPaneList.get(2), cardRealIndex.get(2).toString(), players.get(2).getName() + "\n" + "Score: "
                        + players.get(2).getScore() + "\nCardLeft: " + players.get(2).getHand().size(),
                colors.get(2), true);
        updateCardPane(
                cardPaneList.get(3), cardRealIndex.get(3).toString(), players.get(3).getName() + "\n" + "Score: "
                        + players.get(3).getScore() + "\nCardLeft: " + players.get(3).getHand().size(),
                colors.get(3), false);

        if (centerCards.size() == 4) {
            if (centerCards.get(0).getSuit() == Suit.BLANK && centerCards.get(1).getSuit() == Suit.BLANK
                    && centerCards.get(2).getSuit() == Suit.BLANK && centerCards.get(3).getSuit() == Suit.BLANK) {
                createAlertPane("Error", "Please play accoring to the rule!").showAndWait();
                centerCards.clear();
                refreshContent();
            } else {
                int winnerIndex = checkWinTrick();
                for (int i = 0; i < 4; i++) {
                    cardRealIndex.set(i, new Card(Suit.BLANK, Rank.BLANK));
                }

                alertWin(winnerIndex + 1);
                refreshContent();
                nextTrick(winnerIndex);
            }
        }

        if (trickNumber == 1) {
            updateCardPane(cardPaneList.get(4), leadCard.toString(),
                    ("Deck: " + Integer.toString(centerDeck.size()) + "\nRound: " + round + "\nTrick: " + trickNumber),
                    "black", false);
        } else {
            int winnerIndex;
            if (centerCards.size() == 0) {
                winnerIndex = 0;
            } else {
                Entry<Integer, Card> firstEntry = centerCards.entrySet().iterator().next();
                winnerIndex = firstEntry.getKey() + 1;
            }
            updateCardPane(cardPaneList.get(4), leadCard.toString(), ("Deck: " + Integer.toString(centerDeck.size())
                    + "\nRound: " + round + "\nTrick: " + trickNumber + "\nLead: Player " + (currentPlayerIndex + 1)),
                    "black", false);
        }

        if (centerCards.size() == 1 && trickNumber != 1) {
            leadCard = centerCards.get(currentPlayerIndex);
        }

        for (Player player : players) {
            if (player.getScore() >= 100) {
                System.out.println("Game Over");
                gameFinishAlert(players).showAndWait();
                mediaPlayer1.stop();
                Stage stage = (Stage) cardPaneList.get(0).getScene().getWindow();
                stage.close();
                start(new Stage());

            }
            if (player.getHand().size() == 0) {
                calculateScore();
                // refreshContent();
                nextRound();
                refreshContent();
            }
        }

    }

    private void calculateScore() {
        for (Player player : players) {
            int score = player.getScore();
            if (player.getHand().size() == 0) {
                score += 0;
            } else {
                for (Card card : player.getHand()) {
                    score += card.getRank().getValue();
                }
            }
            player.setScore(score);
        }
    }

    private void updateCardPane(StackPane cardPane, String imagePath, String labelText, String color,
            boolean vertical) {
        // Clear the existing content
        cardPane.getChildren().clear();

        // Create a new ImageView with the updated image
        ImageView imageView = createCardImageView("./images/back.png");

        ImageView imageView2 = createCardImageView(imagePath);
        
        if(cardPane == cardPaneList.get(4)) {
            if(trickNumber == 1) {
                imageView.setOpacity(1);
            } else {
                imageView2.setOpacity(0.4);
            }
        }

        // Create a new Label with the updated text
        Label label = createDetailsLabel(labelText, color);

        // Create a new VBox or HBox based on the orientation
        Pane pane = vertical ? new VBox(10) : new HBox(10);

        if (pane instanceof VBox) {
            ((VBox) pane).setAlignment(Pos.CENTER);
        } else if (pane instanceof HBox) {
            ((HBox) pane).setAlignment(Pos.CENTER);
        }
        pane.getChildren().addAll(imageView2, imageView, label);

        // Add the new pane to the cardPane
        cardPane.getChildren().add(pane);
    }

    // ------------------------------------------------------ Start a new Trick Methods ------------------------------------------------------

    public void nextTrick(int winnerIndex) {
        trickNumber++;
        leadCard = new Card(Suit.BLANK, Rank.BLANK);
        currentPlayerIndex = winnerIndex;

        for (int i = 0; i < 4; i++) {
            colors.set(i, "white");
        }
        colors.set(winnerIndex, "yellow");
        refreshContent();
    }

    private void nextRound() {
        musicPlayer.setFile(aa.smallWin);
        musicPlayer.play(aa.smallWin);
        roundAlert().showAndWait();
        round++;
        trickNumber = 1;
        deck.clear();
        centerCards.clear();
        colors.clear();
        centerDeck.clear();
        cardRealIndex.clear();
        // cardPaneList.clear();

        deck = createDeck();
        Collections.shuffle(deck);
        for (Player player : players) {
            player.getHand().clear();
        }
        // Initialize the center cards list
        centerCards = new LinkedHashMap<>();
        centerDeck = new ArrayList<>();

        colors = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            colors.add("white");
        }

        // Deal cards to each player
        dealCards();

        currentPlayerIndex = determineFirstPlayerIndex();
        colors.set(currentPlayerIndex, "yellow");

        cardRealIndex = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            cardRealIndex.add(new Card(Suit.BLANK, Rank.BLANK));
        }
        displayGameState();
    }

    // ------------------------------------------------------ Image / Label Create Methods ------------------------------------------------------

    private ImageView createCardImageView(String imagePath) {
        Image cardImage = new Image(imagePath);
        ImageView imageView = new ImageView(cardImage);
        imageView.setFitWidth(80); // Set the desired card width
        imageView.setFitHeight(110);
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        imageView.setCache(true);
        return imageView;
    }

    private Label createDetailsLabel(String text, String color) {
        Label label = new Label(text);
        label.setStyle("-fx-font-weight: bold; -fx-text-fill: " + color
                + "; -fx-font-size: 18; -fx-font-family: 'Bebas Neue';");

        return label;
    }

    private ImageView createIconImageView(String imagePath, int width, int height) {
        Image cardImage = new Image(imagePath);
        ImageView imageView = new ImageView(cardImage);
        imageView.setFitWidth(width); // Set the desired card width
        imageView.setFitHeight(height);
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        imageView.setCache(true);
        return imageView;
    }

    // ------------------------------------------------------ Card Pane Create Methods ------------------------------------------------------

    private StackPane createCardPane(String frontCardPNG, String labelText, String color, boolean visible,
            boolean vertical) {
        ImageView backCard = createCardImageView("./images/back.png");
        ImageView frontCard = createCardImageView(frontCardPNG);

        frontCard.setVisible(visible);
        Label label = createDetailsLabel(labelText, color);

        Pane pane = vertical ? new VBox(10) : new HBox(10);

        pane.getChildren().addAll(frontCard, backCard, label);

        if (pane instanceof VBox) {
            ((VBox) pane).setAlignment(Pos.CENTER);
        } else if (pane instanceof HBox) {
            ((HBox) pane).setAlignment(Pos.CENTER);
        }

        StackPane cardPane = new StackPane(pane);
        cardPane.setAlignment(Pos.CENTER);
        return cardPane;
    }

    private StackPane createVerticalCardPane(String frontCardPNG, String labelText, String color, boolean visible) {
        return createCardPane(frontCardPNG, labelText, color, visible, true);
    }

    private StackPane createHorizontalCardPane(String frontCardPNG, String labelText, String color, boolean visible) {
        return createCardPane(frontCardPNG, labelText, color, visible, false);
    }

    // ------------------------------------------------------ Game Methods ------------------------------------------------------
    private void initializeGame() {
        // Create a new deck of 52 cards
        // deck.clear();
        trickNumber = 1;
        round = 1;
        seconds = 0;

        deck = createDeck();

        // Shuffle the deck
        Collections.shuffle(deck);

        // Initialize the players
        players = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            players.add(new Player("Player" + (i + 1)));
        }

        // Initialize the center cards list
        centerCards = new LinkedHashMap<>();
        centerDeck = new ArrayList<>();

        colors = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            colors.add("white");
        }

        // Deal cards to each player
        dealCards();

        currentPlayerIndex = determineFirstPlayerIndex();
        colors.set(currentPlayerIndex, "yellow");

        cardRealIndex = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            cardRealIndex.add(new Card(Suit.BLANK, Rank.BLANK));
        }

        cardPaneList = new ArrayList<>();
        // Create the stack panes for the cards

        StackPane card1Pane = createVerticalCardPane(cardRealIndex.get(0).toString(), players.get(0).getName() + "\n"
                + "Score: " + players.get(0).getScore() + "\nCardLeft: " + players.get(0).getHand().size(),
                colors.get(0), false);
        StackPane card2Pane = createHorizontalCardPane(cardRealIndex.get(1).toString(), players.get(1).getName() + "\n"
                + "Score: " + players.get(1).getScore() + "\nCardLeft: " + players.get(1).getHand().size(),
                colors.get(1), false);
        StackPane card3Pane = createVerticalCardPane(cardRealIndex.get(2).toString(), players.get(2).getName() + "\n"
                + "Score: " + players.get(2).getScore() + "\nCardLeft: " + players.get(2).getHand().size(),
                colors.get(2), false);
        StackPane card4Pane = createHorizontalCardPane(cardRealIndex.get(3).toString(), players.get(3).getName() + "\n"
                + "Score: " + players.get(3).getScore() + "\nCardLeft: " + players.get(3).getHand().size(),
                colors.get(3), false);

        StackPane centerDeckPane = createHorizontalCardPane(leadCard.toString(),
                ("Deck: " + Integer.toString(centerDeck.size()) + "\nRound: " + round + "\nTrick: " + trickNumber),
                "black", true);

        cardPaneList.add(card1Pane);
        cardPaneList.add(card2Pane);
        cardPaneList.add(card3Pane);
        cardPaneList.add(card4Pane);
        cardPaneList.add(centerDeckPane);


    }

    private List<Card> createDeck() {
        List<Card> deck = new ArrayList<>();
        // Add cards to the deck
        for (int i = 1; i < Suit.values().length; i++) {
            for (int j = 1; j < Rank.values().length; j++) {
                Suit suit = Suit.values()[i];
                Rank rank = Rank.values()[j];
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
        System.out.println(extractImgWord(deck.toString()));

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
        for (int i = currentCardIndex; i < deck.size(); i++) {
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

    private void displayGameState() {
        System.out.println("\n\nTrick #" + trickNumber);
        // System.out.println(players.size());

        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            List<Card> hand = player.getHand();
            System.out.print("Player" + (i + 1) + ": ");

            System.out.println(extractImgWord(hand.toString()));
        }

        System.out.print("Center: ");
        System.out.print("[ ");
        if (trickNumber == 1) {
            System.out.print(extractImgWord(leadCard.toString()) + " ");
        }
   
        Set set = centerCards.entrySet();// Converting to Set so that we can traverse
        Iterator itr = set.iterator();
        while (itr.hasNext()) {
            // Converting to Map.Entry so that we can get key and value separately
            Map.Entry entry = (Map.Entry) itr.next();
            System.out.print(extractImgWord(entry.getValue().toString()) + " ");
        }

        System.out.println("]");

        System.out.print("Deck: ");
        System.out.println(extractImgWord(centerDeck.toString()));

        System.out.print("Card Real Index: " + extractImgWord(cardRealIndex.toString()));
    }

    private String extractImgWord(String word) {
        String extractWord = word.replace("./images/", "").replace(".png", "");
        return extractWord;
    }

    private int checkWinTrick() {
        int largestCardValue = Integer.MIN_VALUE;
        int winnerIndex = -1;
        for (Entry<Integer, Card> entry : centerCards.entrySet()) {
            int currentPoint = entry.getValue().getRank().getValue();
            if (currentPoint > largestCardValue) {
                largestCardValue = currentPoint;
                winnerIndex = entry.getKey();
            }
        }
        System.out.println("\nGAME LOG: Winner is " + (winnerIndex + 1));
        centerCards.clear();
        return winnerIndex;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
