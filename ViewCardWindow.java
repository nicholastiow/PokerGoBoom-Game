
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.List;

public class ViewCardWindow extends Stage {


    public ViewCardWindow(List<Card> imageUrls, int playerNumber) {

        setTitle("View Mode Card For Player " + playerNumber);
        
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new javafx.geometry.Insets(10));
        gridPane.setHgap(20); // horizontal gap between images
        gridPane.setVgap(20); // vertical gap between images
        BackgroundFill backgroundFill = new BackgroundFill(Color.GREEN, null, null);
        Background background = new Background(backgroundFill);
        gridPane.setBackground(background);

        gridPane.setOnMouseClicked(event -> {
            Alert alert = new Alert(Alert.AlertType.WARNING);

            alert.setTitle("Invalid");
            alert.setHeaderText(null);
            alert.setContentText("This is not your turn now! View Mode Only!");
            alert.showAndWait();
        });
        
        // loop through the image URLs and create an image view for each one
        for (int i = 0; i < imageUrls.size(); i++) {
            Card imageUrl = imageUrls.get(i);
            Image image = new Image(imageUrl.toString());
            ImageView imageView = new ImageView(image);
            imageView.setFitWidth(80); // Set the desired card width
            imageView.setFitHeight(110); // set the width of the image view
            imageView.setPreserveRatio(true); // preserve the aspect ratio of the image
            gridPane.add(imageView, i % 5, i / 5); // add the image view to the grid pane
        }

        Button button = new Button("Close View");
        //set button size
        button.setOnAction(event -> close());
        gridPane.add(button, 2, 3);

        // create a scene with the grid pane as the root node
        Scene scene = new Scene(gridPane);
        
        setScene(scene);

        
        // Auto close after 10 seconds
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(10000), event -> close()));
        timeline.play();
        
    }


}
