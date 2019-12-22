package intcodecomputer;

import intcodecomputer.domain.IntcodeComputer;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Main {

  public static void main(String[] args) throws URISyntaxException, IOException {
    List<String> memoryStrings =
        Files.readAllLines(
            Paths.get(Main.class.getResource("/day_9_input.txt").toURI()), StandardCharsets.UTF_8);

    memoryStrings.forEach(
        memoryString -> {
          var computer = new IntcodeComputer();
          computer.init(memoryString);
          computer.run();
        });
  }
}
