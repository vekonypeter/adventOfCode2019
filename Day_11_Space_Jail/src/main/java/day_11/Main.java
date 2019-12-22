package day_11;

import day_11.domain.Hull;
import day_11.domain.HullPanel;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Main {

  public static void main(String[] args) throws URISyntaxException, IOException {
    String input =
        Files.readString(
            Paths.get(Main.class.getResource("/day_11_input.txt").toURI()), StandardCharsets.UTF_8);

    Hull hull = new Hull(input);
    hull.paint();

    System.out.println(hull.toString());

    long numberOfChangedPanels =
        hull.getPanels().stream().flatMap(List::stream).filter(HullPanel::isColorChanged).count();

    System.out.println("NUMBER OF PANELS THAT CHANGED COLOR: " + numberOfChangedPanels);
  }
}
