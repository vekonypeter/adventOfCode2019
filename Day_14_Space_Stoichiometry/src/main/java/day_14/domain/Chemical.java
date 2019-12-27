package day_14.domain;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Chemical {

  private String id;
  private Map<String, Integer> recipe;
  private int outputQuantity;

  public Chemical(String id, Map<String, Integer> recipe, int outputQuantity) {
    this.id = id;
    this.recipe = recipe;
    this.outputQuantity = outputQuantity;
  }

  public String getId() {
    return id;
  }

  public Map<String, Integer> getRecipe() {
    return recipe;
  }

  public int getOutputQuantity() {
    return outputQuantity;
  }

  public Map<String, Integer> getInputsForReaction(
      int quantityNeeded, Map<String, Chemical> chemicals) {
    Map<String, Integer> inputs = new HashMap<>();
    int multiplier = quantityNeeded / outputQuantity + (quantityNeeded != outputQuantity ? 1 : 0);

    recipe.forEach(
        (id, quantity) -> {
          inputs.compute(
              id,
              (key, value) -> {
                if (value == null) {
                  value = 0;
                }
                return value + multiplier * quantity;
              });
          if (chemicals.containsKey(id)) {
            Map<String, Integer> inputsForInput =
                chemicals.get(id).getInputsForReaction(inputs.get(id), chemicals);
            inputsForInput.forEach((key, value) -> inputs.merge(key, value, Integer::sum));
          }
        });

    return inputs;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Chemical chemical = (Chemical) o;

    return Objects.equals(id, chemical.id);
  }

  @Override
  public int hashCode() {
    return id != null ? id.hashCode() : 0;
  }

  public static Chemical parse(String reactionString) {
    var matches =
        Pattern.compile("(\\d+)\\s(\\w+),?")
            .matcher(reactionString)
            .results()
            .collect(Collectors.toList());
    var outputMatch = matches.get(matches.size() - 1);
    var inputMatches = matches.subList(0, matches.size() - 1);
    var recipe =
        inputMatches.stream()
            .collect(
                Collectors.toMap(
                    match -> match.group(2), match -> Integer.parseInt(match.group(1))));

    return new Chemical(outputMatch.group(2), recipe, Integer.parseInt(outputMatch.group(1)));
  }
}
