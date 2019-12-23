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
}
