package day_7;

import com.google.common.collect.Collections2;
import day_7.domain.Amplifier;
import day_7.domain.IOHandler;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class Main {

  public static void main(String[] args) throws URISyntaxException, IOException {
    String memoryString =
        Files.readAllLines(
                Paths.get(Main.class.getResource("/day_7_input.txt").toURI()),
                StandardCharsets.UTF_8)
            .get(0);

    int maxOutput = -1;
    List<Integer> phasesForMaxOutput = null;

    Collection<List<Integer>> phasePermutations =
        Collections2.permutations(Arrays.asList(5, 6, 7, 8, 9));
    System.out.println("NUMBER OF PERMUTATIONS: " + phasePermutations.size());

    for (List<Integer> phases : phasePermutations) {

      Amplifier amplifier_A = new Amplifier("A", phases.get(0));
      Amplifier amplifier_B = new Amplifier("B", phases.get(1));
      Amplifier amplifier_C = new Amplifier("C", phases.get(2));
      Amplifier amplifier_D = new Amplifier("D", phases.get(3));
      Amplifier amplifier_E = new Amplifier("E", phases.get(4));

      amplifier_A.init(memoryString);
      amplifier_B.init(memoryString);
      amplifier_C.init(memoryString);
      amplifier_D.init(memoryString);
      amplifier_E.init(memoryString);

      Integer amplifier_A_input = 0;
      Integer amplifier_B_input = null;
      Integer amplifier_C_input = null;
      Integer amplifier_D_input = null;
      Integer amplifier_E_input = null;

      Integer result;

      do {
        result = amplifier_A.executeNext(amplifier_A_input);
        if (result != null) {
          amplifier_B_input = result;
        }

        result = amplifier_B.executeNext(amplifier_B_input);
        if (result != null) {
          amplifier_C_input = result;
        }

        result = amplifier_C.executeNext(amplifier_C_input);
        if (result != null) {
          amplifier_D_input = result;
        }

        result = amplifier_D.executeNext(amplifier_D_input);
        if (result != null) {
          amplifier_E_input = result;
        }

        result = amplifier_E.executeNext(amplifier_E_input);
        if (result != null) {
          amplifier_A_input = result;
        }

      } while (result != null);

      if (amplifier_A_input > maxOutput) {
        maxOutput = amplifier_A_input;
        phasesForMaxOutput = phases;
      }
    }
    System.out.println("MAX OUTPUT: " + maxOutput + "; WITH PHASES: " + phasesForMaxOutput);

  }
}
