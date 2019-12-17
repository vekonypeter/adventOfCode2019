package day_7.domain;

import day_7.domain.expection.ProgramHaltedException;

import java.util.Scanner;

public class Instruction {

    private static final IOHandler ioHandler = new IOHandler();

    private Integer opcode;
    private Integer[] params;
    private Integer[] paramModes;
    private Integer outputAddress;

    private Instruction(Integer opcode) {
        this.opcode = opcode;
    }

    private Instruction(Integer opcode, Integer outputAddress) {
        this.opcode = opcode;
        this.outputAddress = outputAddress;
    }

    private Instruction(Integer opcode, Integer[] params, Integer[] paramModes) {
        this.opcode = opcode;
        this.params = params;
        this.paramModes = paramModes;
    }

    private Instruction(Integer opcode, Integer[] params, Integer[] paramModes, Integer outputAddress) {
        this.opcode = opcode;
        this.params = params;
        this.paramModes = paramModes;
        this.outputAddress = outputAddress;
    }

    private int getLength() {
        return 1 + (params != null ? params.length : 0) + (outputAddress != null ? 1 : 0);
    }

    public void executeOnMemory(Memory memory) {
        switch (opcode) {
            case 1:
                memory.setValue(this.outputAddress, getParamValue(memory, 0) + getParamValue(memory, 1));
                memory.movePointer(this.getLength());
                break;
            case 2:
                memory.setValue(this.outputAddress, getParamValue(memory, 0) * getParamValue(memory, 1));
                memory.movePointer(this.getLength());
                break;
            case 3:
                int input = IOHandler.getInput();
                memory.setValue(this.outputAddress, input);
                memory.movePointer(this.getLength());
                break;
            case 4:
                IOHandler.produceOutput(getParamValue(memory, 0));
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
            case 99:
                throw new ProgramHaltedException();
            default:
                throw new RuntimeException(String.format("Unknown opcode '%s' found during execution-time!", opcode));
        }
    }

    private Integer getParamValue(Memory memory, int paramIndex) {
        switch (paramModes[paramIndex]) {
            case 0:
                return memory.getValue(params[paramIndex]);
            case 1:
                return params[paramIndex];
            default:
                throw new RuntimeException(String.format("Unknown parameter mode: %s", paramModes[paramIndex]));
        }
    }


    public static Instruction getNext(Memory memory) {
        int pointer = memory.getPointer();
        int currentValue = memory.getValue(pointer);
        int opcode = currentValue % 100;
        int param1Mode = (currentValue % 1000) / 100;
        int param2Mode = (currentValue % 10000) / 1000;

        switch (opcode) {
            case 1:
            case 2:
            case 7:
            case 8:
                return new Instruction(opcode,
                        new Integer[]{memory.getValue(pointer + 1), memory.getValue(pointer + 2)},
                        new Integer[]{param1Mode, param2Mode},
                        memory.getValue(pointer + 3)
                );
            case 3:
                return new Instruction(opcode, memory.getValue(pointer + 1));
            case 4:
                return new Instruction(opcode,
                        new Integer[]{memory.getValue(pointer + 1)},
                        new Integer[]{param1Mode}
                );
            case 5:
            case 6:
                return new Instruction(opcode,
                        new Integer[]{memory.getValue(pointer + 1), memory.getValue(pointer + 2)},
                        new Integer[]{param1Mode, param2Mode},
                        null
                );
            case 99:
                return new Instruction(99);
            default:
                throw new RuntimeException(String.format("Unknown opcode '%s' found during parse-time!", opcode));
        }
    }

}
