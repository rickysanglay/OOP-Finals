package package01;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class VotingController {
    private Storage storage;
    private Map<String, String> adminCredentials;

    public VotingController(Storage storage) {
        this.storage = storage;
        this.adminCredentials = new HashMap<>();
        adminCredentials.put("admin", "admin123");
    }

    public boolean registerVoter(String name, String idNumber) throws VotingException {
        if (!validateId(idNumber)) {
            throw new VotingException("Invalid ID number format (must be 8 digits)");
        }
        if (storage.getVoter(idNumber) != null) {
            throw new VotingException("Voter already registered");
        }
        Voter voter = new Voter(name, idNumber);
        storage.saveVoter(voter);
        return true;
    }

    public boolean registerCandidate(String name, String idNumber, String position) throws VotingException {
        if (!validateId(idNumber)) {
            throw new VotingException("Invalid ID number format (must be 8 digits)");
        }
        if (storage.getCandidate(idNumber) != null) {
            throw new VotingException("Candidate already registered");
        }
        Candidate candidate = new Candidate(name, idNumber, position);
        storage.saveCandidate(candidate);
        return true;
    }

    public boolean castVote(String voterId, String candidateId) throws VotingException {
        Voter voter = storage.getVoter(voterId);
        Candidate candidate = storage.getCandidate(candidateId);

        if (voter == null || candidate == null) {
            throw new VotingException("Invalid voter or candidate ID");
        }
        if (voter.hasVoted()) {
            throw new VotingException("Voter has already voted");
        }

        voter.markVoted();
        candidate.addVote();
        if (storage instanceof DatabaseStorage) {
            ((DatabaseStorage) storage).saveVote(voterId, candidateId);
            ((DatabaseStorage) storage).updateVoter(voter);
            ((DatabaseStorage) storage).updateCandidate(candidate);
        }
        return true;
    }

    public Map<String, List<Map<String, Object>>> getResults() {
        Map<String, List<Map<String, Object>>> results = new HashMap<>();
        for (Candidate candidate : storage.getAllCandidates()) {
            results.computeIfAbsent(candidate.getPosition(), k -> new ArrayList<>());
            Map<String, Object> candidateData = new HashMap<>();
            candidateData.put("name", candidate.getName());
            candidateData.put("votes", candidate.getVoteCount());
            results.get(candidate.getPosition()).add(candidateData);
        }
        return results;
    }

    public boolean authenticateAdmin(String username, String password) {
        return adminCredentials.getOrDefault(username, "").equals(password);
    }

    public List<Map<String, String>> getVoteHistory() {
        if (storage instanceof DatabaseStorage) {
            return ((DatabaseStorage) storage).getVoteHistory();
        }
        return new ArrayList<>();
    }

    public List<Voter> getAllVoters() {
        if (storage instanceof DatabaseStorage) {
            return ((DatabaseStorage) storage).getAllVoters();
        }
        return new ArrayList<>();
    }

    public List<Candidate> getAllCandidates() {
        return storage.getAllCandidates();
    }

    private boolean validateId(String idNumber) {
        return Pattern.matches("\\d{8}", idNumber);
    }
}