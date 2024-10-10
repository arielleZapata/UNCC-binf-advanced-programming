package group.finalprojectextinctionanalyzer;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ExtinctionAnalyzer extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load Scene 1
        FXMLLoader fxmlLoader1 = new FXMLLoader(getClass().getResource("Scene1_Start.fxml"));
        Parent root1 = fxmlLoader1.load();
        Scene scene1 = new Scene(root1);
        Scene1Controller scene1Controller = fxmlLoader1.getController();

        // Load Scene 2
        FXMLLoader fxmlLoader2 = new FXMLLoader(getClass().getResource("Scene2_DataSelection.fxml"));
        Parent root2 = fxmlLoader2.load();
        Scene scene2 = new Scene(root2);
        Scene2Controller scene2Controller = fxmlLoader2.getController();

        // Load Scene 3
        FXMLLoader fxmlLoader3 = new FXMLLoader(getClass().getResource("Scene3_SimulationTemplateTBD.fxml"));
        Parent root3 = fxmlLoader3.load();
        Scene scene3 = new Scene(root3);
        // Get the controller associated with Scene3_SimulationTemplateTBD.fxml
        Scene3Controller scene3Controller = fxmlLoader3.getController();

        // Load Scene 4
        FXMLLoader fxmlLoader4 = new FXMLLoader(getClass().getResource("Scene4_Report.fxml"));
        Parent root4 = fxmlLoader4.load();
        Scene scene4 = new Scene(root4);
        Scene4Controller scene4Controller = fxmlLoader4.getController();

        // Set controllers for each scene if needed
        primaryStage.setTitle("Extinction Analyzer");
        primaryStage.setScene(scene1);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
