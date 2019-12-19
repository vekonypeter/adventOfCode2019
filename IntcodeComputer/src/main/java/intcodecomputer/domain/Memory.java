package intcodecomputer.domain;

public class Memory {

    long[] values;
    long pointer;
    long relativeBase;

    public long getPointer() {
        return pointer;
    }

    public void setPointer(long pointer) {
        this.pointer = pointer;
    }

    public long getRelativeBase() {
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

    public long getValue(long address) {
        return values[(int)address];
    }

    public void setValue(long address, long value) {
        values[(int)address] = value;
    }

    public void movePointer(long move) {
        pointer += move;
    }

    public void moveRelativeBase(long move) {
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
