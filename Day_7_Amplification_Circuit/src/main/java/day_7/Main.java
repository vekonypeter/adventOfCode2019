package day_7;

import day_7.domain.Instruction;
import day_7.domain.Memory;
import day_7.domain.expection.ProgramHaltedException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) throws URISyntaxException, IOException {
        String memoryString = Files.readAllLines(Paths.get(Main.class.getResource("/day_7_input.txt").toURI()), StandardCharsets.UTF_8).get(0);

        Memory memory = new Memory();
        memory.loadProgram(memoryString);

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
