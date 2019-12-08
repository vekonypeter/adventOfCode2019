package intcodecomputer.domain.expection;

public class ProgramHaltedException extends RuntimeException {
    public ProgramHaltedException() {
        super("Program halted!");
    }
}
