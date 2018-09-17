package gr.kgiannakelos.atmsimulator.atm;

import gr.kgiannakelos.atmsimulator.exception.IllegalDispenseException;
import gr.kgiannakelos.atmsimulator.exception.InsufficientFundsException;
import gr.kgiannakelos.atmsimulator.exception.NonPositiveAmountException;
import gr.kgiannakelos.atmsimulator.exception.WithdrawalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AtmWithdrawalService {

    private final AtmDispenserService atmDispenserService;

    @Autowired
    public AtmWithdrawalService(AtmDispenserService atmDispenserService) {
        this.atmDispenserService = atmDispenserService;
    }

    public List<Cash> withdrawCash(Atm atm, long requestedCash) throws WithdrawalException {
        long totalCashAmount = atm.getTotalCashAmount();

        validateRequestedCash(requestedCash, totalCashAmount);

        Optional<Dispenser> firstDispenser = Optional.ofNullable(atm.getFirstDispenserInChain());

        if (firstDispenser.isPresent()) {
            List<Cash> cashToWithdraw = atmDispenserService.dispenseCash(firstDispenser.get(), requestedCash);

            commitWithdrawalFromAtm(atm, requestedCash, cashToWithdraw);

            return cashToWithdraw;
        } else {
            throw new WithdrawalException("Dispensers of notes are missing");
        }
    }

    private void validateRequestedCash(long requestedCash, long totalCashAmount)
            throws NonPositiveAmountException, InsufficientFundsException {
        if (requestedCash <= 0) {
            throw new NonPositiveAmountException(requestedCash);
        }

        if (totalCashAmount < requestedCash) {
            throw new InsufficientFundsException(requestedCash);
        }
    }

    private void commitWithdrawalFromAtm(Atm atm, long requestedCash, List<Cash> cashToWithdraw) throws WithdrawalException {
        try {
            long totalCashToDispense = cashToWithdraw.stream().mapToLong(Cash::getTotalAmount).sum();

            boolean successfulWithdrawal = requestedCash == totalCashToDispense;

            if (successfulWithdrawal) {
                Map<Note, Dispenser> dispensers = atm.getDispensers();

                for (Cash cash : cashToWithdraw) {
                    Dispenser dispenser = dispensers.get(cash.getNote());
                    dispenser.setTotalNumberOfNotes(dispenser.getTotalNumberOfNotes() - cash.getTotalNumberOfNotes());
                }

                atm.setTotalCashAmount(atm.getTotalCashAmount() - totalCashToDispense);
            } else {
                throw new IllegalDispenseException(requestedCash);
            }
        } catch (IllegalDispenseException e) {
            throw e;
        } catch (Exception e) {
            throw new WithdrawalException();
        }
    }
}