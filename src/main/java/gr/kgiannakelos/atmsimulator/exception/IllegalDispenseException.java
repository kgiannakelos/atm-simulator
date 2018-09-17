package gr.kgiannakelos.atmsimulator.exception;

public class IllegalDispenseException extends WithdrawalException {

    public IllegalDispenseException(long amount) {
        super("The requested amount " + amount + "$ cannot be dispensed due to illegal combination of notes. Please try again with a different amount.");
    }

}
