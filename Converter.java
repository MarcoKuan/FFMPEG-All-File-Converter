import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * File converter class that will grab the names of all mkv files in the
 * directory and convert it into mp4 files.
 * 
 * @author Marco
 */
public class Converter {

  /**
   * Stores the original file name and converted file name
   */
  private Map<String, String> videoNames;

  /**
   * File that holds the directory
   */
  private File dir;

  /**
   * Constructor that initializes the ArrayList for storing video names
   */
  public Converter() {
    videoNames = new HashMap<>();
  }

  /**
   * Converts all the files from the directory from mkv to mp4
   */
  public void convertFiles() {
    // Grabs the directory and stores it into the Converter object
    this.getDirectory();
    
    // Store all mkv files into the Map and store the values as mp4 type names
    for (File f : dir.listFiles()) {
      System.out.println(f.toString());
    }
    
  }

  /**
   * Grabs the name of the directory location through user input
   */
  private void getDirectory() {
    // Prompt for directory
    System.out.print("Enter the location of the directory: ");

    try (Scanner scan = new Scanner(System.in)) {
      String dirName = scan.nextLine();

      this.dir = new File(dirName);
    }
  }
}
