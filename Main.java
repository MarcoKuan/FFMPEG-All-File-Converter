import java.io.IOException;

public class Main {
  public static void main(String[] args) {
    try {
      // Run function
    } catch (IOException IOErr) {
      System.out.print("Error: ");
      IOErr.printStackTrace();
    }
  }
}
