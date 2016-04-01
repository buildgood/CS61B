/* TIFFEncoder.java */

/* DO NOT CHANGE THIS FILE. */
/* YOUR SUBMISSION MUST WORK CORRECTLY WITH _OUR_ COPY OF THIS FILE. */

/* You may wish to make temporary changes or insert println() statements     */
/* while testing your code.  When you're finished testing and debugging,     */
/* though, make sure your code works with the original version of this file. */

/**
 *  The TIFFEncoder class allows us to write a TIFF file from a pixel array
 *  in PixImage format or from a run-length encoding in RunLengthEncoding
 *  format.  A TIFF file written from a RunLengthEncoding is compressed as
 *  a run-length encoding, so it may be much shorter than a TIFF file written
 *  from a PixImage.
 *
 *  @author Joel Galenson
 **/

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.stream.FileImageOutputStream;

public class TIFFEncoder {

  /**
   * The TiffType enum represents the type of a value of a TIFF field,
   * which can have a variety of sizes.
   */
  private static enum TiffType { SHORT, LONG }

  /**
   * getTypeInt() returns the integer flag for the specified TIFF type, which
   * is used to specify which type the value has.
   *
   * @param type the type whose integer flag we want.
   * @return the integer representing the given type.
   */
  private static int getTypeInt(TiffType type) {
    switch (type) {
    case SHORT:
      return 3;
    case LONG:
      return 4;
    default:
      throw new IllegalArgumentException();
    }
  }

  /**
   * writeLeftAlignedValue() writes a given value of the given type into
   * a given stream as a left-justified four-byte record.
   * See Section 2 (page 15) of the TIFF spec for details.
   *
   * @param stream the stream representing the file being written.
   * @param type the type of the value.
   * @param val the value to write.
   * @throws IOException
   */
  private static void writeLeftAlignedValue(FileImageOutputStream stream,
                                            TiffType type, int val)
                                           throws IOException {
    switch (type) {
    case SHORT:
      stream.writeShort(val);
      stream.writeShort(0);
      break;
    case LONG:
      stream.writeInt(val);
      break;
    default:  // There are other possible types, but we're not using them.
      throw new IllegalArgumentException();
    }
  }

  /**
   * writeValueTag() writes an image file directory (IFD) entry whose value
   * fits into the Value Offset.
   * See Section 2 (page 15) of the TIFF spec for more details.
   *
   * @param stream the stream representing the file being written.
   * @param tag the tag that identifies the field.
   * @param type the type of the value.
   * @param value the value of the field.
   * @throws IOException
   */
  private static void writeValueTag(FileImageOutputStream stream, int tag,
                                    TiffType type, int value)
                                   throws IOException {
    stream.writeShort(tag);
    stream.writeShort(getTypeInt(type));
    stream.writeInt(1);
    writeLeftAlignedValue(stream, type, value);
  }

  /**
   * writeOffsetTag() writes an image file directory (IFD) entry whose value
   * does not fit into the Value Offset, so we store it at another offset.
   * See Section 2 (page 15) of the TIFF spec for more details.
   *
   * @param stream the stream representing the file being written.
   * @param tag the tag that identifies the field.
   * @param type the type of the value.
   * @param count the number of values of the indicated type. 
   * @param offset the offset in the file where the actual value is stored.
   * @throws IOException
   */
  private static void writeOffsetTag(FileImageOutputStream stream, int tag,
                                     TiffType type, int count, int offset)
                                    throws IOException {
    stream.writeShort(tag);
    stream.writeShort(getTypeInt(type));
    stream.writeInt(count);
    // The offset is always a LONG (a 32-bit TiffType).
    writeLeftAlignedValue(stream, TiffType.LONG, offset);
  }

