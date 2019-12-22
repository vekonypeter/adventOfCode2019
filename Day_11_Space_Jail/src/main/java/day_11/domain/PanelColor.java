package day_11.domain;

public enum PanelColor {
  BLACK(0, "."),
  WHITE(1, "#"),
  NONE(2, "-");

  private int value;
  private String symbol;

  PanelColor(int value, String symbol) {
    this.value = value;
    this.symbol = symbol;
  }

  public int getValue() {
    return value;
  }

  public String getSymbol() {
    return symbol;
  }

  @Override
  public String toString() {
    return symbol;
  }
}
