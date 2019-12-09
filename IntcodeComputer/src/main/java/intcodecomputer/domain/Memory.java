package intcodecomputer.domain;

public class Memory {

    long[] values;
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
        String[] stringValues = programString.split(",");
        values = new long[stringValues.length * 10];
        for (int i = 0; i < stringValues.length; i++) {
            values[i] = Long.parseLong(stringValues[i]);
        }
    }

    public long getValue(int address) {
        return values[address];
    }

    public void setValue(int address, long value) {
        values[address] = value;
    }

    public void movePointer(int move) {
        pointer += move;
    }

    public void moveRelativeBase(int move) {
        relativeBase += move;
    }

    public int size() {
        return values.length;
    }

    public void print() {
        System.out.println("MEMORY LENGTH: " + size() + "; POINTER: " + pointer + "; RELATIVE BASE: " + relativeBase);
        for (long value : values) {
            System.out.print(value + ",");
        }
        System.out.println();
    }
}
