import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.List;

public class ViewCardWindow extends Stage {

    public ViewCardWindow(List<Card> imageUrls, int playerNumber) {
        setTitle("View Mode Card For Player " + playerNumber);

        FlowPane flowPane = new FlowPane();
        flowPane.setPadding(new javafx.geometry.Insets(10));
        flowPane.setHgap(20);
        flowPane.setVgap(20);
        BackgroundFill backgroundFill = new BackgroundFill(Color.GREEN, null, null);
        Background background = new Background(backgroundFill);
        flowPane.setBackground(background);

        flowPane.setOnMouseClicked(event -> {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Invalid");
            alert.setHeaderText(null);
            alert.setContentText("This is not your turn now! View Mode Only!");
            alert.showAndWait();
        });

        // Loop through the image URLs and create an image view for each one
        for (Card imageUrl : imageUrls) {
            Image image = new Image(imageUrl.toString());
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(80);
            imageView.setFitHeight(110);
            imageView.setPreserveRatio(true);
            flowPane.getChildren().add(imageView);
        }

        Button button = new Button("Close View");
        button.setOnAction(event -> close());
        flowPane.getChildren().add(button);

        ScrollPane scrollPane = new ScrollPane(flowPane);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setPrefSize(600, 400);

        Scene scene = new Scene(scrollPane);

        setScene(scene);
        setResizable(false);

        // Auto close after 10 seconds
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(10000), event -> close()));
        timeline.play();
    }
}
