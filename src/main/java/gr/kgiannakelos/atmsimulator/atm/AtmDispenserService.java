package gr.kgiannakelos.atmsimulator.atm;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AtmDispenserService {

    List<Cash> dispenseCash(Dispenser dispenser, long requestedCash) {
        List<Cash> cashToDispense = new ArrayList<>();

        long numberOfNotesToDispense = calculateNumberOfNotesToDispense(dispenser, requestedCash);

        boolean dispenserSatisfiesRequest = checkIfDispenserSatisfiesRequest(dispenser, requestedCash);

        if (!dispenserSatisfiesRequest) {
            long noteValue = dispenser.getNote().getValue();

            requestedCash = calculateRemainingRequestedCash(requestedCash, numberOfNotesToDispense, noteValue);

            Optional<Dispenser> nextDispenser = Optional.ofNullable(dispenser.getNextChain());

            if (nextDispenser.isPresent()) {
                List<Cash> cashDispensedFromOtherDispensers = dispenseCash(nextDispenser.get(), requestedCash);

                while (!requestIsFulfilled(cashDispensedFromOtherDispensers, requestedCash) && numberOfNotesToDispense > 0) {
                    requestedCash += noteValue;
                    numberOfNotesToDispense--;

                    cashDispensedFromOtherDispensers = dispenseCash(nextDispenser.get(), requestedCash);
                }

                cashToDispense.addAll(cashDispensedFromOtherDispensers);
            }
        }

        cashToDispense.add(new Cash(dispenser.getNote(), numberOfNotesToDispense));

        return cashToDispense;
    }

    private boolean requestIsFulfilled(List<Cash> cashToDispense, long requestedAmount) {
        long dispensedAmount = cashToDispense.stream().mapToLong(Cash::getTotalAmount).sum();

        return requestedAmount == dispensedAmount;
    }

    private boolean checkIfDispenserSatisfiesRequest(Dispenser dispenser, long amount) {
        long noteValue = dispenser.getNote().getValue();
        long totalNumberOfNotes = dispenser.getTotalNumberOfNotes();

        boolean notesAreEligible = amount % noteValue == 0;
        boolean notesAreSufficient = amount / noteValue <= totalNumberOfNotes;

        return notesAreEligible && notesAreSufficient;
    }

    private long calculateNumberOfNotesToDispense(Dispenser dispenser, long requestedAmount) {
        long noteValue = dispenser.getNote().getValue();
        long totalNumberOfNotes = dispenser.getTotalNumberOfNotes();

        return requestedAmount / noteValue <= totalNumberOfNotes
                ? requestedAmount / noteValue
                : totalNumberOfNotes;
    }

    private long calculateRemainingRequestedCash(long requestedAmount, long dispensedNumberOfNotes, long noteValue) {
        return requestedAmount - dispensedNumberOfNotes * noteValue;
    }
}