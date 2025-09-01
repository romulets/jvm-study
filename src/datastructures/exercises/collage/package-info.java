/**
 * Problem 1-3. Collage Collating
 * Fodoby is a company that makes customized software tools for creative people. Their newest
 * software, Ottoshop, helps users make collages by allowing them to overlay images on top of each
 * other in a single document. Describe a database to keep track of the images in a given document
 * which supports the following operations:
 * 1. make document(): construct an empty document containing no images
 * 2. import image(x): add an image with unique integer ID x to the top of the document
 * 3. display(): return an array of the document’s image IDs in order from bottom to top
 * 4. move below(x, y): move the image with ID x directly below the image with ID y
 * Operation (1) should run in worst-case O(1) time, operations (2) and (3) should each run in worst-
 * case O(n) time, while operation (4) should run in worst-case O(log n) time, where n is the number
 * of images contained in a document at the time of the operation.
 */
package datastructures.exercises.collage;