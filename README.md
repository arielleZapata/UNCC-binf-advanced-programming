
# UNCC Bioinformatics Advanced Programming

This repository contains the Java-based assignments and final project for the **Bioinformatics Advanced Programming** course at the University of North Carolina at Charlotte (UNCC). It demonstrates advanced programming concepts applied to bioinformatics, focusing on sequence analysis, probability-based DNA generation, and extinction event simulations.

## Table of Contents
- [Overview](#overview)
- [Project Structure](#project-structure)
- [Installation](#installation)
- [Usage](#usage)
- [Features](#features)
- [Technologies](#technologies)
- [Contributing](#contributing)
- [License](#license)

## Overview

This repository includes:
- **Lab Assignments**: Practical implementations of bioinformatics algorithms, including sequence parsing, DNA probability calculations, and random sequence generation.
- **Final Project**: A JavaFX-based GUI for simulating extinction events based on species data loaded from CSV files.

## Project Structure

```
src/
├── example/
│   └── HelloWorld.java                  # Example starter code
├── final_project/
│   └── FinalProject.java                # Final project: extinction event simulation
├── lab1/
│   └── Lab1_Code.java                   # Lab 1: Random DNA sequence generation and analysis
├── lab2/
│   └── Lab2_Code.java                   # Lab 2: Placeholder (extend as needed)
├── lab3/
│   ├── FastaSequence.java               # Lab 3: FASTA file parsing and GC ratio calculation
│   └── lab3_dna_fasta_practice.txt      # Lab 3: Example input data (FASTA format)
│   └── outputFileLab3.txt               # Lab 3: Example output file (results from GC ratio)
├── lab4/
│   ├── lab4_Code.java                   # Lab 4: Amino acid quiz game (Java Swing)
│   └── javascript_version/
│       └── lab4_CodeJS.html             # Lab 4: Amino acid quiz game (JavaScript version)
└── lab5/
    └── lab5_Code.java                   # Lab 5: Multi-threaded DNA GC content calculator
```

## Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/uncc-binf-advanced-programming.git
   ```
2. Navigate into the project directory:
   ```bash
   cd uncc-binf-advanced-programming
   ```
3. Compile the Java code:
   ```bash
   javac -d out src/**/*.java
   ```

## Features

- **Lab 1**: Generates random DNA sequences and analyzes repeating 3-mer sequences like "AAA" and "CCC".
- **Lab 3**: Reads a FASTA file, calculates the GC ratio, and outputs the results in a tabular format.
- **Lab 4**: Provides an interactive quiz on amino acids, available in both Java Swing and JavaScript versions.
- **Lab 5**: Multi-threaded GC content calculator with real-time progress updates.
- **Final Project**: A GUI to simulate extinction events using JavaFX and data input from CSV files.

## Technologies

- **Java**: Main language used to implement all the labs and the final project.
- **JavaFX**: For the GUI in the final project.
- **Java Swing**: For GUI-based labs (like the amino acid quiz).
- **JavaScript**: For the web-based version of the amino acid quiz.
- **Multithreading**: Used in Lab 5 to improve the performance of GC content calculations.
  
## Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository.
2. Create a new branch (`git checkout -b feature-branch`).
3. Commit your changes (`git commit -m 'Add feature'`).
4. Push the branch (`git push origin feature-branch`).
5. Open a pull request.
