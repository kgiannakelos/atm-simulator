package gr.kgiannakelos.atmsimulator;

import gr.kgiannakelos.atmsimulator.atm.Atm;
import gr.kgiannakelos.atmsimulator.atm.AtmInitializationService;
import gr.kgiannakelos.atmsimulator.atm.AtmWithdrawalService;
import gr.kgiannakelos.atmsimulator.atm.Cash;
import gr.kgiannakelos.atmsimulator.exception.InitializationException;
import gr.kgiannakelos.atmsimulator.exception.WithdrawalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
class AtmSimulation {

    private final AtmWithdrawalService atmWithdrawalService;

    private final AtmInitializationService atmInitializationService;

    @Autowired
    public AtmSimulation(AtmWithdrawalService atmWithdrawalService, AtmInitializationService atmInitializationService) {
        this.atmWithdrawalService = atmWithdrawalService;
        this.atmInitializationService = atmInitializationService;
    }

    public void run() {
        System.out.println("\n\nWelcome to the ATM Simulation. Press 'q' or 'quit' or 'exit' ");

        try {
            List<Cash> initialCash = Menu.initializeCash();

            Atm atm = atmInitializationService.createAtmWithCash(initialCash);

            while (true) {
                try {
                    long requestedCash = Menu.requestWithdrawal();

                    List<Cash> withdrawnCash = atmWithdrawalService.withdrawCash(atm, requestedCash);

                    System.out.println(requestedCash + "$ can be dispensed into ");

                    withdrawnCash.forEach(cash -> System.out.println(cash.getTotalNumberOfNotes() + " x " + cash.getNote()));

                    System.out.println("Total cash amount in ATM left is " + atm.getTotalCashAmount() + "$\n");
                } catch (WithdrawalException e) {
                    System.err.println(e.getMessage());
                } catch (Exception e) {
                    System.err.println("Something went wrong with the application.");
                }
            }
        } catch (InitializationException e) {
            System.err.println(e.getMessage());
        }
    }
}
