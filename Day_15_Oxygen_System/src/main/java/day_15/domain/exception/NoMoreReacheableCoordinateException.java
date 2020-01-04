package day_15.domain.exception;

public class NoMoreReacheableCoordinateException extends RuntimeException {

  public NoMoreReacheableCoordinateException() {
    super("NO MORE REACHABLE COORDINATE EXISTS!");
  }
}
