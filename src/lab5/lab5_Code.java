package lab5;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class lab5_Code extends JFrame {
    private final JPanel buttonPanel;
    private final JTextArea inputArea;
    private final JButton startButton;
    private final JButton cancelButton;
    private final JTextArea resultsArea;
    private boolean calculationCancelled;

    public lab5_Code() {
        setTitle("DNA Calculator");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel inputPanel = new JPanel(new BorderLayout());
        JLabel inputLabel = new JLabel("Enter DNA Sequence:");
        inputArea = new JTextArea(10, 40);
        inputPanel.add(inputLabel, BorderLayout.NORTH);
        inputPanel.add(new JScrollPane(inputArea), BorderLayout.CENTER);

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 1));

        startButton = new JButton("Start");
        cancelButton = new JButton("Cancel");
        cancelButton.setEnabled(false);

        JPanel sliderPanel = new JPanel(new BorderLayout());
        JLabel label = new JLabel("Number of Threads", SwingConstants.CENTER);
        JSlider numThreadSlider = new JSlider(JSlider.HORIZONTAL, 1, 5, 3);
        numThreadSlider.setMajorTickSpacing(1);
        numThreadSlider.setMinorTickSpacing(1);
        numThreadSlider.setPaintTicks(true);
        numThreadSlider.setPaintLabels(true);

        sliderPanel.add(label, BorderLayout.NORTH);
        sliderPanel.add(numThreadSlider, BorderLayout.CENTER);

        buttonPanel.add(startButton);
        buttonPanel.add(cancelButton);
        buttonPanel.add(sliderPanel);

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

        JPanel resultsPanel = new JPanel(new BorderLayout());
        JLabel progressLabel = new JLabel("Progress:");
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
        // clear results area and reset
        resultsArea.setText("");
        startButton.setEnabled(false);
        cancelButton.setEnabled(true);
        calculationCancelled = false;

        // initialize variables for keeping track of the threads
        String seq = inputArea.getText();
        int numThreads = ((JSlider) ((JPanel) buttonPanel.getComponent(2)).getComponent(1)).getValue();
        int partLength = seq.length() / numThreads;
        int[] threadResults = new int[numThreads];
        List<Thread> threads = new ArrayList<>();

        // Track the start time
        long startTime = System.nanoTime();

        // divide the genome equally to the number of threads wanted and calculate GC content
        for (int i = 0; i < numThreads; i++) {
            final int index = i;
            Thread thread = new Thread(() -> {
                int start = index * partLength;
                int end = (index == numThreads - 1) ? seq.length() : start + partLength;
                int gcsFound = 0;
                int lastProgress = 0; // Track the last progress displayed
                for (int j = start; j < end; j++) {
                    char currentChar = seq.charAt(j);
                    if (currentChar == 'G' || currentChar == 'C') {
                        gcsFound++;
                        // Update progress every 100000 nanoseconds
                        long currentTime = System.nanoTime() - startTime;
                        if (currentTime % 100000 <= 10) { // Check if current time is at increments of 100000 nanoseconds
                            final int currentResult = gcsFound;
                            final long elapsed = currentTime;
                            // Print progress only when it changes
                            if (currentResult != lastProgress) {
                                SwingUtilities.invokeLater(() -> {
                                    resultsArea.append("Thread " + index + ": GCs found so far: " + currentResult +
                                            "\nElapsed Time: " + elapsed + " nanoseconds\n");
                                });
                                lastProgress = currentResult;
                            }
                        }
                    }
                }
                threadResults[index] = gcsFound;

                // update when the thread completes its task
                SwingUtilities.invokeLater(() -> {
                    resultsArea.append("Thread " + index + " finished processing.\n");
                });
            });
            threads.add(thread);
            thread.start();
        }
        // wait for all threads to finish
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        long elapsedTime = System.nanoTime() - startTime;
        int totalGcsFound = 0;

        for (int i = 0; i < numThreads; i++) {
            totalGcsFound += threadResults[i];
        }

        // update gui after displaying progress
        if (calculationCancelled) {
            resultsArea.setText("Calculation cancelled.");
        } else {
            // display final results after all progress
            resultsArea.append("Final GCs found: " + totalGcsFound + "\nTotal Time Elapsed: " + elapsedTime + " nanoseconds");
        }
        startButton.setEnabled(true);
        cancelButton.setEnabled(false);
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
