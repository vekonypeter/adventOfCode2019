package day_13.domain;

import java.util.ArrayList;
import java.util.List;

public class Field {

  private List<Tile> tiles;

  public Field() {
    this.tiles = new ArrayList<>();
  }

  public List<Tile> getTiles() {
    return tiles;
  }

  public void addTile(int x, int y, int type) {
    switch (type) {
      case 0:
        tiles.add(new Tile(x, y));
        break;
      case 1:
        tiles.add(new Wall(x, y));
        break;
      case 2:
        tiles.add(new Block(x, y));
        break;
      case 3:
        tiles.add(new Paddle(x, y));
        break;
      case 4:
        tiles.add(new Ball(x, y));
        break;
      default:
        throw new RuntimeException(String.format("Unexpected tile type: %s", type));
    }
  }
}
