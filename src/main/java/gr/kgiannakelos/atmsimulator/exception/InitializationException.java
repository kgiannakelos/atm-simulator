package gr.kgiannakelos.atmsimulator.exception;

public class InitializationException extends Exception {

    public InitializationException() {
        super("ATM has not been initialized properly. Please report this incident immediately.");
    }

    public InitializationException(String message) {
        super(message);
    }
}
