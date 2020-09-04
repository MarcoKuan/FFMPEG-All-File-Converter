import java.io.IOException;
import java.util.Scanner;

/**
 * Class that runs the converter. Asks for input and copy converts the files
 * 
 * @author Marco
 */
public class Driver {
  /**
   * The main method of the driver. Runs the converter with a Scanner.
   * 
   * @param args The arguments given in the terminal
   */
  public static void main(String[] args) {
    // Converts the file
    try (Scanner sc = new Scanner(System.in)) {
      Converter convert = new Converter(sc);
      convert.convertFiles();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
