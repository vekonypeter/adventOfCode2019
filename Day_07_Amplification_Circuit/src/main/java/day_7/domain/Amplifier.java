package day_7.domain;

import day_7.domain.expection.ProgramHaltedException;

public class Amplifier {

  private String id;
  private int phase;
  private Memory memory;
  private IOHandler ioHandler;

  public Amplifier(String id, int phase) {
    this.id = id;
    this.phase = phase;
  }

  public void init(String memoryString) {
    memory = new Memory();
    memory.loadProgram(memoryString);
    ioHandler = new IOHandler();
  }

  public Integer executeNext(Integer input) {
    try {
      Instruction instruction;
      do {
        if (ioHandler.numberOfInputCalls < 1) {
          ioHandler.nextInput = phase;
        } else {
          ioHandler.nextInput = input;
        }
        instruction = Instruction.getNext(memory, ioHandler);
        instruction.executeOnMemory(memory);
      } while (instruction.getOpcode() != 4);
      return ioHandler.lastOutput;
    } catch (ProgramHaltedException ex) {
      System.out.println("AMPLIFIER '" + id + "' TERMINATED");
      return null;
    }
  }
}
