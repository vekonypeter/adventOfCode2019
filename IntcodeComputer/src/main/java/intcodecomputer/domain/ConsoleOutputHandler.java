package intcodecomputer.domain;

public class ConsoleOutputHandler implements OutputHandler{

  @Override
  public void handleOutput(long output) {
    System.out.println("OUTPUT GENERATED: " + output);
  }
}
