package lab5;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.TitledBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class lab5_Code {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(500, 500);
        frame.setTitle("Find Prime Numbers GUI");
        frame.setLayout(new GridLayout(6, 6));
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // user input area
        JPanel userInputArea = new JPanel(new GridLayout(1, 2));
        userInputArea.setBorder(new TitledBorder("Input a Number"));
        JTextField numberField = new JTextField(2);
        userInputArea.add(numberField);
        frame.add(userInputArea);

        // result display
        JPanel resultsDisplay = new JPanel(new GridLayout(1, 2));
        resultsDisplay.setBorder(new TitledBorder("Results"));
        frame.add(resultsDisplay);


        frame.setVisible(true);
    }
}
