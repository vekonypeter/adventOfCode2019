package day_16.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class FftCalculator {

  public static List<Integer> calculate(List<Integer> input, int numberOfPhases) {
    for (int i = 0; i < numberOfPhases; i++) {
      input = calculate(input);
    }
    return input;
  }

  public static List<Integer> calculate(List<Integer> input) {
    List<Integer> output = new ArrayList<>();
    for (int i = 0; i < input.size(); i++) {
      output.add(calculateElement(input, i + 1));
    }
    return output;
  }

  private static Integer calculateElement(List<Integer> input, int position) {
    List<Integer> pattern = calculatePattern(position, input.size());
    int result = 0;
    for (int i = 0; i < input.size(); i++) {
      result += input.get(i) * pattern.get(i);
    }
    return Math.abs(result % 10);
  }

  private static List<Integer> calculatePattern(int position, int length) {
    List<Integer> basePattern = getBasePattern();
    List<Integer> result = new ArrayList<>();
    while (result.size() - position < length) {
      result.addAll(
          basePattern.stream()
              .flatMap(patternElement -> Collections.nCopies(position, patternElement).stream())
              .collect(Collectors.toList()));
    }
    return result.subList(1, length + 1);
  }

  private static List<Integer> getBasePattern() {
    return List.of(0, 1, 0, -1);
  }
}
