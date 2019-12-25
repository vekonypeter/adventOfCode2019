package day_13.domain;

import java.util.Arrays;

public class Field {

  private static final int WIDTH = 42;
  private static final int HEIGHT = 24;

  private Tile[][] tiles;
  private long score;

  public Field() {
    this.tiles = new Tile[HEIGHT][WIDTH];
    this.score = 0;
  }

  public Tile[][] getTiles() {
    return tiles;
  }

  public long getScore() {
    return score;
  }

  public void setScore(long score) {
    this.score = score;
  }

  public Paddle getPaddle() {
    return Arrays.stream(this.tiles)
        .flatMap(Arrays::stream)
        .filter(tile -> tile instanceof Paddle)
        .findAny()
        .map(tile -> (Paddle) tile)
        .get();
  }

  public Ball getBall() {
    return Arrays.stream(this.tiles)
        .flatMap(Arrays::stream)
        .filter(tile -> tile instanceof Ball)
        .findAny()
        .map(tile -> (Ball) tile)
        .get();
  }

  public void addTile(int x, int y, int type) {
    switch (type) {
      case 0:
        tiles[y][x] = new Tile(x, y);
        break;
      case 1:
        tiles[y][x] = new Wall(x, y);
        break;
      case 2:
        tiles[y][x] = new Block(x, y);
        break;
      case 3:
        tiles[y][x] = new Paddle(x, y);
        break;
      case 4:
        tiles[y][x] = new Ball(x, y);
        break;
      default:
        throw new RuntimeException(String.format("Unexpected tile type: %s", type));
    }
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder("SCORE: " + this.score + "\n");
    for (int y = 0; y < HEIGHT; y++) {
      for (int x = 0; x < WIDTH; x++) {
        builder.append(tiles[y][x]);
      }
      builder.append("\n");
    }
    return builder.toString();
  }
}
