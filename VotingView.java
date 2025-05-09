package package01;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Map;

public class VotingView {
    private VotingController controller;
    private JFrame frame;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private static final Color PRIMARY_COLOR = new Color(33, 150, 243);
    private static final Color SECONDARY_COLOR = new Color(240, 240, 240);
    private static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 24);
    private static final Font LABEL_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font BUTTON_FONT = new Font("Segoe UI", Font.BOLD, 14);

    public VotingView(VotingController controller) {
        this.controller = controller;
        initializeUI();
    }

    private void initializeUI() {
        frame = new JFrame("Electronic Voting System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setMinimumSize(new Dimension(600, 400));

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setBackground(Color.WHITE);

        JPanel menuPanel = createMenuPanel();
        JPanel voterPanel = createVoterRegistrationPanel();
        JPanel candidatePanel = createCandidateRegistrationPanel();
        JPanel votePanel = createVotePanel();
        JPanel resultsPanel = createResultsPanel();
        JPanel adminMenuPanel = createAdminMenuPanel();
        JPanel adminLoginPanel = createAdminLoginPanel();
        JPanel votersPanel = createVotersPanel();
        JPanel candidatesPanel = createCandidatesPanel();
        JPanel voteHistoryPanel = createVoteHistoryPanel();
        JPanel candidatesViewPanel = createCandidatesViewPanel();

        mainPanel.add(createTitledPanel(menuPanel, "Main Menu"), "Menu");
        mainPanel.add(createTitledPanel(voterPanel, "Voter Registration"), "Voter");
        mainPanel.add(createTitledPanel(candidatePanel, "Candidate Registration"), "Candidate");
        mainPanel.add(createTitledPanel(votePanel, "Cast Vote"), "Vote");
        mainPanel.add(createTitledPanel(resultsPanel, "Election Results"), "Results");
        mainPanel.add(createTitledPanel(adminLoginPanel, "Admin Login"), "AdminLogin");
        mainPanel.add(createTitledPanel(adminMenuPanel, "Admin Dashboard"), "AdminMenu");
        mainPanel.add(createTitledPanel(votersPanel, "Voter Information"), "Voters");
        mainPanel.add(createTitledPanel(candidatesPanel, "Candidate Information"), "Candidates");
        mainPanel.add(createTitledPanel(voteHistoryPanel, "Vote History"), "VoteHistory");
        mainPanel.add(createTitledPanel(candidatesViewPanel, "View Candidates"), "CandidatesView");

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private JPanel createTitledPanel(JPanel contentPanel, String title) {
        JPanel titledPanel = new JPanel(new BorderLayout());
        titledPanel.setBackground(Color.WHITE);

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(PRIMARY_COLOR);
        headerPanel.setBorder(new EmptyBorder(10, 20, 10, 20));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel, BorderLayout.WEST);

        titledPanel.add(headerPanel, BorderLayout.NORTH);
        titledPanel.add(contentPanel, BorderLayout.CENTER);
        return titledPanel;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(BUTTON_FONT);
        button.setBackground(PRIMARY_COLOR);
        button.setForeground(Color.BLACK);
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(PRIMARY_COLOR.brighter());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(PRIMARY_COLOR);
            }
        });

        return button;
    }

    private JPanel createMenuPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(30, 30, 30, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 0, 10, 0);

        String[] buttonLabels = {
                "Register Voter",
                "Register Candidate",
                "Cast Vote",
                "View Candidates",
                "View Results",
                "Admin Login",
                "Exit"
        };

        for (String label : buttonLabels) {
            JButton button = createStyledButton(label);
            button.setToolTipText(label);

            if (label.equals("Exit")) {
                button.setBackground(new Color(220, 53, 69));
                button.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        button.setBackground(new Color(220, 53, 69).brighter());
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        button.setBackground(new Color(220, 53, 69));
                    }
                });
            }

            button.addActionListener(e -> {
                switch (label) {
                    case "Register Voter":
                        cardLayout.show(mainPanel, "Voter");
                        break;
                    case "Register Candidate":
                        cardLayout.show(mainPanel, "Candidate");
                        break;
                    case "Cast Vote":
                        cardLayout.show(mainPanel, "Vote");
                        break;
                    case "View Candidates":
                        cardLayout.show(mainPanel, "CandidatesView");
                        break;
                    case "View Results":
                        cardLayout.show(mainPanel, "Results");
                        break;
                    case "Admin Login":
                        cardLayout.show(mainPanel, "AdminLogin");
                        break;
                    case "Exit":
                        System.exit(0);
                        break;
                }
            });

            panel.add(button, gbc);
        }

        return panel;
    }

    private JPanel createVoterRegistrationPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(LABEL_FONT);
        JTextField nameField = new JTextField(20);
        nameField.setToolTipText("Enter full name");

        JLabel idLabel = new JLabel("ID Number (8 digits):");
        idLabel.setFont(LABEL_FONT);
        JTextField idField = new JTextField(20);
        idField.setToolTipText("Enter 8-digit ID number");

        JButton submitBtn = createStyledButton("Register");
        JButton backBtn = createStyledButton("Back");

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(nameLabel, gbc);
        gbc.gridx = 1;
        panel.add(nameField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(idLabel, gbc);
        gbc.gridx = 1;
        panel.add(idField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(submitBtn);
        buttonPanel.add(backBtn);
        panel.add(buttonPanel, gbc);

        submitBtn.addActionListener(e -> {
            try {
                if (nameField.getText().trim().isEmpty()) {
                    throw new VotingException("Name cannot be empty");
                }
                controller.registerVoter(nameField.getText(), idField.getText());
                JOptionPane.showMessageDialog(frame, "Voter registered successfully!",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                nameField.setText("");
                idField.setText("");
            } catch (VotingException ex) {
                JOptionPane.showMessageDialog(frame, ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "Menu"));

        return panel;
    }

    private JPanel createCandidateRegistrationPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(LABEL_FONT);
        JTextField nameField = new JTextField(20);
        nameField.setToolTipText("Enter full name");

        JLabel idLabel = new JLabel("ID Number (8 digits):");
        idLabel.setFont(LABEL_FONT);
        JTextField idField = new JTextField(20);
        idField.setToolTipText("Enter 8-digit ID number");

        JLabel positionLabel = new JLabel("Position:");
        positionLabel.setFont(LABEL_FONT);
        JTextField positionField = new JTextField(20);
        positionField.setToolTipText("Enter position title");

        JButton submitBtn = createStyledButton("Register");
        JButton backBtn = createStyledButton("Back");

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(nameLabel, gbc);
        gbc.gridx = 1;
        panel.add(nameField, gbc);
        gbc.gridx =  0;
        gbc.gridy = 1;
        panel.add(idLabel, gbc);
        gbc.gridx = 1;
        panel.add(idField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(positionLabel, gbc);
        gbc.gridx = 1;
        panel.add(positionField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(submitBtn);
        buttonPanel.add(backBtn);
        panel.add(buttonPanel, gbc);

        submitBtn.addActionListener(e -> {
            try {
                if (nameField.getText().trim().isEmpty() || positionField.getText().trim().isEmpty()) {
                    throw new VotingException("Name and position cannot be empty");
                }
                controller.registerCandidate(nameField.getText(), idField.getText(), positionField.getText());
                JOptionPane.showMessageDialog(frame, "Candidate registered successfully!",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                nameField.setText("");
                idField.setText("");
                positionField.setText("");
            } catch (VotingException ex) {
                JOptionPane.showMessageDialog(frame, ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "Menu"));

        return panel;
    }

    private JPanel createVotePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel voterIdLabel = new JLabel("Voter ID:");
        voterIdLabel.setFont(LABEL_FONT);
        JTextField voterIdField = new JTextField(20);
        voterIdField.setToolTipText("Enter your 8-digit voter ID");

        JLabel candidateIdLabel = new JLabel("Candidate ID:");
        candidateIdLabel.setFont(LABEL_FONT);
        JTextField candidateIdField = new JTextField(20);
        candidateIdField.setToolTipText("Enter candidate's 8-digit ID");

        JButton submitBtn = createStyledButton("Cast Vote");
        JButton backBtn = createStyledButton("Back");

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(voterIdLabel, gbc);
        gbc.gridx = 1;
        panel.add(voterIdField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(candidateIdLabel, gbc);
        gbc.gridx = 1;
        panel.add(candidateIdField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(submitBtn);
        buttonPanel.add(backBtn);
        panel.add(buttonPanel, gbc);

        submitBtn.addActionListener(e -> {
            try {
                controller.castVote(voterIdField.getText(), candidateIdField.getText());
                JOptionPane.showMessageDialog(frame, "Vote cast successfully!",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                voterIdField.setText("");
                candidateIdField.setText("");
            } catch (VotingException ex) {
                JOptionPane.showMessageDialog(frame, ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "Menu"));

        return panel;
    }

    private JPanel createResultsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JTextArea resultsArea = new JTextArea();
        resultsArea.setEditable(false);
        resultsArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        resultsArea.setBackground(SECONDARY_COLOR);
        resultsArea.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));

        JScrollPane scrollPane = new JScrollPane(resultsArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        JButton refreshBtn = createStyledButton("Refresh Results");
        JButton backBtn = createStyledButton("Back");

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(refreshBtn);
        buttonPanel.add(backBtn);

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        refreshBtn.addActionListener(e -> {
            StringBuilder sb = new StringBuilder();
            Map<String, List<Map<String, Object>>> results = controller.getResults();
            for (Map.Entry<String, List<Map<String, Object>>> entry : results.entrySet()) {
                sb.append("Results for ").append(entry.getKey()).append(":\n");
                sb.append("----------------------------------------\n");
                for (Map<String, Object> candidate : entry.getValue()) {
                    sb.append(String.format("%-30s: %d votes\n",
                            candidate.get("name"), candidate.get("votes")));
                }
                sb.append("\n");
            }
            resultsArea.setText(sb.toString());
        });

        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "Menu"));

        return panel;
    }

    private JPanel createAdminLoginPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(LABEL_FONT);
        JTextField usernameField = new JTextField(20);
        usernameField.setToolTipText("Enter admin username");

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(LABEL_FONT);
        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setToolTipText("Enter admin password");

        JButton loginBtn = createStyledButton("Login");
        JButton backBtn = createStyledButton("Back");

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(usernameLabel, gbc);
        gbc.gridx = 1;
        panel.add(usernameField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(passwordLabel, gbc);
        gbc.gridx = 1;
        panel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(loginBtn);
        buttonPanel.add(backBtn);
        panel.add(buttonPanel, gbc);

        loginBtn.addActionListener(e -> {
            if (controller.authenticateAdmin(usernameField.getText(),
                    new String(passwordField.getPassword()))) {
                cardLayout.show(mainPanel, "AdminMenu");
                JOptionPane.showMessageDialog(frame, "Admin login successful!",
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                usernameField.setText("");
                passwordField.setText("");
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid credentials",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "Menu"));

        return panel;
    }

    private JPanel createAdminMenuPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(30, 30, 30, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 0, 10, 0);

        String[] buttonLabels = {
                "View Voter Information",
                "View Candidate Information",
                "View Vote History",
                "View Election Results",
                "Back to Main Menu"
        };

        for (String label : buttonLabels) {
            JButton button = createStyledButton(label);
            button.setToolTipText(label);

            if (label.equals("Back to Main Menu")) {
                button.setBackground(new Color(220, 53, 69));
                button.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        button.setBackground(new Color(220, 53, 69).brighter());
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        button.setBackground(new Color(220, 53, 69));
                    }
                });
            }

            button.addActionListener(e -> {
                switch (label) {
                    case "View Voter Information":
                        cardLayout.show(mainPanel, "Voters");
                        break;
                    case "View Candidate Information":
                        cardLayout.show(mainPanel, "Candidates");
                        break;
                    case "View Vote History":
                        cardLayout.show(mainPanel, "VoteHistory");
                        break;
                    case "View Election Results":
                        cardLayout.show(mainPanel, "Results");
                        break;
                    case "Back to Main Menu":
                        cardLayout.show(mainPanel, "Menu");
                        break;
                }
            });

            panel.add(button, gbc);
        }

        return panel;
    }

    private JPanel createVotersPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"ID Number", "Name", "Person ID", "Has Voted"}, 0);
        JTable table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.setRowHeight(25);
        table.setGridColor(new Color(200, 200, 200));
        table.setBackground(SECONDARY_COLOR);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        JButton refreshBtn = createStyledButton("Refresh");
        JButton backBtn = createStyledButton("Back");

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(refreshBtn);
        buttonPanel.add(backBtn);

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        refreshBtn.addActionListener(e -> {
            model.setRowCount(0);
            List<Voter> voters = controller.getAllVoters();
            for (Voter voter : voters) {
                model.addRow(new Object[]{
                        voter.getIdNumber(),
                        voter.getName(),
                        voter.getPersonId(),
                        voter.hasVoted() ? "Yes" : "No"
                });
            }
        });

        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "AdminMenu"));

        return panel;
    }

    private JPanel createCandidatesPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"ID Number", "Name", "Person ID", "Position", "Vote Count"}, 0);
        JTable table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.setRowHeight(25);
        table.setGridColor(new Color(200, 200, 200));
        table.setBackground(SECONDARY_COLOR);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        JButton refreshBtn = createStyledButton("Refresh");
        JButton backBtn = createStyledButton("Back");

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(refreshBtn);
        buttonPanel.add(backBtn);

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        refreshBtn.addActionListener(e -> {
            model.setRowCount(0);
            List<Candidate> candidates = controller.getAllCandidates();
            for (Candidate candidate : candidates) {
                model.addRow(new Object[]{
                        candidate.getIdNumber(),
                        candidate.getName(),
                        candidate.getPersonId(),
                        candidate.getPosition(),
                        candidate.getVoteCount()
                });
            }
        });

        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "AdminMenu"));

        return panel;
    }

    private JPanel createVoteHistoryPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"Voter ID", "Voter Name", "Candidate ID", "Candidate Name", "Position", "Vote Time"}, 0);
        JTable table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.setRowHeight(25);
        table.setGridColor(new Color(200, 200, 200));
        table.setBackground(SECONDARY_COLOR);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        JButton refreshBtn = createStyledButton("Refresh");
        JButton backBtn = createStyledButton("Back");

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(refreshBtn);
        buttonPanel.add(backBtn);

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        refreshBtn.addActionListener(e -> {
            model.setRowCount(0);
            List<Map<String, String>> history = controller.getVoteHistory();
            for (Map<String, String> vote : history) {
                model.addRow(new Object[]{
                        vote.get("voter_id"),
                        vote.get("voter_name"),
                        vote.get("candidate_id"),
                        vote.get("candidate_name"),
                        vote.get("position"),
                        vote.get("vote_time")
                });
            }
        });

        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "AdminMenu"));

        return panel;
    }

    private JPanel createCandidatesViewPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"ID Number", "Name", "Position"}, 0);
        JTable table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.setRowHeight(25);
        table.setGridColor(new Color(200, 200, 200));
        table.setBackground(SECONDARY_COLOR);
        table.setEnabled(false); // Read-only for voters

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        JButton refreshBtn = createStyledButton("Refresh");
        JButton backBtn = createStyledButton("Back");

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(refreshBtn);
        buttonPanel.add(backBtn);

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        refreshBtn.addActionListener(e -> {
            model.setRowCount(0);
            List<Candidate> candidates = controller.getAllCandidates();
            for (Candidate candidate : candidates) {
                model.addRow(new Object[]{
                        candidate.getIdNumber(),
                        candidate.getName(),
                        candidate.getPosition()
                });
            }
        });

        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "Menu"));

        return panel;
    }
}