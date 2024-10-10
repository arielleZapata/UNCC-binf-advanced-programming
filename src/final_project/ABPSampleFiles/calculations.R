library(geomorph)
library(phytools)
library(ggplot2)

file_lines <- readLines("~/ABP-final/calculationSettings.txt")

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

tree <- read.tree(tree.file)
tree <- as.phylo(tree)
csv <- read.csv(csv.file)

rownames(csv) <- csv$species_name
matched_names <- intersect(rownames(csv), tree$tip.label)

# Check if graphX and graphY are PCA1 and PCA2
if (graphX == "PCA1" && graphY == "PCA2") {
  PCA <- gm.prcomp(csv[matched_names, c("value1", "value2")])

  jpeg("src/main/imagesFromPhylomorphospaces/phylomorphospace_PCA.jpg", width = 1200, height = 1200, units = "px", res = 300)
  phylomorphospace(tree, PCA$x[, 1:2], label = FALSE)
  title(main = "Phylomorphospace with PCA", font.main = 3)
  dev.off()

  pdf("src/main/imagesFromPhylomorphospaces/phylomorphospace_PCA.pdf", width = 8, height = 6)
  phylomorphospace(tree, PCA$x[, 1:2], label = FALSE)
  title(main = "Phylomorphospace with PCA", font.main = 3)
  dev.off()
} else {
  x <- csv[matched_names, graphX]
  y <- csv[matched_names, graphY]

  jpeg("src/main/imagesFromPhylomorphospaces/phylomorphospace_noPCA.jpg", width = 1200, height = 1200, units = "px", res = 300)
  phylomorphospace(tree, cbind(x, y), label = FALSE)
  title(main = "Phylomorphospace without PCA", font.main = 3)
  dev.off()

  pdf("src/main/imagesFromPhylomorphospaces/phylomorphospace_noPCA.pdf", width = 8, height = 6)
  phylomorphospace(tree, cbind(x, y), label = FALSE)
  title(main = "Phylomorphospace without PCA", font.main = 3)
  dev.off()
}
