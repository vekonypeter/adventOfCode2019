package intcodecomputer.domain;

import intcodecomputer.domain.expection.ProgramHaltedException;
import java.util.Scanner;

public class Instruction {

  private static final Scanner SCANNER = new Scanner(System.in);

  private Long opcode;
  private Long[] params;
  private Long[] paramModes;
  private Long outputAddress;

  public Instruction(Long opcode) {
    this.opcode = opcode;
  }

  public Instruction(Long opcode, Long outputAddress) {
    this.opcode = opcode;
    this.outputAddress = outputAddress;
  }

  public Instruction(Long opcode, Long[] params, Long[] paramModes) {
    this.opcode = opcode;
    this.params = params;
    this.paramModes = paramModes;
  }

  public Instruction(Long opcode, Long[] params, Long[] paramModes, Long outputAddress) {
    this.opcode = opcode;
    this.params = params;
    this.paramModes = paramModes;
    this.outputAddress = outputAddress;
  }

  public int getLength() {
    return 1 + (params != null ? params.length : 0) + (outputAddress != null ? 1 : 0);
  }

  public void executeOnMemory(Memory memory) {
    switch (opcode.intValue()) {
      case 1:
        memory.setValue(this.outputAddress, getParamValue(memory, 0) + getParamValue(memory, 1));
        memory.movePointer(this.getLength());
        break;
      case 2:
        memory.setValue(this.outputAddress, getParamValue(memory, 0) * getParamValue(memory, 1));
        memory.movePointer(this.getLength());
        break;
      case 3:
        System.out.print("INPUT NEEDED: ");
        int input = Integer.parseInt(SCANNER.nextLine());
        memory.setValue(this.outputAddress, input);
        memory.movePointer(this.getLength());
        break;
      case 4:
        System.out.println("OUTPUT GENERATED: " + getParamValue(memory, 0));
        memory.movePointer(this.getLength());
        break;
      case 5:
        if (getParamValue(memory, 0) != 0) {
          memory.setPointer(getParamValue(memory, 1));
        } else {
          memory.movePointer(this.getLength());
        }
        break;
      case 6:
        if (getParamValue(memory, 0) == 0) {
          memory.setPointer(getParamValue(memory, 1));
        } else {
          memory.movePointer(this.getLength());
        }
        break;
      case 7:
        if (getParamValue(memory, 0) < getParamValue(memory, 1)) {
          memory.setValue(this.outputAddress, 1);
        } else {
          memory.setValue(this.outputAddress, 0);
        }
        memory.movePointer(this.getLength());
        break;
      case 8:
        if (getParamValue(memory, 0).equals(getParamValue(memory, 1))) {
          memory.setValue(this.outputAddress, 1);
        } else {
          memory.setValue(this.outputAddress, 0);
        }
        memory.movePointer(this.getLength());
        break;
      case 9:
        memory.moveRelativeBase(getParamValue(memory, 0));
        memory.movePointer(this.getLength());
        break;
      case 99:
        throw new ProgramHaltedException();
      default:
        throw new RuntimeException(
            String.format("Unknown opcode '%s' found during execution-time!", opcode));
    }
  }

  private Long getParamValue(Memory memory, int paramIndex) {
    switch (paramModes[paramIndex].intValue()) {
      case 0:
        return memory.getValue(params[paramIndex]);
      case 1:
        return params[paramIndex];
      case 2:
        return memory.getValue(params[paramIndex] + memory.getRelativeBase());
      default:
        throw new RuntimeException(
            String.format("Unknown parameter mode: %s", paramModes[paramIndex]));
    }
  }

  public static Instruction getNext(Memory memory) {
    long pointer = memory.getPointer();
    long currentValue = memory.getValue(pointer);
    long opcode = currentValue % 100;
    long param1Mode = (currentValue % 1000) / 100;
    long param2Mode = (currentValue % 10000) / 1000;

    switch ((int) opcode) {
      case 1:
      case 2:
      case 7:
      case 8:
        return new Instruction(
            opcode,
            new Long[] {memory.getValue(pointer + 1), memory.getValue(pointer + 2)},
            new Long[] {param1Mode, param2Mode},
            memory.getValue(pointer + 3));
      case 3:
        return new Instruction(opcode, memory.getValue(pointer + 1));
      case 4:
      case 9:
        return new Instruction(
            opcode, new Long[] {memory.getValue(pointer + 1)}, new Long[] {param1Mode});
      case 5:
      case 6:
        return new Instruction(
            opcode,
            new Long[] {memory.getValue(pointer + 1), memory.getValue(pointer + 2)},
            new Long[] {param1Mode, param2Mode},
            null);
      case 99:
        return new Instruction(opcode);
      default:
        throw new RuntimeException(
            String.format("Unknown opcode '%s' found during parse-time!", opcode));
    }
  }
}
