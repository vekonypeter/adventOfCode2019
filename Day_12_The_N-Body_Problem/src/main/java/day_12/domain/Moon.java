package day_12.domain;

public class Moon {

  private String name;

  private int xPos;
  private int yPos;
  private int zPos;
  private int xVel;
  private int yVel;
  private int zVel;

  public Moon(String name, int xPos, int yPos, int zPos) {
    this.name = name;
    this.xPos = xPos;
    this.yPos = yPos;
    this.zPos = zPos;
    this.xVel = 0;
    this.yVel = 0;
    this.zVel = 0;
  }

  public String getName() {
    return name;
  }

  public void calculateVelocity(Moon that) {
    if (this.xPos < that.xPos) {
      this.xVel++;
      that.xVel--;
    } else if (this.xPos > that.xPos) {
      this.xVel--;
      that.xVel++;
    }
    if (this.yPos < that.yPos) {
      this.yVel++;
      that.yVel--;
    } else if (this.yPos > that.yPos) {
      this.yVel--;
      that.yVel++;
    }
    if (this.zPos < that.zPos) {
      this.zVel++;
      that.zVel--;
    } else if (this.zPos > that.zPos) {
      this.zVel--;
      that.zVel++;
    }
  }

  public void applyVelocity() {
    xPos += xVel;
    yPos += yVel;
    zPos += zVel;
  }

  public int calculatePotentialEnergy() {
    return Math.abs(this.xPos) + Math.abs(this.yPos) + Math.abs(this.zPos);
  }

  public int calculateKineticEnergy() {
    return Math.abs(this.xVel) + Math.abs(this.yVel) + Math.abs(this.zVel);
  }

  public int getTotalEnergy() {
    return calculatePotentialEnergy() * calculateKineticEnergy();
  }

  @Override
  public String toString() {
    return "Moon{" + "name='" + name + '\'' + ", xPos=" + xPos + ", yPos=" + yPos + ", zPos=" + zPos
        + ", xVel=" + xVel + ", yVel=" + yVel + ", zVel=" + zVel + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Moon moon = (Moon) o;

    if (xPos != moon.xPos) {
      return false;
    }
    if (yPos != moon.yPos) {
      return false;
    }
    if (zPos != moon.zPos) {
      return false;
    }
    if (xVel != moon.xVel) {
      return false;
    }
    if (yVel != moon.yVel) {
      return false;
    }
    if (zVel != moon.zVel) {
      return false;
    }
    return name.equals(moon.name);
  }

  @Override
  public int hashCode() {
    int result = name.hashCode();
    result = 31 * result + xPos;
    result = 31 * result + yPos;
    result = 31 * result + zPos;
    result = 31 * result + xVel;
    result = 31 * result + yVel;
    result = 31 * result + zVel;
    return result;
  }
}
