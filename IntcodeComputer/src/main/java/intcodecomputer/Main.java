package intcodecomputer;

import intcodecomputer.domain.ConsoleInputProvider;
import intcodecomputer.domain.ConsoleOutputHandler;
import intcodecomputer.domain.InputProvider;
import intcodecomputer.domain.IntcodeComputer;
import intcodecomputer.domain.OutputHandler;
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

    InputProvider inputProvider = new ConsoleInputProvider();
    OutputHandler outputHandler = new ConsoleOutputHandler();

    memoryStrings.forEach(
        memoryString -> {
          var computer = new IntcodeComputer(inputProvider, outputHandler);
          computer.init(memoryString);
          computer.run();
        });
  }
}
