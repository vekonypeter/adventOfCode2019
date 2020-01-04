package day_15;

import day_15.domain.Direction;
import day_15.domain.RepairDroid;
import day_15.domain.SpaceShipMap;
import day_15.domain.exception.NoMoreReacheableCoordinateException;
import intcodecomputer.domain.ConsoleInputProvider;
import intcodecomputer.domain.InputProvider;
import intcodecomputer.domain.OutputHandler;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Objects;

public class Main {

  public static void main(String[] args) throws URISyntaxException, IOException {
    final String fileName = "day_15_input";
    String input =
        Files.readString(
            Paths.get(Main.class.getResource("/" + fileName + ".txt").toURI()),
            StandardCharsets.UTF_8);

    RepairDroid repairDroid = new RepairDroid();
    SpaceShipMap spaceShipMap = new SpaceShipMap(repairDroid);

    // ONLY FOR MANUAL TESTING
    // RepairDroidInputProvider inputProvider = new ManualRepairDroidInputProvider(spaceShipMap);

    RepairDroidInputProvider inputProvider = new AutomaticRepairDroidInputProvider(spaceShipMap);
    RepairDroidOutputHandler outputHandler =
        new RepairDroidOutputHandler(spaceShipMap, inputProvider);

    repairDroid.init(input, inputProvider, outputHandler);

    try {
      repairDroid.run();
    }
    // PART 1.
    //    catch (OxygenSystemReachedException e) {
    //      System.out.println(e.getMessage());
    //    }
    // PART 2.
    catch (NoMoreReacheableCoordinateException e) {
      System.out.println(e.getMessage());
    }

    int timeElapsed = spaceShipMap.fillWithOxygen();
    System.out.println(
        String.format("IT TOOK %s MINUTES TO FILL THE WHOLE SHIP WITH OXYGEN", timeElapsed));
  }

  private interface RepairDroidInputProvider extends InputProvider {
    Direction getLastDirection();
  }

  // ONLY FOR MANUAL TESTING
  private static class ManualRepairDroidInputProvider extends ConsoleInputProvider
      implements RepairDroidInputProvider {

    private SpaceShipMap spaceShipMap;
    private Direction lastDirection;

    public ManualRepairDroidInputProvider(SpaceShipMap spaceShipMap) {
      this.spaceShipMap = spaceShipMap;
    }

    @Override
    public Direction getLastDirection() {
      return lastDirection;
    }

    @Override
    public long getInput() {
      System.out.println(spaceShipMap.toString());
      long input = super.getInput();
      switch ((int) input) {
        case 1:
          lastDirection = Direction.NORTH;
          break;
        case 2:
          lastDirection = Direction.SOUTH;
          break;
        case 3:
          lastDirection = Direction.WEST;
          break;
        case 4:
          lastDirection = Direction.EAST;
          break;
      }
      return input;
    }
  }

  private static class AutomaticRepairDroidInputProvider implements RepairDroidInputProvider {

    private SpaceShipMap spaceShipMap;
    private Direction lastDirection;

    public AutomaticRepairDroidInputProvider(SpaceShipMap spaceShipMap) {
      this.spaceShipMap = spaceShipMap;
    }

    @Override
    public Direction getLastDirection() {
      return lastDirection;
    }

    @Override
    public long getInput() {
      System.out.println(spaceShipMap.toString());

      Direction undiscoveredDirection = getFirstUndiscoveredDirection();

      if (undiscoveredDirection != null) {
        // GO TO NEW DIRECTION!
        lastDirection = undiscoveredDirection;
      } else {
        // DEAD-END REACHED, FALL BACK!
        lastDirection = spaceShipMap.fallback();
      }
      return lastDirection.getCode();
    }

    private Direction getFirstUndiscoveredDirection() {
      return spaceShipMap.getObjectsAroundDroid().entrySet().stream()
          .filter(entry -> Objects.isNull(entry.getValue()))
          .findFirst()
          .map(Map.Entry::getKey)
          .orElse(null);
    }
  }

  private static class RepairDroidOutputHandler implements OutputHandler {

    private SpaceShipMap spaceShipMap;
    private RepairDroidInputProvider inputProvider;

    public RepairDroidOutputHandler(
        SpaceShipMap spaceShipMap, RepairDroidInputProvider inputProvider) {
      this.spaceShipMap = spaceShipMap;
      this.inputProvider = inputProvider;
    }

    @Override
    public void handleOutput(long output) {
      spaceShipMap.update(inputProvider.getLastDirection(), (int) output);
    }
  }
}
