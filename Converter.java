import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * File converter class that will grab the names of all currentType files in the
 * directory and convert it into convertedType files.
 * 
 * @author Marco
 */
public class Converter {

  /**
   * Stores the original file name and converted file name
   */
  private final Map<String, String> videoNames;

  /**
   * Manipulate the file names for map storage
   */
  private final StringBuilder sb;

  /**
   * Takes input and uses it to store into variables
   */
  private final Scanner sc;

  /**
   * File that holds the directory
   */
  private File dir;

  /**
   * Stores the type that needs to be converted
   */
  private String currentType;

  /**
   * Stores the type to be converted
   */
  private String convertedType;
  
  /**
   * Determines if there should be any conversion
   */
  private boolean convertBool;

  /**
   * String asking for the option
   */
  private static final String OPTION_MSG = "Choose 1 (Current Directory) or 2 (Enter the Directory Location) (NOTE: Other options will exit): ";

  /**
   * String asking for the directory
   */
  private static final String DIR_MSG = "Enter the location of the directory: ";

  /**
   * String for toString about names of the file
   */
  private static final String NAMES_MSG = " names: ";

  /**
   * String asking what file type to search for
   */
  private static final String SEARCH_TYPE_MSG = "Enter the file type to search for (example: mp4, mkv, etc): ";
  
  /**
   * String asking what file type to convert to
   */
  private static final String CONVERT_TYPE_MSG = "Enter the file type to convert to (example: mp4, mkv, etc): ";
  
  /**
   * String for the first option
   */
  private static final String OPTION_ONE = "1";
  
  /**
   * String for the second option
   */
  private static final String OPTION_TWO = "2";
  
  /**
   * String for Copying the message
   */
  private static final String CONVERT_MSG = "Copy conversion complete!";
  
  /**
   * String for exiting message
   */
  private static final String EXIT_MSG = "Exiting!";

  /**
   * Constructor that initializes the ArrayList for storing video names
   * 
   * @param sc the scanner that reads input
   */
  public Converter(Scanner sc) {
    this.videoNames = new HashMap<>();
    this.sb = new StringBuilder();
    this.sc = sc;
    this.convertBool = true;
  }

  /**
   * Converts all the files from the directory from currentType to convertedType
   * 
   * @throws IOException
   */
  public void convertFiles() throws IOException {
    // Grabs the directory and stores it into the Converter object
    this.getDirectory();

    // Exit the program if no directory was obtained
    if (!this.convertBool) {
      return;
    }

    // Get file types to read and convert to
    this.getFileTypes();

    // Stores the file names and the new file names into the Map
    this.storeFiles();

    // Output all currentType files to convertedType
    ProcessBuilder pb = new ProcessBuilder().directory(this.dir);

    for (String file : this.videoNames.keySet()) {
      sb.setLength(0);
      sb.append("ffmpeg -i " + "\"" + file + "\"" + " -codec copy " + "\"" + this.videoNames.get(file) + "\"");

      pb.command("cmd", "/c", sb.toString());
      pb.start();
    }
    
    System.out.println(CONVERT_MSG);
  }

  /**
   * Grabs the name of the directory location through user input
   */
  private void getDirectory() {
    String choice;
    String dirName;

    // Choose the current directory or enter directory location
    System.out.print(OPTION_MSG);
    choice = sc.nextLine();

    if (choice.equals(OPTION_ONE)) {
      // Gets the directory from the location of the program
      dirName = System.getProperty("user.dir");
    } else if (choice.equals(OPTION_TWO)) {
      // Prompt for directory
      System.out.print(DIR_MSG);
      dirName = sc.nextLine();
    } else {
      // Exit
      this.convertBool = false;
      System.out.println(EXIT_MSG);
      return;
    }

    this.dir = new File(dirName);
  }

  /**
   * Grabs the type of the file and converted file through user input
   */
  private void getFileTypes() {
    // Prompt for directory
    System.out.print(SEARCH_TYPE_MSG);
    this.currentType = "." + sc.nextLine();

    System.out.print(CONVERT_TYPE_MSG);
    this.convertedType = "." + sc.nextLine();
  }

  /**
   * Accesses the directory and stores all files that need to be converted into
   * the Map, as well as the new file names as its values
   */
  private void storeFiles() {
    // Store all currentType files into the Map and store the values as
    // convertedType names
    for (File f : dir.listFiles()) {
      // Stores the currentType files into the map and the value is stored as the
      // convertedType
      if (f.toString().endsWith(this.currentType)) {
        // Resets to the newest name of the file
        this.sb.setLength(0);

        // Obtain the last index of the type in the string
        int typeIndex = f.getName().lastIndexOf(this.currentType);

        // Append the new file name into convertedType
        sb.append(f.getName().toString().substring(0, typeIndex));
        sb.append(this.convertedType);

        // Store the converted version as the value
        this.videoNames.put(f.getName(), sb.toString());
      }
    }
  }

  /**
   * Prints out the names of the files that were converted and the new file names
   */
  @Override
  public String toString() {
    // Reset the current StringBuilder
    sb.setLength(0);

    // Store the currentType names
    int i = 1;

    sb.append(this.currentType + NAMES_MSG + "\n");

    for (String fileName : this.videoNames.keySet()) {
      sb.append(i + ": " + fileName + "\n");
      i++;
    }

    // Separate the convertedType and currentType by a new line
    sb.append("\n");

    // Store the convertedType names
    i = 1;

    sb.append(this.convertedType + NAMES_MSG + "\n");

    for (String fileName : this.videoNames.values()) {
      sb.append(i + ": " + fileName + "\n");
      i++;
    }

    return sb.toString();
  }
}
