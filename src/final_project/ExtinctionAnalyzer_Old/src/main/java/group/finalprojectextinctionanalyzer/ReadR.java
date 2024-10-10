package group.finalprojectextinctionanalyzer;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class ReadR {
    public static void main(String[] args) {
        ReadR.runRScript("C:\\Users\\zapat\\IdeaProjects\\FinalProject-ExtinctionAnalyzer\\src\\main\\RScripts\\executeSimpleExtinctionRScript.R", "C:\\Users\\zapat\\IdeaProjects\\FinalProject-ExtinctionAnalyzer\\src\\main\\TextFiles");
    }

    public static void runRScript(String scriptPath, String outputFolder) {
        try {
            String rScriptExecutable = "\\Program Files\\R\\R-4.3.2\\bin\\Rscript.exe";
            String[] command = {rScriptExecutable, scriptPath};

            ProcessBuilder processBuilder = new ProcessBuilder(command);
            processBuilder.directory(new File(outputFolder));

            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("R stdout: " + line);
            }

            // Capture error output
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            while ((line = errorReader.readLine()) != null) {
                System.err.println("R stderr: " + line);
            }

            int exitCode = process.waitFor();
            System.out.println("R script executed, exit code: " + exitCode);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
