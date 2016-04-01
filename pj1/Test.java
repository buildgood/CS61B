/* Test.java */

/**
 *  The Test class is a program that tests the functionality of the PixImage
 *  and RunLengthEncoding classes.
 *
 *  @author Jonathan Shewchuk and Joel Galenson
 */

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class Test {

  // This should be true when we generate 
  private static final boolean WRITE_MODE = false;
  
  // The maximum number of rows/columns/lines we should print.
  // Change this to Integer.MAX_VALUE to print everything.
  private static int MAX_PRINT_SIZE = 5;

  // Handle reading and writing the good versions.
  // We can't serialize PixImages and RLEs because it gives away
  // our implementation and the names conflict, so
  // we convert to/from strings/ints.

  private static ObjectInputStream is;
  private static ObjectOutputStream os;

  private static void init() throws FileNotFoundException, IOException {
    File file = new File("data.gz");
    if (WRITE_MODE) {
      if (file.exists())  // Let's make it hard to overwrite the data.
        throw new RuntimeException("Please delete " + file.getAbsolutePath());
      os = new ObjectOutputStream(
             new GZIPOutputStream(new FileOutputStream(file)));
    } else
      is = new ObjectInputStream(
             new GZIPInputStream(new FileInputStream(file)));
  }

  /**
   *  colorToString() returns a length-2 hex string (00 through FF), given
   *  a value in the range [0, 256).
   */
  private static String colorToString(int color) {
    return String.format("%2s", Integer.toHexString(color).toUpperCase())
      .replace(' ', '0');
  }

  private static String stringOfPixImage(PixImage image) {
    StringBuffer sb =
      new StringBuffer(image.getWidth() * image.getHeight() * 3 * 2);
    for (int y = 0; y < image.getHeight(); y++) {
      for (int x = 0; x < image.getWidth(); x++) {
        sb.append(colorToString(image.getRed(x, y)));
        sb.append(colorToString(image.getGreen(x, y)));
        sb.append(colorToString(image.getBlue(x, y)));
      }
    }
    return sb.toString();
  }

  private static short getNextColor(InputStream strStream, byte[] bytes)
                                   throws IOException {
    strStream.read(bytes);
    return Short.parseShort(new String(bytes), 16);
  }

  private static BufferedImage imageOfString(int width, int height,
                                             String imageStr)
                                            throws IOException {
    BufferedImage image =
      new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    InputStream strStream = new ByteArrayInputStream(imageStr.getBytes());
    byte[] bytes = new byte[2];
    for (int y = 0; y < image.getHeight(); y++) {
      for (int x = 0; x < image.getWidth(); x++) {
        image.setRGB(x, y, new Color(getNextColor(strStream, bytes), 
                                     getNextColor(strStream, bytes),
                                     getNextColor(strStream, bytes)).getRGB());
      }
    }
    return image;
  }

  // If WRITE_MODE then image is our solution; otherwise it is the student's.
  private static BufferedImage readOrWriteNextImage(PixImage image)
                               throws IOException, ClassNotFoundException {
    if (WRITE_MODE) {
      os.writeInt(image.getWidth());
      os.writeInt(image.getHeight());
      os.writeObject(stringOfPixImage(image));
      return ImageUtils.pixImage2buffer(image);
    } else {
      return imageOfString(is.readInt(), is.readInt(),
                           (String) is.readObject());
    }
  }

  private static ArrayList<int[]> rleToList(RunLengthEncoding rle) {
    ArrayList<int[]> runs = new ArrayList<int[]>();
    for (RunIterator it = rle.iterator(); it.hasNext(); )
      runs.add(it.next());
    return runs;
  }

  private static void encodeRLE(RunLengthEncoding rle, ObjectOutputStream os)
                               throws IOException {
    os.writeInt(rle.getWidth());
    os.writeInt(rle.getHeight());
    ArrayList<int[]> runs = rleToList(rle);
    os.writeInt(runs.size());
    for (int[] run: runs)
      os.writeObject(run);
  }

  private static ArrayList<int[]> decodeRLE(ObjectInputStream os)
                                  throws IOException, ClassNotFoundException {
    int width = os.readInt();
    int height = os.readInt();
    int numRuns = os.readInt();
    ArrayList<int[]> runs = new ArrayList<int[]>(numRuns);
    for (int i = 0; i < numRuns; i++) {
      runs.add((int[])os.readObject());
    }
    return runs;
  }

  // If WRITE_MODE then image is our solution; otherwise it is the students'.
  private static ArrayList<int[]> readOrWriteNextRLE(RunLengthEncoding rle)
                                  throws IOException, ClassNotFoundException {
    if (WRITE_MODE) {
      encodeRLE(rle, os);
      return rleToList(rle);
    } else {
      return decodeRLE(is);
    }
  }

  // Stuff from old Test.java.  Many names are unchanged.

  /**
   *  Default parameters.
   */

  private static final int iBlur = 11;
  private static final int jBlur = 15;
  private static final int iterBlur = 5;

  private static PixImage randomImage(int i, int j) {
    /**
     *  Visit each cell (in a roundabout order); randomly pick a color.
     */
    PixImage image = new PixImage(i, j);
    Random random = new Random(0);      // Create a "Random" object with seed 0
    int x = 0;
    int y = 0;
    for (int xx = 0; xx < i; xx++) {
      x = (x + 78887) % i;           // This will visit every x-coordinate once
      for (int yy = 0; yy < j; yy++) {
        y = (y + 78887) % j;         // This will visit every y-coordinate once
        image.setPixel(x, y, (short) random.nextInt(256),
                       (short) random.nextInt(256),
                       (short) random.nextInt(256));
      }
    }
    return image;
  }
  
  private static int clamp(int coord, int max) {
    int d = MAX_PRINT_SIZE / 2;
    coord -= MAX_PRINT_SIZE / 2;
    if (coord + MAX_PRINT_SIZE > max)
      coord = max - MAX_PRINT_SIZE;
    if (coord < 0)
      coord = 0;
    return coord;
  }

  private static void printHorizBound(int width, int printWidth,
      int startX) {
    if (startX > 0)  // Handle an extra column of "..."s at the beginning.
      System.out.print("----");
    for (int x = 0; x < printWidth; x++)
      System.out.print("-------");
    if (printWidth < width)  // Handle an extra column of "..."s at the end.
      System.out.print("----");
    System.out.println("-");
  }

  private static void printEllipsisRow(int width, int printWidth,
      int startX) {
    System.out.print("|");
    if (startX > 0)  // Print an extra column of "..."s at the beginning.
      System.out.print("...,");
    for (int x = 0; x < printWidth; x++)
      System.out.print("  ...  ");
    if (printWidth < width)  // Print an extra column of "..."s at the end.
      System.out.print("...");
    System.out.println("|");
  }

  private static void paint(BufferedImage image, int[] firstDiff) {
    /* Draw the image. */
    int printWidth = Math.min(image.getWidth(), MAX_PRINT_SIZE);
    int printHeight = Math.min(image.getHeight(), MAX_PRINT_SIZE);
    int startX = 0, startY = 0;
    if (firstDiff != null) {
      startX = clamp(firstDiff[0], image.getWidth());
      startY = clamp(firstDiff[1], image.getHeight());
    }
    if (startX != 0 || startY != 0)
      System.out.println("Starting to print the image from (" + startX + "," + startY + ").");
    // Print out the top boundary.
    printHorizBound(image.getWidth(), printWidth, startX);
    // Print out a row of "..."s if we're not starting from the first row.
    if (startY > 0)
      printEllipsisRow(image.getWidth(), printWidth, startX);
    for (int y = 0; y < printHeight; y++) {
      System.out.print("|");
      if (startX > 0)  // Print an extra column of "..."s at the beginning.
        System.out.print("...,");
      for (int x = 0; x < printWidth; x++) {
        if (x > 0)
          System.out.print(",");
        Color color = new Color(image.getRGB(startX + x, startY + y));
        System.out.print(colorToString(color.getRed()) +
                         colorToString(color.getGreen()) +
                         colorToString(color.getBlue()));
      }
      if (printWidth < image.getWidth())  // Print an extra column of "..."s at the end.
        System.out.print(",...");
      System.out.println("|");
    }
    // Print out a row of "..."s if we're not ending at the last row.
    if (printHeight < image.getHeight())
      printEllipsisRow(image.getWidth(), printWidth, startX);
    // Print out the bottom boundary.
    printHorizBound(image.getWidth(), printWidth, startX);
  }

  private static void paint(PixImage image, int[] firstDiff) {
    paint(ImageUtils.pixImage2buffer(image), firstDiff);
  }

  private static int[] findFirstDiff(BufferedImage image1, PixImage image2,
                                    int border) {
    if (image1.getWidth() != image2.getWidth() ||
        image1.getHeight() != image2.getHeight()) {
      return new int[] { -1, -1 };
    }
    BufferedImage bImage2 = ImageUtils.pixImage2buffer(image2);
    for (int y = border; y < image1.getHeight() - border; y++) {
      for (int x = border; x < image1.getWidth() - border; x++) {
        if (image1.getRGB(x, y) != bImage2.getRGB(x, y)) {
          return new int[] { x, y };
        }
      }
    }
    return null;
  }

  private static boolean innerEqual(BufferedImage image1, PixImage image2,
                                    int border) {
    return findFirstDiff(image1, image2, border) == null;
  }

  private static boolean equal(BufferedImage image1, PixImage image2) {
    return innerEqual(image1, image2, 0);
  }

  private static void diffImages(BufferedImage goodImage, PixImage studentPixImage) {
    BufferedImage studentImage = ImageUtils.pixImage2buffer(studentPixImage);
    int diffCount = 0;
    System.out.println("The difference is:");
    if (studentImage.getWidth() != goodImage.getWidth()) {
      System.out.println("The width is " + studentImage.getWidth() + " but should be " + goodImage.getWidth());
      diffCount++;
    }
    if (studentImage.getHeight() != goodImage.getHeight()) {
      System.out.println("The height is " + studentImage.getHeight() + " but should be " + goodImage.getHeight());
      diffCount++;
    }
    for (int y = 0; y < goodImage.getHeight() && y < studentImage.getHeight(); y++) {
      for (int x = 0; x < goodImage.getWidth() && x < studentImage.getWidth(); x++) {
        if (goodImage.getRGB(x, y) != studentImage.getRGB(x, y)) {
          if (diffCount == MAX_PRINT_SIZE) {
            System.out.println("  ...");
            return;
          }
          System.out.println("  (" + x + "," + y + ") is " + new Color(studentImage.getRGB(x, y)) + " but should be " + new Color(goodImage.getRGB(x, y)));
          diffCount++;
        }
      }
    }
  }

  private static void printPixImages(PixImage studentImage,
                                     BufferedImage goodImage) {
    int[] firstDiff = findFirstDiff(goodImage, studentImage, 0);
    System.out.println("The correct current image is:");
    paint(goodImage, firstDiff);
    System.out.println("Your PixImage is:");
    paint(studentImage, firstDiff);
    diffImages(goodImage, studentImage);
  }

  private static void printPixImages(PixImage studentImage,
                                     BufferedImage goodImage,
                                     BufferedImage JRSOld) {
    System.out.println("The previous image was:");
    paint(JRSOld, findFirstDiff(goodImage, studentImage, 0));
    printPixImages(studentImage, goodImage);
  }

  private static void printRLE(ArrayList<int[]> runs) {
    System.out.println("    Here is the correct encoding:");
    for (int[] run: runs) {
      System.out.println(run[0] + "x[red=" + run[1] + ",green=" + run[2] +
                         ",blue=" + run[3] + "]");
    }
  }

  private static void runTests() throws FileNotFoundException, IOException,
                                        ClassNotFoundException {
    BufferedImage goodImage;
    BufferedImage prevImage;
    PixImage studentImage;
    int blurInitScore = 1;
    int blurInnerScore = 1;
    int blurBoundaryScore = 1;
    int blurMultiScore = 1;
    int sobelInitScore = 1;
    int sobelInnerScore = 2;
    int sobelBoundaryScore = 1;

    System.out.println("Beginning Part I.");
    System.out.println("Performing " + iterBlur + " boxBlur(1) calls, then " +
                       "one boxBlur(3) calls on a " + iBlur + "x" + jBlur +
                       " image.");

    /**
     *  Create a random image to blur.
     */

    studentImage = randomImage(iBlur, jBlur);
    goodImage = readOrWriteNextImage(studentImage);

    /**
     *  Plot and compare our image with the student's.
     */

    if (!equal(goodImage, studentImage)) {
      // The student's image is incorrect after initialization.
      System.out.println("Your initial PixImage is incorrect.");
      printPixImages(studentImage, goodImage);
      // Student loses all four blur points.
      blurInitScore = 0;
      blurInnerScore = 0;
      blurBoundaryScore = 0;
      blurMultiScore = 0;
    }

    /**
     *  Perform multiple steps of blurring.
     *
     *  Note:  These loops ensure that we always call readOrWriteNext* the
     *  same number of times even if the tests fail.
     */

    for (int x = 1; x <= iterBlur; x++) {
      // Perform one blurring step in the student's image and my image.
      prevImage = goodImage;
      studentImage = studentImage.boxBlur(1);
      goodImage = readOrWriteNextImage(studentImage);
      //      studentImage.setPixel(0, 3, (short) 2, (short) 5, (short) 7);

      // Plot and compare them.
      if (blurInnerScore > 0 && !equal(goodImage, studentImage)) {
        // The student's image is incorrect.  Check if it's only a problem
        //   at the boundaries.
        boolean innerEq = innerEqual(goodImage, studentImage, x);
        // Print error message, but only if this is the first time.
        if (blurBoundaryScore > 0) {
          blurBoundaryScore = 0;
          blurMultiScore = 0;
          // Draw previous image; correct new image; student's new image.
          System.out.println("Your image is incorrect after " + x +
                             "step(s) of boxBlur(1).");
          printPixImages(studentImage, goodImage, prevImage);
          if (innerEq) {
            System.out.println(
                "(The problem seems to be only at the boundaries.)");
          } else {
            blurInnerScore = 0;
          }
        } else if (!innerEq) {
          // Control should only get here if bugs near the boundary were
          //   encountered on a previous iteration, but bugs in the
          //   interior first surfaced on this iteration.
          blurInnerScore = 0;
          System.out.println(
            "Your image's interior is incorrect after blurring step " + x +
            ".");
          printPixImages(studentImage, goodImage, prevImage);
        }
      }
    }

    // Perform multiple blurring steps in the student's image and my image.
    prevImage = goodImage;
    studentImage = studentImage.boxBlur(3);
    goodImage = readOrWriteNextImage(studentImage);

    // Plot and compare them.
    if (blurMultiScore > 0 && !equal(goodImage, studentImage)) {
      // The student's image is incorrect.
      blurMultiScore = 0;
      // Draw previous image; correct new image; student's new image.
      System.out.println("Your image is incorrect after boxBlur(3).");
    }

    if (blurMultiScore == 1) {
      System.out.println("  Test successful.");
    }


    System.out.println();
    System.out.println("Performing a sobelEdges call on feathers.tiff.");

    /**
     *  Create a random image to perform edge detection on.
     */

    try {
      System.out.println("  Reading feathers.tiff.");
      studentImage = ImageUtils.readTIFFPix("feathers.tiff");
    } catch (IllegalArgumentException e) {
      System.out.println("Cannot read feathers.tiff.  Exiting.");
      System.exit(1);
      // studentImage = new PixImage(1, 1);
    }
    goodImage = readOrWriteNextImage(studentImage);

    /**
     *  Plot and compare our image with the student's.
     */

    if (!equal(goodImage, studentImage)) {
      // The student's image is incorrect after initialization.
      System.out.println("Your initial PixImage is incorrect.");
      printPixImages(studentImage, goodImage);
      // Student loses all four Sobel points.
      sobelInitScore = 0;
      sobelInnerScore = 0;
      sobelBoundaryScore = 0;
    }

    /**
     *  Perform Sobel edge detection in my image and the student's image.
     */

    prevImage = goodImage;
    studentImage = studentImage.sobelEdges();
    goodImage = readOrWriteNextImage(studentImage);

    // Plot and compare them.
    if (sobelInnerScore > 0 && !equal(goodImage, studentImage)) {
      sobelBoundaryScore = 0;
      // Draw previous image; correct new image; student's new image.
      System.out.println("Your image is incorrect after sobelEdges.");
      printPixImages(studentImage, goodImage, prevImage);

      // Check if it's only a problem at the boundaries.
      if (innerEqual(goodImage, studentImage, 1)) {
        System.out.println(
          "(The problem seems to be only at the boundaries.)");
      } else {
        sobelInnerScore = 0;
      }
    }

    if (sobelBoundaryScore > 0) {
      System.out.println("  Test successful.");
    }


    int parti = blurInitScore + blurInnerScore + blurBoundaryScore +
      blurMultiScore + sobelInitScore + sobelInnerScore + sobelBoundaryScore;
    System.out.println();
    System.out.println("Total Part I score:  " + parti + " out of 8.");
    System.out.println("Total Autogradable score so far:  " + parti +
                       " out of 8.");
    System.out.println();




    System.out.println("Beginning Part II.");
    System.out.println("Performing a 4x4 RunLengthEncoding-to-PixImage test.");
    int readBackScore = 2;
    int toPixImageScore = 3;

    System.out.println("  Calling the six-parameter constructor.");

    int[] rr = {0, 1, 2, 3, 4, 5};
    int[] rg = {20, 18, 16, 14, 12, 10};
    int[] rb = {42, 42, 42, 137, 137, 137};
    int[] rl = {3, 2, 5, 1, 1, 4};
    RunLengthEncoding rle1 = new RunLengthEncoding(4, 4, rr, rg, rb, rl);
    ArrayList<int[]> jrle1 = readOrWriteNextRLE(rle1);

    System.out.println("  Reading back the encoding through nextRun.");
    RunIterator rle1it = rle1.iterator();
    for (int i = 0; i < rl.length; i++) {
      if (!rle1it.hasNext()) {
        System.out.println("    Run # " + i +
                           " missing.  (Runs are indexed from zero.)");
        System.out.println("    (In other words, your nextRun() is " +
                           "returning null when it shouldn't.)");
        printRLE(jrle1);
        readBackScore = 0;
        break;
      }

      int[] ts = rle1it.next();
      if ((ts[0] != rl[i]) || (ts[1] != rr[i]) || (ts[2] != rg[i])
          || (ts[3] != rb[i])) {
        System.out.println("    Run # " + i + " should be " + rl[i] + ", " +
                           rr[i] + ", " + rg[i] + ", " + rb[i] +
                           ".  (Runs are indexed from zero.)");
        System.out.println("    Instead, it's " + ts[0] + ", " + ts[1] + ", "
                           + ts[2] + ", " + ts[3]);
        printRLE(jrle1);
        readBackScore = 0;
        break;
      }
    }

    if ((readBackScore == 1) && (rle1it.hasNext())) {
      System.out.println("    Your hasNext() is failing to return false when" +
                         " the runs run out.");
      printRLE(jrle1);
      readBackScore = 0;
    }

    System.out.println("  Calling toPixImage.");
    PixImage o1 = rle1.toPixImage();
    BufferedImage jo1 = readOrWriteNextImage(o1);
    if (!equal(jo1, o1)) {
      printPixImages(o1, jo1);
      toPixImageScore = 0;
    }

    int partii = readBackScore + toPixImageScore;
    if (partii == 5) {
      System.out.println("  Test successful.");
    }

    System.out.println();
    System.out.println("Total Part II score:  " + partii + " out of 5.");
    System.out.println("Total Autogradable score so far:  " +
                       (parti + partii) + " out of 13.");
    System.out.println();




    System.out.println("Beginning Part III.");
    System.out.println("Run-length encoding a PixImage.");
    int toRLEScore = 3;
    int backToPixImageScore = 1;

    try {
      System.out.println("  Reading highcontrast.tiff.");
      studentImage = ImageUtils.readTIFFPix("highcontrast.tiff");
    } catch (IllegalArgumentException e) {
      System.out.println("Cannot read highcontrast.tiff.  Exiting.");
      System.exit(1);
      // studentImage = new PixImage(1, 1);
    }
    goodImage = readOrWriteNextImage(studentImage);

    System.out.println("  Calling the one-parameter RunLengthEncoding " +
                       "constructor.");
    RunLengthEncoding rle2 = new RunLengthEncoding(studentImage);
    ArrayList<int[]> jrle2 = readOrWriteNextRLE(rle2);

    int i = 0;
    RunIterator rle2it = rle2.iterator();
    for (int[] jts2: jrle2) {
      if (!rle2it.hasNext()) {
        System.out.println("    Run # " + i +
                           " missing.  (Runs are indexed from zero.)");
        System.out.println("    (In other words, your nextRun() is " +
                           "returning null when it shouldn't.)");
        toRLEScore = 0;
        break;
      }

      int[] ts2 = rle2it.next();
      if ((jts2[0] != ts2[0]) || (jts2[1] != ts2[1]) || (jts2[2] != ts2[2])
          || (jts2[3] != ts2[3])) {
        System.out.println("    Run # " + i + " should be " + jts2[0] +
            ", " + jts2[1] + ", " + jts2[2] + ", " + jts2[3] + "."
            + "  (Runs indexed from zero.)");
        System.out.println("    Instead, it's " + ts2[0] + ", " + ts2[1] + ", "
            + ts2[2] + ", " + ts2[3]);
        toRLEScore = 0;
        break;
      }
      i++;
    }

    if ((toRLEScore == 1) && (rle2it.hasNext())) {
      System.out.println("    Your nextRun is failing to return null when" +
                         " the runs run out.");
      System.out.println("    Here is the correct image.");
      paint(goodImage, null);
      toRLEScore = 0;
    }

    System.out.println("  Converting back to a PixImage.");
    PixImage o2j = rle2.toPixImage();
    if (!equal(goodImage, o2j)) {
      printPixImages(o2j, goodImage);
      backToPixImageScore = 0;
    }

    int partiii = toRLEScore + backToPixImageScore;
    if (partiii == 4) {
      System.out.println("  Test successful.");
    }

    System.out.println();
    System.out.println("Total Part III score:  " + partiii + " out of 4.");
    System.out.println("Total Autogradable score so far:  " +
                       (parti + partii + partiii) + " out of 17.");
    System.out.println();




    System.out.println("Beginning Part IV.");
    System.out.println("Setting pixels in your 4x4 run-length encoding" +
                       " (from Part II).");
    int addScore1 = 1;
    int addScore2 = 1;

    System.out.println("  Setting (3, 2) to [red=5, green=10, blue=136].");
    rle1.setPixel(3, 2, (short) 5, (short) 10, (short) 136);
    jrle1 = readOrWriteNextRLE(rle1);
    i = 0;
    rle1it = rle1.iterator();
    for (int[] jts1: jrle1) {
      if (!rle1it.hasNext()) {
        System.out.println("    Run # " + i +
                           " missing.  (Runs are indexed from zero.)");
        System.out.println("    (In other words, your nextRun() is " +
                           "returning null when it shouldn't.)");
        System.out.println("    Here is your encoding:\n" + rle1);
        printRLE(jrle1);
        addScore1 = 0;
        break;
      }

      int[] ts1 = rle1it.next();
      if ((jts1[0] != ts1[0]) || (jts1[1] != ts1[1]) || (jts1[2] != ts1[2])
          || (jts1[3] != ts1[3])) {
        System.out.println("    Run # " + i + " should be " + jts1[0] +
            ", " + jts1[1] + ", " + jts1[2] + ", " + jts1[3] + "."
            + "  (Runs indexed from zero.)");
        System.out.println("    Instead, it's " + ts1[0] + ", " + ts1[1] + ", "
            + ts1[2] + ", " + ts1[3]);
        System.out.println("    Here is your encoding:\n" + rle1);
        printRLE(jrle1);
        addScore1 = 0;
        break;
      }
      i++;
    }

    System.out.println("  Setting (0, 3) to [red=5, green=10, blue=136].");
    rle1.setPixel(0, 3, (short) 5, (short) 10, (short) 136);
    jrle1 = readOrWriteNextRLE(rle1);
    i = 0;
    rle1it = rle1.iterator();
    for (int[] jts1: jrle1) {
      if (!rle1it.hasNext()) {
        System.out.println("    Run # " + i +
                           " missing.  (Runs are indexed from zero.)");
        System.out.println("    (In other words, your nextRun() is " +
                           "returning null when it shouldn't.)");
        System.out.println("    Here is your encoding:\n" + rle1);
        printRLE(jrle1);
        addScore1 = 0;
        break;
      }

      int[] ts1 = rle1it.next();
      if ((jts1[0] != ts1[0]) || (jts1[1] != ts1[1]) || (jts1[2] != ts1[2])
          || (jts1[3] != ts1[3])) {
        System.out.println("    Run # " + i + " should be " + jts1[0] +
            ", " + jts1[1] + ", " + jts1[2] + ", " + jts1[3] + "."
            + "  (Runs indexed from zero.)");
        System.out.println("    Instead, it's " + ts1[0] + ", " + ts1[1] + ", "
            + ts1[2] + ", " + ts1[3]);
        System.out.println("    Here is your encoding:\n" + rle1);
        printRLE(jrle1);
        addScore1 = 0;
        break;
      }
      i++;
    }

    System.out.println("  Setting (1, 0) to [red=1, green=18, blue=42].");
    rle1.setPixel(1, 0, (short) 1, (short) 18, (short) 42);
    jrle1 = readOrWriteNextRLE(rle1);
    i = 0;
    rle1it = rle1.iterator();
    for (int[] jts1: jrle1) {
      if (!rle1it.hasNext()) {
        System.out.println("    Run # " + i +
                           " missing.  (Runs are indexed from zero.)");
        System.out.println("    (In other words, your nextRun() is " +
                           "returning null when it shouldn't.)");
        System.out.println("    Here is your encoding:\n" + rle1);
        printRLE(jrle1);
        addScore2 = 0;
        break;
      }

      int[] ts1 = rle1it.next();
      if ((jts1[0] != ts1[0]) || (jts1[1] != ts1[1]) || (jts1[2] != ts1[2])
          || (jts1[3] != ts1[3])) {
        System.out.println("    Run # " + i + " should be " + jts1[0] +
            ", " + jts1[1] + ", " + jts1[2] + ", " + jts1[3] + "."
            + "  (Runs indexed from zero.)");
        System.out.println("    Instead, it's " + ts1[0] + ", " + ts1[1] + ", "
            + ts1[2] + ", " + ts1[3]);
        System.out.println("    Here is your encoding:\n" + rle1);
        printRLE(jrle1);
        addScore2 = 0;
        break;
      }
      i++;
    }

    System.out.println("  Setting (2, 0) to [red=1, green=18, blue=42].");
    rle1.setPixel(2, 0, (short) 1, (short) 18, (short) 42);
    jrle1 = readOrWriteNextRLE(rle1);
    i = 0;
    rle1it = rle1.iterator();
    for (int[] jts1: jrle1) {
      if (!rle1it.hasNext()) {
        System.out.println("    Run # " + i +
                           " missing.  (Runs are indexed from zero.)");
        System.out.println("    (In other words, your nextRun() is " +
                           "returning null when it shouldn't.)");
        System.out.println("    Here is your encoding:\n" + rle1);
        printRLE(jrle1);
        addScore2 = 0;
        break;
      }

      int[] ts1 = rle1it.next();
      if ((jts1[0] != ts1[0]) || (jts1[1] != ts1[1]) || (jts1[2] != ts1[2])
          || (jts1[3] != ts1[3])) {
        System.out.println("    Run # " + i + " should be " + jts1[0] +
            ", " + jts1[1] + ", " + jts1[2] + ", " + jts1[3] + "."
            + "  (Runs indexed from zero.)");
        System.out.println("    Instead, it's " + ts1[0] + ", " + ts1[1] + ", "
            + ts1[2] + ", " + ts1[3]);
        System.out.println("    Here is your encoding:\n" + rle1);
        printRLE(jrle1);
        addScore2 = 0;
        break;
      }
      i++;
    }

    System.out.println("  Setting (0, 0) to [red=1, green=18, blue=42].");
    rle1.setPixel(0, 0, (short) 1, (short) 18, (short) 42);
    jrle1 = readOrWriteNextRLE(rle1);
    i = 0;
    rle1it = rle1.iterator();
    for (int[] jts1: jrle1) {
      if (!rle1it.hasNext()) {
        System.out.println("    Run # " + i +
                           " missing.  (Runs are indexed from zero.)");
        System.out.println("    (In other words, your nextRun() is " +
                           "returning null when it shouldn't.)");
        System.out.println("    Here is your encoding:\n" + rle1);
        printRLE(jrle1);
        addScore2 = 0;
        break;
      }

      int[] ts1 = rle1it.next();
      if ((jts1[0] != ts1[0]) || (jts1[1] != ts1[1]) || (jts1[2] != ts1[2])
          || (jts1[3] != ts1[3])) {
        System.out.println("    Run # " + i + " should be " + jts1[0] +
            ", " + jts1[1] + ", " + jts1[2] + ", " + jts1[3] + "."
            + "  (Runs indexed from zero.)");
        System.out.println("    Instead, it's " + ts1[0] + ", " + ts1[1] + ", "
            + ts1[2] + ", " + ts1[3]);
        System.out.println("    Here is your encoding:\n" + rle1);
        printRLE(jrle1);
        addScore2 = 0;
        break;
      }
      i++;
    }

    int partiv = addScore1 + addScore2;
    if (partiv == 2) {
      System.out.println("  Test successful.");
    }

    System.out.println();
    System.out.println("Total Part IV score:  " + partiv + " out of 2.");
    System.out.println("Total Autogradable score:  " +
                       (parti + partii + partiii + partiv) + " out of 19.");

    System.out.println();
    System.out.println("(Note:  1 hand-graded point will be for your check()" +
                       " method.)");
  }

  public static void main(String[] args) {
    try {
      init();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    try {
      runTests();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      try {
        if (WRITE_MODE) {
          os.close();
        } else {
          is.close();
        }
      } catch (IOException e) {
      }
    }
  }
}
