package PROJECT;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class InboxGUI extends JFrame {
    private JList<String> messageList;
    private RideSharePage parent;

    public InboxGUI(RideSharePage parent) {
        this.parent = parent;
        setTitle("Inbox");
        setSize(400, 300);
        setLayout(new BorderLayout());

        // Set dark theme background
        getContentPane().setBackground(Color.BLACK);

        // Create message list
        messageList = new JList<>();
        messageList.setBackground(Color.BLACK);  // Set background to black
        messageList.setForeground(Color.WHITE);  // Set text color to white
        messageList.setFont(new Font("Arial", Font.PLAIN, 14));  // Font style

        JScrollPane scrollPane = new JScrollPane(messageList);
        scrollPane.setBackground(Color.BLACK);  // Set background of scroll pane to black

        // Create and style back button with rounded corners
        RoundedButton backButton = new RoundedButton("Back");
        backButton.addActionListener(e -> {
            this.dispose();
            parent.setVisible(true);
        });

        // Add components to layout
        add(scrollPane, BorderLayout.CENTER);
        add(backButton, BorderLayout.SOUTH);

        // Load messages from the MessageManager
        loadMessages();

        // Set the window location and make it visible
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void loadMessages() {
        String username = parent.getUsername();
        List<Message> messages = MessageManager.getMessagesForUser(username);
        DefaultListModel<String> model = new DefaultListModel<>();

        // Add messages to the list with custom formatting
        for (Message message : messages) {
            model.addElement((message.isRideRequest() ? "[Ride Request] " : "[Message] ") +
                    "From: " + message.getSender() + " - " + message.getContent());
        }

        messageList.setModel(model);
    }
}
