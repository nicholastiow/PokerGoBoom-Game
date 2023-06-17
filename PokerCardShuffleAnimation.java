import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class PokerCardShuffleAnimation extends Stage {

    private static final int CARD_WIDTH = 70;
    private static final int CARD_HEIGHT = 100;
    private static final int SPACING = 15;
    private static final int NUM_CARDS = 52;
    private static final int STOP_TIME_MS = 5000;
    private static final int REST_TIME_MS = 500;

    private List<ImageView> cardImages;
    private List<Card> deckAllList;
    private Pane cardPane;

    private Card leadCard;

    private String selectedFile = "";

    MusicPlayer musicPlayer = new MusicPlayer();
    AudioAsset audioAsset = new AudioAsset();

    MediaPlayer mediaPlayerMain;


    public PokerCardShuffleAnimation(Card leadCard) {

        this.leadCard = leadCard;

        setResizable(false);

        Media media1 = new Media(new File("./music/main.wav").toURI().toString());
        mediaPlayerMain = new MediaPlayer(media1);
        mediaPlayerMain.setVolume(0.5);
        mediaPlayerMain.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayerMain.play();
        

        setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0);
        });

        cardImages = new ArrayList<>();
        cardPane = new Pane();
        Image backgroundImage = new Image("./images/background.jpg");

        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        cardPane.setBackground(new Background(background));
        cardPane.setPadding(new Insets(10));
        cardPane.setPrefSize((CARD_WIDTH + SPACING) * 10, (CARD_HEIGHT + SPACING) * 6);

        deckAllList = createDeck();


        // Load card images
        for (int i = 0; i < deckAllList.size(); i++) {
            String cardFileName = deckAllList.get(i).toString();

            Image cardImage = new Image(getClass().getResourceAsStream(cardFileName));
            ImageView cardImageView = new ImageView(cardImage);
            cardImageView.setFitWidth(CARD_WIDTH);
            cardImageView.setFitHeight(CARD_HEIGHT);
            cardImageView.setX((i) % 10 * (CARD_WIDTH + SPACING));
            cardImageView.setY((i) / 10 * (CARD_HEIGHT + SPACING));
            cardPane.getChildren().add(cardImageView);
            cardImages.add(cardImageView);
        }

        Button shuffleButton = new Button("Click Me New game! Card Shuffle");
        shuffleButton.setStyle("-fx-font-size: 20pt; -fx-font-weight: bold;"); // normal and hover background color

        Button loadButton = new Button("Click Me! Load Saved Game!");
        loadButton.setStyle("-fx-font-size: 20pt; -fx-font-weight: bold;"); // normal and hover background color

        shuffleButton.setMinWidth(500);
        loadButton.setMinWidth(500);

        shuffleButton.setTranslateX(200);
        shuffleButton.setTranslateY(280);
        loadButton.setTranslateX(200);
        loadButton.setTranslateY(350);

        loadButton.setOnAction(event -> {
            mediaPlayerMain.stop();
            FileListView fileListView = new FileListView();
            fileListView.showAndWait();
            selectedFile = fileListView.getSelectedFile();
            close();
        });


        shuffleButton.setOnAction(event -> {
            shuffleButton.setVisible(false);
            mediaPlayerMain.stop();
            musicPlayer = new MusicPlayer();
            audioAsset = new AudioAsset();

            musicPlayer.setFile(audioAsset.shuffle);
            musicPlayer.play(audioAsset.shuffle);

            cardPane.getChildren().clear();

            cardImages.clear();
            for (int i = 0; i < deckAllList.size(); i++) {
                String cardFileName = "./images/back.png";
    
                Image cardImage = new Image(getClass().getResourceAsStream(cardFileName));
                ImageView cardImageView = new ImageView(cardImage);
                cardImageView.setFitWidth(CARD_WIDTH);
                cardImageView.setFitHeight(CARD_HEIGHT);
                cardImageView.setX((i) % 10 * (CARD_WIDTH + SPACING));
                cardImageView.setY((i) / 10 * (CARD_HEIGHT + SPACING));
                cardPane.getChildren().add(cardImageView);
                cardImages.add(cardImageView);
            }

            shuffleCards();
            pause(STOP_TIME_MS, this::animateCombine);

        });

        cardPane.getChildren().addAll(shuffleButton,loadButton);

        Scene scene = new Scene(cardPane);
        setScene(scene);
        setTitle("Poker Card Shuffle");
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

    private void shuffleCards() {
        Collections.shuffle(cardImages);

        // Random row of card shuffling
        Random random = new Random();
        int randomIndex = random.nextInt(10) + 1;
        
        Timeline timeline = new Timeline();
        for (int i = 0; i < NUM_CARDS; i++) {
            KeyValue xValue = new KeyValue(cardImages.get(i).xProperty(),
                    (i % randomIndex) * (CARD_WIDTH + SPACING),
                    Interpolator.EASE_BOTH);
            KeyValue yValue = new KeyValue(cardImages.get(i).yProperty(),
                    (i / randomIndex) * (CARD_HEIGHT + SPACING),
                    Interpolator.EASE_BOTH);
            KeyFrame keyFrame = new KeyFrame(Duration.seconds(1), xValue, yValue);
            timeline.getKeyFrames().add(keyFrame);
        }

        timeline.play();
        timeline.setOnFinished(event -> {
            pause(REST_TIME_MS, this::shuffleCards);
        });
    }


    private void animateCombine() {
        double centerX = cardPane.getWidth() / 2;
        double centerY = cardPane.getHeight() / 2;
    
        Timeline timeline = new Timeline();
        for (int i = 0; i < NUM_CARDS; i++) {
            KeyValue xValue = new KeyValue(cardImages.get(i).xProperty(),
                    centerX - CARD_WIDTH / 2,
                    Interpolator.EASE_BOTH);
            KeyValue yValue = new KeyValue(cardImages.get(i).yProperty(),
                    centerY - CARD_HEIGHT / 2,
                    Interpolator.EASE_BOTH);
            KeyFrame keyFrame = new KeyFrame(Duration.seconds(1), xValue, yValue);
            timeline.getKeyFrames().add(keyFrame);
        }
    
        timeline.setOnFinished(event -> {
            cardPane.getChildren().clear();

            List<ImageView> cardList = new ArrayList<>();

            for (int i = 0; i < 30; i++) {
                ImageView card = new ImageView(new Image(getClass().getResourceAsStream("./images/back.png")));
                card.setFitWidth(CARD_WIDTH);
                card.setFitHeight(CARD_HEIGHT);
                card.setPreserveRatio(true);
                card.setSmooth(true);
                card.setCache(true);
                cardList.add(card);
            }


            
            for (int i = 0; i < cardList.size(); i++) {
                ImageView card = cardList.get(i);
                card.setLayoutX((cardPane.getWidth() - card.getFitWidth()) / 2);
                card.setLayoutY((cardPane.getHeight() - card.getFitHeight()) / 2);
                cardPane.getChildren().add(card);
            }


            for(int i = 0; i < 28; i++) {
                ImageView card = cardList.get(i);
                TranslateTransition tt = new TranslateTransition(Duration.seconds(1), card);
                if(i % 4 == 0) {
                    // set to X = 370
                    tt.setToX(370);
                } else if(i % 4 == 1) {
                    // set to Y = -290
                    tt.setToY(-290);
                } else if(i % 4 == 2) {
                    // set to X = -370
                    tt.setToX(-370);
                } else {
                    // set to Y = 290
                    tt.setToY(290);
                }
                tt.setDelay(Duration.seconds(i * 0.2)); // add delay based on the index i
                tt.play();
            }

            ImageView leadcard = new ImageView(new Image(getClass().getResourceAsStream(leadCard.toString())));
            leadcard.setFitWidth(CARD_WIDTH);
            leadcard.setFitHeight(CARD_HEIGHT);
            leadcard.setPreserveRatio(true);
            leadcard.setSmooth(true);
            leadcard.setCache(true);
            leadcard.setLayoutX((cardPane.getWidth() - leadcard.getFitWidth()) / 2);
            leadcard.setLayoutY((cardPane.getHeight() - leadcard.getFitHeight()) / 2);
            cardPane.getChildren().add(leadcard);
            TranslateTransition tt = new TranslateTransition(Duration.seconds(1), leadcard);
            tt.setToX(-90);
            tt.play();

            // Wait 9sec the animation to finish and close window
            pause(9000, this::close);
        });
    
        timeline.play();
    }

    private void pause(int milliseconds, Runnable action) {
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(milliseconds), event -> action.run()));
        timeline.play();
    }

    public String getSelectedFile() {
        return selectedFile;
    }
}
