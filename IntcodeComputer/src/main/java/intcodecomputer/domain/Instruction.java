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

    public Instruction(Integer opcode, Integer[] paramAddresses, Integer[] paramModes, Integer outputAddress) {
        this.opcode = opcode;
        this.paramAddresses = paramAddresses;
        this.paramModes = paramModes;
        this.outputAddress = outputAddress;
    }

    public int getLength() {
        return 1 + (paramAddresses != null ? paramAddresses.length : 0) + (outputAddress != null ? 1 : 0);
    }

    public void executeOnMemory(Integer[] memory) {
        switch (opcode) {
            case 1:
                memory[this.outputAddress] = getParamValue(memory, 0) +getParamValue(memory, 1);
                break;
            case 2:
                memory[this.outputAddress] = getParamValue(memory, 0) * getParamValue(memory, 1);
                break;
            case 3:
                int input = Integer.parseInt(SCANNER.nextLine());
                memory[this.outputAddress] = input;
                break;
            case 4:
                System.out.println(memory[this.outputAddress]);
                break;
            case 99:
                throw new ProgramHaltedException();
            default:
                throw new RuntimeException("Unknown optcode found during execution-time!");
        }
    }

    private Integer getParamValue(Integer[] memory, int paramIndex) {
        switch (paramModes[paramIndex]) {
            case 0:
                return memory[paramAddresses[paramIndex]];
            case 1:
                return paramAddresses[paramIndex];
            default:
                throw new RuntimeException("Unknown parameter mode: " + paramModes[paramIndex]);
        }
    }

    public static Instruction getAtPointer(int pointer, Integer[] memory) {
        int opcode = memory[pointer] % 100;
        int param1Mode = (memory[pointer] % 1000) / 100;
        int param2Mode = (memory[pointer] % 10000) / 1000;

        switch (opcode) {
            case 1:
                return new Instruction(1,
                        new Integer[]{memory[pointer + 1], memory[pointer + 2]},
                        new Integer[]{param1Mode, param2Mode},
                        memory[pointer + 3]
                );
            case 2:
                return new Instruction(2,
                        new Integer[]{memory[pointer + 1], memory[pointer + 2]},
                        new Integer[]{param1Mode, param2Mode},
                        memory[pointer + 3]
                );
            case 3:
                return new Instruction(3, memory[pointer + 1]);
            case 4:
                return new Instruction(4, memory[pointer + 1]);
            case 99:
                return new Instruction(99);
            default:
                throw new RuntimeException("Unknown optcode found during parse-time!");
        }
    }

}
