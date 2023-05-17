package com.example.code_clause_one;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private TextArea textArea;

    private File currentFile;
    // Define the root variable
    @FXML
    private Parent root;
    @FXML
    private void handleNew(ActionEvent event) {
        currentFile = null;
        textArea.setText("");
    }

    @FXML
    private void handleOpen(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(fileChooser.getTitle());

        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        File selectedFile = fileChooser.showOpenDialog(root.getScene().getWindow());

        if (selectedFile != null) {
            try (BufferedReader reader = new BufferedReader(new FileReader(selectedFile))) {
                textArea.clear();

                String line;
                while ((line = reader.readLine()) != null) {
                    textArea.appendText(line + "\n");
                }

                currentFile = selectedFile;
                updateTitle();
            } catch (IOException e) {
                showErrorAlert("Error opening file", e.getMessage());
            }
        }
    }
    private void updateTitle() {
        String title = "Untitled";

        if (currentFile != null) {
            title = currentFile.getName();
        }

        Stage stage = (Stage) root.getScene().getWindow();
        stage.setTitle(title + " - Text Editor");
    }


    @FXML
    private void handleSave(ActionEvent event) {
        if (currentFile == null) {
            handleSaveAs(event);
            return;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(currentFile.getParentFile());
        fileChooser.setInitialFileName(currentFile.getName());
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text files (*.txt)", "*.txt"));

        try {
            FileWriter writer = new FileWriter(currentFile);
            writer.write(textArea.getText());
            writer.close();
            textArea.clear();
        } catch (IOException e) {
            showErrorAlert("Error saving file", e.getMessage());
        }
    }




    @FXML
    private void handleSaveAs(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save File");
        File selectedFile = fileChooser.showSaveDialog(textArea.getScene().getWindow());
        if (selectedFile != null) {
            currentFile = selectedFile;
            handleSave(event);
        }
    }

    @FXML
    private void handleClose(ActionEvent event) {
        if (textArea.getText().isEmpty()) {
            currentFile = null;
            return;
        }

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirm");
        alert.setContentText("Do you want to save changes?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            handleSave(event);
        }

        currentFile = null;
        textArea.setText("");
    }

    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

        double preferredWidth = screenBounds.getWidth() * 0.9; // 90% of screen width
        double preferredHeight = screenBounds.getHeight() * 0.8; // 80% of screen height

        textArea.setPrefSize(preferredWidth, preferredHeight);

    }

    public void handleFindAndReplace(ActionEvent actionEvent) {
        System.out.println("Called the find aand replace");
        String findText = "a";
                String replaceText = "b";

        int start = textArea.getSelection().getStart();

        int index = textArea.getText().indexOf(findText, start);

        if (index != -1) {
            // Replace the text and select the replaced
            //textArea.replaceText(index,
              //                 index + findText.length()+ replaceText);
            textArea.selectRange(index, index + replaceText.length());
        }
    }

}

