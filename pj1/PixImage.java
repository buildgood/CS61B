/* PixImage.java */

/**
 *  The PixImage class represents an image, which is a rectangular grid of
 *  color pixels.  Each pixel has red, green, and blue intensities in the range
 *  0...255.  Descriptions of the methods you must implement appear below.
 *  They include a constructor of the form
 *
 *      public PixImage(int width, int height);
 *
 *  that creates a black (zero intensity) image of the specified width and
 *  height.  Pixels are numbered in the range (0...width - 1, 0...height - 1).
 *
 *  All methods in this class must be implemented to complete Part I.
 *  See the README file accompanying this project for additional details.
 */

public class PixImage {

  /**
   *  Define any variables associated with a PixImage object here.  These
   *  variables MUST be private.
   */
   private int width;
   private int height;
   private short[][] r;
   private short[][] g;
   private short[][] b;


  /**
   * PixImage() constructs an empty PixImage with a specified width and height.
   * Every pixel has red, green, and blue intensities of zero (solid black).
   *
   * @param width the width of the image.
   * @param height the height of the image.
   */
  public PixImage(int width, int height) {
    // Your solution here.
    this.width = width;
    this.height = height;
    r = new short[width][height];
    g = new short[width][height];
    b = new short[width][height];
    for(int i = 0; i < width; i++)
      for(int j = 0; j < height; j++){
        r[i][j] = 0;
        g[i][j] = 0;
        b[i][j] = 0;
      }
      
  }

  /**
   * getWidth() returns the width of the image.
   *
   * @return the width of the image.
   */
  public int getWidth() {
    // Replace the following line with your solution.
    return width;
  }

  /**
   * getHeight() returns the height of the image.
   *
   * @return the height of the image.
   */
  public int getHeight() {
    // Replace the following line with your solution.
    return height;
  }

  /**
   * getRed() returns the red intensity of the pixel at coordinate (x, y).
   *
   * @param x the x-coordinate of the pixel.
   * @param y the y-coordinate of the pixel.
   * @return the red intensity of the pixel at coordinate (x, y).
   */
  public short getRed(int x, int y) {
    // Replace the following line with your solution.
    return r[x][y];
  }

  /**
   * getGreen() returns the green intensity of the pixel at coordinate (x, y).
   *
   * @param x the x-coordinate of the pixel.
   * @param y the y-coordinate of the pixel.
   * @return the green intensity of the pixel at coordinate (x, y).
   */
  public short getGreen(int x, int y) {
    // Replace the following line with your solution.
    return g[x][y];
  }

  /**
   * getBlue() returns the blue intensity of the pixel at coordinate (x, y).
   *
   * @param x the x-coordinate of the pixel.
   * @param y the y-coordinate of the pixel.
   * @return the blue intensity of the pixel at coordinate (x, y).
   */
  public short getBlue(int x, int y) {
    // Replace the following line with your solution.
    return b[x][y];
  }

  /**
   * setPixel() sets the pixel at coordinate (x, y) to specified red, green,
   * and blue intensities.
   *
   * If any of the three color intensities is NOT in the range 0...255, then
   * this method does NOT change any of the pixel intensities.
   *
   * @param x the x-coordinate of the pixel.
   * @param y the y-coordinate of the pixel.
   * @param red the new red intensity for the pixel at coordinate (x, y).
   * @param green the new green intensity for the pixel at coordinate (x, y).
   * @param blue the new blue intensity for the pixel at coordinate (x, y).
   */
  public void setPixel(int x, int y, short red, short green, short blue) {
    // Your solution here.
    if(0 <= red && red <= 255 && 0 <= green && green <= 255 && 0 <= blue && blue <= 255){
      r[x][y] = red;
      g[x][y] = green;
      b[x][y] = blue;
    }
  }

  /**
   * toString() returns a String representation of this PixImage.
   *
   * This method isn't required, but it should be very useful to you when
   * you're debugging your code.  It's up to you how you represent a PixImage
   * as a String.
   *
   * @return a String representation of this PixImage.
   */
  public String toString() {
    // Replace the following line with your solution.
    String p = new String();
    for(int j = 0; j < height; j++)
      for(int i = 0; i < width; i++){
        p = p + "| "+ r[i][j] + " " + g[i][j] + " " + b[i][j] + " ";
        if(i == width - 1){
          p = p + "|\n";
        }
      }
    return p;
  }
  
