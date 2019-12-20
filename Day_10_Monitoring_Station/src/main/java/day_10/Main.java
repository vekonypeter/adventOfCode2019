package day_10;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {

  public static void main(String[] args) throws URISyntaxException, IOException {
    String input =
        Files.readString(
            Paths.get(Main.class.getResource("/day_10_input.txt").toURI()), StandardCharsets.UTF_8);

    System.out.println(input);
  }
}
