package intcodecomputer;

import intcodecomputer.domain.Instruction;
import intcodecomputer.domain.Memory;
import intcodecomputer.domain.expection.ProgramHaltedException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Main {

    public static void main(String[] args) throws URISyntaxException, IOException {
        List<String> memoryStrings = Files.readAllLines(Paths.get(Main.class.getResource("/day_9_1_examples.txt").toURI()), StandardCharsets.UTF_8);

        memoryStrings.forEach(memoryString -> {
            System.out.println(">>> RUNNING PROGRAM...");
            Memory memory = new Memory();
            memory.loadProgram(memoryString);
            runProgram(memory);
            System.out.println(">>> PROGRAM DONE!\n");
        });
    }

    private static void runProgram(Memory memory) {
        try {
            while (true) {
                var instruction = Instruction.getNext(memory);
                instruction.executeOnMemory(memory);
                // memory.print();
            }
        } catch (ProgramHaltedException e) {
            System.out.println(e.getMessage());

            System.out.println("OUTPUT: " + memory.getValue(0));
            System.out.println("NOUN: " + memory.getValue(1));
            System.out.println("VERB: " + memory.getValue(2));
        }
    }

}
