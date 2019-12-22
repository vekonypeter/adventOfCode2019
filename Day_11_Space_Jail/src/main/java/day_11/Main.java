package day_11;

import intcodecomputer.domain.IntcodeComputer;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {

  public static void main(String[] args) throws URISyntaxException, IOException {
    String input =
        Files.readString(
            Paths.get(Main.class.getResource("/day_11_input.txt").toURI()), StandardCharsets.UTF_8);

    IntcodeComputer computer = new IntcodeComputer();
    computer.init(input);
    computer.run();
  }
}
