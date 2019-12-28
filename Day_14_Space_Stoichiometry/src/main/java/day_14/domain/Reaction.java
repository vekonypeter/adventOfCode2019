package day_14.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Reaction {

  private Map<String, Integer> inputs;
  private String output;
  private int outputQuantity;

  public Reaction(Map<String, Integer> inputs, String output, int outputQuantity) {
    this.inputs = inputs;
    this.output = output;
    this.outputQuantity = outputQuantity;
  }

  public String getOutput() {
    return output;
  }

  public void getIngredients

  public void run(
      int neededQuantity,
      Map<String, Reaction> reactionMap,
      Map<String, Integer> inputMap,
      Map<String, Integer> leftOverMap) {

    // 1.) CHECK IF THERE IS ANY LEFTOVER IN THE MAP TO SKIP RUNNING THE REACTION
    int fromLeftOver = getFromLeftOvers(neededQuantity, leftOverMap);
    // 1.1.) If any, move amounts to the inputs from leftovers
    addToMap(output, fromLeftOver, inputMap);

    // 2.) DETERMINE THE NEEDED QUANTITY TO PRODUCE (IF ANY)
    int toProduce = neededQuantity - fromLeftOver;
    if (toProduce <= 0) {
      return;
    }

    // 3.) PRODUCE THE NEEDED QUANTITY
    // 3.1.) Determine the output quantity
    int multiplier = toProduce / outputQuantity + (toProduce != outputQuantity ? 1 : 0);

    // 3.2.) Run the reaction
    for (var input : inputs.entrySet()) {
      Reaction reactionToProduce = reactionMap.get(input.getKey());
      reactionToProduce.run(input.getValue(), reactionMap, inputMap, leftOverMap);
    }

    // 3.3.) Put the output either into the input map or the leftover map
    int quantityProduced = outputQuantity * multiplier;
    int leftOverQuantity = quantityProduced - toProduce;
    addToMap(output, toProduce, inputMap);
    addToMap(output, leftOverQuantity, leftOverMap);
  }

  private int getFromLeftOvers(int neededQuantity, Map<String, Integer> leftOverMap) {
    int leftOverQuantity = leftOverMap.get(output);
    int usedFromLeftOver;
    int remainingLeftOver;
    if (leftOverQuantity >= neededQuantity) {
      usedFromLeftOver = neededQuantity;
      remainingLeftOver = leftOverQuantity - neededQuantity;
    } else {
      usedFromLeftOver = leftOverQuantity;
      remainingLeftOver = 0;
    }
    leftOverMap.compute(output, (key, currentValue) -> remainingLeftOver);
    return usedFromLeftOver;
  }

  private void addToMap(String chemicalId, Integer quantity, Map<String, Integer> map) {
    map.compute(chemicalId, (key, currentValue) -> currentValue + quantity);
  }

  public static Reaction parse(String reactionString) {
    var matches =
        Pattern.compile("(\\d+)\\s(\\w+),?")
            .matcher(reactionString)
            .results()
            .collect(Collectors.toList());
    var outputMatch = matches.get(matches.size() - 1);
    var inputs =
        matches.subList(0, matches.size() - 1).stream()
            .collect(
                Collectors.toMap(
                    match -> match.group(2), match -> Integer.parseInt(match.group(1))));

    return new Reaction(inputs, outputMatch.group(2), Integer.parseInt(outputMatch.group(1)));
  }
}
