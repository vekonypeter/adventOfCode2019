package day_16.domain;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class FftCalculatorTest {

  @Test
  public void calculateOnePhaseForShortInput() {
    List<Integer> input = List.of(1, 2, 3, 4, 5, 6, 7, 8);
    List<Integer> output = FftCalculator.calculate(input, 1);
    assertThat(output).containsExactly(4, 8, 2, 2, 6, 1, 5, 8);
  }

  @Test
  public void calculateOTwoPhasesForShortInput() {
    List<Integer> input = List.of(1, 2, 3, 4, 5, 6, 7, 8);
    List<Integer> output = FftCalculator.calculate(input, 2);
    assertThat(output).containsExactly(3, 4, 0, 4, 0, 4, 3, 8);
  }

  @Test
  public void calculateThreePhasesForShortInput() {
    List<Integer> input = List.of(1, 2, 3, 4, 5, 6, 7, 8);
    List<Integer> output = FftCalculator.calculate(input, 3);
    assertThat(output).containsExactly(0, 3, 4, 1, 5, 5, 1, 8);
  }

  @Test
  public void calculateFourPhasesForShortInput() {
    List<Integer> input = List.of(1, 2, 3, 4, 5, 6, 7, 8);
    List<Integer> output = FftCalculator.calculate(input, 4);
    assertThat(output).containsExactly(0, 1, 0, 2, 9, 4, 9, 8);
  }

  @Test
  public void calculateHundredPhasesForLargerInput_1() {
    List<Integer> input =
        List.of(
            8, 0, 8, 7, 1, 2, 2, 4, 5, 8, 5, 9, 1, 4, 5, 4, 6, 6, 1, 9, 0, 8, 3, 2, 1, 8, 6, 4, 5,
            5, 9, 5);
    List<Integer> output = FftCalculator.calculate(input, 100);
    assertThat(output.subList(0, 8)).containsExactly(2, 4, 1, 7, 6, 1, 7, 6);
  }

  @Test
  public void calculateHundredPhasesForLargerInput_2() {
    List<Integer> input =
        List.of(
            1, 9, 6, 1, 7, 8, 0, 4, 2, 0, 7, 2, 0, 2, 2, 0, 9, 1, 4, 4, 9, 1, 6, 0, 4, 4, 1, 8, 9,
            9, 1, 7);
    List<Integer> output = FftCalculator.calculate(input, 100);
    assertThat(output.subList(0, 8)).containsExactly(7, 3, 7, 4, 5, 4, 1, 8);
  }

  @Test
  public void calculateHundredPhasesForLargerInput_3() {
    List<Integer> input =
        List.of(
            6, 9, 3, 1, 7, 1, 6, 3, 4, 9, 2, 9, 4, 8, 6, 0, 6, 3, 3, 5, 9, 9, 5, 9, 2, 4, 3, 1, 9,
            8, 7, 3);
    List<Integer> output = FftCalculator.calculate(input, 100);
    assertThat(output.subList(0, 8)).containsExactly(5, 2, 4, 3, 2, 1, 3, 3);
  }

  @Test
  public void getMessage_1() {
    List<Integer> input =
        List.of(
            0, 3, 0, 3, 6, 7, 3, 2, 5, 7, 7, 2, 1, 2, 9, 4, 4, 0, 6, 3, 4, 9, 1, 5, 6, 5, 4, 7, 4,
            6, 6, 4);
    List<Integer> message = FftCalculator.getMessage(input);
    assertThat(message).containsExactly(8, 4, 4, 6, 2, 0, 2, 6);
  }
}
