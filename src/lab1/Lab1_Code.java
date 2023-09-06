package lab1;

import java.util.Random;

public class Lab1_Code {
	public static void main(String[] args) {
		Random random = new Random();
		int total = 0;
		int count = 0;
		int countAAA = 0;
		while (total < 1000) {
			String dna = "";
			while(dna.length()<=2) {
				int num = random.nextInt(3);
				if (num == 0) {
					dna = dna + "A";
				}
				else if(num == 1) {
					dna = dna + "G";
				}
				else if (num == 2) {
					dna = dna + "C";
				}
				else if (num == 3) {
					dna = dna + "T";
				}
			}
			if (dna.equals("AAA") || dna.equals("CCC") || dna.equals("GGG") || dna.equals("TTT")) {
				count++;
			}
			if (dna.equals("AAA")) {
				countAAA++;
			}
			System.out.println(dna);
			total++;
		}
		System.out.println(count);
		
		
		// different probabilities
		System.out.println("With Weighted Probabilities");
		int total2 = 0;
		int count2 = 0;
		int countAAA2 = 0;
		while (total2 < 1000) {
			String dna2 = "";
			while(dna2.length()<=2) {
				float num2 = random.nextFloat();
				float probA = 0.12f; //0-.12
				float probC = 0.38f; // .12-.5
				float probG = 0.39f; // .5-.89
				float probT = 0.11f; // .89-1
				float dnaA = probA;
				float dnaC = probA+probC;
				float dnaG = probA+probC+probG;
				float dnaT = probA+probC+probG+probT;
				if (num2 < dnaA) {
                    dna2 = dna2 + "A";
                } else if (num2 < dnaC) {
                    dna2 = dna2 + "C";
                } else if (num2 < dnaG) {
                    dna2 = dna2 + "G";
                } else if (num2 < dnaT) {
                    dna2 = dna2 + "T";
                }
			}
			if (dna2.equals("AAA") || dna2.equals("CCC") || dna2.equals("GGG") || dna2.equals("TTT")) {
				count2++;
			}
			if (dna2.equals("AAA")) {
				countAAA2++;
			}
			System.out.println(dna2);
			total2++;
		}
		System.out.println(count2);
	System.out.println("Summary:");
	System.out.println("No probabilities.");
	System.out.println(count);
	System.out.println(countAAA);
	System.out.println("Weighted probabilities.");
	System.out.println(count2);
	System.out.println(countAAA2);
	}
}

/* (2) The expected "AAA" count is according close to what. 
 * P(A) has a 0.25 chance of occurring
 * in a 3-mer. 0.25*0.25*0.25=0.015625 and the expected number
 * of "AAA" in the 1,000 is around 15 to 16. So the generated
 * values that I got from the random were a bit higher 
 * (30-40).
 * 
 * (3) The expected "AAA" count now is 0.12*0.12*0.12=0.001728.
 * The number of "AAA" in 1,000 is around 1 to 2. In this case,
 * Java was closer to expected (3-5)
 */
	