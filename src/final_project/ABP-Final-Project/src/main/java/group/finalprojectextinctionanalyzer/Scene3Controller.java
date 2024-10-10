package group.finalprojectextinctionanalyzer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Scene3Controller {
    @FXML
    private void switchToScene1(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Scene1_Start.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private void switchToScene2(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Scene2_DataSelection.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }
    @FXML
    private void switchToScene4(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Scene4_Report.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }
    private final Stack<String> extinctionOrder = new Stack<>();
    private boolean initialized = false;
    @FXML
    private ListView<CheckBox> listOfSpeciesAlive;
    public void initialize() {
        listOfSpeciesAlive.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        executeRScript();
        if (!initialized) {
            initialized = true;
            List<String> allSpecies = new ArrayList<>();
            List<String> obtainedSpeciesList = obtainSpeciesList();
            if (obtainedSpeciesList != null) {
                allSpecies = obtainedSpeciesList;
            }
            ObservableList<CheckBox> checkboxes = FXCollections.observableArrayList();
            for (String species : allSpecies) {
                CheckBox checkBox = new CheckBox(species);
                checkboxes.add(checkBox);
            }
            listOfSpeciesAlive.setItems(checkboxes);
            return;
        }
        checkChecklistSize();
    }
    private void checkChecklistSize() {
        ObservableList<CheckBox> checkboxes = listOfSpeciesAlive.getItems();
        if (checkboxes.size() < 4) {
            showAlert("Error", "Checklist must have at least 4 species to run.");
        }
    }
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    private void saveExtinctSpeciesToFile(ActionEvent event) {
        ObservableList<CheckBox> allItems = listOfSpeciesAlive.getItems();
        List<String> extinctSpecies = new ArrayList<>();
        for (CheckBox checkBox : allItems) {
            if (checkBox.isSelected()) {
                extinctSpecies.add(checkBox.getText());
                extinctionOrder.push(checkBox.getText());
            }
        }
        String filePath = "src/main/TextFiles/extinctSpeciesList.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            StringBuilder speciesLine = new StringBuilder();
            for (String species : extinctSpecies) {
                speciesLine.append(species).append(",");
            }
            if (speciesLine.length() > 0) {
                speciesLine.deleteCharAt(speciesLine.length() - 1);
                writer.write(speciesLine.toString() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        allItems.removeIf(CheckBox::isSelected);
        executeRScript();
    }
    @FXML
    private ImageView phylomorphospaceIMG;

    private void executeRScript() {
        String rScriptOutputPath = "C:\\Users\\zapat\\IdeaProjects\\FinalProject-ExtinctionAnalyzer\\src\\main\\RScriptOutputs";
        ReadR.runRScript("C:\\Users\\zapat\\IdeaProjects\\FinalProject-ExtinctionAnalyzer\\src\\main\\RScripts\\executeSimpleExtinctionRScript.R", rScriptOutputPath);

        String imagePath = rScriptOutputPath + "\\phylomorphospace.jpg";
        Image image = new Image(new File(imagePath).toURI().toString());
        phylomorphospaceIMG.setImage(image);
    }
    private List<String> obtainSpeciesList() {
        return Scene2Controller.SharedData.getTaxaList();
    }
}
