package intcodecomputer.domain;

import intcodecomputer.domain.exception.ProgramHaltedException;
import java.util.Scanner;

public class Instruction {

  private static final Scanner SCANNER = new Scanner(System.in);

  private Long opcode;
  private Long[] params;
  private Long[] paramModes;

  public Instruction(Long opcode) {
    this.opcode = opcode;
  }

  public Instruction(Long opcode, Long[] params, Long[] paramModes) {
    this.opcode = opcode;
    this.params = params;
    this.paramModes = paramModes;
  }

  public int getLength() {
    return 1 + (params != null ? params.length : 0);
  }

  public void executeOnMemory(Memory memory) {
    switch (opcode.intValue()) {
      case 1:
        setMemoryValue(memory, getMemoryValue(memory, 0) + getMemoryValue(memory, 1), 2);
        memory.movePointer(this.getLength());
        break;
      case 2:
        setMemoryValue(memory, getMemoryValue(memory, 0) * getMemoryValue(memory, 1), 2);
        memory.movePointer(this.getLength());
        break;
      case 3:
        System.out.print("INPUT NEEDED: ");
        int input = Integer.parseInt(SCANNER.nextLine());
        setMemoryValue(memory, input, 0);
        memory.movePointer(this.getLength());
        break;
      case 4:
        System.out.println("OUTPUT GENERATED: " + getMemoryValue(memory, 0));
        memory.movePointer(this.getLength());
        break;
      case 5:
        if (getMemoryValue(memory, 0) != 0) {
          memory.setPointer(getMemoryValue(memory, 1));
        } else {
          memory.movePointer(this.getLength());
        }
        break;
      case 6:
        if (getMemoryValue(memory, 0) == 0) {
          memory.setPointer(getMemoryValue(memory, 1));
        } else {
          memory.movePointer(this.getLength());
        }
        break;
      case 7:
        if (getMemoryValue(memory, 0) < getMemoryValue(memory, 1)) {
          setMemoryValue(memory, 1, 2);
        } else {
          setMemoryValue(memory, 0, 2);
        }
        memory.movePointer(this.getLength());
        break;
      case 8:
        if (getMemoryValue(memory, 0).equals(getMemoryValue(memory, 1))) {
          setMemoryValue(memory, 1, 2);
        } else {
          setMemoryValue(memory, 0, 2);
        }
        memory.movePointer(this.getLength());
        break;
      case 9:
        memory.moveRelativeBase(getMemoryValue(memory, 0));
        memory.movePointer(this.getLength());
        break;
      case 99:
        throw new ProgramHaltedException();
      default:
        throw new RuntimeException(
            String.format("Unknown opcode '%s' found during execution-time!", opcode));
    }
  }

  private Long getMemoryValue(Memory memory, int paramIndex) {
    switch (paramModes[paramIndex].intValue()) {
      case 0:
        return memory.getValue(params[paramIndex]);
      case 1:
        return params[paramIndex];
      case 2:
        return memory.getValue(params[paramIndex] + memory.getRelativeBase());
      default:
        throw new RuntimeException(
            String.format(
                "Unknown parameter mode for getting memory value: %s", paramModes[paramIndex]));
    }
  }

  private void setMemoryValue(Memory memory, long value, int paramIndex) {
    switch (paramModes[paramIndex].intValue()) {
      case 0:
        memory.setValue(params[paramIndex], value);
        break;
      case 2:
        memory.setValue(params[paramIndex] + memory.getRelativeBase(), value);
        break;
      default:
        throw new RuntimeException(
            String.format(
                "Unknown parameter mode for setting memory value: %s", paramModes[paramIndex]));
    }
  }

  public static Instruction getNext(Memory memory) {
    long pointer = memory.getPointer();
    long currentValue = memory.getValue(pointer);
    long opcode = currentValue % 100;
    long param1Mode = (currentValue % 1000) / 100;
    long param2Mode = (currentValue % 10000) / 1000;
    long param3Mode = (currentValue % 100000) / 10000;

    switch ((int) opcode) {
      case 1:
      case 2:
      case 7:
      case 8:
        return new Instruction(
            opcode,
            new Long[] {
              memory.getValue(pointer + 1),
              memory.getValue(pointer + 2),
              memory.getValue(pointer + 3)
            },
            new Long[] {param1Mode, param2Mode, param3Mode});
      case 3:
      case 4:
      case 9:
        return new Instruction(
            opcode, new Long[] {memory.getValue(pointer + 1)}, new Long[] {param1Mode});
      case 5:
      case 6:
        return new Instruction(
            opcode,
            new Long[] {memory.getValue(pointer + 1), memory.getValue(pointer + 2)},
            new Long[] {param1Mode, param2Mode});
      case 99:
        return new Instruction(opcode);
      default:
        throw new RuntimeException(
            String.format("Unknown opcode '%s' found during parse-time!", opcode));
    }
  }
}
