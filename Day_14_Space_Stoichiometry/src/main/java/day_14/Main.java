package day_14;

import day_14.domain.Chemical;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Main {

  public static void main(String[] args) throws URISyntaxException, IOException {
    final String fileName = "day_14_1_example_1";
    List<String> input =
        Files.readAllLines(
            Paths.get(Main.class.getResource("/" + fileName + ".txt").toURI()),
            StandardCharsets.UTF_8);

    Map<String, Chemical> chemicals =
        input.stream()
            .map(Chemical::parse)
            .collect(Collectors.toMap(Chemical::getId, Function.identity()));

    Map<String, Integer> recipe =
        chemicals.get("FUEL").getInputsForReaction(1, chemicals);

    System.out.println("ORE NEEDED FOR 1 FUEL: " + recipe.get("ORE"));
  }
}
