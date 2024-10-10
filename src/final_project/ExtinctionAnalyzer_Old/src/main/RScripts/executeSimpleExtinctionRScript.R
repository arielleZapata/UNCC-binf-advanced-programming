library(geomorph)
library(phytools)
library(ggplot2)

calc_file_lines <-
  readLines("C:\\Users\\zapat\\IdeaProjects\\FinalProject-ExtinctionAnalyzer\\src\\main\\TextFiles\\calculationSettings.txt")
extinct_species <-
  readLines("C:\\Users\\zapat\\IdeaProjects\\FinalProject-ExtinctionAnalyzer\\src\\main\\TextFiles\\extinctSpeciesList.txt")

csv.file <- ""
tree.file <- ""
graphX <- ""
graphY <- ""

# read calculations file
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

# method to remove species from the tree and csv
remove_extinct_species <-
  function(tree, csv, extinct_species_line) {
    if (length(extinct_species_line) == 0) {
      return(list(tree = tree, csv = csv))
    } else {
      extinct_list <- unlist(strsplit(extinct_species_line, ", "))
      
      extinct_indices <- match(extinct_list, tree$tip.label)
      extinct_indices <- extinct_indices[!is.na(extinct_indices)]
      
      pruned_tree <- drop.tip(tree, extinct_indices)
      pruned_csv <- csv[!rownames(csv) %in% extinct_list,]
      
      return(list(tree = pruned_tree, csv = pruned_csv))
    }
  }

# make variables from calculation settings
tree <- read.tree(tree.file)
tree <- as.phylo(tree)
csv <- read.csv(csv.file)
rownames(csv) <- csv$species_name

# remove extinct species from the tree and CSV data if needed
for (extinct_line in extinct_species) {
  modified_data <- remove_extinct_species(tree, csv, extinct_line)
  tree <- modified_data$tree
  csv <- modified_data$csv
}
# Check if the extinct species list is empty, if so, generate the phylomorphospace plots with full data
if (length(extinct_species) == 0) {
  # method to create phylomorphospace depending on if PCA or other
  if (graphX == "PCA1" && graphY == "PCA2") {
    PCA <- gm.prcomp(csv[tree$tip.label, c("value1", "value2")])
    
    jpeg(
      "C:\\Users\\zapat\\IdeaProjects\\FinalProject-ExtinctionAnalyzer\\src\\main\\RScriptOutputs\\phylomorphospace.jpg",
      width = 1200,
      height = 1200,
      units = "px",
      res = 300
    )
    phylomorphospace(tree,
                     PCA$x[, 1:2],
                     label = FALSE,
                     xlab = "PCA1",
                     ylab = "PCA2")
    title(main = "Phylomorphospace with PCA", font.main = 3)
    dev.off()
  } else {
    x <- csv[tree$tip.label, graphX]
    y <- csv[tree$tip.label, graphY]
    
    jpeg(
      "C:\\Users\\zapat\\IdeaProjects\\FinalProject-ExtinctionAnalyzer\\src\\main\\RScriptOutputs\\phylomorphospace.jpg",
      width = 1200,
      height = 1200,
      units = "px",
      res = 300
    )
    phylomorphospace(tree,
                     cbind(x, y),
                     label = FALSE,
                     xlab = graphX,
                     ylab = graphY)
    title(main = "Phylomorphospace without PCA", font.main = 3)
    dev.off()
  }
} else {
  if (graphX == "PCA1" && graphY == "PCA2") {
    PCA <- gm.prcomp(csv[tree$tip.label, c("value1", "value2")])
    
    jpeg(
      "C:\\Users\\zapat\\IdeaProjects\\FinalProject-ExtinctionAnalyzer\\src\\main\\RScriptOutputs\\phylomorphospace.jpg",
      width = 1200,
      height = 1200,
      units = "px",
      res = 300
    )
    phylomorphospace(tree,
                     PCA$x[, 1:2],
                     label = FALSE,
                     xlab = "PCA1",
                     ylab = "PCA2")
    title(main = "Phylomorphospace with PCA", font.main = 3)
    dev.off()
  } else {
    x <- csv[tree$tip.label, graphX]
    y <- csv[tree$tip.label, graphY]
    
    jpeg(
      "C:\\Users\\zapat\\IdeaProjects\\FinalProject-ExtinctionAnalyzer\\src\\main\\RScriptOutputs\\phylomorphospace.jpg",
      width = 1200,
      height = 1200,
      units = "px",
      res = 300
    )
    phylomorphospace(tree,
                     cbind(x, y),
                     label = FALSE,
                     xlab = graphX,
                     ylab = graphY)
    title(main = "Phylomorphospace without PCA", font.main = 3)
    dev.off()
  }
}