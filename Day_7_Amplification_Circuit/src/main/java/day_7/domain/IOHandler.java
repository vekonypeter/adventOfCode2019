package day_7.domain;

public class IOHandler {

  public int numberOfInputCalls = 0;
  public int nextInput = -1;
  public int lastOutput = -1;

  public Integer getInput() {
    System.out.println("INPUT SUPPLIED:  " + nextInput);
    numberOfInputCalls++;
    return nextInput;
  }

  public void produceOutput(Integer output) {
    System.out.println("OUTPUT PRODUCED: " + output);
    lastOutput = output;
  }
}
