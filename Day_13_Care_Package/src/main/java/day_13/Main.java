package day_13;

import day_13.domain.Block;
import day_13.domain.Field;
import intcodecomputer.domain.InputProvider;
import intcodecomputer.domain.IntcodeComputer;
import intcodecomputer.domain.OutputHandler;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class Main {

  public static void main(String[] args) throws URISyntaxException, IOException {
    String input =
        Files.readString(
            Paths.get(Main.class.getResource("/day_13_input.txt").toURI()), StandardCharsets.UTF_8);

    Field field = new Field();

    IntcodeComputer computer =
        new IntcodeComputer(new GameInputProvider(field), new GameOutputHandler(field));
    computer.init(input);
    computer.run();

    long numberOfBlockTiles =
        Arrays.stream(field.getTiles())
            .flatMap(Arrays::stream)
            .filter(tile -> tile instanceof Block)
            .count();

    System.out.println("NUMBER OF BLOCK TILES: " + numberOfBlockTiles);
    System.out.println(field.toString());
  }

  private static class GameInputProvider implements InputProvider {

    private Field field;

    public GameInputProvider(Field field) {
      this.field = field;
    }

    @Override
    public long getInput() {
      System.out.println(field.toString());

      int paddleX = field.getPaddle().getX();
      int ballX = field.getBall().getX();

      if (paddleX < ballX) {
        return 1;
      } else if (paddleX > ballX) {
        return -1;
      } else {
        return 0;
      }
    }
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
          if (xCache == -1 && yCache == 0) {
            field.setScore(output);
          } else {
            field.addTile(xCache, yCache, (int) output);
          }
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
