package day_11.domain;

public class HullPanel {

  private PanelColor color;
  private boolean colorChanged;

  public HullPanel(PanelColor color) {
    this.color = color;
  }

  public PanelColor getColor() {
    return color;
  }

  public boolean isColorChanged() {
    return colorChanged;
  }

  public void changeColor(PanelColor color) {
    this.color = color;
    colorChanged = true;
  }

  @Override
  public String toString() {
    return color.toString();
  }
}
