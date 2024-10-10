# Extinction Analyzer

## Overview
The Extinction Analyzer, integrated with R scripts, underwent significant refinement to achieve functionality. Despite efforts, potential issues might persist due to the complexity of the calculations and the deadline constraints.

The initial project proposal aimed for a comprehensive analysis, yet the intricate calculations couldn't be fully validated by the project's deadline.

As a modification, the program was adapted to receive input files, generate a species disappearance list from the tree, and produce a new JPG for each alteration. Additionally, it compiles all outputs into a single PDF file.

These modifications aimed to simplify the project scope, focusing on tree alterations and visual outputs rather than the full-fledged complex analysis initially intended.

## Getting Started
### Prerequisites
- Java Development Kit (JDK)
- JavaFX
  - Installation instructions available at the [JavaFX Website](https://openjfx.io/)
- IntelliJ IDEA or any Java IDE

### Installation
1. Clone the repository:
    ```bash
    git clone https://github.com/your-username/final-project-extinction-analyzer.git
    ```
2. Open the project in IntelliJ IDEA.

### Sample Data (Optional for Testing)
If you wish to test the application with sample data:

1. Add sample CSV files and Newick Tree files to the `src/main/TextFiles` directory.
2. Use these files during the application's data selection process.

## Usage
1. Run the `ExtinctionAnalyzer.java` file.
2. The application will launch with multiple scenes:
    - **Scene 1:** Start scene
    - **Scene 2:** Data selection
    - **Scene 3:** Simulation template
    - **Scene 4:** Report scene

3. Navigate through scenes using the provided UI elements top to bottom in order.

## Features
- Scene 1: Start the application.
- Scene 2: Select CSV and Newick Tree files, modify settings.
- Scene 3: View and interact with a checklist of species, save extinct species to a file, execute R scripts.
- Scene 4: Export results to a chosen directory.

## Acknowledgements
- [JavaFX](https://openjfx.io/)
- [FXML](https://openjfx.io/javadoc/17/javafx.fxml/javafx/fxml/doc-files/introduction_to_fxml.html)
