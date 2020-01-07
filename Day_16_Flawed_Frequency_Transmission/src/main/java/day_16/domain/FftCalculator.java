package day_16.domain;

import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

import java.util.Arrays;

import static org.nd4j.linalg.ops.transforms.Transforms.abs;

public class FftCalculator {

  public static double[] calculate(double[] input, int numberOfPhases) {
    return calculate(input, numberOfPhases, 1);
  }

  public static double[] calculate(double[] input, int numberOfPhases, int inputRepetitionNumber) {
    INDArray inputMatrix = repeatItemsNTimes(input, inputRepetitionNumber);
    INDArray patternMatrix = calculatePatternMatrix(inputMatrix.columns());

    for (int i = 0; i < numberOfPhases; i++) {
      System.out.println("Phase " + i);
      inputMatrix = calculate(inputMatrix, patternMatrix);
    }
    return inputMatrix.toDoubleVector();
  }

  private static INDArray calculate(INDArray inputMatrix, INDArray patternMatrix) {
    INDArray output = patternMatrix.mmul(inputMatrix);
    return abs(output.fmod(10));
  }

  private static INDArray calculatePatternMatrix(int dim) {
    double[][] result = new double[dim][dim];
    for (int i = 0; i < dim; i++) {
      result[i] = calculatePatternMatrixRow(i + 1, dim);
    }
    return Nd4j.create(result);
  }

  private static double[] calculatePatternMatrixRow(int rowNum, int length) {
    int[] basePattern = getBasePattern();
    double[] result = new double[length];
    Arrays.fill(result, 0, rowNum - 1, basePattern[0]);
    int patternIdx = 1;
    for (int i = rowNum - 1; i < length; i += rowNum) {
      Arrays.fill(result, i, Math.min(i + rowNum, length), basePattern[patternIdx]);

      patternIdx++;
      if (patternIdx >= basePattern.length) {
        patternIdx = 0;
      }
    }
    return result;
  }

  private static int[] getBasePattern() {
    return new int[] {0, 1, 0, -1};
  }

  private static INDArray repeatItemsNTimes(double[] items, int repetitionNumber) {
    INDArray base = Nd4j.create(items);
    INDArray result = Nd4j.create(items);
    for (int i = 1; i < repetitionNumber; i++) {
      result = Nd4j.concat(0, result, base);
    }
    return result;
  }
}
