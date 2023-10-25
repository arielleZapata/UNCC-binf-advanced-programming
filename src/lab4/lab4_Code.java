package lab4;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.TitledBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.Timer;

public class lab4_Code {
    private static JFrame frame;
    private static JLabel questionLabel;
    private static JTextField answerField;
    private static JLabel correctScoreLabel;
    private static JLabel incorrectScoreLabel;
    private static JLabel timeLabel;
    private static JButton startButton;
    private static JButton submitButton;
    private static JButton cancelButton;
    private static int correctScore;
    private static int incorrectScore;
    private static Timer timer;
    private static Random random;
    private static boolean quizStarted = false; // Added a flag for quiz start

    public static void main(String[] args) {
        frame = new JFrame();
        frame.setSize(500, 500);
        frame.setTitle("Amino Acid Quiz");
        frame.setLayout(new GridLayout(5, 4));
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Top half of the game: question, input, enter, cancel
        JPanel topOfGame = new JPanel(new GridLayout(1, 2));
        topOfGame.setBorder(new TitledBorder("Game"));
        frame.add(topOfGame);

        // Question
        JPanel question = new JPanel(new GridLayout(2, 1));
        question.setBorder(new TitledBorder("Full Name:"));
        questionLabel = new JLabel("full name here.");
        question.add(questionLabel);
        topOfGame.add(question);

        // Answer
        JPanel answer = new JPanel(new GridLayout(2, 1));
        answer.setBorder(new TitledBorder("Short Name:"));
        answerField = new JTextField(2);
        answer.add(answerField);
        topOfGame.add(answer);

        // Start/Cancel quiz buttons
        JPanel buttons = new JPanel(new GridLayout(1, 3));
        buttons.setBorder(new TitledBorder("Game Buttons"));
        startButton = new JButton("Start Quiz");
        buttons.add(startButton);
        submitButton = new JButton("Submit Answer");
        buttons.add(submitButton);
        cancelButton = new JButton("Cancel");
        buttons.add(cancelButton);
        frame.add(buttons);

        // Bottom half of the game: correct score, incorrect score, time left
        JPanel bottomOfGame = new JPanel(new GridLayout(1, 2));
        bottomOfGame.setBorder(new TitledBorder("Game Stats"));
        frame.add(bottomOfGame);

        // Score panel
        JPanel score = new JPanel(new GridLayout(2, 1));
        score.setBorder(new TitledBorder("Score"));
        correctScoreLabel = new JLabel("Correct Score: " + correctScore);
        score.add(correctScoreLabel);
        incorrectScoreLabel = new JLabel("Incorrect Score: " + incorrectScore);
        score.add(incorrectScoreLabel);
        bottomOfGame.add(score);

        // Time panel
        JPanel timePanel = new JPanel(new GridLayout(2, 1));
        timePanel.setBorder(new TitledBorder("Time"));
        timeLabel = new JLabel("Time Left: 30");
        timePanel.add(timeLabel);
        bottomOfGame.add(timePanel);

        // Time logic
        timer = new Timer(1000, new ActionListener() {
            int timeLeft = 30;

            @Override
            public void actionPerformed(ActionEvent e) {
                timeLeft--;
                timeLabel.setText("Time Left: " + timeLeft);
                if (timeLeft == 0) {
                    timer.stop();
                    quitQuiz();
                }
            }
        });

        // Start button logic
        random = new Random();
        startButton.addActionListener(e -> {
            if (!quizStarted) { // Check if the quiz has not started
                quizStarted = true;
                correctScore = 0;
                incorrectScore = 0;
                timer.start();
                startButton.setEnabled(false);
                submitButton.setEnabled(true);
                cancelButton.setEnabled(true);
                askQuestion();
            }
        });

        submitButton.addActionListener(e -> checkAnswer());

        cancelButton.addActionListener(e -> {
            timer.stop();
            quitQuiz();
        });

        frame.setVisible(true);
    }

    public static void quitQuiz() {
        quizStarted = false; // Reset the quiz started flag
        startButton.setEnabled(true);
        submitButton.setEnabled(false);
        cancelButton.setEnabled(false);
        JOptionPane.showMessageDialog(frame, "Quiz Over!\nCorrect Score: " + correctScore + "\nIncorrect Score: " + incorrectScore);
        answerField.setText("");
        timeLabel.setText("Time Left: 30");
    }

    private static void askQuestion() {
        int randomI = random.nextInt(FULL_NAMES.length);
        String randomAA = FULL_NAMES[randomI];
        questionLabel.setText(randomAA);
        answerField.setText("");
    }

    private static void checkAnswer() {
        String input = answerField.getText().toUpperCase();
        int randomI = random.nextInt(FULL_NAMES.length);
        String correctAnswer = SHORT_NAMES[randomI];

        if (input.equals(correctAnswer)) {
            correctScore++;
        } else {
            incorrectScore++;
        }
        answerField.setText("");
        correctScoreLabel.setText("Correct Score: " + correctScore);
        incorrectScoreLabel.setText("Incorrect Score: " + incorrectScore);
        askQuestion();
    }

    private static final String[] SHORT_NAMES = {
            "A", "R", "N", "D", "C", "Q", "E",
            "G", "H", "I", "L", "K", "M", "F",
            "P", "S", "T", "W", "Y", "V"
    };

    private static final String[] FULL_NAMES = {
            "alanine", "arginine", "asparagine",
            "aspartic acid", "cysteine",
            "glutamine", "glutamic acid",
            "glycine", "histidine", "isoleucine",
            "leucine", "lysine", "methionine",
            "phenylalanine", "proline",
            "serine", "threonine", "tryptophan",
            "tyrosine", "valine"
    };
}
