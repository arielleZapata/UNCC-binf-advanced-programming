package group.finalprojectextinctionanalyzer;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class CalculationSettingsHandler {
    public static void modifySettingsFile(boolean usePCA) {
        String filePath = "src/main/TextFiles/calculationSettings.txt";
        try {
            File file = new File(filePath);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            Map<String, String> settings = new HashMap<>();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("=");
                if (parts.length == 2) {
                    settings.put(parts[0].trim(), parts[1].trim());
                }
            }
            reader.close();
            if (usePCA) {
                settings.put("graphX", "PCA1");
                settings.put("graphY", "PCA2");
            } else {
                settings.put("graphX", "selectedHeaderX");
                settings.put("graphY", "selectedHeaderY");
            }
            FileWriter writer = new FileWriter(file);

            for (Map.Entry<String, String> entry : settings.entrySet()) {
                writer.write(entry.getKey() + "=" + entry.getValue() + "\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void updateSettingsWithSelectedHeaders(String graphX, String graphY) {
        String filePath = "src/main/TextFiles/calculationSettings.txt";
        try {
            File file = new File(filePath);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            Map<String, String> settings = new HashMap<>();
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("=");
                if (parts.length == 2) {
                    settings.put(parts[0].trim(), parts[1].trim());
                }
            }
            reader.close();

            settings.put("graphX", graphX);
            settings.put("graphY", graphY);

            FileWriter writer = new FileWriter(file);

            for (Map.Entry<String, String> entry : settings.entrySet()) {
                writer.write(entry.getKey() + "=" + entry.getValue() + "\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}