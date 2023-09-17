package lab2;

import java.util.Random;

public class Lab2_Code {
    public static void main(String[] args) {
        // game settings
        System.out.println("Length of practice (in seconds): ");
        String inputSeconds = System.console().readLine();
        Float secsWanted = Float.valueOf(inputSeconds);

        System.out.println("Question length of practice : ");
        String inputQuestions = System.console().readLine();
        int questionsWanted = Integer.valueOf(inputQuestions);
        
        // calculate time 
        Random random = new Random();
        long initialTime = System.currentTimeMillis();
        float secondsElapsed = (System.currentTimeMillis() - initialTime) / 1000f;
        int score = 0;
        int questionsShown = 0;
        
        // track the correct and incorrect answers
        String[] correctAnswers = new String[questionsWanted];
        int[] correctCounts = new int[SHORT_NAMES.length];

        String[] incorrectAnswers = new String[questionsWanted];
        int[] incorrectCounts = new int[SHORT_NAMES.length];

        
        while (secondsElapsed < secsWanted) {
        	// end prompt for max questions
            if (questionsShown >= questionsWanted) {
                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                System.out.println("Max questions reached.");
                System.out.println("Score: " + score + " of " + questionsWanted);
                System.out.println("Total Time: " + secondsElapsed + " of " + secsWanted);
                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                break;
            }
            
            // ask for names
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            int randomI = random.nextInt(FULL_NAMES.length);
            String randomAA = FULL_NAMES[randomI];
            System.out.println("Name: " + randomAA);
            System.out.println("Enter short name: ");
            String input = System.console().readLine().toUpperCase();
            // if the user types quit give results and quit the program
            if (input.equals("QUIT")) {
                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                System.out.println("User has quit.");
                System.out.println("Score: " + score + " of " + questionsWanted);
                System.out.println("Total Time: " + secondsElapsed + " of " + secsWanted);
                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                System.exit(0);
            }
            String AAchar = "" + input.charAt(0);

            // track whether the answers are correct or incorrect and add them to the array
            if (AAchar.equals(SHORT_NAMES[randomI])) {
                System.out.println("Correct!");
                correctAnswers[score] = randomAA;
                correctCounts[randomI]++;
                score++;
            } else {
                System.out.println("Incorrect! Correct answer: " + SHORT_NAMES[randomI]);
                incorrectAnswers[questionsShown] = randomAA;
                incorrectCounts[randomI]++;
            }
            questionsShown++;
            
            // game info after every question
            secondsElapsed = (System.currentTimeMillis() - initialTime) / 1000f;
            System.out.println("Your Answer: " + AAchar);
            System.out.println("Score: " + score);
            System.out.println("Total Time: " + secondsElapsed + " of " + secsWanted);
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        }
        
        // print out game scoring and which ones were correct and incorrect
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        System.out.println("~~|Practice End|~~");
        System.out.println("Summary of Correct Answers:");
        System.out.println("Correct Answer Counts:");
        for (int i = 0; i < FULL_NAMES.length; i++) {
            System.out.println(FULL_NAMES[i] + ": " + correctCounts[i]);
        }
        System.out.println("Incorrect Answer Counts:");
        for (int i = 0; i < FULL_NAMES.length; i++) {
            System.out.println(FULL_NAMES[i] + ": " + incorrectCounts[i]);
        }
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    }

    public static String[] SHORT_NAMES =
            {"A", "R", "N", "D", "C", "Q", "E",
                    "G", "H", "I", "L", "K", "M", "F",
                    "P", "S", "T", "W", "Y", "V"};

    public static String[] FULL_NAMES =
            {
                    "alanine", "arginine", "asparagine",
                    "aspartic acid", "cysteine",
                    "glutamine", "glutamic acid",
                    "glycine", "histidine", "isoleucine",
                    "leucine", "lysine", "methionine",
                    "phenylalanine", "proline",
                    "serine", "threonine", "tryptophan",
                    "tyrosine", "valine"};
}
