import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

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
   * Manipulate the file names for map storage
   */
  private StringBuilder sb;

  /**
   * Private static strings
   */
  private static final String PATTERN_MKV = ".*\\.mkv";
  private static final String TYPE_MKV = ".mkv";
  private static final String TYPE_MP4 = ".mp4";

  /**
   * Constructor that initializes the ArrayList for storing video names
   */
  public Converter() {
    this.videoNames = new HashMap<>();
    this.sb = new StringBuilder();
  }

  /**
   * Converts all the files from the directory from mkv to mp4
   */
  public void convertFiles() {
    // Grabs the directory and stores it into the Converter object
    this.getDirectory();

    // Stores the file names and the new file names into the Map
    this.storeFiles();

    // Run the ffmpeg command on the cmd

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

  /**
   * Accesses the directory and stores all files that need to be converted into
   * the Map, as well as the new file names as its values
   */
  private void storeFiles() {
    // Store all mkv files into the Map and store the values as mp4 type names
    for (File f : dir.listFiles()) {
      System.out.println(f.getName());

      // Regex patterns to find the mkv files
      boolean isMKV = Pattern.matches(PATTERN_MKV, f.getName());

      // Stores the mkv file into the map and the value is stored as mp4
      if (isMKV) {
        // Resets to the newest name of the file
        this.sb.setLength(0);

        // Obtain the last index of the type in the string
        int typeIndex = f.getName().lastIndexOf(TYPE_MKV);

        // Append the new file name into mp4
        sb.append(f.getName().toString().substring(0, typeIndex));
        sb.append(TYPE_MP4);

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

    // Store the mkv names
    int i = 1;

    sb.append(".mkv names: " + "\n");

    for (String fileName : this.videoNames.keySet()) {
      sb.append(i + ": " + fileName + "\n");
      i++;
    }

    // Separate the mp4 and mkv by a new line
    sb.append("\n");

    // Store the mp4 names
    i = 1;

    sb.append(".mp4 names: " + "\n");

    for (String fileName : this.videoNames.values()) {
      sb.append(i + ": " + fileName + "\n");
      i++;
    }

    return sb.toString();
  }
}
