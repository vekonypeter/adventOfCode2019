package day_11.domain;

import static day_11.domain.RobotDirection.getNext;

import intcodecomputer.domain.IntcodeComputer;

public class Robot {

  private RobotDirection direction;
  private int x;
  private int y;
  private IntcodeComputer brain;

  public Robot(IntcodeComputer brain, RobotDirection direction, int x, int y) {
    this.direction = direction;
    this.x = x;
    this.y = y;
    this.brain = brain;
  }

  public RobotDirection getDirection() {
    return direction;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public void run() {
    this.brain.run();
  }

  public void turn(long output) {
    RobotDirection newDirection = getNext(this.direction, (int) output);
    this.direction = newDirection;
    switch (newDirection) {
      case UP:
        this.y += 1;
        break;
      case RIGHT:
        this.x -= 1;
        break;
      case DOWN:
        this.y -= 1;
        break;
      case LEFT:
        this.x += 1;
        break;
    }
  }

  @Override
  public String toString() {
    return this.direction.toString();
  }
}
