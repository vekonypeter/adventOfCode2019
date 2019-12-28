package day_14;

import day_14.domain.Reaction;
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

    Map<String, Reaction> reactions =
        input.stream()
            .map(Reaction::parse)
            .collect(Collectors.toMap(Reaction::getOutput, Function.identity()));

    Map<String, Integer> inputs =
        reactions.keySet().stream().collect(Collectors.toMap(Function.identity(), chemicalId -> 0));
    inputs.put("ORE", 0);
    Map<String, Integer> leftOvers =
        reactions.keySet().stream().collect(Collectors.toMap(Function.identity(), chemicalId -> 0));

    Map<Integer, List<Reaction>> chain = new HashMap<>();
    reactions.get("FUEL").getReactionChain(reactions, chain, 0);

    System.out.println("ORE NEEDED FOR 1 FUEL: " + inputs.get("ORE"));
  }
}
