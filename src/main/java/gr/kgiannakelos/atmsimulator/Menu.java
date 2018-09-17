package gr.kgiannakelos.atmsimulator;

import gr.kgiannakelos.atmsimulator.atm.Cash;
import gr.kgiannakelos.atmsimulator.atm.Note;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

final class Menu {

    private static final Scanner keyboard = new Scanner(System.in);

    private Menu() {
    }

    static List<Cash> initializeCash() {
        Note[] notes = Note.values();

        System.out.println("\n\nInitializing a new ATM . . .");

        List<Cash> initialCash = new ArrayList<>();

        for (Note note : notes) {
            System.out.println("Enter the total number of " + note + " notes");

            long totalNumberOfNotes = getLongInputFromKeyboard();

            Cash cashInNotes = Cash.Builder.aCash()
                    .withNote(note)
                    .withTotalNumberOfNotes(totalNumberOfNotes)
                    .build();

            initialCash.add(cashInNotes);
        }

        return initialCash;
    }

    static long requestWithdrawal() {
        System.out.println("Enter the desired cash amount to withdraw (or press q for quit)");

        return getLongInputFromKeyboard();
    }

    private static long getLongInputFromKeyboard() {
        while (true) {
            if (keyboard.hasNextLong()) {
                return keyboard.nextLong();
            } else {
                checkExitCommand();

                keyboard.next();
            }

            System.out.println("Invalid input. Please use a numeric value as input");
        }
    }

    private static void checkExitCommand() {
        boolean exitCommand = keyboard.hasNext("q") || keyboard.hasNext("quit") || keyboard.hasNext("exit");

        if (exitCommand) {
            System.out.println("Exitting application . . .");
            System.exit(0);
        }
    }
}
