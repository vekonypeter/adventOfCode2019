package intcodecomputer.domain.exception;

public class ProgramHaltedException extends RuntimeException {
    public ProgramHaltedException() {
        super("Program halted!");
    }
}
