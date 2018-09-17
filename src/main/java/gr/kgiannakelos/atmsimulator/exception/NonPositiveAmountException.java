package gr.kgiannakelos.atmsimulator.exception;

public class NonPositiveAmountException extends WithdrawalException {

    public NonPositiveAmountException(long amount) {
        super("The requested amount " + amount + "$ must be a positive number. Please try again with a different (positive) amount.");
    }

}
