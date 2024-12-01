package PROJECT;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.awt.*;
import java.awt.event.*;

public class AskGUI extends JFrame {
    private JComboBox<String> locationComboBox;
    private JButton checkButton, requestButton, backButton;
    private JList<String> offerList;
    private DefaultListModel<String> listModel;
    private JTextArea detailsArea;
    private JTextArea statusArea;
    private RideSharePage parent;
    private Offer selectedOffer;
    private JLabel rideStatusLabel;

    public AskGUI(RideSharePage parent) {
        this.parent = parent;
        setTitle("Ask for a Ride");

        // Set screen size to 50% of desktop size
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize.width / 2, screenSize.height / 2);

        // Set black background
        getContentPane().setBackground(Color.BLACK);
        setLayout(new BorderLayout());

        // Top panel with location combobox and check button
        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.setBackground(Color.BLACK);
        String[] locations = {"Abu Dhabi", "Dubai", "Sharjah", "Ajman", "Umm Al Quwain", "Ras Al Khaimah", "Fujairah"};
        locationComboBox = new JComboBox<>(locations);
        locationComboBox.setPreferredSize(new Dimension(200, 30));
        locationComboBox.setFont(new Font("Arial", Font.PLAIN, 16));

        // Round button for "Check"
        checkButton = createRoundedButton("Check");
        checkButton.addActionListener(this::fetchOffers);

        topPanel.add(locationComboBox);
        topPanel.add(checkButton);
        add(topPanel, BorderLayout.NORTH);

        // List to display offers
        listModel = new DefaultListModel<>();
        offerList = new JList<>(listModel);
        offerList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        offerList.setFont(new Font("Arial", Font.PLAIN, 16));
        offerList.setBackground(Color.DARK_GRAY);
        offerList.setForeground(Color.WHITE);
        offerList.addListSelectionListener(this::displayOfferDetails);
        JScrollPane listScrollPane = new JScrollPane(offerList);
        add(listScrollPane, BorderLayout.CENTER);

        // Bottom panel with status, offer details, and buttons
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));  // Vertical stacking
        bottomPanel.setBackground(Color.BLACK);

        // Status area
        statusArea = new JTextArea(3, 40);
        statusArea.setEditable(false);
        statusArea.setForeground(Color.RED);  // Red color for status
        statusArea.setFont(new Font("Arial", Font.PLAIN, 16));
        statusArea.setBackground(Color.DARK_GRAY);
        bottomPanel.add(new JScrollPane(statusArea));

        // Details area
        detailsArea = new JTextArea();
        detailsArea.setEditable(false);
        detailsArea.setFont(new Font("Arial", Font.PLAIN, 16));
        detailsArea.setBackground(Color.DARK_GRAY);
        detailsArea.setForeground(Color.WHITE);
        bottomPanel.add(new JScrollPane(detailsArea));

        detailsArea.setPreferredSize(new Dimension(450, 150)); // Set a bigger area for the details section
        statusArea.setPreferredSize(new Dimension(450, 30));

        // Request button
        requestButton = createRoundedButton("Request Ride");
        requestButton.setEnabled(false);  // Initially disabled
        requestButton.addActionListener(this::sendRequest);
        bottomPanel.add(requestButton);

        // Back button
        backButton = createRoundedButton("Back");
        backButton.addActionListener(e -> goBackToInbox());
        bottomPanel.add(backButton);

        rideStatusLabel = new JLabel("", SwingConstants.CENTER);
        rideStatusLabel.setForeground(Color.RED); // Set the color to red
        rideStatusLabel.setFont(new Font("Arial", Font.BOLD, 16));
        bottomPanel.add(rideStatusLabel);

        // Add bottomPanel to the frame
        add(bottomPanel, BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Creates a rounded button with custom styling
    private JButton createRoundedButton(String text) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(60, 63, 65)); // Button background color
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20); // Rounded corners
                super.paintComponent(g2);
                g2.dispose();
            }

            @Override
            public void setContentAreaFilled(boolean b) {
                // Override to avoid filling the background
            }
        };
        button.setForeground(Color.WHITE);  // Text color
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Padding
        button.setOpaque(false);
        return button;
    }

    private void fetchOffers(ActionEvent e) {
        String location = (String) locationComboBox.getSelectedItem();
        statusArea.setForeground(Color.RED);  // Set the text color to red
        statusArea.setText("Searching for offers in: " + location);  // Display searching message
        updateOfferList(location);
    }

    private void updateOfferList(String location) {
        java.util.List<Offer> offers = OfferManager.getOffersByLocation(location);
        listModel.clear(); // Clear existing list

        if (offers.isEmpty()) {
            rideStatusLabel.setText("No rides available for " + location + ".");
        } else {
            rideStatusLabel.setText("Found " + offers.size() + " offers for " + location + ".");
        }

        // Update status area with found offers message
        statusArea.setText("Found offers: " + offers.size());

        // Add offers to the list
        for (Offer offer : offers) {
            listModel.addElement(offer.getCarType() + " - " + offer.getLocation() + " - $" + offer.getFarePerMeter());
        }
    }

    private void displayOfferDetails(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting() && !offerList.isSelectionEmpty()) {
            // Get the selected location and the list of offers for that location
            String location = (String) locationComboBox.getSelectedItem();
            java.util.List<Offer> offers = OfferManager.getOffersByLocation(location);

            // Get the selected offer from the list
            selectedOffer = offers.get(offerList.getSelectedIndex());

            // Display the ride details in the detailsArea
            detailsArea.setText("Car Type: " + selectedOffer.getCarType() +
                                "\nSeats: " + selectedOffer.getNumberOfSeats() +
                                "\nFare: $" + selectedOffer.getFarePerMeter() +
                                "\nMobile: " + selectedOffer.getMobileNumber() +
                                "\nLocation: " + selectedOffer.getLocation() +
                                "\n\nDo you want to reserve a seat?");

            // Enable the "Request Ride" button when an offer is selected
            requestButton.setEnabled(true);
        }
    }

    private void sendRequest(ActionEvent e) {
        if (selectedOffer != null) {
            String sender = parent.getUsername();  // Get the logged-in user
            String receiver = selectedOffer.getUsername();
            String content = "Ride request for " + selectedOffer.getCarType() + " from " + sender;

            Message message = new Message(sender, receiver, content, false);
            MessageManager.addMessage(message);  // Add to a central MessageManager
            JOptionPane.showMessageDialog(this, "Request sent to " + receiver, "Request Sent", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void goBackToInbox() {
        this.dispose();  // Close the AskGUI window
        parent.setVisible(true);  // Make the parent (RideSharePage) visible again
    }
}
