package intcodecomputer;

import intcodecomputer.domain.Instruction;
import intcodecomputer.domain.expection.ProgramHaltedException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class Main {

    public static void main(String[] args) throws URISyntaxException, IOException {
        String memoryString = Files.readAllLines(Paths.get(Main.class.getResource("/day_5_input.txt").toURI()), StandardCharsets.UTF_8).get(0);

        Integer[] memory = Arrays.stream(memoryString.split(",")).map(Integer::parseInt).toArray(Integer[]::new);

        try {
            int i = 0;
            while (i < memory.length) {
                var instruction = Instruction.getAtPointer(i, memory);
                i = instruction.executeOnMemory(i, memory);
                // printMemory(memory, i);
            }
        } catch (ProgramHaltedException e) {
            System.out.println(e.getMessage());

            System.out.println("OUTPUT: " + memory[0]);
            System.out.println("NOUN: " + memory[1]);
            System.out.println("VERB: " + memory[2]);
        }
    }

    private static void printMemory(Integer[] memory, int pointer) {
        System.out.println("MEMORY {" + memory.length + "}, POINTER: " + pointer);
        for (Integer integer : memory) {
            System.out.print(integer + ",");
        }
        System.out.println();
    }

}
