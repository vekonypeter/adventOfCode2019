package day_13;

import day_13.domain.Block;
import day_13.domain.Field;
import intcodecomputer.domain.ConsoleInputProvider;
import intcodecomputer.domain.IntcodeComputer;
import intcodecomputer.domain.OutputHandler;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {

  public static void main(String[] args) throws URISyntaxException, IOException {
    String input =
        Files.readString(
            Paths.get(Main.class.getResource("/day_13_input.txt").toURI()), StandardCharsets.UTF_8);

    Field field = new Field();

    IntcodeComputer computer =
        new IntcodeComputer(new ConsoleInputProvider(), new GameOutputHandler(field));
    computer.init(input);
    computer.run();

    long numberOfBlockTiles =
        field.getTiles().stream().filter(tile -> tile instanceof Block).count();

    System.out.println("NUMBER OF BLOCK TILES: " + numberOfBlockTiles);
  }

  private static class GameOutputHandler implements OutputHandler {

    private Field field;

    private int outputCount = 0;

    private int xCache;
    private int yCache;

    public GameOutputHandler(Field field) {
      this.field = field;
    }

    @Override
    public void handleOutput(long output) {
      outputCount++;
      int step = outputCount % 3;

      switch (step) {
        case 0:
          field.addTile(xCache, yCache, (int) output);
          break;
        case 1:
          xCache = (int) output;
          break;
        case 2:
          yCache = (int) output;
          break;
      }
    }
  }
}
