import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class GameManual extends Stage {

    public GameManual() {
        setTitle("Game Manual");

        // Create a VBox to hold the content
        VBox root = new VBox();
        root.setSpacing(10);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);
        root.setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));

        // Add labels for each section of the game manual
        Label title = new Label("Game Manual");
        title.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: yellow;");

        Label section1 = createSectionLabel("1. Start a new game with randomized 52 cards.");
        Label section2 = createSectionLabel("2. The first card in the deck is the first lead card and is placed at the center.");
        Label section3 = createSectionLabel("3. The first lead card determines the first player:");
        Label section4 = createSectionLabel("4. Deal 7 cards to each of the 4 players.");
        Label section5 = createSectionLabel("5. All players must follow the suit or rank of the lead card.");
        Label section6 = createSectionLabel("6. The highest-rank card with the same suit as the lead card wins the trick.");
        Label section7 = createSectionLabel("7. The winner of a trick leads the next card.");
        Label section8 = createSectionLabel("8. If a player cannot follow suit or rank, the player must draw from the deck until a card can be played.");
        Label section9 = createSectionLabel("9. When the remaining deck is exhausted and the player cannot play, the player skips (does not play) the trick.");
        Label section10 = createSectionLabel("10. Finish a round of game correctly. Display the score of each player.");

        // Add the labels to the root container
        root.getChildren().addAll(title, section1, section2, section3, section4, section5, section6, section7, section8, section9, section10);

        Scene scene = new Scene(root, 950, 430);
        setScene(scene);
        setResizable(false);
    }

    private Label createSectionLabel(String text) {
        Label label = new Label(text);
        label.setStyle("-fx-font-size: 17px; -fx-font-weight: normal; -fx-text-fill: white; -fx-font-weight: bold;");
        return label;
    }
}
