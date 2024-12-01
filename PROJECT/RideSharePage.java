package PROJECT;
import javax.swing.*;
import java.awt.*;

public class RideSharePage extends JFrame {
    private JLabel usernameLabel;
    private String username;

    public RideSharePage() {
        setTitle("RideShare Options");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600); // Default size, scalable
        setLayout(new BorderLayout(10, 10));

        // Top panel for username display and additional buttons
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.GRAY);
        usernameLabel = new JLabel("Logged in as: ");
        usernameLabel.setForeground(Color.WHITE);
        topPanel.add(usernameLabel, BorderLayout.WEST);

        JPanel rightButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton inboxButton = new JButton("Inbox");
        JButton logoutButton = new JButton("Logout");
        styleButton(inboxButton);
        styleButton(logoutButton);
        rightButtonPanel.add(inboxButton);
        rightButtonPanel.add(logoutButton);

        topPanel.add(rightButtonPanel, BorderLayout.EAST);

        // Central panel for main buttons
        JPanel centerPanel = new JPanel(null); // Absolute positioning for flexibility
        centerPanel.setBackground(Color.BLACK);

        // Create main buttons
        JButton offerButton = new JButton("Offer a Ride");
        JButton askButton = new JButton("Ask for a Ride");

        // Style main buttons
        styleButton(offerButton);
        styleButton(askButton);

        // Add buttons to the central panel
        centerPanel.add(offerButton);
        centerPanel.add(askButton);

        // Add resize listener for dynamic positioning
        addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent evt) {
                resizeComponents(centerPanel, offerButton, askButton);
            }
        });

        // Add panels to the frame
        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);

        // Add button functionality
        offerButton.addActionListener(e -> openOfferGUI());
        askButton.addActionListener(e -> openAskGUI());
        inboxButton.addActionListener(e -> openInboxGUI());
        logoutButton.addActionListener(e -> logout());

        setLocationRelativeTo(null); // Center the frame on the screen
        setVisible(true);
    }

    private void styleButton(JButton button) {
        button.setBackground(Color.DARK_GRAY);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.LIGHT_GRAY); // Hover effect
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.DARK_GRAY); // Reset hover effect
            }
        });
    }

    private void resizeComponents(JPanel panel, JButton offerButton, JButton askButton) {
        int panelWidth = panel.getWidth();
        int panelHeight = panel.getHeight();

        // Calculate button width and height
        int buttonWidth = (int) (panelWidth * 0.35);
        int buttonHeight = (int) (panelHeight * 0.15);
        int padding = (int) (panelWidth * 0.1);

        // Position buttons dynamically
        int xCenter = (panelWidth - buttonWidth) / 2;
        int yOfferButton = padding;
        int yAskButton = padding + buttonHeight + 20;

        offerButton.setBounds(xCenter, yOfferButton, buttonWidth, buttonHeight);
        askButton.setBounds(xCenter, yAskButton, buttonWidth, buttonHeight);
    }

    public void setUsername(String username) {
        this.username = username;
        usernameLabel.setText("Logged in as: " + username);
    }

    public String getUsername() {
        return username;
    }

    private void openOfferGUI() {
        OfferGUI offerGUI = new OfferGUI(this);
        offerGUI.setVisible(true);
        this.setVisible(false);
    }

    private void openAskGUI() {
        AskGUI askGUI = new AskGUI(this);
        askGUI.setVisible(true);
        this.setVisible(false);
    }

    private void openInboxGUI() {
        InboxGUI inboxGUI = new InboxGUI(this);
        inboxGUI.setVisible(true);
        this.setVisible(false);
    }

    private void logout() {
        // Close any open child windows
        for (Frame frame : JFrame.getFrames()) {
            if (frame != this && frame.isVisible()) {
                frame.dispose();
            }
        }
        dispose();
        LoginPage loginPage = new LoginPage();
        loginPage.setVisible(true);
    }

    public static void main(String[] args) {
        RideSharePage rideSharePage = new RideSharePage();
        rideSharePage.setUsername("JohnDoe"); // Example username
    }
}
