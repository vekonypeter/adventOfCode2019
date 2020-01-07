package day_16.domain;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class FftCalculatorTest {

  @Test
  public void calculateOnePhaseForShortInput() {
    double[] input = new double[] {1, 2, 3, 4, 5, 6, 7, 8};
    double[] output = FftCalculator.calculate(input, 1);
    assertThat(output).containsExactly(4, 8, 2, 2, 6, 1, 5, 8);
  }

  @Test
  public void calculateOTwoPhasesForShortInput() {
    double[] input = new double[] {1, 2, 3, 4, 5, 6, 7, 8};
    double[] output = FftCalculator.calculate(input, 2);
    assertThat(output).containsExactly(3, 4, 0, 4, 0, 4, 3, 8);
  }

  @Test
  public void calculateThreePhasesForShortInput() {
    double[] input = new double[] {1, 2, 3, 4, 5, 6, 7, 8};
    double[] output = FftCalculator.calculate(input, 3);
    assertThat(output).containsExactly(0, 3, 4, 1, 5, 5, 1, 8);
  }

  @Test
  public void calculateFourPhasesForShortInput() {
    double[] input = new double[] {1, 2, 3, 4, 5, 6, 7, 8};
    double[] output = FftCalculator.calculate(input, 4);
    assertThat(output).containsExactly(0, 1, 0, 2, 9, 4, 9, 8);
  }

  @Test
  public void calculateHundredPhasesForLargerInput_1() {
    double[] input =
        new double[] {
          8, 0, 8, 7, 1, 2, 2, 4, 5, 8, 5, 9, 1, 4, 5, 4, 6, 6, 1, 9, 0, 8, 3, 2, 1, 8, 6, 4, 5, 5,
          9, 5
        };
    double[] output = FftCalculator.calculate(input, 100);
    assertThat(Arrays.copyOfRange(output, 0, 8)).containsExactly(2, 4, 1, 7, 6, 1, 7, 6);
  }

  @Test
  public void calculateHundredPhasesForLargerInput_2() {
    double[] input =
        new double[] {
          1, 9, 6, 1, 7, 8, 0, 4, 2, 0, 7, 2, 0, 2, 2, 0, 9, 1, 4, 4, 9, 1, 6, 0, 4, 4, 1, 8, 9, 9,
          1, 7
        };
    double[] output = FftCalculator.calculate(input, 100);
    assertThat(Arrays.copyOfRange(output, 0, 8)).containsExactly(7, 3, 7, 4, 5, 4, 1, 8);
  }

  @Test
  public void calculateHundredPhasesForLargerInput_3() {
    double[] input =
        new double[] {
          6, 9, 3, 1, 7, 1, 6, 3, 4, 9, 2, 9, 4, 8, 6, 0, 6, 3, 3, 5, 9, 9, 5, 9, 2, 4, 3, 1, 9, 8,
          7, 3
        };
    double[] output = FftCalculator.calculate(input, 100);
    assertThat(Arrays.copyOfRange(output, 0, 8)).containsExactly(5, 2, 4, 3, 2, 1, 3, 3);
  }

  @Test
  public void calculateMessage_1() {
    double[] input =
        Arrays.stream(
                "59738571488265718089358904960114455280973585922664604231570733151978336391124265667937788506879073944958411270241510791284757734034790319100185375919394328222644897570527214451044757312242600574353568346245764353769293536616467729923693209336874623429206418395498129094105619169880166958902855461622600841062466017030859476352821921910265996487329020467621714808665711053916709619048510429655689461607438170767108694118419011350540476627272614676919542728299869247813713586665464823624393342098676116916475052995741277706794475619032833146441996338192744444491539626122725710939892200153464936225009531836069741189390642278774113797883240104687033645"
                    .split(""))
            .mapToDouble(Double::parseDouble)
            .toArray();
    double[] output = FftCalculator.calculate(input, 1, 10000);
  }
}
