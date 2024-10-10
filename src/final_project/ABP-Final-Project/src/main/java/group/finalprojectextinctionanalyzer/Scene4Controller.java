package group.finalprojectextinctionanalyzer;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Scene4Controller {
    @FXML
    private void switchToScene1(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Scene1_Start.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private Button exportResults;
    @FXML
    private void initialize() {
        exportResults.setOnAction(this::exportResultsClicked);
    }
    @FXML
    private void exportResultsClicked(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Choose Output Directory");

        File selectedDirectory = directoryChooser.showDialog(((Node) event.getSource()).getScene().getWindow());

        if (selectedDirectory != null) {
            String outputDirectory = selectedDirectory.getAbsolutePath();
            String scriptFilePath = "C:\\Users\\zapat\\IdeaProjects\\FinalProject-ExtinctionAnalyzer\\src\\main\\RScripts\\executeFinalRScript.R";

            ReadR.runRScript(scriptFilePath, outputDirectory);

            clearExtinctSpeciesList("C:\\Users\\zapat\\IdeaProjects\\FinalProject-ExtinctionAnalyzer\\src\\main\\TextFiles\\extinctSpeciesList.txt");
            showSuccessDialog();
        }
    }
    private void clearExtinctSpeciesList(String filePath) {
        try {
            File file = new File(filePath);
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);

            // Write an empty string to the file to clear its contents
            bw.write("");
            bw.close();

            System.out.println("extinctSpeciesList.txt has been cleared successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void showSuccessDialog() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText("The run was successful and the file is saved!");
        alert.showAndWait();
    }

}
