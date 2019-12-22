package day_11.domain;

public enum RobotDirection {
  UP("^", 0),
  RIGHT(">", 1),
  DOWN("v", 2),
  LEFT("<", 3);

  private String symbol;
  private int value;

  RobotDirection(String symbol, int value) {
    this.symbol = symbol;
    this.value = value;
  }

  @Override
  public String toString() {
    return symbol;
  }

  public static RobotDirection getNext(RobotDirection currentDirection, int move) {
    if (move == 0) {
      move = -1;
    }
    int newDirectionValue = currentDirection.value + move;
    switch (newDirectionValue) {
      case 0:
      case 4:
        return RobotDirection.UP;
      case 1:
        return RobotDirection.RIGHT;
      case 2:
        return RobotDirection.DOWN;
      case 3:
      case -1:
        return RobotDirection.LEFT;
      default:
        throw new RuntimeException(
            String.format("Cannot get next RobotDirection from value [%s]", newDirectionValue));
    }
  }
}
