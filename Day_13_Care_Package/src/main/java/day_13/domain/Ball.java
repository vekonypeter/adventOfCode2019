package day_13.domain;

public class Ball extends Tile {

  public Ball(int x, int y) {
    super(x, y);
  }

  @Override
  public String toString() {
    return "O";
  }
}
