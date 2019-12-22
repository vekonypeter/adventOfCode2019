package intcodecomputer.domain;

import intcodecomputer.domain.exception.ProgramHaltedException;

public class IntcodeComputer {

  private Memory memory;
  private InputProvider inputProvider;
  private OutputHandler outputHandler;

  public IntcodeComputer(InputProvider inputProvider, OutputHandler outputHandler) {
    this.inputProvider = inputProvider;
    this.outputHandler = outputHandler;
  }

  public void init(String programString) {
    memory = new Memory();
    memory.loadProgram(programString);
  }

  public void run() {
    System.out.println(">>> RUNNING PROGRAM...");
    try {
      while (true) {
        var instruction = Instruction.getNext(memory, inputProvider, outputHandler);
        instruction.executeOnMemory();
      }
    } catch (ProgramHaltedException e) {
      System.out.println(e.getMessage());
    }
    System.out.println(">>> PROGRAM DONE!\n");
  }

  public void printMemory() {
    memory.print();
  }
}
