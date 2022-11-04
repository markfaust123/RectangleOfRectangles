import java.util.Scanner; 
import java.io.PrintWriter; 
import java.io.IOException; 
import java.io.FileInputStream; 
import java.awt.Color;

/**
 * EN.500.112 Gateway Computing: Java, Project 4
 * 
 * This class is responsible for printing out Rectangle objects 
 * of different sizes, colors, and fills onto the screen in 
 * various different patterns using the Rectangle and StdDraw 
 * classes. This program is also able to print and read Rectangle-drawing
 * instructions to and from specified files. 
 * 
 * @author Mark Faust (mfaust4 — 11/4/22)
 */

public class Project4 {

   /**
    * This method creates a file containing characteristics of Rectangle objects
    * that can be printed. The method also creates an array of Rectangle objects
    * with color and dimension specified by the client.
    * @param scan the Scanner object that is able to read the client's input
    * @return array containing the newly-created Rectangle objects
    * @throws IOException
    */
   public static Rectangle[][] printFileAndCreateArray(Scanner scan) 
                                                            throws IOException {
      //Prompts user to enter checkerboard characteristics
      System.out.print("Enter checkerboard size: ");
      int rows = scan.nextInt();
      int cols = rows;
      Rectangle[][] array = new Rectangle[rows][cols];
      
      System.out.print("Enter RGB values, each [0,255]: ");
      int red = scan.nextInt();
      int green = scan.nextInt();
      int blue = scan.nextInt();
      
      double width = 1.0 / rows;
      double height = 1.0 / cols;
      double xCenter = width / 2.0;
      double yCenter = height / 2.0;
      
      boolean filled = true;
      Color color;

      //Creates 2D array with the Rectangle objects and prints characteristics
      //to appropriately-named file
      String checkerboard = "checkerboard" + Integer.toString(rows) + ".txt";
      PrintWriter file = new PrintWriter(checkerboard);
      file.println(rows + " " + cols);
      for (int i = 0; i < rows; ++i) {                        
         for (int j = 0; j < cols; ++j) {
            if ((i % 2 == 0 && j % 2 == 0) || (i % 2 == 1 && j % 2 == 1)) {
               color = new Color(255, 255, 255);
            }
            else {
               color = new Color(red, green, blue);
            }
            array[i][j] = new Rectangle(color, width, height, filled, 
               (j * width) + xCenter, 1 - ((i * height) + yCenter)); 
            file.println(array[i][j]);
         }
      }
      file.flush();
      file.close();
      return array;
   }

   /**
    * This method scans an inputted file and creates a new array containing
    * Rectangle objects with characteristics specified within the inputted
    * file.
    * @param keyboard the Scanner object that is able to read the inputted file
    * @return array containing the newly-created Rectangle objects
    * @throws IOException
    */
   public static Rectangle[][] scanFileAndCreateArray(Scanner keyboard) 
                                                         throws IOException {
      String file = keyboard.next();
      Scanner scan = new Scanner(new FileInputStream(file));
      
      int rows = scan.nextInt();
      int cols = scan.nextInt();
      scan.nextLine();
      Rectangle[][] array = new Rectangle[rows][cols];
      
      while (scan.hasNextLine()) {
         for (int i = 0; i < array.length; ++i) {
            for (int j = 0; j < array[i].length; ++j) {
               array[i][j] = new Rectangle(scan.nextLine());
            }
         }
      }
 
      scan.close();
      return array;
   }
   
   /**
    * This method draws the rectangle objects that are passed as array
    * elements in a checkerboard pattern (row-major order).
    * @param array the array containing the to-be-printed Rectangle objects
    */
   public static void drawCheckerboard(Rectangle[][] array) {
      for (int i = 0; i < array.length; ++i) {
         for (int j = 0; j < array[i].length; ++j) {
            array[i][j].draw();
            StdDraw.pause(50);
         }
      }
   }
   
   /**
    * This method draws the rectangle objects that are passed as array
    * elements in a snake pattern.
    * @param array the array containing the to-be-printed Rectangle objects
    */
   public static void drawSnake(Rectangle[][] array) {
      for (int i = 0; i < array.length; ++i) {
         if (i % 2 == 0) {
            for (int j = 0; j < array[i].length; ++j) {
               array[j][i].draw();
               StdDraw.pause(50);
            }
         }
         else {
            for (int j = array[i].length - 1; j >= 0; --j) {
               array[j][i].draw();
               StdDraw.pause(50);
            }
         }
      }
   }
   
   /**
    * This method draws the rectangle objects that are passed as array
    * elements in a spiral pattern.
    * @param array the array containing the to-be-printed Rectangle objects
    */
   public static void drawSpiral(Rectangle[][] array) {
      int rows = array.length;
      int cols = array[0].length;
      int xDistance = array[0].length;
      int yDistance = array.length;
      int numElements = array[0].length * array.length;
      
      int times = (rows < cols) ? (rows / 2) + 1 : (cols / 2) + 1;
      
      for (int k = 0; k <= times; ++k) {
         
         for (int j = xDistance - k - 1; j > k; --j) {
            array[yDistance - k - 1][j].draw();
            StdDraw.pause(50);
         }
         
         for (int i = yDistance - k - 1; i > k; --i) {
            array[i][k].draw();
            StdDraw.pause(50);
         }
         
         for (int j = k; j < xDistance - k - 1; ++j) {
            array[k][j].draw();
            StdDraw.pause(50);
         }
         
         for (int i = k; i < yDistance - k - 1; ++i) {
            array[i][xDistance - k - 1].draw();
            StdDraw.pause(50);
         }
      }
      if (rows % 2 == 1 && cols % 2 == 1) {
         array[rows / 2][cols / 2].draw();
      }
   }
   
   /** 
    * Usage method for the Project4 class. This prompts client for input 
    * and output files to print Rectangle object onto the screen and the 
    * Rectangles' characteristics onto a specified file.
       @param args not used
       @throws IOException
   */ 
   public static void main(String[] args) throws IOException {
   
      Scanner keyboard = new Scanner(System.in);
      
      //Getting checkerboard input and creating file
      Rectangle[][] checkerboardArray = printFileAndCreateArray(keyboard);

      //Drawing Checkerboard
      StdDraw.clear(Color.LIGHT_GRAY);
      drawCheckerboard(checkerboardArray);
      
      //Getting snake input
      System.out.print("Enter snake input filename: ");
      Rectangle[][] snakeArray = scanFileAndCreateArray(keyboard);
      
      //Drawing Snake
      StdDraw.clear(Color.LIGHT_GRAY);
      drawSnake(snakeArray);
      
      //Getting spiral input
      System.out.print("Enter spiral input filename: ");
      Rectangle[][] spiralArray = scanFileAndCreateArray(keyboard);
      
      //Drawing Spiral
      StdDraw.clear(Color.LIGHT_GRAY);
      drawSpiral(spiralArray);

   }
   
}