package gr.kgiannakelos.atmsimulator.atm;

import gr.kgiannakelos.atmsimulator.exception.InitializationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AtmInitializationService {

    public Atm createAtmWithCash(List<Cash> initialCash) throws InitializationException {
        Atm atm = new Atm();

        initializeAtmWithCash(atm, initialCash);

        return atm;
    }

    public Atm createAtmWithZeroCash() throws InitializationException {
        return createAtmWithCash(new ArrayList<>());
    }

    private void initializeAtmWithCash(Atm atm, List<Cash> initialCash) throws InitializationException {
        try {
            initializeNoteDispensers(atm);
            initializeCash(atm, initialCash);
        } catch (InitializationException e) {
            throw e;
        } catch (Exception e) {
            e.printStackTrace(System.err);
            throw new InitializationException();
        }

        System.out.println("\nATM has been initialized with total cash amount " + atm.getTotalCashAmount() + "$. More specifically with ");

        atm.getDispensers().values().forEach(Dispenser::printTotalNumberOfNotes);

        System.out.println();
    }

    private void initializeNoteDispensers(Atm atm) {
        Note[] notes = Note.values();

        Optional<Dispenser> lastDispenser = Optional.empty();
        Optional<Dispenser> firstDispenser = Optional.empty();

        for (Note note : notes) {
            Dispenser dispenser;

            dispenser = firstDispenser
                    .map(dispenser1 -> new Dispenser(note, dispenser1))
                    .orElseGet(() -> new Dispenser(note));

            if (!lastDispenser.isPresent()) {
                lastDispenser = Optional.of(dispenser);
            }

            firstDispenser = Optional.of(dispenser);

            addDispenser(atm, dispenser);
        }

        firstDispenser.ifPresent(atm::setFirstDispenserInChain);
    }

    private void initializeCash(Atm atm, List<Cash> initialCash) throws InitializationException {
        initializeNoteDispensersCash(atm, initialCash);
        initializeTotalCashAmount(atm, initialCash);
    }

    private void initializeTotalCashAmount(Atm atm, List<Cash> initialCash) throws InitializationException {
        for (Cash cash : initialCash) {
            if (cash.getTotalAmount() < 0) {
                throw new InitializationException("Negative cash amount is not permitted");
            }

            atm.setTotalCashAmount(atm.getTotalCashAmount() + cash.getTotalAmount());
        }
    }

    private void initializeNoteDispensersCash(Atm atm, List<Cash> initialCash) {
        Map<Note, Dispenser> dispensers = atm.getDispensers();

        initialCash.forEach(cash -> dispensers.get(cash.getNote()).addCash(cash));
    }

    private void addDispenser(Atm atm, Dispenser dispenser) {
        Map<Note, Dispenser> dispensers = atm.getDispensers();

        dispensers.put(dispenser.getNote(), dispenser);
    }
}