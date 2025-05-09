package package01;

public class Voter extends Person {
    private boolean hasVoted;

    public Voter(String name, String idNumber) {
        super(name, idNumber);
        this.hasVoted = false;
    }

    public boolean hasVoted() {
        return hasVoted;
    }

    public void markVoted() {
        this.hasVoted = true;
    }
}