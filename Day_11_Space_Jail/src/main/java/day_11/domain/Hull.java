package day_11.domain;

import intcodecomputer.domain.ConsoleOutputHandler;
import intcodecomputer.domain.InputProvider;
import intcodecomputer.domain.IntcodeComputer;
import java.util.ArrayList;
import java.util.List;

public class Hull {

  public static final int DIM = 100;

  private List<List<HullPanel>> panels;
  private Robot robot;

  public Hull(String robotMemoryString) {
    init(robotMemoryString);
  }

  public List<List<HullPanel>> getPanels() {
    return panels;
  }

  public Robot getRobot() {
    return robot;
  }

  private void init(String robotMemoryString) {
    int robotStartPointCord = DIM / 2;
    IntcodeComputer robotBrain =
        new IntcodeComputer(new RobotInputProvider(this), new RobotOutputHandler(this));
    robotBrain.init(robotMemoryString);

    robot = new Robot(robotBrain, RobotDirection.UP, robotStartPointCord, robotStartPointCord);

    panels = new ArrayList<>();
    for (int y = 0; y < DIM; y++) {
      panels.add(new ArrayList<>());
      for (int x = 0; x < DIM; x++) {
        PanelColor color =
            x == robotStartPointCord && y == robotStartPointCord
                ? PanelColor.WHITE
                : PanelColor.BLACK;
        panels.get(y).add(new HullPanel(color));
      }
    }
  }

  public void paint() {
    this.getRobot().run();
  }

  public HullPanel getPanel(int x, int y) {
    return panels.get(y).get(x);
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder(">>> HULL <<<\n");
    for (int y = DIM - 1; y >= 0; y--) {
      for (int x = DIM - 1; x >= 0; x--) {
        if (x == robot.getX() && y == robot.getY()) {
          builder.append(robot.toString());
        } else {
          builder.append(panels.get(y).get(x).toString());
        }
      }
      builder.append("\n");
    }
    builder.append(">>>>>><<<<<<");
    return builder.toString();
  }

  // TODO: enhance this with function reference input providers / output handlers
  private static class RobotInputProvider implements InputProvider {

    private Hull hull;

    public RobotInputProvider(Hull hull) {
      this.hull = hull;
    }

    @Override
    public long getInput() {
      return hull.getPanel(hull.getRobot().getX(), hull.getRobot().getY()).getColor().getValue();
    }
  }

  private static class RobotOutputHandler extends ConsoleOutputHandler {

    private Hull hull;
    private int outputCount;

    public RobotOutputHandler(Hull hull) {
      this.hull = hull;
      this.outputCount = 0;
    }

    @Override
    public void handleOutput(long output) {
      super.handleOutput(output);

      if (outputCount % 2 == 0) {
        PanelColor color;
        switch ((int) output) {
          case 0:
            color = PanelColor.BLACK;
            break;
          case 1:
            color = PanelColor.WHITE;
            break;
          default:
            throw new RuntimeException(
                String.format("Unkown output for coloring panel: [%]", output));
        }
        hull.getPanel(hull.getRobot().getX(), hull.getRobot().getY()).changeColor(color);
      } else {
        hull.getRobot().turn(output);
      }

      this.outputCount += 1;
    }
  }
}
