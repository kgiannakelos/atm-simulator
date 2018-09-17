package gr.kgiannakelos.atmsimulator.atm;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class NoteTest {

    @Test
    public void notes_shouldBeGreaterThanZero() {
        for (Note note : Note.values()) {
            assertThat(note.getValue()).isGreaterThan(0);
        }
    }

    @Test
    public void notes_shouldBeCreatedInAscendingOrder() {
        Note previousNote = null;
        for (Note note : Note.values()) {
            if (previousNote != null) {
                assertThat(note.getValue()).isGreaterThan(previousNote.getValue());
            }
            previousNote = note;
        }
    }

}