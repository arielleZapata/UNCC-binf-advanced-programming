package group.finalprojectextinctionanalyzer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Scene2Controller {
    @FXML
    private Label csvLabel;
    @FXML
    private ListView<String> csvHeaders;
    @FXML
    private Label treeFileLabel;
    private File selectedCSVFile;
    private File selectedTreeFile;
    @FXML
    private Label speciesNameValidationStatus;
    @FXML
    private RadioButton yesRadioButton;
    @FXML
    private RadioButton noRadioButton;
    @FXML
    private ToggleGroup radioGroup;
    @FXML
    private Label xaxis;
    @FXML
    private Label yaxis;
    @FXML
    private void switchToScene1(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Scene1_Start.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    private void handleCSVButtonClick(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select CSV File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        selectedCSVFile = fileChooser.showOpenDialog(stage);

        if (selectedCSVFile != null) {
            csvLabel.setText(selectedCSVFile.getAbsolutePath());
            String filePath = selectedCSVFile.getAbsolutePath();

            List<String> taxaList = obtainTaxaListFromCSV(filePath);
            SharedData.setTaxaList(taxaList);

            try (BufferedReader br = new BufferedReader(new FileReader(selectedCSVFile))) {
                String headerLine = br.readLine();
                String[] columns = headerLine.split(",");

                ObservableList<String> headersObservableList = FXCollections.observableArrayList(columns);
                csvHeaders.setItems(headersObservableList);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void handleTreeImportButtonClick(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Newick Tree File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Newick Tree Files", "*.tre"));
        selectedTreeFile = fileChooser.showOpenDialog(stage);

        if (selectedTreeFile != null) {
            treeFileLabel.setText(selectedTreeFile.getAbsolutePath());
        }
    }
    @FXML
    private void handleModifySettingsButtonClick(ActionEvent event) {
        Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();

        boolean usePCA = yesRadioButton.isSelected();
        CalculationSettingsHandler.modifySettingsFile(usePCA);
        if (usePCA) {
            xaxis.setText("PCA1");
            yaxis.setText("PCA2");
            showAlert("Settings Modified", "PCA settings applied for X and Y axes.");
        } else {
            try {
                if (selectedCSVFile != null) {
                    BufferedReader br = new BufferedReader(new FileReader(selectedCSVFile));
                    String headerLine = br.readLine();
                    br.close();

                    String[] columns = headerLine.split(",");
                    List<String> allHeaders = Arrays.asList(columns);

                    List<String> selectedHeaders = showHeaderSelectionDialog(allHeaders);

                    if (selectedHeaders != null && selectedHeaders.size() >= 2) {
                        String graphX = selectedHeaders.get(0);
                        String graphY = selectedHeaders.get(1);

                        CalculationSettingsHandler.updateSettingsWithSelectedHeaders(graphX, graphY);

                        xaxis.setText(graphX);
                        yaxis.setText(graphY);

                        showAlert("Settings Modified", "Settings updated for selected headers.");
                    } else {
                        showAlert("Error", "Please select at least two headers.");
                    }
                } else {
                    showAlert("Error", "Please select a CSV file first.");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private List<String> obtainTaxaListFromCSV(String csvFilePath) {
        List<String> taxaList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            String headerLine = br.readLine();

            String line;
            while ((line = br.readLine()) != null) {
                String[] columns = line.split(",");
                if (columns.length > 0) {
                    String speciesName = columns[0];
                    taxaList.add(speciesName);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return taxaList;
    }
    @FXML
    private void handleValidationButtonClick(ActionEvent event) {
        if (selectedCSVFile != null && selectedTreeFile != null) {
            ReadNValidateFiles fileReader = new ReadNValidateFiles();
            boolean isValid = fileReader.validateSpeciesNameInCSVAndTree(
                    selectedCSVFile.getAbsolutePath(),
                    selectedTreeFile.getAbsolutePath(),
                    speciesNameValidationStatus // Pass the label here
            );
            if (isValid) {
                updateCalculationSettingsFile(selectedCSVFile.getAbsolutePath(), selectedTreeFile.getAbsolutePath());
            }
        } else {
            System.out.println("Please select both CSV and Tree files.");
        }
    }
    private void updateCalculationSettingsFile(String csvPath, String treePath) {
        try {
            String settingsFilePath = "src/main/TextFiles/calculationSettings.txt";
            File settingsFile = new File(settingsFilePath);

            if (settingsFile.exists()) {
                BufferedReader reader = new BufferedReader(new FileReader(settingsFile));
                String line;
                StringBuilder content = new StringBuilder();

                while ((line = reader.readLine()) != null) {
                    if (line.startsWith("csv.file=")) {
                        content.append("csv.file=\"").append(csvPath).append("\"").append(System.lineSeparator());
                    } else if (line.startsWith("tree.file=")) {
                        content.append("tree.file=\"").append(treePath).append("\"").append(System.lineSeparator());
                    } else {
                        content.append(line).append(System.lineSeparator());
                    }
                }
                reader.close();

                FileWriter writer = new FileWriter(settingsFile);
                writer.write(content.toString());
                writer.close();

                System.out.println("Calculation settings file updated.");
            } else {
                System.out.println("Calculation settings file not found.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private List<String> showHeaderSelectionDialog(List<String> headers) {
        Dialog<List<String>> dialog = new Dialog<>();
        dialog.setTitle("Select Headers");
        dialog.setHeaderText("Choose headers for graphX and graphY");

        ListView<String> listView = new ListView<>();
        listView.getItems().addAll(headers);
        listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        dialog.getDialogPane().setContent(listView);

        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dialog.setResultConverter(buttonType -> {
            if (buttonType == ButtonType.OK) {
                return new ArrayList<>(listView.getSelectionModel().getSelectedItems());
            }
            return null;
        });

        Optional<List<String>> result = dialog.showAndWait();
        return result.orElse(null);
    }
    @FXML
    private void handleExtinctionButtonClick(ActionEvent event) {
        try {
            switchToScene3(event);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void switchToScene3(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Scene3_SimulationTemplateTBD.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    public class SharedData {
        private static List<String> taxaList;
        public static List<String> getTaxaList() {
            return taxaList;
        }
        public static void setTaxaList(List<String> taxa) {
            taxaList = taxa;
        }
    }
}
