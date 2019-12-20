package day_7.domain.expection;

public class ProgramHaltedException extends RuntimeException {
    public ProgramHaltedException() {
        super("Program halted!");
    }
}
