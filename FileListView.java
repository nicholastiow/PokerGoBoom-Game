import java.io.File;
import java.util.HashSet;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class FileListView extends Stage{
    private String selectedFile = "";

    public FileListView() {
        DataHandle dataHandle = new DataHandle();
        dataHandle.readDataFolder();
        HashSet<String> savedFileSet = dataHandle.getSavedFileSet();

        ObservableList<String> itemList = FXCollections.observableArrayList(savedFileSet);
        
        
        ListView<String> listView = new ListView<>(itemList);
        listView.setStyle("-fx-font-size: 20px; -fx-font-family: 'Courier New';");
        Label chosenFile = new Label("Chosen File: " + selectedFile);
        chosenFile.setStyle("-fx-font-size: 20px; -fx-font-family: 'Courier New'; -fx-font-weight: bold;; -fx-text-fill: green;");
        
        if(savedFileSet.size() == 0) {
            chosenFile.setText("INFORMATION: No saved file found!");
        }
        
        listView.setOnMouseClicked(event -> {
            if(savedFileSet.size() == 0) {
                chosenFile.setText("INFORMATION: No saved file found!");
                return;
            }

            selectedFile = listView.getSelectionModel().getSelectedItem();
            String filePath = selectedFile;
            File file = new File(filePath);
            String fileName = file.getName();

            // Remove the file extension if present
            int dotIndex = fileName.lastIndexOf(".");
            if (dotIndex > 0) {
                fileName = fileName.substring(0, dotIndex);
            }
            chosenFile.setText("Selected File Restore: " + fileName);
        });

        Button restoreButton = new Button("Restore");
        restoreButton.setOnAction(event -> {
            if(selectedFile.equals("")) {
                chosenFile.setText("INFORMATION: No file selected!");
            } else {
                close();
            }
        });

        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(event -> {
            String selectedFile = listView.getSelectionModel().getSelectedItem();
            if (selectedFile != null) {
                File fileToDelete = new File(selectedFile);
                if (fileToDelete.exists()) {
                    if (fileToDelete.delete()) {
                        itemList.remove(selectedFile);
                        chosenFile.setText("Selected File Restore: ");
                    } else {
                        System.out.println("Failed to delete file: " + selectedFile);
                    }
                }
            }
        });

        Button newGameButton = new Button("NewGame");
        newGameButton.setOnAction(event -> {
            selectedFile = "";
            close();
        });

        Button exitButton = new Button("Exit");
        exitButton.setOnAction(event -> {
            // Exit whole application
            System.exit(0);
            Platform.exit();
        });

        // Consume exit function
        setOnCloseRequest(event -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Exit");
            alert.setHeaderText("Please use the Exit button to exit the game!");
            alert.showAndWait();
            event.consume();
        });



        deleteButton.setStyle("-fx-font-size: 20px; -fx-font-family: 'Courier New'; -fx-font-weight: bold;; -fx-text-fill: red;");
        restoreButton.setStyle("-fx-font-size: 20px; -fx-font-family: 'Courier New'; -fx-font-weight: bold;; -fx-text-fill: green;");
        newGameButton.setStyle("-fx-font-size: 20px; -fx-font-family: 'Courier New'; -fx-font-weight: bold;; -fx-text-fill: blue;");
        exitButton.setStyle("-fx-font-size: 20px; -fx-font-family: 'Courier New'; -fx-font-weight: bold;; -fx-text-fill: gray;");
        

        // Add some padding between the buttons
        HBox.setMargin(restoreButton, new javafx.geometry.Insets(10, 10, 10, 10));
        HBox.setMargin(deleteButton, new javafx.geometry.Insets(10, 10, 10, 10));
        HBox.setMargin(newGameButton, new javafx.geometry.Insets(10, 10, 10, 10));
        HBox.setMargin(exitButton, new javafx.geometry.Insets(10, 10, 10, 10));
        HBox hBox = new HBox(restoreButton,deleteButton, newGameButton,exitButton);
        
        VBox root = new VBox(chosenFile,listView,hBox);
        Scene scene = new Scene(root, 800, 400);
        setTitle("Saved Game Progress List");
        setScene(scene);
        setResizable(false);
    }

    public String getSelectedFile() {
        return selectedFile;
    }
}
