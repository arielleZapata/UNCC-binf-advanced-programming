package group.finalprojectextinctionanalyzer;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class ReadNValidateFiles {
    public boolean validateSpeciesNameInCSVAndTree(String csvFilePath, String newickTreeFilePath, Label speciesNameValidationStatus) {
        if (csvFilePath == null || newickTreeFilePath == null) {
            return false;
        }
        File csvFile = new File(csvFilePath);
        File newickTreeFile = new File(newickTreeFilePath);

        Set<String> speciesNamesFromCSV = extractSpeciesNamesFromCSV(csvFile);
        Set<String> speciesNamesFromTree = extractSpeciesNamesFromNewickTree(newickTreeFile);

        boolean match = speciesNamesFromCSV.containsAll(speciesNamesFromTree) &&
                speciesNamesFromTree.containsAll(speciesNamesFromCSV);

        if (match) {
            speciesNameValidationStatus.setText("Species names in CSV match those in the Newick tree.");
        } else {
            speciesNameValidationStatus.setText("Species names in CSV do not match those in the Newick tree.");
        }
        return match;
    }
    private Set<String> extractSpeciesNamesFromCSV(File csvFile) {
        Set<String> speciesNames = new HashSet<>();
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String headerLine = br.readLine();
            String[] columns = headerLine.split(",");

            int speciesIndex = -1;
            for (int i = 0; i < columns.length; i++) {
                if (columns[i].trim().equalsIgnoreCase("species_name")) {
                    speciesIndex = i;
                    break;
                }
            }
            if (speciesIndex != -1) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] values = line.split(",");
                    if (values.length > speciesIndex) {
                        speciesNames.add(values[speciesIndex].trim());
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return speciesNames;
    }
    private Set<String> extractSpeciesNamesFromNewickTree(File newickTreeFile) {
        Set<String> speciesNames = new HashSet<>();

        try (BufferedReader br = new BufferedReader(new FileReader(newickTreeFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split("[,:;()]");
                for (String token : tokens) {
                    if (!token.isEmpty() && !token.matches(".*[0-9]+.*")) {
                        speciesNames.add(token.trim());
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return speciesNames;
    }
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}