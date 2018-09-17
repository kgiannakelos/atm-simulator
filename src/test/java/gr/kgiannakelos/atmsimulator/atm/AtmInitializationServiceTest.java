package gr.kgiannakelos.atmsimulator.atm;

import gr.kgiannakelos.atmsimulator.exception.InitializationException;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class AtmInitializationServiceTest {
    private AtmInitializationService atmInitializationService;

    @Before
    public void setUp() {
        atmInitializationService = new AtmInitializationService();
    }

    @Test
    public void givenInitialCash_whenInitializeAtm_thenShowInitialCash() throws InitializationException {
        Cash cashInFiftyNotes = Cash.Builder.aCash().withNote(Note.FIFTY).withTotalNumberOfNotes(5).build();
        Cash cashInTwentyNotes = Cash.Builder.aCash().withNote(Note.TWENTY).withTotalNumberOfNotes(5).build();

        List<Cash> initialCash = Arrays.asList(cashInFiftyNotes, cashInTwentyNotes);

        Atm atm = atmInitializationService.createAtmWithCash(initialCash);

        long totalCashAmount = cashInFiftyNotes.getTotalAmount() + cashInTwentyNotes.getTotalAmount();

        assertThat(totalCashAmount).isEqualTo(atm.getTotalCashAmount());
    }

    @Test(expected = InitializationException.class)
    public void givenNegativeCashAmount_shouldThrowInitializationException() throws InitializationException {
        Cash cashInFiftyNotes = Cash.Builder.aCash().withNote(Note.FIFTY).withTotalNumberOfNotes(-1).build();

        atmInitializationService.createAtmWithCash(Collections.singletonList(cashInFiftyNotes));
    }

}
