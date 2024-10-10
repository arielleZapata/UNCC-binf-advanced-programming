library(geomorph)
library(phytools)
library(ggplot2)

# Read settings from calculationSettings.txt
file_lines <- readLines("C:/Users/zapat/IdeaProjects/ExtinctionAnalyzer/src/main/TextFiles/calculationSettings.txt")

csv.file <- ""
tree.file <- ""
graphX <- ""
graphY <- ""

for (line in file_lines) {
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

# Read tree and CSV data
tree <- read.tree(tree.file)
tree <- as.phylo(tree)
csv <- read.csv(csv.file)
rownames(csv) <- csv$species_name

# Read extinct species list
extinct_species <- readLines("extinctSpeciesList.txt")

# Function to remove extinct species from the tree
remove_extinct_species <- function(tree, extinct_species) {
  extinct_indices <- match(extinct_species, tree$tip.label)
  extinct_indices <- extinct_indices[!is.na(extinct_indices)]

  if (length(extinct_indices) > 0) {
    pruned_tree <- drop.tip(tree, extinct_indices)
    return(pruned_tree)
  } else {
    return(tree)
  }
}

# Remove extinct species from the tree
tree <- remove_extinct_species(tree, extinct_species)

# Check if graphX and graphY are PCA1 and PCA2
if (graphX == "PCA1" && graphY == "PCA2") {
  PCA <- gm.prcomp(csv[tree$tip.label, c("value1", "value2")])

  jpeg("src/main/imagesFromPhylomorphospaces/phylomorphospace_PCA.jpg", width = 1200, height = 1200, units = "px", res = 300)
  phylomorphospace(tree, PCA$x[, 1:2], label = FALSE)
  title(main = "Phylomorphospace with PCA", font.main = 3)
  dev.off()

  pdf("src/main/imagesFromPhylomorphospaces/phylomorphospace_PCA.pdf", width = 8, height = 6)
  phylomorphospace(tree, PCA$x[, 1:2], label = FALSE)
  title(main = "Phylomorphospace with PCA", font.main = 3)
  dev.off()
} else {
  x <- csv[tree$tip.label, graphX]
  y <- csv[tree$tip.label, graphY]

  jpeg("src/main/imagesFromPhylomorphospaces/phylomorphospace_noPCA.jpg", width = 1200, height = 1200, units = "px", res = 300)
  phylomorphospace(tree, cbind(x, y), label = FALSE)
  title(main = "Phylomorphospace without PCA", font.main = 3)
  dev.off()

  pdf("src/main/imagesFromPhylomorphospaces/phylomorphospace_noPCA.pdf", width = 8, height = 6)
  phylomorphospace(tree, cbind(x, y), label = FALSE)
  title(main = "Phylomorphospace without PCA", font.main = 3)
  dev.off()
}
