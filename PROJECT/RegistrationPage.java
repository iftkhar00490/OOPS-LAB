package PROJECT;
import javax.swing.*;
import java.awt.*;

public class RegistrationPage extends JFrame {
    private JTextField usernameField, emailField;
    private JPasswordField passwordField;
    private JButton submitButton, cancelButton;

    public RegistrationPage() {
        setTitle("Register");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLayout(null); // Using absolute positioning for full customization

        // Set background color to black
        getContentPane().setBackground(Color.BLACK);

        // Initialize components with custom styling
        usernameField = createRoundedTextField("Username");
        emailField = createRoundedTextField("Email ID");
        passwordField = createRoundedPasswordField("Password");
        submitButton = createStyledButton("Submit");
        cancelButton = createStyledButton("Cancel");

        // Set bounds for components
        usernameField.setBounds(100, 50, 200, 30);
        emailField.setBounds(100, 100, 200, 30);
        passwordField.setBounds(100, 150, 200, 30);
        submitButton.setBounds(100, 200, 90, 30);
        cancelButton.setBounds(210, 200, 90, 30);

        // Add components to the JFrame
        add(usernameField);
        add(emailField);
        add(passwordField);
        add(submitButton);
        add(cancelButton);

        // Add action listeners for buttons
        submitButton.addActionListener(e -> registerAccount());
        cancelButton.addActionListener(e -> cancelRegistration());

        // Center the frame on screen
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Helper method to create rounded text fields with placeholders
    private JTextField createRoundedTextField(String placeholder) {
        JTextField textField = new JTextField(placeholder) {
            @Override
            protected void paintComponent(Graphics g) {
                if (g instanceof Graphics2D) {
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(Color.DARK_GRAY);
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                    super.paintComponent(g);
                }
            }
        };
        textField.setOpaque(false);
        textField.setForeground(Color.LIGHT_GRAY);
        textField.setFont(new Font("Roboto", Font.PLAIN, 14));
        textField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // Padding inside the text field
        textField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(Color.WHITE);
                }
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                if (textField.getText().isEmpty()) {
                    textField.setForeground(Color.LIGHT_GRAY);
                    textField.setText(placeholder);
                }
            }
        });
        return textField;
    }

    // Helper method to create rounded password fields
    private JPasswordField createRoundedPasswordField(String placeholder) {
        JPasswordField passwordField = new JPasswordField(placeholder) {
            @Override
            protected void paintComponent(Graphics g) {
                if (g instanceof Graphics2D) {
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(Color.DARK_GRAY);
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                    super.paintComponent(g);
                }
            }
        };
        passwordField.setOpaque(false);
        passwordField.setForeground(Color.LIGHT_GRAY);
        passwordField.setFont(new Font("Roboto", Font.PLAIN, 14));
        passwordField.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // Padding inside the password field
        passwordField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (new String(passwordField.getPassword()).equals(placeholder)) {
                    passwordField.setText("");
                    passwordField.setForeground(Color.WHITE);
                }
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                if (new String(passwordField.getPassword()).isEmpty()) {
                    passwordField.setForeground(Color.LIGHT_GRAY);
                    passwordField.setText(placeholder);
                }
            }
        });
        return passwordField;
    }

    // Helper method to create styled buttons
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(Color.WHITE);
        button.setForeground(Color.BLACK);
        button.setFont(new Font("Roboto", Font.BOLD, 12));
        button.setFocusPainted(false); // Remove focus border
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        return button;
    }

    // Method to handle registration logic
    private void registerAccount() {
        String username = usernameField.getText();
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());

        if (!email.endsWith("bits-pilani.ac.in")) {
            JOptionPane.showMessageDialog(this, "Use your BITS Email ID", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (CredentialsManager.registerNewUser(username, email, password)) {
            JOptionPane.showMessageDialog(this, "Registration successful.");
            dispose(); // Close the registration window on success
            new LoginPage().setVisible(true); // Optionally open the login page
        } else {
            JOptionPane.showMessageDialog(this, "Username already exists or registration failed.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to handle cancel action
    private void cancelRegistration() {
        dispose(); // Close the registration window
        new LoginPage().setVisible(true); // Open the login page
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(RegistrationPage::new);
    }
}
