package intcodecomputer.domain;

import intcodecomputer.domain.expection.ProgramHaltedException;

public class Instruction {

    private Integer opcode;
    private Integer[] paramAddresses;
    private Integer outputAddress;

    public Instruction(Integer opcode, Integer[] paramAddresses, Integer outputAddress) {
        this.opcode = opcode;
        this.paramAddresses = paramAddresses;
        this.outputAddress = outputAddress;
    }

    public int getLength() {
        return 1 + (paramAddresses != null ? paramAddresses.length : 0) + (outputAddress != null ? 1 : 0);
    }

    public void executeOnMemory(Integer[] memory) {
        switch (opcode) {
            case 1:
                memory[this.outputAddress] = memory[this.paramAddresses[0]] + memory[this.paramAddresses[1]];
                break;
            case 2:
                memory[this.outputAddress] = memory[this.paramAddresses[0]] * memory[this.paramAddresses[1]];
                break;
            case 99:
                throw new ProgramHaltedException();
            default:
                throw new RuntimeException("Unknown optcode found during execution-time!");
        }
    }

    public static Instruction getAtPointer(int pointer, Integer[] memory) {
        switch (memory[pointer]) {
            case 1:
                return new Instruction(1, new Integer[]{memory[pointer + 1], memory[pointer + 2]}, memory[pointer + 3]);
            case 2:
                return new Instruction(2, new Integer[]{memory[pointer + 1], memory[pointer + 2]}, memory[pointer + 3]);
            case 99:
                return new Instruction(99, null, null);
            default:
                throw new RuntimeException("Unknown optcode found during parse-time!");
        }
    }
}
