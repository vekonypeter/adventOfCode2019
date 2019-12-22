package intcodecomputer.domain;

import intcodecomputer.domain.exception.ProgramHaltedException;

public class Instruction {

  // TODO: refactor class structure
  private Memory memory;
  private InputProvider inputProvider;
  private OutputHandler outputHandler;

  private Long opcode;
  private Long[] params;
  private Long[] paramModes;

  public Instruction(
      Memory memory, InputProvider inputProvider, OutputHandler outputHandler, Long opcode) {
    this.memory = memory;
    this.inputProvider = inputProvider;
    this.outputHandler = outputHandler;
    this.opcode = opcode;
  }

  public Instruction(
      Memory memory,
      InputProvider inputProvider,
      OutputHandler outputHandler,
      Long opcode,
      Long[] params,
      Long[] paramModes) {
    this.memory = memory;
    this.inputProvider = inputProvider;
    this.outputHandler = outputHandler;
    this.opcode = opcode;
    this.params = params;
    this.paramModes = paramModes;
  }

  public int getLength() {
    return 1 + (params != null ? params.length : 0);
  }

  public void executeOnMemory() {
    switch (opcode.intValue()) {
      case 1:
        setMemoryValue(getMemoryValue(0) + getMemoryValue(1), 2);
        memory.movePointer(this.getLength());
        break;
      case 2:
        setMemoryValue(getMemoryValue(0) * getMemoryValue(1), 2);
        memory.movePointer(this.getLength());
        break;
      case 3:
        long input = inputProvider.getInput();
        setMemoryValue(input, 0);
        memory.movePointer(this.getLength());
        break;
      case 4:
        outputHandler.handleOutput(getMemoryValue(0));
        memory.movePointer(this.getLength());
        break;
      case 5:
        if (getMemoryValue(0) != 0) {
          memory.setPointer(getMemoryValue(1));
        } else {
          memory.movePointer(this.getLength());
        }
        break;
      case 6:
        if (getMemoryValue(0) == 0) {
          memory.setPointer(getMemoryValue(1));
        } else {
          memory.movePointer(this.getLength());
        }
        break;
      case 7:
        if (getMemoryValue(0) < getMemoryValue(1)) {
          setMemoryValue(1, 2);
        } else {
          setMemoryValue(0, 2);
        }
        memory.movePointer(this.getLength());
        break;
      case 8:
        if (getMemoryValue(0).equals(getMemoryValue(1))) {
          setMemoryValue(1, 2);
        } else {
          setMemoryValue(0, 2);
        }
        memory.movePointer(this.getLength());
        break;
      case 9:
        memory.moveRelativeBase(getMemoryValue(0));
        memory.movePointer(this.getLength());
        break;
      case 99:
        throw new ProgramHaltedException();
      default:
        throw new RuntimeException(
            String.format("Unknown opcode '%s' found during execution-time!", opcode));
    }
  }

  private Long getMemoryValue(int paramIndex) {
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

  private void setMemoryValue(long value, int paramIndex) {
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

  public static Instruction getNext(
      Memory memory, InputProvider inputProvider, OutputHandler outputHandler) {
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
            memory,
            inputProvider,
            outputHandler,
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
            memory,
            inputProvider,
            outputHandler,
            opcode,
            new Long[] {memory.getValue(pointer + 1)},
            new Long[] {param1Mode});
      case 5:
      case 6:
        return new Instruction(
            memory,
            inputProvider,
            outputHandler,
            opcode,
            new Long[] {memory.getValue(pointer + 1), memory.getValue(pointer + 2)},
            new Long[] {param1Mode, param2Mode});
      case 99:
        return new Instruction(memory, inputProvider, outputHandler, opcode);
      default:
        throw new RuntimeException(
            String.format("Unknown opcode '%s' found during parse-time!", opcode));
    }
  }
}
