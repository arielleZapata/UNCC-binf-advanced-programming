package lab3;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class FastaSequence {
	private String header;
	private String sequence;

	public static void main(String[] args) throws Exception {
		ArrayList<FastaSequence> list = FastaSequence.readFastaFile(
				"C:\\Users\\zapat\\git\\binf-advanced-programming\\src\\lab3\\lab3_dna_fasta_practice.txt");
		for (FastaSequence data : list) {
			System.out.println(data.getHeader());
			System.out.println(data.getSequence());
			System.out.println(data.getGCRatio());
		}
		File outputFile = new File("C:\\Users\\zapat\\git\\binf-advanced-programming\\src\\lab3\\outputFileLab3.txt");
		FastaSequence.writeTableSummary(list, outputFile);
	}

	public FastaSequence(String header, String sequence) {
		this.header = header;
		this.sequence = sequence;
	}

	public String getHeader() {
		return header.substring(1);
	}

	public String getSequence() {
		return sequence;
	}

	public float getGCRatio() {
		int count = 0;
		for (char base : sequence.toCharArray()) {
			if (base == 'g' || base == 'c') {
				count++;
			}
		}
		return (float) count / sequence.length();
	}

	public static ArrayList<FastaSequence> readFastaFile(String filepath) throws Exception {
		ArrayList<FastaSequence> list = new ArrayList<>();
		String header = "";
		String sequence = "";

		BufferedReader reader = new BufferedReader(new FileReader(filepath));
		String line = "";
		while ((line = reader.readLine()) != null) {
			if (line.startsWith(">")) {
				if (!header.isEmpty()) {
					list.add(new FastaSequence(header, sequence));
				}
				header = line;
				sequence = "";
			} else {
				sequence += line;
			}
		}
		if (!header.isEmpty()) {
			list.add(new FastaSequence(header, sequence));
		}
		reader.close();
		return list;
	}

	public static void writeTableSummary(ArrayList<FastaSequence> list, File outputFile) throws Exception{
		BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
		writer.write("sequenceID\tnumA\tnumC\tnumG\tnumT\tsequence");
		writer.newLine();
		for (FastaSequence data: list) {
			int countA = 0;
			int countG = 0;
			int countC = 0;
			int countT = 0;
			for (char base : data.getSequence().toCharArray()) {
				switch(base) {
				case 'a':
					countA++;
					break;
				case 'g':
					countG++;
					break;
				case 'c':
					countC++;
					break;
				case 't':
					countT++;
					break;
			}
		}
		writer.write(data.getHeader() + "\t" + countA + "\t" + countG + "\t" + countC + "\t" + countT + "\t" + data.getSequence());
		writer.newLine();
		}
		writer.close();
	}
}