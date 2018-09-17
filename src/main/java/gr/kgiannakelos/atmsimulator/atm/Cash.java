package gr.kgiannakelos.atmsimulator.atm;

public class Cash {

    private final Note note;

    private final long totalNumberOfNotes;

    public Cash(Note note, long totalNumberOfNotes) {
        this.note = note;
        this.totalNumberOfNotes = totalNumberOfNotes;
    }

    public long getTotalNumberOfNotes() {
        return totalNumberOfNotes;
    }

    public Note getNote() {
        return note;
    }

    public long getTotalAmount() {
        return note.getValue() * totalNumberOfNotes;
    }

    public static final class Builder {
        private Note note;
        private long totalNumberOfNotes;

        private Builder() {
        }

        public static Builder aCash() {
            return new Builder();
        }

        public Builder withNote(Note note) {
            this.note = note;
            return this;
        }

        public Builder withTotalNumberOfNotes(long totalNumberOfNotes) {
            this.totalNumberOfNotes = totalNumberOfNotes;
            return this;
        }

        public Cash build() {
            return new Cash(note, totalNumberOfNotes);
        }
    }
}