  /**
   * boxBlur() returns a blurred version of "this" PixImage.
   *
   * If numIterations == 1, each pixel in the output PixImage is assigned
   * a value equal to the average of its neighboring pixels in "this" PixImage,
   * INCLUDING the pixel itself.
   *
   * A pixel not on the image boundary has nine neighbors--the pixel itself and
   * the eight pixels surrounding it.  A pixel on the boundary has six
   * neighbors if it is not a corner pixel; only four neighbors if it is
   * a corner pixel.  The average of the neighbors is the sum of all the
   * neighbor pixel values (including the pixel itself) divided by the number
   * of neighbors, with non-integer quotients rounded toward zero (as Java does
   * naturally when you divide two integers).
   *
   * Each color (red, green, blue) is blurred separately.  The red input should
   * have NO effect on the green or blue outputs, etc.
   *
   * The parameter numIterations specifies a number of repeated iterations of
   * box blurring to perform.  If numIterations is zero or negative, "this"
   * PixImage is returned (not a copy).  If numIterations is positive, the
   * return value is a newly constructed PixImage.
   *
   * IMPORTANT:  DO NOT CHANGE "this" PixImage!!!  All blurring/changes should
   * appear in the new, output PixImage only.
   *
   * @param numIterations the number of iterations of box blurring.
   * @return a blurred version of "this" PixImage.
   */
  public PixImage boxBlur(int numIterations) {
    // Replace the following line with your solution.
    if(numIterations <= 0){
      return this;
    }else{
      PixImage cal_this = this;
      PixImage cal_that;
      int cal_r;
      int cal_g;
      int cal_b;
      for(int k = 1; k <= numIterations; k++){
        cal_that = new PixImage(width, height);
        for(int i = 0; i < width; i++)
          for(int j = 0; j < height; j++){
            if(i == 0 && j == 0){
              cal_r = (cal_this.r[i][j] + cal_this.r[i+1][j] + cal_this.r[i][j+1] + cal_this.r[i+1][j+1])/4;
              cal_g = (cal_this.g[i][j] + cal_this.g[i+1][j] + cal_this.g[i][j+1] + cal_this.g[i+1][j+1])/4;
              cal_b = (cal_this.b[i][j] + cal_this.b[i+1][j] + cal_this.b[i][j+1] + cal_this.b[i+1][j+1])/4;
          }else if(i == width-1 && j == 0){
            cal_r = (cal_this.r[i][j] + cal_this.r[i-1][j] + cal_this.r[i][j+1] + cal_this.r[i-1][j+1])/4; 
            cal_g = (cal_this.g[i][j] + cal_this.g[i-1][j] + cal_this.g[i][j+1] + cal_this.g[i-1][j+1])/4;
            cal_b = (cal_this.b[i][j] + cal_this.b[i-1][j] + cal_this.b[i][j+1] + cal_this.b[i-1][j+1])/4;
          }else if(i == 0 && j == height -1){
            cal_r = (cal_this.r[i][j] + cal_this.r[i+1][j] + cal_this.r[i][j-1] + cal_this.r[i+1][j-1])/4;
            cal_g = (cal_this.g[i][j] + cal_this.g[i+1][j] + cal_this.g[i][j-1] + cal_this.g[i+1][j-1])/4;
            cal_b = (cal_this.b[i][j] + cal_this.b[i+1][j] + cal_this.b[i][j-1] + cal_this.b[i+1][j-1])/4;
          }else if(i == width-1 && j == height - 1){
            cal_r = (cal_this.r[i][j] + cal_this.r[i-1][j] + cal_this.r[i][j-1] + cal_this.r[i-1][j-1])/4;
            cal_g = (cal_this.g[i][j] + cal_this.g[i-1][j] + cal_this.g[i][j-1] + cal_this.g[i-1][j-1])/4;
            cal_b = (cal_this.b[i][j] + cal_this.b[i-1][j] + cal_this.b[i][j-1] + cal_this.b[i-1][j-1])/4;
          }else if(j == 0){
            cal_r = (cal_this.r[i][j] + cal_this.r[i-1][j] + cal_this.r[i+1][j] + cal_this.r[i][j+1] + cal_this.r[i-1][j+1] + cal_this.r[i+1][j+1])/6;
            cal_g = (cal_this.g[i][j] + cal_this.g[i-1][j] + cal_this.g[i+1][j] + cal_this.g[i][j+1] + cal_this.g[i-1][j+1] + cal_this.g[i+1][j+1])/6;
            cal_b = (cal_this.b[i][j] + cal_this.b[i-1][j] + cal_this.b[i+1][j] + cal_this.b[i][j+1] + cal_this.b[i-1][j+1] + cal_this.b[i+1][j+1])/6;
          }else if(j == height-1){
            cal_r = (cal_this.r[i][j] + cal_this.r[i-1][j] + cal_this.r[i+1][j] + cal_this.r[i][j-1] + cal_this.r[i-1][j-1] + cal_this.r[i+1][j-1])/6;
            cal_g = (cal_this.g[i][j] + cal_this.g[i-1][j] + cal_this.g[i+1][j] + cal_this.g[i][j-1] + cal_this.g[i-1][j-1] + cal_this.g[i+1][j-1])/6;
            cal_b = (cal_this.b[i][j] + cal_this.b[i-1][j] + cal_this.b[i+1][j] + cal_this.b[i][j-1] + cal_this.b[i-1][j-1] + cal_this.b[i+1][j-1])/6;
          }else if(i == 0){
            cal_r = (cal_this.r[i][j] + cal_this.r[i][j-1] + cal_this.r[i][j+1] + cal_this.r[i+1][j] + cal_this.r[i+1][j-1] + cal_this.r[i+1][j+1])/6;
            cal_g = (cal_this.g[i][j] + cal_this.g[i][j-1] + cal_this.g[i][j+1] + cal_this.g[i+1][j] + cal_this.g[i+1][j-1] + cal_this.g[i+1][j+1])/6;
            cal_b = (cal_this.b[i][j] + cal_this.b[i][j-1] + cal_this.b[i][j+1] + cal_this.b[i+1][j] + cal_this.b[i+1][j-1] + cal_this.b[i+1][j+1])/6;
          }else if(i == width-1){
            cal_r = (cal_this.r[i][j] + cal_this.r[i][j-1] + cal_this.r[i][j+1] + cal_this.r[i-1][j] + cal_this.r[i-1][j-1] + cal_this.r[i-1][j+1])/6;
            cal_g = (cal_this.g[i][j] + cal_this.g[i][j-1] + cal_this.g[i][j+1] + cal_this.g[i-1][j] + cal_this.g[i-1][j-1] + cal_this.g[i-1][j+1])/6;
            cal_b = (cal_this.b[i][j] + cal_this.b[i][j-1] + cal_this.b[i][j+1] + cal_this.b[i-1][j] + cal_this.b[i-1][j-1] + cal_this.b[i-1][j+1])/6;
          }else{
            cal_r = (cal_this.r[i][j] + cal_this.r[i-1][j] + cal_this.r[i+1][j] + cal_this.r[i][j-1] + cal_this.r[i-1][j-1] + cal_this.r[i+1][j-1] + cal_this.r[i][j+1] + cal_this.r[i-1][j+1] + cal_this.r[i+1][j+1])/9;
            cal_g = (cal_this.g[i][j] + cal_this.g[i-1][j] + cal_this.g[i+1][j] + cal_this.g[i][j-1] + cal_this.g[i-1][j-1] + cal_this.g[i+1][j-1] + cal_this.g[i][j+1] + cal_this.g[i-1][j+1] + cal_this.g[i+1][j+1])/9;
            cal_b = (cal_this.b[i][j] + cal_this.b[i-1][j] + cal_this.b[i+1][j] + cal_this.b[i][j-1] + cal_this.b[i-1][j-1] + cal_this.b[i+1][j-1] + cal_this.b[i][j+1] + cal_this.b[i-1][j+1] + cal_this.b[i+1][j+1])/9;
          }
          cal_that.setPixel(i, j, (short)cal_r, (short)cal_g, (short)cal_b);
        }
        cal_this = cal_that;
      }
      return cal_this;
    }
  }

