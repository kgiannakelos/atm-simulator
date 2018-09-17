package gr.kgiannakelos.atmsimulator.exception;

public class InsufficientFundsException extends WithdrawalException {

    public InsufficientFundsException(long amount) {
        super("The requested amount " + amount + "$ cannot be dispensed due to insufficient funds. Please try again tomorrow.");
    }

}
