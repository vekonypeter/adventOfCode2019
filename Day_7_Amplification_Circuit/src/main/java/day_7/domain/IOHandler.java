package day_7.domain;

public class IOHandler {

  public static int numberOfInputCalls = 0;
  public static int nextInput = -1;
  public static int lastOutput = -1;

  public static Integer getInput() {
    System.out.println("INPUT PROVIDED: " + nextInput);
    numberOfInputCalls++;
    return nextInput;
  }

  public static void produceOutput(Integer output) {
    System.out.println("OUPTUT PRODUCED: " + output);
    lastOutput = output;
  }
}
