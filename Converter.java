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
   * File that holds the directory
   */
  private File dir;

  /**
   * Manipulate the file names for map storage
   */
  private final StringBuilder sb;

  /**
   * Stores the type that needs to be converted
   */
  private String currentType;

  /**
   * Stores the type to be converted
   */
  private String convertedType;

  /**
   * Takes input and uses it to store into variables
   */
  private final Scanner sc;

/**
 * Constructor that initializes the ArrayList for storing video names
 * 
 * @param sc the scanner that reads input
 */
  public Converter(Scanner sc) {
    this.videoNames = new HashMap<>();
    this.sb = new StringBuilder();
    this.sc = sc;
  }

  /**
   * Converts all the files from the directory from currentType to convertedType
   * 
   * @throws IOException
   */
  public void convertFiles() throws IOException {
    // Grabs the directory and stores it into the Converter object
    this.getDirectory();

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
  }

  /**
   * Grabs the name of the directory location through user input
   */
  private void getDirectory() {
    // Prompt for directory
    System.out.print("Enter the location of the directory: ");
    String dirName = sc.nextLine();

    this.dir = new File(dirName);
  }

  /**
   * Grabs the type of the file and converted file through user input
   */
  private void getFileTypes() {
    // Prompt for directory
    System.out.print("Enter the file type to search for (example: mp4, mkv, etc): ");
    this.currentType = "." + sc.nextLine();

    System.out.print("Enter the file type to convert to (example: mp4, mkv, etc): ");
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

    sb.append(this.currentType + " names: " + "\n");

    for (String fileName : this.videoNames.keySet()) {
      sb.append(i + ": " + fileName + "\n");
      i++;
    }

    // Separate the convertedType and currentType by a new line
    sb.append("\n");

    // Store the convertedType names
    i = 1;

    sb.append(this.convertedType + " names: " + "\n");

    for (String fileName : this.videoNames.values()) {
      sb.append(i + ": " + fileName + "\n");
      i++;
    }

    return sb.toString();
  }
}
