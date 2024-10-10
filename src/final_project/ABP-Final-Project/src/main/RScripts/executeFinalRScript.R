library(geomorph)
library(phytools)
library(ggplot2)

# Read calculation settings
calc_file_lines <- readLines("C:\\Users\\zapat\\IdeaProjects\\FinalProject-ExtinctionAnalyzer\\src\\main\\TextFiles\\calculationSettings.txt")
extinct_species <- readLines("C:\\Users\\zapat\\IdeaProjects\\FinalProject-ExtinctionAnalyzer\\src\\main\\TextFiles\\extinctSpeciesList.txt")

csv.file <- ""
tree.file <- ""
graphX <- ""
graphY <- ""

# Read calculations file
for (line in calc_file_lines) {
  parts <- strsplit(line, "=")[[1]]
  key <- trimws(parts[1])
  value <- trimws(parts[2])
  
  if (key == "csv.file") {
    csv.file <- gsub('"', '', value)
  } else if (key == "tree.file") {
    tree.file <- gsub('"', '', value)
  } else if (key == "graphX") {
    graphX <- gsub('"', '', value)
  } else if (key == "graphY") {
    graphY <- gsub('"', '', value)
  }
}

# Method to remove species from the tree and csv
remove_extinct_species <- function(tree, csv, extinct_species_line) {
  if (length(extinct_species_line) == 0 || is.null(extinct_species_line)) {
    return(list(tree = tree, csv = csv))
  } else {
    extinct_list <- unlist(strsplit(extinct_species_line, ",\\s*", perl = TRUE))
    extinct_list <- tolower(trimws(extinct_list))
    
    extinct_indices <- match(extinct_list, tolower(tree$tip.label))
    extinct_indices <- extinct_indices[!is.na(extinct_indices)]
    
    pruned_tree <- drop.tip(tree, extinct_indices)
    pruned_csv <- csv[!tolower(rownames(csv)) %in% extinct_list, ]
    
    return(list(tree = pruned_tree, csv = pruned_csv))
  }
}

# Make variables from calculation settings 
tree <- read.tree(tree.file)
tree <- as.phylo(tree)
csv <- read.csv(csv.file)
rownames(csv) <- csv$species_name

original_tree <- tree
original_csv <- csv

# Open PDF for writing all plots in append mode
pdf("all_extinct_species_graphs.pdf", onefile = TRUE)

# Plot with original data
if (graphX == "PCA1" && graphY == "PCA2") {
  PCA <- gm.prcomp(csv[, c("value1", "value2")])
  phylomorphospace(tree, PCA$x[, 1:2], label = FALSE, xlab = "PCA1", ylab = "PCA2")
  title(main = "Phylomorphospace with PCA (Original Data)", font.main = 3)
} else {
  x <- csv[, graphX]
  y <- csv[, graphY]
  phylomorphospace(tree, cbind(x, y), label = FALSE, xlab = graphX, ylab = graphY)
  title(main = "Phylomorphospace without PCA (Original Data)", font.main = 3)
}

# Generate phylomorphospace plots for each extinct species
for (extinct_line in extinct_species) {
  extinct_species_list <- unlist(strsplit(extinct_line, ",\\s*", perl = TRUE))
  
  modified_data <- remove_extinct_species(original_tree, original_csv, extinct_species_list)
  pruned_tree <- modified_data$tree
  pruned_csv <- modified_data$csv
  
  if (graphX == "PCA1" && graphY == "PCA2") {
    PCA <- gm.prcomp(pruned_csv[pruned_tree$tip.label, c("value1", "value2")])
    phylomorphospace(pruned_tree, PCA$x[, 1:2], label = FALSE, xlab = "PCA1", ylab = "PCA2")
    title(main = paste("Phylomorphospace with PCA (Extinct Species:", paste(extinct_species_list, collapse = ", "), ")", sep = " "), font.main = 3)
  } else {
    x <- pruned_csv[pruned_tree$tip.label, graphX]
    y <- pruned_csv[pruned_tree$tip.label, graphY]
    phylomorphospace(pruned_tree, cbind(x, y), label = FALSE, xlab = graphX, ylab = graphY)
    title(main = paste("Phylomorphospace without PCA (Extinct Species:", paste(extinct_species_list, collapse = ", "), ")", sep = " "), font.main = 3)
  }
}

# Close the PDF file
dev.off()
