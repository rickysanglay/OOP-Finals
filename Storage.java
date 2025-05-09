package package01;

import java.util.List;

public interface Storage {
    void saveVoter(Voter voter);
    void saveCandidate(Candidate candidate);
    Voter getVoter(String idNumber);
    Candidate getCandidate(String idNumber);
    List<Candidate> getAllCandidates();
}