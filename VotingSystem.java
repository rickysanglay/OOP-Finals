package package01;

import javax.swing.*;

public class VotingSystem {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Replace with your MySQL connection details
            String url = "jdbc:mysql://localhost:3306/voting_system";
            String username = "root";
            String password = "Ee2ybgfi";
            Storage storage = new DatabaseStorage(url, username, password);
            VotingController controller = new VotingController(storage);
            new VotingView(controller);
        });
    }
}

