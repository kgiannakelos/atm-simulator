package gr.kgiannakelos.atmsimulator.atm;

import gr.kgiannakelos.atmsimulator.exception.*;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class AtmWithdrawalServiceTest {

    private AtmDispenserService atmDispenserService;
    private AtmWithdrawalService atmWithdrawalService;
    private AtmInitializationService atmInitializationService;

    @Before
    public void setUp() {
        atmInitializationService = new AtmInitializationService();
        atmDispenserService = new AtmDispenserService();
        atmWithdrawalService = new AtmWithdrawalService(atmDispenserService);
    }

    private long sumTotalCashAmounts(List<Cash> cash) {
        return cash.stream().mapToLong(Cash::getTotalAmount).sum();
    }

    private List<Cash> createCashOfFiftyAndTwentyNotes(long numberOfFiftyNotes, long numberOfTwentyNotes) {
        Cash cashInFiftyNotes = Cash.Builder.aCash()
                .withNote(Note.FIFTY)
                .withTotalNumberOfNotes(numberOfFiftyNotes)
                .build();

        Cash cashInTwentyNotes = Cash.Builder.aCash()
                .withNote(Note.TWENTY)
                .withTotalNumberOfNotes(numberOfTwentyNotes)
                .build();

        return Arrays.asList(cashInFiftyNotes, cashInTwentyNotes);
    }

    @Test(expected = InsufficientFundsException.class)
    public void givenInitialZeroCash_whenWithdraw_ThenThrowInsufficientFundsException() throws WithdrawalException, InitializationException {
        Atm atm = atmInitializationService.createAtmWithZeroCash();

        atmWithdrawalService.withdrawCash(atm, 10);
    }

    @Test(expected = IllegalDispenseException.class)
    public void givenFiftyAndTwentyNotes_whenWithdraw10_ThenThrowIllegalDispenseFundsException()
            throws WithdrawalException, InitializationException {
        List<Cash> initialCash = createCashOfFiftyAndTwentyNotes(1, 1);

        Atm atm = atmInitializationService.createAtmWithCash(initialCash);

        atmWithdrawalService.withdrawCash(atm, 10);
    }

    @Test(expected = NonPositiveAmountException.class)
    public void givenInitialCash_whenWithdrawNegativeCash_ThenThrowNonPositiveAmountException()
            throws WithdrawalException, InitializationException {
        List<Cash> initialCash = createCashOfFiftyAndTwentyNotes(1, 1);

        Atm atm = atmInitializationService.createAtmWithCash(initialCash);

        atmWithdrawalService.withdrawCash(atm, -10);
    }

    @Test(expected = IllegalDispenseException.class)
    public void givenFiftyAndTwentyNotes_whenWithdraw30_ThenThrowIllegalDispenseFundsException()
            throws WithdrawalException, InitializationException {
        List<Cash> initialCash = createCashOfFiftyAndTwentyNotes(1, 1);

        Atm atm = atmInitializationService.createAtmWithCash(initialCash);

        atmWithdrawalService.withdrawCash(atm, 30);
    }

    @Test(expected = IllegalDispenseException.class)
    public void given350InitialCash_whenWithdraw340_ThenThrowIllegalDispenseFundsException()
            throws WithdrawalException, InitializationException {
        List<Cash> initialCash = createCashOfFiftyAndTwentyNotes(5, 5);

        Atm atm = atmInitializationService.createAtmWithCash(initialCash);

        atmWithdrawalService.withdrawCash(atm, 340);
    }

    @Test
    public void given350InitialCash_whenWithdraw300_ThenWithdraw2FiftyNotesAnd10TwentyNotes()
            throws WithdrawalException, InitializationException {
        List<Cash> initialCash = createCashOfFiftyAndTwentyNotes(3, 10);

        Atm atm = atmInitializationService.createAtmWithCash(initialCash);

        List<Cash> withdrawnCash = atmWithdrawalService.withdrawCash(atm, 300);
        Cash cashInTwentyNotes = withdrawnCash.get(0);
        Cash cashInFiftyNotes = withdrawnCash.get(1);

        assertThat(cashInTwentyNotes.getTotalNumberOfNotes()).isEqualTo(10);
        assertThat(cashInTwentyNotes.getTotalAmount()).isEqualTo(200);

        assertThat(cashInFiftyNotes.getTotalNumberOfNotes()).isEqualTo(2);
        assertThat(cashInFiftyNotes.getTotalAmount()).isEqualTo(100);

        assertThat(atm.getTotalCashAmount()).isEqualTo(50);
    }

    @Test
    public void given350InitialCash_whenWithdraw350_ThenWithdraw5FiftyNotes()
            throws WithdrawalException, InitializationException {
        List<Cash> initialCash = createCashOfFiftyAndTwentyNotes(7, 0);

        Atm atm = atmInitializationService.createAtmWithCash(initialCash);

        List<Cash> withdrawnCash = atmWithdrawalService.withdrawCash(atm, 350);
        Cash cashInFiftyNotes = withdrawnCash.get(0);

        assertThat(cashInFiftyNotes.getTotalNumberOfNotes()).isEqualTo(7);
        assertThat(cashInFiftyNotes.getTotalAmount()).isEqualTo(350);

        assertThat(atm.getTotalCashAmount()).isEqualTo(0);
    }

    @Test
    public void given150InitialCash_whenWithdraw100_ThenWithdraw5TwentyNotes()
            throws WithdrawalException, InitializationException {
        List<Cash> initialCash = createCashOfFiftyAndTwentyNotes(1, 5);

        Atm atm = atmInitializationService.createAtmWithCash(initialCash);

        List<Cash> withdrawnCash = atmWithdrawalService.withdrawCash(atm, 100);
        Cash cashInTwentyNotes = withdrawnCash.get(0);

        assertThat(cashInTwentyNotes.getTotalNumberOfNotes()).isEqualTo(5);
        assertThat(cashInTwentyNotes.getTotalAmount()).isEqualTo(100);

        assertThat(atm.getTotalCashAmount()).isEqualTo(50);
    }

}