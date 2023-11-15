package final_project;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class FinalProject extends Application {

    // Variables for components
    private Stage stage;
    private VBox initialInterfaceLayout;
    private VBox dataSelectionLayout;
    private VBox extinctionEventLayout;
    private VBox progressReportLayout;

    // Variables for component parts
    private TextField speciesNameField;
    private TextField otherDataField;
    private Label selectedFileLabel;

    // Method to initialize GUI and first thing user sees
    private void initializeInitialInterface() {
        initialInterfaceLayout = new VBox(10);
        Label titleLabel = new Label("Initial Interface");

        Button startButton = new Button("Start");
        startButton.setOnAction(e -> changePage(dataSelectionLayout));

        Button helpButton = new Button("Help/Documentation");
        // Add functionality to help button (link to open documentation)
        // helpButton.setOnAction(e -> handleHelpButton());

        Button nextButton = new Button("Next");
        nextButton.setOnAction(e -> changePage(dataSelectionLayout));

        initialInterfaceLayout.getChildren().addAll(titleLabel, startButton, helpButton, nextButton);
    }

    // Method to initialize the data selection page
    private void initializeDataSelection() {
            dataSelectionLayout = new VBox(10);
            Label titleLabel = new Label("Data Selection");

            Button loadDataset = new Button("Load Dataset (CSV File)");
            loadDataset.setOnAction(e -> handleLoadDataset());

            // Eventually will print out the information that was loaded/parsed
            // Species + Other data
            selectedFileLabel = new Label("No file selected");

            dataSelectionLayout.getChildren().addAll(
                    titleLabel,
                    loadDataset,
                    selectedFileLabel
            );
        }

    private void handleLoadDataset() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Dataset File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            selectedFileLabel.setText("Selected File: " + selectedFile.getName());
            // add functionality to parse the selected file and display the information
        } else {
            selectedFileLabel.setText("No file selected");
        }
    }

    // Method to initialize the extinction event simulation page
    private void initializeExtinctionEvent() {
    }

    // Method to initialize the progress report page
    private void initializeProgressReport() {
    }

    // Method to switch between pages
    private void changePage(VBox layout) {
        Scene scene = new Scene(layout, 800, 600);
        stage.setScene(scene);
    }

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        initializeInitialInterface();
        initializeDataSelection();
        initializeExtinctionEvent();
        initializeProgressReport();

        // Set initial scene to initial interface layout
        changePage(initialInterfaceLayout);

        stage.setTitle("Final Project");
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}