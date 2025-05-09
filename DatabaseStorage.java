package package01;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class DatabaseStorage implements Storage {
    private final String url;
    private final String username;
    private final String password;

    public DatabaseStorage(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    @Override
    public void saveVoter(Voter voter) {
        String sql = "INSERT INTO voters (id_number, name, person_id, has_voted) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, voter.getIdNumber());
            pstmt.setString(2, voter.getName());
            pstmt.setString(3, voter.getPersonId());
            pstmt.setBoolean(4, voter.hasVoted());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to save voter: " + e.getMessage());
        }
    }

    @Override
    public void saveCandidate(Candidate candidate) {
        String sql = "INSERT INTO candidates (id_number, name, person_id, position, vote_count) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, candidate.getIdNumber());
            pstmt.setString(2, candidate.getName());
            pstmt.setString(3, candidate.getPersonId());
            pstmt.setString(4, candidate.getPosition());
            pstmt.setInt(5, candidate.getVoteCount());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to save candidate: " + e.getMessage());
        }
    }

    @Override
    public Voter getVoter(String idNumber) {
        String sql = "SELECT * FROM voters WHERE id_number = ?";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, idNumber);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Voter voter = new Voter(rs.getString("name"), rs.getString("id_number"));
                if (rs.getBoolean("has_voted")) {
                    voter.markVoted();
                }
                return voter;
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve voter: " + e.getMessage());
        }
    }

    @Override
    public Candidate getCandidate(String idNumber) {
        String sql = "SELECT * FROM candidates WHERE id_number = ?";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, idNumber);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                Candidate candidate = new Candidate(
                        rs.getString("name"),
                        rs.getString("id_number"),
                        rs.getString("position"));
                candidate.setVoteCount(rs.getInt("vote_count"));
                return candidate;
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve candidate: " + e.getMessage());
        }
    }

    @Override
    public List<Candidate> getAllCandidates() {
        List<Candidate> candidates = new ArrayList<>();
        String sql = "SELECT * FROM candidates";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Candidate candidate = new Candidate(
                        rs.getString("name"),
                        rs.getString("id_number"),
                        rs.getString("position"));
                candidate.setVoteCount(rs.getInt("vote_count"));
                candidates.add(candidate);
            }
            return candidates;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve candidates: " + e.getMessage());
        }
    }

    public void updateVoter(Voter voter) {
        String sql = "UPDATE voters SET has_voted = ? WHERE id_number = ?";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setBoolean(1, voter.hasVoted());
            pstmt.setString(2, voter.getIdNumber());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update voter: " + e.getMessage());
        }
    }

    public void updateCandidate(Candidate candidate) {
        String sql = "UPDATE candidates SET vote_count = ? WHERE id_number = ?";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, candidate.getVoteCount());
            pstmt.setString(2, candidate.getIdNumber());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to update candidate: " + e.getMessage());
        }
    }

    public void saveVote(String voterId, String candidateId) {
        String sql = "INSERT INTO votes (voter_id, candidate_id) VALUES (?, ?)";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, voterId);
            pstmt.setString(2, candidateId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to save vote: " + e.getMessage());
        }
    }

    public List<Map<String, String>> getVoteHistory() {
        List<Map<String, String>> history = new ArrayList<>();
        String sql = "SELECT v.voter_id, v.candidate_id, v.vote_time, " +
                     "vtr.name AS voter_name, c.name AS candidate_name, c.position " +
                     "FROM votes v " +
                     "JOIN voters vtr ON v.voter_id = vtr.id_number " +
                     "JOIN candidates c ON v.candidate_id = c.id_number " +
                     "ORDER BY v.vote_time DESC";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Map<String, String> vote = new HashMap<>();
                vote.put("voter_id", rs.getString("voter_id"));
                vote.put("voter_name", rs.getString("voter_name"));
                vote.put("candidate_id", rs.getString("candidate_id"));
                vote.put("candidate_name", rs.getString("candidate_name"));
                vote.put("position", rs.getString("position"));
                vote.put("vote_time", rs.getTimestamp("vote_time").toString());
                history.add(vote);
            }
            return history;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve vote history: " + e.getMessage());
        }
    }

    public List<Voter> getAllVoters() {
        List<Voter> voters = new ArrayList<>();
        String sql = "SELECT * FROM voters";
        try (Connection conn = DriverManager.getConnection(url, username, password);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Voter voter = new Voter(
                        rs.getString("name"),
                        rs.getString("id_number"));
                if (rs.getBoolean("has_voted")) {
                    voter.markVoted();
                }
                voters.add(voter);
            }
            return voters;
        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve voters: " + e.getMessage());
        }
    }
}