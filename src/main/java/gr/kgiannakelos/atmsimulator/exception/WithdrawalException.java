package gr.kgiannakelos.atmsimulator.exception;

public class WithdrawalException extends Exception {

    public WithdrawalException() {
        super("Could not dispense money from ATM due to internal error");
    }

    public WithdrawalException(String message) {
        super(message);
    }

}
