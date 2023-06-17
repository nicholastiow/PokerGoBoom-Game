import java.io.File;
import java.net.URL;

import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class MainPage extends Stage{

    MusicPlayer music = new MusicPlayer();
    AudioAsset audioAsset = new AudioAsset();
    MediaPlayer mediaPlayerMain;

    public MainPage() {
        BorderPane pane = new BorderPane();
        // playMusic(audioAsset.main, true);
        Media media1 = new Media(new File("./music/main.wav").toURI().toString());
        mediaPlayerMain = new MediaPlayer(media1);
        mediaPlayerMain.setVolume(0.5);
        mediaPlayerMain.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayerMain.play();

        Image image = new Image("./images/main.jpg");
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(100, 100, true, true, true, false));
        Background background = new Background(backgroundImage);
        pane.setBackground(background);

        ImageView startView = new ImageView("./images/start.png");
        BorderPane.setAlignment(startView, Pos.CENTER);
        pane.setCenter(startView);
        startView.setFitWidth(300);
        startView.setFitHeight(330);
        startView.setPreserveRatio(true);
        startView.setSmooth(true);
        startView.setCache(true);

        ImageView titleView = new ImageView("./images/title.png");
        BorderPane.setAlignment(titleView, Pos.CENTER);
        pane.setTop(titleView);

        startView.setOnMouseClicked(event -> {
            mediaPlayerMain.stop();
            close();
        });

        startView.setOnMouseEntered(event -> {
            // set opacity to 0.5 when mouse enters
            playMusic(audioAsset.hover, false);
            startView.setFitWidth(260); // Set the desired card width
            startView.setFitHeight(290);
            startView.setOpacity(0.5);
        });
        
        startView.setOnMouseExited(event -> {
            // set opacity back to 1 when mouse exits
            startView.setFitWidth(300); // Set the desired card width
            startView.setFitHeight(330);
            startView.setOpacity(1);
        });

        setOnCloseRequest(event -> {
            Platform.exit();
            System.exit(0);
        });



        Scene scene = new Scene(pane);
        setScene(scene);
        setHeight(759);
        setWidth(736);
        setResizable(false);
    }

    private void playMusic(URL url, Boolean loop) {
        music.setFile(url);
        music.play(url);
        if (loop) {
            music.loop(url);
        }
    }

}
