package lab5;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class lab5_Code extends JFrame {
    private JLabel inputLabel;
    private JTextArea inputArea;
    private JButton startButton;
    private JButton cancelButton;
    private JTextArea resultsArea;
    private JLabel progressLabel;

    private boolean calculationRunning;
    private boolean calculationCancelled;

    public lab5_Code() {
        setTitle("DNA Calculator");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputLabel = new JLabel("Enter DNA Sequence:");
        inputArea = new JTextArea(10, 40);
        inputPanel.add(inputLabel, BorderLayout.NORTH);
        inputPanel.add(new JScrollPane(inputArea), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        startButton = new JButton("Start");
        cancelButton = new JButton("Cancel");
        cancelButton.setEnabled(false);

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startCalculation();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cancelCalculation();
            }
        });

        buttonPanel.add(startButton);
        buttonPanel.add(cancelButton);

        JPanel resultsPanel = new JPanel(new BorderLayout());
        progressLabel = new JLabel("Progress:");
        resultsArea = new JTextArea(10, 40);
        resultsPanel.add(progressLabel, BorderLayout.NORTH);
        resultsPanel.add(new JScrollPane(resultsArea), BorderLayout.CENTER);

        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(resultsPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void startCalculation() {
        // Disable start button, enable cancel button
        startButton.setEnabled(false);
        cancelButton.setEnabled(true);
        calculationRunning = true;
        calculationCancelled = false;

        // Perform calculation in a separate thread
        Thread calculationThread = new Thread(new Runnable() {
            @Override
            public void run() {
                // Get input DNA sequence
                String dnaSequence = inputArea.getText();

                // Perform slow calculation (e.g., find GC content)
                int gcsFound = 0; // Replace this with actual calculation

                // Update results every few seconds
                while (calculationRunning && !calculationCancelled) {
                    resultsArea.setText("GCs found so far: " + gcsFound);
                    try {
                        Thread.sleep(3000); // Update every 3 seconds
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                // Display final results or cancellation message
                if (calculationCancelled) {
                    resultsArea.setText("Calculation cancelled.");
                } else {
                    resultsArea.setText("Final GCs found: " + gcsFound);
                }

                // Enable start button, disable cancel button
                startButton.setEnabled(true);
                cancelButton.setEnabled(false);
                calculationRunning = false;
            }
        });

        calculationThread.start();
    }

    private void cancelCalculation() {
        calculationCancelled = true;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new lab5_Code();
            }
        });
    }
}
