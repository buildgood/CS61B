/* Sobel.java */

/* DO NOT CHANGE THIS FILE. */
/* YOUR SUBMISSION MUST WORK CORRECTLY WITH _OUR_ COPY OF THIS FILE. */

/* You may wish to make temporary changes or insert println() statements     */
/* while testing your code.  When you're finished testing and debugging,     */
/* though, make sure your code works with the original version of this file. */

/**
 *  The Sobel class is a program that reads an image file in TIFF format,
 *  performs Sobel edge detection and uses it to create a grayscale image
 *  showing the intensities of the strongest edges, writes the grayscale image
 *  as a TIFF file, and displays both images.  Optionally, it can also blur the
 *  image with one or more iterations of a 3x3 box blurring kernel (similar to
 *  our Blur program) before performing edge detection, which tends to make
 *  edge detection more robust.  If blurring is selected, this program writes
 *  both the blurred image and the grayscale-edge image to files and displays
 *  all three images (the input image and the two output images).
 *
 *  The Sobel program takes up to three parameters.  The first parameter is
 *  the name of the TIFF-format file to read.  (The output image file is
 *  constructed by adding "edge_" to the beginning of the input filename.)
 *  An optional second parameter specifies the number of iterations of the
 *  box blurring operation.  (The default is zero iterations.)  If a third
 *  parameter is present (regardless of what it is), a second output grayscale
 *  image is written, run-length encoded to reduce its file size, with prefix
 *  "rle_".  For example, if you run
 *
 *         java Sobel engine.tiff 5 blah
 *
 *  then Sobel will read engine.tiff, perform 5 iterations of blurring, perform
 *  Sobel edge detection, map the Sobel gradients to grayscale intensities,
 *  write the blurred image to blur_engine.tiff, write a grayscale-edge image
 *  to edge_engine.tiff, and write a run-length encoded grayscale-edge image to
 *  rle_engine.tiff.
 *
 *  @author Joel Galenson and Jonathan Shewchuk
 */

public class Sobel {
  
  /**
   *  sobelFile() reads a TIFF image file, performs Sobel edge detection, maps
   *  the Sobel gradients to grayscale intensities, writes the edges to a new
   *  grayscale TIFF image file, and displays both images.  Optionally, it can
   *  blurs the image before edge detection, in which case it also writes the
   *  blurred image to a file and display all three images.
   *
   *  @param filename the name of the input TIFF image file.
   *  @param numIterations the number of iterations of blurring to perform.
   *  @param rle true if the output TIFF file should be run-length encoded.
   */
  private static void sobelFile(String filename, int numIterations,
                                boolean rle) {
    System.out.println("Reading image file " + filename);
    PixImage image = ImageUtils.readTIFFPix(filename);
    PixImage blurred = image;

    if (numIterations > 0) {
      System.out.println("Blurring image file.");
      blurred = image.boxBlur(numIterations);

      String blurname = "blur_" + filename;
      System.out.println("Writing blurred image file " + blurname);
      TIFFEncoder.writeTIFF(blurred, blurname);
    }

    System.out.println("Performing Sobel edge detection on image file.");
    PixImage sobeled = blurred.sobelEdges();

    String edgename = "edge_" + filename;
    System.out.println("Writing grayscale-edge image file " + edgename);
    TIFFEncoder.writeTIFF(sobeled, edgename);
    if (rle) {
      String rlename = "rle_" + filename;
      System.out.println("Writing run-length encoded grayscale-edge " +
                         "image file " + rlename);
      TIFFEncoder.writeTIFF(new RunLengthEncoding(sobeled), rlename);
    }

    if (numIterations > 0) {
      System.out.println("Displaying input image, blurred image, and " +
                         "grayscale-edge image.");
      System.out.println("Close the image to quit.");
      ImageUtils.displayTIFFs(new PixImage[] { image, blurred, sobeled });
    } else {
      System.out.println("Displaying input image and grayscale-edge image.");
      System.out.println("Close the image to quit.");
      ImageUtils.displayTIFFs(new PixImage[] { image, sobeled });
    }
  }

  /**
   *  main() reads the command-line arguments and initiates the blurring.
   *
   *  The first command-line argument is the name of the image file.
   *  An optional second argument is number of iterations of blurring.
   *  An optional third argument triggers the writing of a run-length encoded
   *  grayscale-edge image.
   *
   *  @param args the usual array of command-line argument Strings.
   */
  public static void main(String[] args) {
    if (args.length == 0) {
      System.out.println("usage:  java Sobel imagefile [iterations] [RLE]");
      System.out.println("  imagefile is an image in TIFF format.");
      System.out.println("  interations is the number of blurring iterations" +
                         " (default 0).");
      System.out.println("  any third argument (RLE) turns on run-length " +
                         "encoding in the output file");
      System.out.println("The grayscale-edge image is written to " +
                         "edge_imagefile.");
      System.out.println("If blurring is selected, " +
                         "the blurred image is written to blur_imagefile.");
      System.exit(0);
    }

    int numIterations = 0;
    if (args.length >= 2) {
      try {
        numIterations = Integer.parseInt(args[1]);
      } catch (NumberFormatException ex) {
        System.err.println("The second argument must be a number.");
        System.exit(1);
      }
    }

    sobelFile(args[0], numIterations, args.length >= 3);
  }
}
