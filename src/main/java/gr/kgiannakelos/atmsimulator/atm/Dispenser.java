package gr.kgiannakelos.atmsimulator.atm;

public class Dispenser {

    private final Note note;
    private final Dispenser nextChain;

    private long totalNumberOfNotes;

    public Dispenser(Note note, Dispenser nextChain) {
        this.note = note;
        this.nextChain = nextChain;
    }

    public Dispenser(Note note) {
        this.note = note;
        this.nextChain = null;
    }

    public Note getNote() {
        return note;
    }

    public Dispenser getNextChain() {
        return nextChain;
    }

    public long getTotalNumberOfNotes() {
        return totalNumberOfNotes;
    }

    public void setTotalNumberOfNotes(long totalNumberOfNotes) {
        this.totalNumberOfNotes = totalNumberOfNotes;
    }

    public void addCash(Cash cash) {
        this.totalNumberOfNotes += cash.getTotalNumberOfNotes();
    }

    public void printTotalNumberOfNotes() {
        System.out.println(totalNumberOfNotes + " x " + note);
    }
}