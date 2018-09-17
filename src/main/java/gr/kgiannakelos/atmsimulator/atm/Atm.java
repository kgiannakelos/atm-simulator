package gr.kgiannakelos.atmsimulator.atm;

import java.util.EnumMap;
import java.util.Map;

public class Atm {

    private final Map<Note, Dispenser> dispensers = new EnumMap<>(Note.class);

    private Dispenser firstDispenserInChain;

    private long totalCashAmount;

    Map<Note, Dispenser> getDispensers() {
        return dispensers;
    }

    Dispenser getFirstDispenserInChain() {
        return firstDispenserInChain;
    }

    void setFirstDispenserInChain(Dispenser firstDispenserInChain) {
        this.firstDispenserInChain = firstDispenserInChain;
    }

    public long getTotalCashAmount() {
        return totalCashAmount;
    }

    void setTotalCashAmount(long totalCashAmount) {
        this.totalCashAmount = totalCashAmount;
    }

}
