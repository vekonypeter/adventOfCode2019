package day_15.domain;

import day_15.domain.exception.NoMoreReacheableCoordinateException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// TODO: replace int[] with a Coordinate or Position class
public class SpaceShipMap {

  private RepairDroid repairDroid;
  private int repairDroidPosX;
  private int repairDroidPosY;

  private List<List<Integer>> coordinates;

  public SpaceShipMap(RepairDroid repairDroid) {
    this.repairDroid = repairDroid;
    this.coordinates = new ArrayList<>();
    this.coordinates.add(new ArrayList<>());
    this.coordinates.get(0).add(1);
    this.repairDroidPosX = 0;
    this.repairDroidPosY = 0;
  }

  public Map<Direction, Integer> getObjectsAroundDroid() {
    return getObjectsAroundCoordinate(repairDroidPosX, repairDroidPosY);
  }

  public void update(Direction direction, int droidOutput) {
    int[] targetPos = getTargetPos(direction, repairDroidPosX, repairDroidPosY);
    targetPos = expandMap(targetPos);
    int targetPosX = targetPos[0];
    int targetPosY = targetPos[1];

    boolean isUndiscovered = coordinates.get(targetPosY).get(targetPosX) == null;
    coordinates.get(targetPosY).set(targetPosX, droidOutput);

    if (droidOutput >= 1) {
      repairDroidPosX = targetPosX;
      repairDroidPosY = targetPosY;

      if (isUndiscovered) {
        repairDroid.pushDirection(direction);
      }
    }
    // PART 1.
    //    if (droidOutput == 2) {
    //      throw new OxygenSystemReachedException(repairDroid.getPositionHistorySize());
    //    }

    // PART 2.
    if (!hasUndiscoveredReachableCoordinate()) {
      throw new NoMoreReacheableCoordinateException();
    }
  }

  public Direction fallback() {
    if (!hasUndiscoveredReachableCoordinate()) {
      throw new NoMoreReacheableCoordinateException();
    }

    Direction fallbackDirection = repairDroid.popDirection();
    return fallbackDirection.getOpposite();
  }

  public int fillWithOxygen() {
    startOxygenFilling();
    System.out.println(this.toString());
    int steps = 0;

    while (runOxygenFillingStep() > 0) {
      System.out.println(this.toString());
      steps++;
    }
    return steps;
  }

  private Map<Direction, Integer> getObjectsAroundCoordinate(int x, int y) {
    Map<Direction, Integer> objects = new EnumMap<>(Direction.class);
    objects.put(Direction.NORTH, getAtPosition(getTargetPos(Direction.NORTH, x, y)));
    objects.put(Direction.EAST, getAtPosition(getTargetPos(Direction.EAST, x, y)));
    objects.put(Direction.SOUTH, getAtPosition(getTargetPos(Direction.SOUTH, x, y)));
    objects.put(Direction.WEST, getAtPosition(getTargetPos(Direction.WEST, x, y)));
    return objects;
  }

  private int[] getTargetPos(Direction direction, int x, int y) {
    switch (direction) {
      case NORTH:
        return new int[] {x, y - 1};
      case SOUTH:
        return new int[] {x, y + 1};
      case WEST:
        return new int[] {x - 1, y};
      case EAST:
        return new int[] {x + 1, y};
      default:
        return new int[] {x, y};
    }
  }

  private int[] expandMap(int[] targetPos) {
    int targetPosX = targetPos[0];
    int targetPosY = targetPos[1];

    if (targetPosX == -1) {
      expandToWest();
      return new int[] {0, targetPosY};
    }
    if (targetPosX == coordinates.get(0).size()) {
      expandToEast();
      return new int[] {targetPosX, targetPosY};
    }
    if (targetPosY == -1) {
      expandToNorth();
      return new int[] {targetPosX, 0};
    }
    if (targetPosY == coordinates.size()) {
      expandToSouth();
      return new int[] {targetPosX, targetPosY};
    }
    return new int[] {targetPosX, targetPosY};
  }

  private void expandToNorth() {
    coordinates.add(0, new ArrayList<>(Collections.nCopies(coordinates.get(0).size(), null)));
    repairDroidPosY++;
  }

  private void expandToSouth() {
    coordinates.add(new ArrayList<>(Collections.nCopies(coordinates.get(0).size(), null)));
  }

  private void expandToWest() {
    coordinates.forEach(line -> line.add(0, null));
    repairDroidPosX++;
  }

  private void expandToEast() {
    coordinates.forEach(line -> line.add(null));
  }

  private boolean hasUndiscoveredReachableCoordinate() {
    for (int y = 0; y < coordinates.size(); y++) {
      for (int x = 0; x < coordinates.get(y).size(); x++) {
        var valueAtPos = getAtPosition(x, y);
        if (valueAtPos != null
            && valueAtPos >= 1
            && getObjectsAroundCoordinate(x, y).containsValue(null)) {
          return true;
        }
      }
    }
    return false;
  }

  private Integer getAtPosition(int[] coords) {
    return getAtPosition(coords[0], coords[1]);
  }

  private Integer getAtPosition(int x, int y) {
    if (0 <= x && x < getWidth() && 0 <= y && y < getHeight()) {
      return coordinates.get(y).get(x);
    }
    return null;
  }

  private int getWidth() {
    return coordinates.get(0).size();
  }

  private int getHeight() {
    return coordinates.size();
  }

  private void startOxygenFilling() {
    int x = 0;
    int y = 0;
    while (y < getHeight() && !Integer.valueOf(2).equals(coordinates.get(y).get(x))) {
      x++;
      if (x == getWidth()) {
        x = 0;
        y++;
      }
    }
    coordinates.get(y).set(x, 10);
  }

  private int runOxygenFillingStep() {
    List<int[]> coordinatesToFill = getCorridorCoordinatesWithoutOxygen();

    var coordinatesCanBeFilledNow =
        coordinatesToFill.stream()
            .filter(this::hasNeighbourWithOxygen)
            .collect(Collectors.toList());
    coordinatesCanBeFilledNow.forEach(coords -> coordinates.get(coords[1]).set(coords[0], 10));
    return coordinatesCanBeFilledNow.size();
  }

  private List<int[]> getCorridorCoordinatesWithoutOxygen() {
    List<int[]> result = new ArrayList<>();
    for (int y = 0; y < coordinates.size(); y++) {
      for (int x = 0; x < coordinates.get(y).size(); x++) {
        Integer value = coordinates.get(y).get(x);
        if (value != null && value.equals(1)) {
          result.add(new int[] {x, y});
        }
      }
    }
    return result;
  }

  private boolean hasNeighbourWithOxygen(int[] coords) {
    return getObjectsAroundCoordinate(coords[0], coords[1]).containsValue(10);
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder("\nSPACESHIPMAP >>>>>>>>>>>>>>>>>>>>>>>>\n\n");
    for (int y = 0; y < coordinates.size(); y++) {
      for (int x = 0; x < coordinates.get(y).size(); x++) {
        if (x == repairDroidPosX && y == repairDroidPosY) {
          builder.append("D");
        } else {
          var cord = coordinates.get(y).get(x);
          if (cord == null) {
            builder.append(".");
          } else {
            switch (cord) {
              case 0:
                builder.append("\u2588");
                break;
              case 1:
                builder.append(" ");
                break;
              case 2:
                builder.append("+");
                break;
              case 10:
                builder.append("O");
                break;
              default:
                builder.append("?");
            }
          }
        }
      }
      builder.append("\n");
    }
    builder.append("\n<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<\n");
    return builder.toString();
  }
}
