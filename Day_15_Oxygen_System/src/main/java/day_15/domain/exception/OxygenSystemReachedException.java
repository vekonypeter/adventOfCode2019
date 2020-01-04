package day_15.domain.exception;

public class OxygenSystemReachedException extends RuntimeException {

  public OxygenSystemReachedException(int numberOfSteps) {
    super(String.format("OXYGEN SYSTEM FOUND! Number of steps needed: %s", numberOfSteps));
  }
}
