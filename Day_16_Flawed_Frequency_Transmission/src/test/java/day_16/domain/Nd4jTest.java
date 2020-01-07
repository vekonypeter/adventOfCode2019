package day_16.domain;

import org.junit.jupiter.api.Test;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.factory.Nd4j;

public class Nd4jTest {

  @Test
  public void simpleMatrixMultiplication() {
    INDArray matrix1 =
        Nd4j.create(
            new double[][] {
              new double[] {1, 2, 3}, new double[] {1, 2, 3}, new double[] {1, 2, 3}
            });
    INDArray matrix2 = Nd4j.create(new double[] {4, 5, 6});
    INDArray result = matrix1.mmul(matrix2);
    System.out.println(result);
  }

  @Test
  public void complexMatrixMultiplication() {
    INDArray matrix1 =
        Nd4j.create(
            new double[][] {
              new double[] {1, 0, -1, 0, 1, 0, -1, 0},
              new double[] {0, 1, 1, 0, 0, -1, -1, 0},
              new double[] {0, 0, 1, 1, 1, 0, 0, 0},
              new double[] {0, 0, 0, 1, 1, 1, 1, 0},
              new double[] {0, 0, 0, 0, 1, 1, 1, 1},
              new double[] {0, 0, 0, 0, 0, 1, 1, 1},
              new double[] {0, 0, 0, 0, 0, 0, 1, 1},
              new double[] {
                0, 0, 0, 0, 0, 0, 0, 1,
              }
            });
    INDArray matrix2 = Nd4j.create(new double[] {1, 2, 3, 4, 5, 6, 7, 8});
    INDArray result = matrix1.mmul(matrix2);
    System.out.println(result);
  }
}