  /**
   * writeTIFF() writes the specified data into a TIFF file.
   * For more details, see the TIFF spec at
   * http://partners.adobe.com/public/developer/en/tiff/TIFF6.pdf.
   * This code adapted from http://paulbourke.net/dataformats/tiff/.
   *
   * @param data the bytes of the image data.
   * @param width the width of the image.
   * @param height the height of the image.
   * @param filename the name of the file to write.
   * @param isCompressed true if the data is compressed in PackBits format;
   * false if it is stored uncompressed.
   */
  private static void writeTIFF(ArrayList<Short> data, int width, int height,
                                String filename, boolean isCompressed) {
    // For simplicity, we hardcode the number of directories we're writing.
    final int NUM_DIRS = 10;
    // The size (in bytes) of various parts of TIFF images.
    final int HEADER_SIZE = 8;
    final int DIR_SIZE = 12;

    try {
      FileImageOutputStream stream =
        new FileImageOutputStream(new File(filename));
      
      // Write the header.
      stream.writeShort(0x4d4d);  // Big-endian byte order.
      stream.writeShort(42);  // Magic number for TIFF files.
      stream.writeInt(data.size() + HEADER_SIZE);  // Offset of image file dir.

      // Write the image data.
      for (Short datum: data) {
        stream.writeByte(datum);
      }

      // Write the footer, including an image file directory (IFD).
      stream.writeShort(NUM_DIRS);  // Number of image file directory entries.
      // TODO:  width and height and others below could be LONG (32 bits).
      // IFD entry 0:  Image width.
      writeValueTag(stream, 256, TiffType.SHORT, width);
      // IFD entry 1:  Image height.
      writeValueTag(stream, 257, TiffType.SHORT, height);
      // IFD entry 2:  Bits per sample.
      writeOffsetTag(stream, 258, TiffType.SHORT, 3,
                     data.size() + HEADER_SIZE + DIR_SIZE * NUM_DIRS + 6);
      // IFD entry 3:  Compression tag.  1 means no compression.
      // 32773 means "PackBits compression", a run-length encoding.
      writeValueTag(stream, 259, TiffType.SHORT, isCompressed ? 32773 : 1);
      // IFD entry 4:  Photometric tag.  2 means it's a full-color RGB image.
      writeValueTag(stream, 262, TiffType.SHORT, 2);
      // IFD entry 5:  "StripOffsets".  The byte offset of the image.
      writeValueTag(stream, 273, TiffType.LONG, HEADER_SIZE);
      // IFD entry 6:  Samples per pixel.  3 for red, green, and blue.
      writeValueTag(stream, 277, TiffType.SHORT, 3);
      // IFD entry 7:  Rows per strip.  Our image is encoded as just one strip.
      writeValueTag(stream, 278, TiffType.SHORT, height);
      // IFD entry 8:  "Strip byte count"; number of bytes in the image.
      writeValueTag(stream, 279, TiffType.LONG, data.size());
      // IFD entry 9:  Planar configuration.  1 means each pixel is continuous
      //   (as opposed to separate sections for red, green, and blue).
      writeValueTag(stream, 284, TiffType.SHORT, 1);

      // Four bytes of zero signify that there are no more IFDs.
      stream.writeInt(0);
      
      // Write the "bits per sample" data for IFD entry 2 (above).
      // There are 8 bits for red, 8 for green, and 8 for blue.
      for (int i = 0; i < 3; i++) {
        stream.writeShort(8);
      }

      stream.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * writeTIFF() writes the specified PixImage into an uncompressed TIFF file.
   *
   * @param image the PixImage.
   * @param filename the name of the file to write.
   */
  public static void writeTIFF(PixImage image, String filename) {
    ArrayList<Short> pixels =
      new ArrayList<Short>(image.getWidth() * image.getHeight() * 3);
    // Note that our for loops iterate in this order so we write by rows.
    for (int j = 0; j < image.getHeight(); j++) {
      for (int i = 0; i < image.getWidth(); i++) {
        pixels.add(image.getRed(i, j));
        pixels.add(image.getGreen(i, j));
        pixels.add(image.getBlue(i, j));
      }
    }
    writeTIFF(pixels, image.getWidth(), image.getHeight(), filename, false);
  }

  /**
   * writeTIFF() writes the given image data into a compressed TIFF file.
   *
   * @param rle a run-length encoding of the image data.
   * @param filename the name of the file to write.
   */
  public static void writeTIFF(RunLengthEncoding rle, String filename) {
    ArrayList<Short> pixels = new ArrayList<Short>();

    int currentX = 0;  // x-position of the next pixel.
    for (RunIterator it = rle.iterator(); it.hasNext(); ) {
      int[] run = it.next();

      // The TIFF format can compress repeated bytes, so it can express a run
      // of grayscale values in compressed form; but it cannot compress
      // repeated red-green-blue triples if the red, green, and blue values are
      // not all the same.  So we check for a grayscale value (in which the
      // red, green, and blue values are equal).
      if (run[1] == run[2] && run[1] == run[3]) {
        // It's a grayscale run.  We can write the run in a compressed format.
        int i = 0;
        while (i < run[0] * 3) {  // run[0] is the number of pixels in the run.
          // Figure the number of bytes to write in one run.  Note that it is
          // always a factor of 3.
          int curCount = Math.min(Math.min(run[0] * 3 - i, 126),
                                  (rle.getWidth() - currentX) * 3);
          pixels.add((short) (1 - curCount));  // # of times value is repeated.
          pixels.add((short) run[1]);  // The value that is repeated.

          // The TIFF format does not allow you to compress across row
          // boundaries, so we must keep track of the current column so we can
          // break a run into smaller runs if it spans multiple rows.
          currentX = (currentX + curCount / 3) % rle.getWidth();
          i += curCount;
        }
      } else {
        // Not grayscale.  We must write every pixel individually.
    	// But we can still encode them as a combined literal run.
        int i = 0;
        while (i < run[0] * 3) {  // run[0] is the number of pixels in the run.
          // Figure the number of bytes to write in one literal.  Note that it
          // is always a factor of 3.
          int curCount = Math.min(Math.min(run[0] * 3 - i, 126),
                                  (rle.getWidth() - currentX) * 3);
          pixels.add((short) (curCount - 1));  // Number of literal values.
          for (int j = 0; j < curCount / 3; j++) {  // The literal values.
            pixels.add((short) run[1]);  // Red.
            pixels.add((short) run[2]);  // Green.
            pixels.add((short) run[3]);  // Blue.
          }

          // The TIFF format does not allow you to compress across row
          // boundaries, so we must keep track of the current column so we can
          // break a run into smaller runs if it spans multiple rows.
          currentX = (currentX + curCount / 3) % rle.getWidth();
          i += curCount;
        }
      }
    }

    writeTIFF(pixels, rle.getWidth(), rle.getHeight(), filename, true);
  }
}
