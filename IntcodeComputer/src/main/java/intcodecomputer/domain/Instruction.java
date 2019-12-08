package intcodecomputer.domain;

import intcodecomputer.domain.expection.ProgramHaltedException;

import java.util.Scanner;

public class Instruction {

    private static final Scanner SCANNER = new Scanner(System.in);

    private Integer opcode;
    private Integer[] paramAddresses;
    private Integer[] paramModes;
    private Integer outputAddress;

    public Instruction(Integer opcode) {
        this.opcode = opcode;
    }

    public Instruction(Integer opcode, Integer outputAddress) {
        this.opcode = opcode;
        this.outputAddress = outputAddress;
    }

    public Instruction(Integer opcode, Integer[] paramAddresses, Integer[] paramModes) {
        this.opcode = opcode;
        this.paramAddresses = paramAddresses;
        this.paramModes = paramModes;
    }

    public Instruction(Integer opcode, Integer[] paramAddresses, Integer[] paramModes, Integer outputAddress) {
        this.opcode = opcode;
        this.paramAddresses = paramAddresses;
        this.paramModes = paramModes;
        this.outputAddress = outputAddress;
    }

    public int getLength() {
        return 1 + (paramAddresses != null ? paramAddresses.length : 0) + (outputAddress != null ? 1 : 0);
    }

    public int executeOnMemory(int pointer, Integer[] memory) {
        switch (opcode) {
            case 1:
                memory[this.outputAddress] = getParamValue(memory, 0) + getParamValue(memory, 1);
                return pointer + this.getLength();
            case 2:
                memory[this.outputAddress] = getParamValue(memory, 0) * getParamValue(memory, 1);
                return pointer + this.getLength();
            case 3:
                System.out.print("INPUT NEEDED: ");
                int input = Integer.parseInt(SCANNER.nextLine());
                memory[this.outputAddress] = input;
                return pointer + this.getLength();
            case 4:
                System.out.println("OUTPUT GENERATED: " + getParamValue(memory, 0));
                return pointer + this.getLength();
            case 5:
                return getParamValue(memory, 0) != 0 ? getParamValue(memory, 1) : pointer + this.getLength();
            case 6:
                return getParamValue(memory, 0) == 0 ? getParamValue(memory, 1) : pointer + this.getLength();
            case 7:
                if (getParamValue(memory, 0) < getParamValue(memory, 1)) {
                    memory[this.outputAddress] = 1;
                } else {
                    memory[this.outputAddress] = 0;
                }
                return pointer + this.getLength();
            case 8:
                if (getParamValue(memory, 0).equals(getParamValue(memory, 1))) {
                    memory[this.outputAddress] = 1;
                } else {
                    memory[this.outputAddress] = 0;
                }
                return pointer + this.getLength();
            case 99:
                throw new ProgramHaltedException();
            default:
                throw new RuntimeException(String.format("Unknown opcode '%s' found during execution-time!", opcode));
        }
    }

    private Integer getParamValue(Integer[] memory, int paramIndex) {
        switch (paramModes[paramIndex]) {
            case 0:
                return memory[paramAddresses[paramIndex]];
            case 1:
                return paramAddresses[paramIndex];
            default:
                throw new RuntimeException(String.format("Unknown parameter mode: %s", paramModes[paramIndex]));
        }
    }

    public static Instruction getAtPointer(int pointer, Integer[] memory) {
        int opcode = memory[pointer] % 100;
        int param1Mode = (memory[pointer] % 1000) / 100;
        int param2Mode = (memory[pointer] % 10000) / 1000;

        switch (opcode) {
            case 1:
            case 2:
            case 7:
            case 8:
                return new Instruction(opcode,
                        new Integer[]{memory[pointer + 1], memory[pointer + 2]},
                        new Integer[]{param1Mode, param2Mode},
                        memory[pointer + 3]
                );
            case 3:
                return new Instruction(opcode, memory[pointer + 1]);
            case 4:
                return new Instruction(opcode,
                        new Integer[]{memory[pointer + 1]},
                        new Integer[]{param1Mode}
                );
            case 5:
            case 6:
                return new Instruction(opcode,
                        new Integer[]{memory[pointer + 1], memory[pointer + 2]},
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
