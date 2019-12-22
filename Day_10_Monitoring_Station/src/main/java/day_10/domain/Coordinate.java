package day_10.domain;

public class Coordinate {

  private int x;
  private int y;

  public Coordinate(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public int getX() {
    return x;
  }

  public void setX(int x) {
    this.x = x;
  }

  public int getY() {
    return y;
  }

  public void setY(int y) {
    this.y = y;
  }

  //
  //  tanAlpha = y / x
  //
  public double getAngleWithOtherCoordinate(Coordinate that) {
    double x = Math.abs(this.x - that.x);
    double y = Math.abs(this.y - that.y);

    double tanAplha = y / x;
    double angle = Math.toDegrees(Math.atan(tanAplha));

    if (that.x < this.x && that.y > this.y) {
      angle += 90;
    } else if (that.x < this.x && that.y < this.y) {
      angle += 180;
    } else if (that.x > this.x && that.y < this.y) {
      angle += 270;
    }

    return angle;
  }

  public double getDistanceFromOtherCoordinate(Coordinate that) {
    double x = Math.abs(this.x - that.x);
    double y = Math.abs(this.y - that.y);
    return Math.sqrt(x * x + y * x);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Coordinate that = (Coordinate) o;

    if (x != that.x) {
      return false;
    }
    return y == that.y;
  }

  @Override
  public int hashCode() {
    int result = x;
    result = 31 * result + y;
    return result;
  }
}
