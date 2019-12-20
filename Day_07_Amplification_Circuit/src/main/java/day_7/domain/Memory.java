package day_7.domain;

import java.util.Arrays;

public class Memory {

    Integer[] values;
    int pointer;
    int relativeBase;

    public int getPointer() {
        return pointer;
    }

    public void setPointer(int pointer) {
        this.pointer = pointer;
    }

    public int getRelativeBase() {
        return relativeBase;
    }

    public Memory() {
        this.pointer = 0;
        this.relativeBase = 0;
    }

    public void loadProgram(String programString) {
        values = Arrays.stream(programString.split(",")).map(Integer::parseInt).toArray(Integer[]::new);
    }

    public Integer getValue(int address) {
        return values[address];
    }

    public void setValue(int address, int value) {
        values[address] = value;
    }

    public void movePointer(int move) {
        pointer += move;
    }

    public int size() {
        return values.length;
    }

    public void print() {
        System.out.println("MEMORY {" + size() + "}, POINTER: " + pointer);
        for (Integer value : values) {
            System.out.print(value + ",");
        }
        System.out.println();
    }
}
