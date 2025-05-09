package package01;

public class Candidate extends Person {
    private String position;
    private int voteCount;

    public Candidate(String name, String idNumber, String position) {
        super(name, idNumber);
        this.position = position;
        this.voteCount = 0;
    }

    public String getPosition() {
        return position;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void addVote() {
        this.voteCount++;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }
}