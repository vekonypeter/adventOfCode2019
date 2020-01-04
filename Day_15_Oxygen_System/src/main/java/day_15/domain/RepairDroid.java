package day_15.domain;

import intcodecomputer.domain.InputProvider;
import intcodecomputer.domain.IntcodeComputer;
import intcodecomputer.domain.OutputHandler;
import java.util.ArrayList;
import java.util.List;

public class RepairDroid {

  private IntcodeComputer computer;
  private List<Direction> previousPositions;

  public void init(
      String intcodeProgram, InputProvider inputProvider, OutputHandler outputHandler) {
    computer = new IntcodeComputer(inputProvider, outputHandler);
    computer.init(intcodeProgram);
    previousPositions = new ArrayList<>();
  }

  public void run() {
    computer.run();
  }

  public void pushDirection(Direction direction) {
    previousPositions.add(direction);
  }

  public Direction popDirection() {
    return previousPositions.remove(previousPositions.size() - 1);
  }

  public int getPositionHistorySize() {
    return previousPositions.size();
  }
}
