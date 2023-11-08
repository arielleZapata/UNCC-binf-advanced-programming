package lab5;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.TitledBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class lab5_Code implements Runnable{
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(500, 500);
        frame.setTitle("Factorial Calculator GUI");
        frame.setLayout(new GridLayout(6, 6));
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // display the formula
        JPanel formulaPanel = new JPanel(new FlowLayout());
        formulaPanel.setBorder(new TitledBorder("Factorial Formula"));
        JLabel formula = new JLabel("n! = ?");
        formulaPanel.add(formula);
        frame.add(formulaPanel);

        // user input area
        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.setBorder(new TitledBorder("Input a Number"));
        JTextField numberField = new JTextField(10);
        JButton startButton = new JButton("Calculate");
        inputPanel.add(new JLabel("n = "));
        inputPanel.add(numberField);
        inputPanel.add(startButton);
        frame.add(inputPanel, BorderLayout.NORTH);

        // result display
        JPanel resultPanel = new JPanel(new BorderLayout());
        resultPanel.setBorder(new TitledBorder("Results"));
        JTextArea resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultPanel.add(new JScrollPane(resultArea), BorderLayout.CENTER);
        frame.add(resultPanel, BorderLayout.CENTER);


        frame.setVisible(true);
    }
    public void run() {

    }
}