  /**
   * mag2gray() maps an energy (squared vector magnitude) in the range
   * 0...24,969,600 to a grayscale intensity in the range 0...255.  The map
   * is logarithmic, but shifted so that values of 5,080 and below map to zero.
   *
   * DO NOT CHANGE THIS METHOD.  If you do, you will not be able to get the
   * correct images and pass the autograder.
   *
   * @param mag the energy (squared vector magnitude) of the pixel whose
   * intensity we want to compute.
   * @return the intensity of the output pixel.
   */
  private static short mag2gray(long mag) {
    short intensity = (short) (30.0 * Math.log(1.0 + (double) mag) - 256.0);

    // Make sure the returned intensity is in the range 0...255, regardless of
    // the input value.
    if (intensity < 0) {
      intensity = 0;
    } else if (intensity > 255) {
      intensity = 255;
    }
    return intensity;
  }

  /**
   * sobelEdges() applies the Sobel operator, identifying edges in "this"
   * image.  The Sobel operator computes a magnitude that represents how
   * strong the edge is.  We compute separate gradients for the red, blue, and
   * green components at each pixel, then sum the squares of the three
   * gradients at each pixel.  We convert the squared magnitude at each pixel
   * into a grayscale pixel intensity in the range 0...255 with the logarithmic
   * mapping encoded in mag2gray().  The output is a grayscale PixImage whose
   * pixel intensities reflect the strength of the edges.
   *
   * See http://en.wikipedia.org/wiki/Sobel_operator#Formulation for details.
   *
   * @return a grayscale PixImage representing the edges of the input image.
   * Whiter pixels represent stronger edges.
   */
  public PixImage sobelEdges() {
    // Replace the following line with your solution.
    long energy;
    short mag;
    long gx_r, gy_r;
    long gx_g, gy_g;
    long gx_b, gy_b;
    PixImage det = new PixImage(width, height);
    for(int i = 0; i < width; i++)
          for(int j = 0; j < height; j++){
            if(i == 0 && j == 0){
              gx_r = (r[i][j] + 0*r[i][j] + (-1)*r[i+1][j] + 2*r[i][j] + 0*r[i][j] + (-2)*r[i+1][j] + r[i][j+1] + 0*r[i][j+1] + (-1)*r[i+1][j+1]);
              gx_g = (g[i][j] + 0*g[i][j] + (-1)*g[i+1][j] + 2*g[i][j] + 0*g[i][j] + (-2)*g[i+1][j] + g[i][j+1] + 0*g[i][j+1] + (-1)*g[i+1][j+1]);
              gx_b = (b[i][j] + 0*b[i][j] + (-1)*b[i+1][j] + 2*b[i][j] + 0*b[i][j] + (-2)*b[i+1][j] + b[i][j+1] + 0*b[i][j+1] + (-1)*b[i+1][j+1]);
              gy_r = (r[i][j] + 2*r[i][j] + r[i+1][j] + 0*r[i][j] + 0*r[i][j] + 0*r[i+1][j] + (-1)*r[i][j+1] + (-2)*r[i][j+1] + (-1)*r[i+1][j+1]);
              gy_g = (g[i][j] + 2*g[i][j] + g[i+1][j] + 0*g[i][j] + 0*g[i][j] + 0*g[i+1][j] + (-1)*g[i][j+1] + (-2)*g[i][j+1] + (-1)*g[i+1][j+1]);
              gy_b = (b[i][j] + 2*b[i][j] + b[i+1][j] + 0*b[i][j] + 0*b[i][j] + 0*b[i+1][j] + (-1)*b[i][j+1] + (-2)*b[i][j+1] + (-1)*b[i+1][j+1]);
          }else if(i == width-1 && j == 0){
            gx_r = (r[i-1][j] + 0*r[i][j] + (-1)*r[i][j] + 2*r[i-1][j] + 0*r[i][j] + (-2)*r[i][j] + r[i-1][j+1] + 0*r[i][j+1] + (-1)*r[i][j+1]); 
            gx_g = (g[i-1][j] + 0*g[i][j] + (-1)*g[i][j] + 2*g[i-1][j] + 0*g[i][j] + (-2)*g[i][j] + g[i-1][j+1] + 0*g[i][j+1] + (-1)*g[i][j+1]); 
            gx_b = (b[i-1][j] + 0*b[i][j] + (-1)*b[i][j] + 2*b[i-1][j] + 0*b[i][j] + (-2)*b[i][j] + b[i-1][j+1] + 0*b[i][j+1] + (-1)*b[i][j+1]); 
            gy_r = (r[i-1][j] + 2*r[i][j] + r[i][j] + 0*r[i-1][j] + 0*r[i][j] + 0*r[i][j] + (-1)*r[i-1][j+1] + (-2)*r[i][j+1] + (-1)*r[i][j+1]); 
            gy_g = (g[i-1][j] + 2*g[i][j] + g[i][j] + 0*g[i-1][j] + 0*g[i][j] + 0*g[i][j] + (-1)*g[i-1][j+1] + (-2)*g[i][j+1] + (-1)*g[i][j+1]); 
            gy_b = (b[i-1][j] + 2*b[i][j] + b[i][j] + 0*b[i-1][j] + 0*b[i][j] + 0*b[i][j] + (-1)*b[i-1][j+1] + (-2)*b[i][j+1] + (-1)*b[i][j+1]); 
          }else if(i == 0 && j == height -1){
            gx_r = (r[i][j-1] + 0*r[i][j-1] + (-1)*r[i+1][j-1] + 2*r[i][j] + 0*r[i][j] + (-2)*r[i+1][j] + r[i][j] + 0*r[i][j] + (-1)*r[i+1][j]);
            gx_g = (g[i][j-1] + 0*g[i][j-1] + (-1)*g[i+1][j-1] + 2*g[i][j] + 0*g[i][j] + (-2)*g[i+1][j] + g[i][j] + 0*g[i][j] + (-1)*g[i+1][j]);
            gx_b = (b[i][j-1] + 0*b[i][j-1] + (-1)*b[i+1][j-1] + 2*b[i][j] + 0*b[i][j] + (-2)*b[i+1][j] + b[i][j] + 0*b[i][j] + (-1)*b[i+1][j]);
            gy_r = (r[i][j-1] + 2*r[i][j-1] + r[i+1][j-1] + 0*r[i][j] + 0*r[i][j] + 0*r[i+1][j] + (-1)*r[i][j] + (-2)*r[i][j] + (-1)*r[i+1][j]);
            gy_g = (g[i][j-1] + 2*g[i][j-1] + g[i+1][j-1] + 0*g[i][j] + 0*g[i][j] + 0*g[i+1][j] + (-1)*g[i][j] + (-2)*g[i][j] + (-1)*g[i+1][j]);
            gy_b = (b[i][j-1] + 2*b[i][j-1] + b[i+1][j-1] + 0*b[i][j] + 0*b[i][j] + 0*b[i+1][j] + (-1)*b[i][j] + (-2)*b[i][j] + (-1)*b[i+1][j]);
          }else if(i == width-1 && j == height - 1){
            gx_r = (r[i-1][j-1] + 0*r[i][j-1] + (-1)*r[i][j-1] + 2*r[i-1][j] + 0*r[i][j] + (-2)*r[i][j] + r[i-1][j] + 0*r[i][j] + (-1)*r[i][j]);
            gx_g = (g[i-1][j-1] + 0*g[i][j-1] + (-1)*g[i][j-1] + 2*g[i-1][j] + 0*g[i][j] + (-2)*g[i][j] + g[i-1][j] + 0*g[i][j] + (-1)*g[i][j]);
            gx_b = (b[i-1][j-1] + 0*b[i][j-1] + (-1)*b[i][j-1] + 2*b[i-1][j] + 0*b[i][j] + (-2)*b[i][j] + b[i-1][j] + 0*b[i][j] + (-1)*b[i][j]);
            gy_r = (r[i-1][j-1] + 2*r[i][j-1] + r[i][j-1] + 0*r[i-1][j] + 0*r[i][j] + 0*r[i][j] + (-1)*r[i-1][j] + (-2)*r[i][j] + (-1)*r[i][j]);
            gy_g = (g[i-1][j-1] + 2*g[i][j-1] + g[i][j-1] + 0*g[i-1][j] + 0*g[i][j] + 0*g[i][j] + (-1)*g[i-1][j] + (-2)*g[i][j] + (-1)*g[i][j]);
            gy_b = (b[i-1][j-1] + 2*b[i][j-1] + b[i][j-1] + 0*b[i-1][j] + 0*b[i][j] + 0*b[i][j] + (-1)*b[i-1][j] + (-2)*b[i][j] + (-1)*b[i][j]);
          }else if(j == 0){
            gx_r = (r[i-1][j] + 0*r[i][j] + (-1)*r[i+1][j] + 2*r[i-1][j] + 0*r[i][j] + (-2)*r[i+1][j] + r[i-1][j+1] + 0*r[i][j+1] + (-1)*r[i+1][j+1]);
            gx_g = (g[i-1][j] + 0*g[i][j] + (-1)*g[i+1][j] + 2*g[i-1][j] + 0*g[i][j] + (-2)*g[i+1][j] + g[i-1][j+1] + 0*g[i][j+1] + (-1)*g[i+1][j+1]);
            gx_b = (b[i-1][j] + 0*b[i][j] + (-1)*b[i+1][j] + 2*b[i-1][j] + 0*b[i][j] + (-2)*b[i+1][j] + b[i-1][j+1] + 0*b[i][j+1] + (-1)*b[i+1][j+1]);
            gy_r = (r[i-1][j] + 2*r[i][j] + r[i+1][j] + 0*r[i-1][j] + 0*r[i][j] + 0*r[i+1][j] + (-1)*r[i-1][j+1] + (-2)*r[i][j+1] + (-1)*r[i+1][j+1]);
            gy_g = (g[i-1][j] + 2*g[i][j] + g[i+1][j] + 0*g[i-1][j] + 0*g[i][j] + 0*g[i+1][j] + (-1)*g[i-1][j+1] + (-2)*g[i][j+1] + (-1)*g[i+1][j+1]);
            gy_b = (b[i-1][j] + 2*b[i][j] + b[i+1][j] + 0*b[i-1][j] + 0*b[i][j] + 0*b[i+1][j] + (-1)*b[i-1][j+1] + (-2)*b[i][j+1] + (-1)*b[i+1][j+1]);
          }else if(j == height-1){
            gx_r = (r[i-1][j-1] + 0*r[i][j-1] + (-1)*r[i+1][j-1] + 2*r[i-1][j] + 0*r[i][j] + (-2)*r[i+1][j]+ r[i-1][j] + 0*r[i][j] + (-1)*r[i+1][j]);
            gx_g = (g[i-1][j-1] + 0*g[i][j-1] + (-1)*g[i+1][j-1] + 2*g[i-1][j] + 0*g[i][j] + (-2)*g[i+1][j]+ g[i-1][j] + 0*g[i][j] + (-1)*g[i+1][j]);
            gx_b = (b[i-1][j-1] + 0*b[i][j-1] + (-1)*b[i+1][j-1] + 2*b[i-1][j] + 0*b[i][j] + (-2)*b[i+1][j]+ b[i-1][j] + 0*b[i][j] + (-1)*b[i+1][j]);
            gy_r = (r[i-1][j-1] + 2*r[i][j-1] + r[i+1][j-1] + 0*r[i-1][j] + 0*r[i][j] + 0*r[i+1][j]+ (-1)*r[i-1][j] + (-2)*r[i][j] + (-1)*r[i+1][j]);
            gy_g = (g[i-1][j-1] + 2*g[i][j-1] + g[i+1][j-1] + 0*g[i-1][j] + 0*g[i][j] + 0*g[i+1][j]+ (-1)*g[i-1][j] + (-2)*g[i][j] + (-1)*g[i+1][j]);
            gy_b = (b[i-1][j-1] + 2*b[i][j-1] + b[i+1][j-1] + 0*b[i-1][j] + 0*b[i][j] + 0*b[i+1][j]+ (-1)*b[i-1][j] + (-2)*b[i][j] + (-1)*b[i+1][j]);
          }else if(i == 0){
            gx_r = (r[i][j-1] + 0*r[i][j-1] + (-1)*r[i+1][j-1] + 2*r[i][j] + 0*r[i][j] + (-2)*r[i+1][j] + r[i][j+1] + 0*r[i][j+1] + (-1)*r[i+1][j+1]);
            gx_g = (g[i][j-1] + 0*g[i][j-1] + (-1)*g[i+1][j-1] + 2*g[i][j] + 0*g[i][j] + (-2)*g[i+1][j] + g[i][j+1] + 0*g[i][j+1] + (-1)*g[i+1][j+1]);
            gx_b = (b[i][j-1] + 0*b[i][j-1] + (-1)*b[i+1][j-1] + 2*b[i][j] + 0*b[i][j] + (-2)*b[i+1][j] + b[i][j+1] + 0*b[i][j+1] + (-1)*b[i+1][j+1]);
            gy_r = (r[i][j-1] + 2*r[i][j-1] + r[i+1][j-1] + 0*r[i][j] + 0*r[i][j] + 0*r[i+1][j] + (-1)*r[i][j+1] + (-2)*r[i][j+1] + (-1)*r[i+1][j+1]);
            gy_g = (g[i][j-1] + 2*g[i][j-1] + g[i+1][j-1] + 0*g[i][j] + 0*g[i][j] + 0*g[i+1][j] + (-1)*g[i][j+1] + (-2)*g[i][j+1] + (-1)*g[i+1][j+1]);
            gy_b = (b[i][j-1] + 2*b[i][j-1] + b[i+1][j-1] + 0*b[i][j] + 0*b[i][j] + 0*b[i+1][j] + (-1)*b[i][j+1] + (-2)*b[i][j+1] + (-1)*b[i+1][j+1]);
          }else if(i == width-1){
            gx_r = (r[i-1][j-1] + 0*r[i][j-1] + (-1)*r[i][j-1] + 2*r[i-1][j] + 0*r[i][j] + (-2)*r[i][j] + r[i-1][j+1] + 0*r[i][j+1] + (-1)*r[i][j+1]);
            gx_g = (g[i-1][j-1] + 0*g[i][j-1] + (-1)*g[i][j-1] + 2*g[i-1][j] + 0*g[i][j] + (-2)*g[i][j] + g[i-1][j+1] + 0*g[i][j+1] + (-1)*g[i][j+1]);
            gx_b = (b[i-1][j-1] + 0*b[i][j-1] + (-1)*b[i][j-1] + 2*b[i-1][j] + 0*b[i][j] + (-2)*b[i][j] + b[i-1][j+1] + 0*b[i][j+1] + (-1)*b[i][j+1]);
            gy_r = (r[i-1][j-1] + 2*r[i][j-1] + r[i][j-1] + 0*r[i-1][j] + 0*r[i][j] + 0*r[i][j] + (-1)*r[i-1][j+1] + (-2)*r[i][j+1] + (-1)*r[i][j+1]);
            gy_g = (g[i-1][j-1] + 2*g[i][j-1] + g[i][j-1] + 0*g[i-1][j] + 0*g[i][j] + 0*g[i][j] + (-1)*g[i-1][j+1] + (-2)*g[i][j+1] + (-1)*g[i][j+1]);
            gy_b = (b[i-1][j-1] + 2*b[i][j-1] + b[i][j-1] + 0*b[i-1][j] + 0*b[i][j] + 0*b[i][j] + (-1)*b[i-1][j+1] + (-2)*b[i][j+1] + (-1)*b[i][j+1]);
          }else{
            gx_r = (r[i-1][j-1] + 0*r[i][j-1] + (-1)*r[i+1][j-1] + 2*r[i-1][j] + 0*r[i][j] + (-2)*r[i+1][j] + r[i-1][j+1] + 0*r[i][j+1] + (-1)*r[i+1][j+1]);
            gx_g = (g[i-1][j-1] + 0*g[i][j-1] + (-1)*g[i+1][j-1] + 2*g[i-1][j] + 0*g[i][j] + (-2)*g[i+1][j] + g[i-1][j+1] + 0*g[i][j+1] + (-1)*g[i+1][j+1]);
            gx_b = (b[i-1][j-1] + 0*b[i][j-1] + (-1)*b[i+1][j-1] + 2*b[i-1][j] + 0*b[i][j] + (-2)*b[i+1][j] + b[i-1][j+1] + 0*b[i][j+1] + (-1)*b[i+1][j+1]);
            gy_r = (r[i-1][j-1] + 2*r[i][j-1] + r[i+1][j-1] + 0*r[i-1][j] + 0*r[i][j] + 0*r[i+1][j] + (-1)*r[i-1][j+1] + (-2)*r[i][j+1] + (-1)*r[i+1][j+1]);
            gy_g = (g[i-1][j-1] + 2*g[i][j-1] + g[i+1][j-1] + 0*g[i-1][j] + 0*g[i][j] + 0*g[i+1][j] + (-1)*g[i-1][j+1] + (-2)*g[i][j+1] + (-1)*g[i+1][j+1]);
            gy_b = (b[i-1][j-1] + 2*b[i][j-1] + b[i+1][j-1] + 0*b[i-1][j] + 0*b[i][j] + 0*b[i+1][j] + (-1)*b[i-1][j+1] + (-2)*b[i][j+1] + (-1)*b[i+1][j+1]);
          }
          energy = gx_r*gx_r + gy_r*gy_r + gx_g*gx_g + gy_g* gy_g + gx_b*gx_b + gy_b*gy_b;
          mag = mag2gray(energy);
          det.setPixel(i, j, mag, mag, mag);
        }
    return det;
    // Don't forget to use the method mag2gray() above to convert energies to
    // pixel intensities.
  }


