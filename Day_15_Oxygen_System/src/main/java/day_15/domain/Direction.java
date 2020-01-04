package day_15.domain;

public enum Direction {
  NORTH(1),
  EAST(4),
  SOUTH(2),
  WEST(3);

  private int code;
  private Direction opposite;

  public int getCode() {
    return code;
  }

  public Direction getOpposite() {
    switch (this) {
      case NORTH:
        return SOUTH;
      case EAST:
        return WEST;
      case SOUTH:
        return NORTH;
      case WEST:
        return EAST;
    }
    return null;
  }

  Direction(int code) {
    this.code = code;
  }
}
