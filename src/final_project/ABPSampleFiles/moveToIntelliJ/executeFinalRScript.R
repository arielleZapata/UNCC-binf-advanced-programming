library(geomorph)
library(phytools)
library(ggplot2)

calc_file_lines <- readLines("~/ABP-final/moveToIntelliJ/sampleCalcSettings.txt")
extinct_species <- readLines("~/ABP-final/moveToIntelliJ/sampleExtinctList.txt")

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
remove_extinct_species <- function(tree, csv, extinct_species_line) {
  if (length(extinct_species_line) == 0) {
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

# make variables from calculation settings 
tree <- read.tree(tree.file)
tree <- as.phylo(tree)
csv <- read.csv(csv.file)
rownames(csv) <- csv$species_name

original_tree <- tree
original_csv <- csv

# method to create phylomorphospace depending on if PCA or other
generate_extinct_graphs <- function(tree, csv, extinct_species, graphX, graphY) {
  pdf("all_extinct_species_graphs.pdf")
  
  for (extinct_line in extinct_species) {
    extinct_species_list <- unlist(strsplit(extinct_line, ",\\s*", perl = TRUE))
    
    modified_data <- remove_extinct_species(tree, csv, extinct_species_list)
    print(modified_data)
    pruned_tree <- modified_data$tree
    pruned_csv <- modified_data$csv
    
    if (graphX == "PCA1" && graphY == "PCA2") {
      PCA <- gm.prcomp(pruned_csv[pruned_tree$tip.label, c("value1", "value2")])
      phylomorphospace(pruned_tree, PCA$x[, 1:2], label = FALSE, xlab = "PCA1", ylab = "PCA2")
      title(main = "Phylomorphospace with PCA", font.main = 3)
    } else {
      x <- pruned_csv[pruned_tree$tip.label, graphX]
      y <- pruned_csv[pruned_tree$tip.label, graphY]
      phylomorphospace(pruned_tree, cbind(x, y), label = FALSE, xlab = graphX, ylab = graphY)
      title(main = "Phylomorphospace without PCA", font.main = 3)
    }
    tree <- pruned_tree
    csv <- pruned_csv
  }
  
  dev.off()
}

# run method with data from calculation settings
generate_extinct_graphs(original_tree, original_csv, extinct_species, graphX, graphY)