  /**
   * TEST CODE:  YOU DO NOT NEED TO FILL IN ANY METHODS BELOW THIS POINT.
   * You are welcome to add tests, though.  Methods below this point will not
   * be tested.  This is not the autograder, which will be provided separately.
   */


  /**
   * doTest() checks whether the condition is true and prints the given error
   * message if it is not.
   *
   * @param b the condition to check.
   * @param msg the error message to print if the condition is false.
   */
  private static void doTest(boolean b, String msg) {
    if (b) {
      System.out.println("Good.");
    } else {
      System.err.println(msg);
    }
  }

  /**
   * array2PixImage() converts a 2D array of grayscale intensities to
   * a grayscale PixImage.
   *
   * @param pixels a 2D array of grayscale intensities in the range 0...255.
   * @return a new PixImage whose red, green, and blue values are equal to
   * the input grayscale intensities.
   */
  private static PixImage array2PixImage(int[][] pixels) {
    int width = pixels.length;
    int height = pixels[0].length;
    PixImage image = new PixImage(width, height);

    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        image.setPixel(x, y, (short) pixels[x][y], (short) pixels[x][y],
                       (short) pixels[x][y]);
      }
    }

    return image;
  }

  /**
   * equals() checks whether two images are the same, i.e. have the same
   * dimensions and pixels.
   *
   * @param image a PixImage to compare with "this" PixImage.
   * @return true if the specified PixImage is identical to "this" PixImage.
   */
  public boolean equals(PixImage image) {
    int width = getWidth();
    int height = getHeight();

    if (image == null ||
        width != image.getWidth() || height != image.getHeight()) {
      return false;
    }

    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        if (! (getRed(x, y) == image.getRed(x, y) &&
               getGreen(x, y) == image.getGreen(x, y) &&
               getBlue(x, y) == image.getBlue(x, y))) {
          return false;
        }
      }
    }
    return true;
  }

  /**
   * main() runs a series of tests to ensure that the convolutions (box blur
   * and Sobel) are correct.
   */
  public static void main(String[] args) {
    // Be forwarned that when you write arrays directly in Java as below,
    // each "row" of text is a column of your image--the numbers get
    // transposed.
    PixImage image1 = array2PixImage(new int[][] { { 0, 10, 240 },
                                                   { 30, 120, 250 },
                                                   { 80, 250, 255 } });
    System.out.println("Testing getWidth/getHeight on a 3x3 image.  " +
                       "Input image:");
    System.out.print(image1);
    doTest(image1.getWidth() == 3 && image1.getHeight() == 3,
           "Incorrect image width and height.");

    System.out.println("Testing blurring on a 3x3 image.");
    doTest(image1.boxBlur(1).equals(
           array2PixImage(new int[][] { { 40, 108, 155 },
                                        { 81, 137, 187 },
                                        { 120, 164, 218 } })),
           "Incorrect box blur (1 rep):\n" + image1.boxBlur(1));
    doTest(image1.boxBlur(2).equals(
           array2PixImage(new int[][] { { 91, 118, 146 },
                                        { 108, 134, 161 },
                                        { 125, 151, 176 } })),
           "Incorrect box blur (2 rep):\n" + image1.boxBlur(2));
    doTest(image1.boxBlur(2).equals(image1.boxBlur(1).boxBlur(1)),
           "Incorrect box blur (1 rep + 1 rep):\n" +
           image1.boxBlur(2) + image1.boxBlur(1).boxBlur(1));

    System.out.println("Testing edge detection on a 3x3 image.");
    doTest(image1.sobelEdges().equals(
           array2PixImage(new int[][] { { 104, 189, 180 },
                                        { 160, 193, 157 },
                                        { 166, 178, 96 } })),
           "Incorrect Sobel:\n" + image1.sobelEdges());


    PixImage image2 = array2PixImage(new int[][] { { 0, 100, 100 },
                                                   { 0, 0, 100 } });
    System.out.println("Testing getWidth/getHeight on a 2x3 image.  " +
                       "Input image:");
    System.out.print(image2);
    doTest(image2.getWidth() == 2 && image2.getHeight() == 3,
           "Incorrect image width and height.");

    System.out.println("Testing blurring on a 2x3 image.");
    doTest(image2.boxBlur(1).equals(
           array2PixImage(new int[][] { { 25, 50, 75 },
                                        { 25, 50, 75 } })),
           "Incorrect box blur (1 rep):\n" + image2.boxBlur(1));

    System.out.println("Testing edge detection on a 2x3 image.");
    doTest(image2.sobelEdges().equals(
           array2PixImage(new int[][] { { 122, 143, 74 },
                                        { 74, 143, 122 } })),
           "Incorrect Sobel:\n" + image2.sobelEdges());
  }
}
