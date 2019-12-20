package day_10.domain;

import java.util.Objects;

public class Asteroid {

  private Coordinate coordinate;

  public Asteroid(Coordinate coordinate) {
    this.coordinate = coordinate;
  }

  public Coordinate getCoordinate() {
    return coordinate;
  }

  public void setCoordinate(Coordinate coordinate) {
    this.coordinate = coordinate;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Asteroid asteroid = (Asteroid) o;

    return Objects.equals(this, asteroid);
  }

  @Override
  public int hashCode() {
    return coordinate != null ? coordinate.hashCode() : 0;
  }
}
