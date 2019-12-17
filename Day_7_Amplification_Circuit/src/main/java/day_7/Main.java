package day_7;

import com.google.common.collect.Collections2;
import day_7.domain.IOHandler;
import day_7.domain.Instruction;
import day_7.domain.Memory;
import day_7.domain.expection.ProgramHaltedException;
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
        Collections2.permutations(Arrays.asList(0, 1, 2, 3, 4));
    System.out.println("NUMBER OF PERMUTATIONS: " + phasePermutations.size());

    for (List<Integer> phases : phasePermutations) {
      IOHandler.nextInput = 0;
      IOHandler.lastOutput = 0;
      for (Integer currentPhase : phases) {
        Memory memory = new Memory();
        memory.loadProgram(memoryString);
        try {
          while (true) {
            IOHandler.nextInput =
                (IOHandler.numberOfInputCalls % 2 == 0) ? currentPhase : IOHandler.lastOutput;
            var instruction = Instruction.getNext(memory);
            instruction.executeOnMemory(memory);
          }
        } catch (ProgramHaltedException e) {
          System.out.println(e.getMessage());
        }
      }
      if (IOHandler.lastOutput > maxOutput) {
        maxOutput = IOHandler.lastOutput;
        phasesForMaxOutput = phases;
      }
    }
    System.out.println("MAX OUTPUT: " + maxOutput + "; WITH PHASES: " + phasesForMaxOutput);
  }
}
