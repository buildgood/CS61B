/* ImageUtils.java */

/* DO NOT CHANGE THIS FILE. */
/* YOUR SUBMISSION MUST WORK CORRECTLY WITH _OUR_ COPY OF THIS FILE. */

/* You may wish to make temporary changes or insert println() statements     */
/* while testing your code.  When you're finished testing and debugging,     */
/* though, make sure your code works with the original version of this file. */

/**
 *  The ImageUtils class reads and writes TIFF file, converting to and from
 *  pixel arrays in PixImage format or run-length encodings in
 *  RunLengthEncoding format.  Methods are also included for displaying images
 *  in PixImage format.
 *
 *  @author Joel Galenson
 **/

import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import javax.media.jai.JAI;
import javax.media.jai.RenderedImageAdapter;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *  ImageUtils contains utilities for reading, writing, and displaying images.
 * 
 *  It uses JAI to read and write TIFF files, as the standard libraries cannot
 *  read them.
 * 
 *  All image data is in RGB format (see BufferedImage.getRGB).
 */
public class ImageUtils {

  /**
   *  buffer2PixImage() converts a BufferedImage to a PixImage.
   *  @param bImage the image to convert.
   *  @return a PixImage with the same pixels as the BufferedImage.
   */
  private static PixImage buffer2PixImage(BufferedImage bImage) {
    PixImage pImage = new PixImage(bImage.getWidth(), bImage.getHeight());
    for (int x = 0; x < bImage.getWidth(); x++) {
      for (int y = 0; y < bImage.getHeight(); y++) {
        Color color = new Color(bImage.getRGB(x, y));
        pImage.setPixel(x, y, (short) color.getRed(), (short) color.getGreen(),
                        (short) color.getBlue());
      }
    }
    return pImage;
  }

  /**
   *  pixImage2buffer() converts a PixImage to a BufferedImage.
   *  @param pImage the image to convert.
   *  @return a BufferedImage with the same pixels as the PixImage.
   */
  static BufferedImage pixImage2buffer(PixImage pImage) {
    BufferedImage bImage = new BufferedImage(pImage.getWidth(),
                                             pImage.getHeight(),
                                             BufferedImage.TYPE_INT_ARGB);
    for (int x = 0; x < bImage.getWidth(); x++) {
      for (int y = 0; y < bImage.getHeight(); y++) {
        bImage.setRGB(x, y, new Color(pImage.getRed(x, y),
                                      pImage.getGreen(x, y),
                                      pImage.getBlue(x, y)).getRGB());
      }
    }
    return bImage;
  }

  /**
   *  readTIFF() reads an image from a file and formats it as a BufferedImage.
   *  @param filename the name of the file to read.
   *  @return a BufferedImage of the file
   */
  private static BufferedImage readTIFF(String filename) {
    return (new RenderedImageAdapter(JAI.create("fileload", filename)))
           .getAsBufferedImage();
  }

  /**
   *  readTIFFPix() reads an image from a file and formats it as a PixImage.
   *  @param filename the name of the file to read.
   *  @return a PixImage of the file
   */
  public static PixImage readTIFFPix(String filename) {
    return buffer2PixImage(readTIFF(filename));
  }

  /**
   *  readTIFFRLE() reads an image from a file and formats it as a run-length
   *  encoding.
   *  @param filename the name of the file to read.
   *  @return a RunLengthEncoding of the file.
   */
  public static RunLengthEncoding readTIFFRLE(String filename) {
    return new RunLengthEncoding(readTIFFPix(filename));
  }

  /**
   *  writeTIFF() writes a BufferedImage to a specified file in TIFF format.
   *  @param rle the input BufferedImage.
   *  @param filename the output filename.
   */
  private static void writeTIFF(BufferedImage image, String filename) {
    JAI.create("filestore", image, filename, "tiff");
  }

  /**
   *  writeTIFF() writes a PixImage to a specified file in TIFF format.
   *  @param image the input PixImage.
   *  @param filename the output filename.
   */
  public static void writeTIFF(PixImage image, String filename) {
    writeTIFF(pixImage2buffer(image), filename);
  }

  /**
   *  writeTIFF() writes a run-length encoding to a specified file in TIFF
   *  format.
   *  @param rle the input run-length encoded image.
   *  @param filename the output filename.
   */
  public static void writeTIFF(RunLengthEncoding rle, String filename) {
    writeTIFF(rle.toPixImage(), filename);
  }

  /**
   *  displayFrame displays a JFrame and pauses until the window is closed.
   *  @param frame a JFrame to display.
   */
  private static void displayFrame(final JFrame frame) {
    try {
      synchronized (ImageUtils.class) {
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
              synchronized (ImageUtils.class) {
                ImageUtils.class.notify();
                frame.dispose();
              }
            }
          });
        frame.pack();
        frame.setVisible(true);
        ImageUtils.class.wait();
      }
    } catch (InterruptedException e) {
      System.out.println("Interrupted Exception in displayFrame().");
      e.printStackTrace();
    }
  }

  /**
   *  displayTIFFs displays a sequence of PixImages and pauses until the window
   *  is closed.
   *  @param images an array of PixImages to display.
   */
  public static void displayTIFFs(PixImage[] images) {
    JFrame frame = new JFrame();
    Box box = Box.createHorizontalBox();
    for (int i = 0; i < images.length; i++) {
      box.add(new JLabel(new ImageIcon(pixImage2buffer(images[i]))));
      if (i < images.length - 1) {
        box.add(Box.createHorizontalStrut(10));
      }
    }
    frame.add(box);
    displayFrame(frame);
  }

  /**
   *  displayTIFF displays a PixIamge and pauses until the window is closed.
   *  @param image the PixImage to display.
   */
  public static void displayTIFF(PixImage image) {
    displayTIFFs(new PixImage[] { image });
  }
}
