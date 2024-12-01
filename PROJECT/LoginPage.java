package PROJECT;

import javax.swing.*;
import javax.swing.plaf.basic.BasicTextFieldUI;
import javax.swing.text.JTextComponent;
import java.awt.*;

public class LoginPage extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton, registerButton, quitButton;
    private JLabel statusLabel;

    public LoginPage() {
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set size relative to screen size
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize.width / 2, screenSize.height / 2);

        // Create main panel with black background
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.BLACK);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);

        // Set Roboto font
        Font font = new Font("Roboto", Font.BOLD, 24);

        // Create components with placeholders and rounded corners
        usernameField = createRoundedTextField("Username", font);
        passwordField = createRoundedPasswordField("Password", font);

        loginButton = createRoundedButton("Login", font);
        registerButton = createRoundedButton("Register", font);
        quitButton = createRoundedButton("Quit", font);

        statusLabel = new JLabel("", SwingConstants.CENTER);
        statusLabel.setFont(font);
        statusLabel.setForeground(Color.RED);

        // Add components to the panel using GridBagLayout
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(usernameField, gbc);

        gbc.gridy = 1;
        mainPanel.add(passwordField, gbc);

        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(loginButton, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        mainPanel.add(registerButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(quitButton, gbc);

        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.NONE;
        mainPanel.add(statusLabel, gbc);

        // Action listeners
        loginButton.addActionListener(e -> performLogin());
        registerButton.addActionListener(e -> openRegistrationPage());
        quitButton.addActionListener(e -> System.exit(0));

        passwordField.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                if (e.getKeyCode() == java.awt.event.KeyEvent.VK_ENTER) {
                    performLogin();
                }
            }
        });

        add(mainPanel);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JTextField createRoundedTextField(String placeholder, Font font) {
        JTextField textField = new JTextField(15) {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (getText().isEmpty()) {
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setColor(Color.GRAY);
                    g2.setFont(getFont().deriveFont(Font.ITALIC));
                    g2.drawString(placeholder, 10, getHeight() - 10);
                }
            }
        };
        textField.setFont(font);
        textField.setOpaque(false);
        textField.setBackground(new Color(60, 63, 65));
        textField.setForeground(Color.WHITE);
        textField.setCaretColor(Color.WHITE);
        textField.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        textField.setPreferredSize(new Dimension(300, 40));
        textField.setUI(new RoundedTextFieldUI(30));
        return textField;
    }
    private JPasswordField createRoundedPasswordField(String placeholder, Font font) {
        JPasswordField passwordField = new JPasswordField(15) {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g); // Render the default JPasswordField behavior (including masking)
                
                // Draw placeholder ONLY if the field is empty and unfocused
                if (getPassword().length == 0 && !isFocusOwner()) {
                    Graphics2D g2 = (Graphics2D) g;
                    g2.setColor(Color.GRAY);
                    g2.setFont(getFont().deriveFont(Font.ITALIC));
                    g2.drawString(placeholder, 10, getHeight() - 10);
                }
            }
        };
        passwordField.setFont(font);
        passwordField.setOpaque(false);
        passwordField.setBackground(new Color(60, 63, 65));
        passwordField.setForeground(Color.WHITE);
        passwordField.setCaretColor(Color.WHITE);
        passwordField.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        passwordField.setPreferredSize(new Dimension(300, 40));
        passwordField.setUI(new RoundedTextFieldUI(30));
        return passwordField;
    }
    

    private JButton createRoundedButton(String text, Font font) {
        JButton button = new JButton(text);
        button.setFont(font);
        button.setBackground(new Color(60, 63, 65));
        button.setForeground(Color.GRAY);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        button.setPreferredSize(new Dimension(300, 40));
        return button;
    }

    private void performLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (CredentialsManager.validate(username, password)) {
            JOptionPane.showMessageDialog(this, "Login successful!");
            RideSharePage rideSharePage = new RideSharePage();
            rideSharePage.setUsername(username);  // Pass the username to RideSharePage
            rideSharePage.setVisible(true);
            dispose();  // Close the login window
        } else {
            JOptionPane.showMessageDialog(this, "Invalid credentials.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void openRegistrationPage() {
        new RegistrationPage(); // This opens the registration page
        dispose(); // Optionally close the current login page
    }
    

    public static void main(String[] args) {
        new LoginPage();
    }

    // RoundedTextFieldUI class for rounded corners
    private static class RoundedTextFieldUI extends BasicTextFieldUI {
        private final int cornerRadius;

        public RoundedTextFieldUI(int cornerRadius) {
            this.cornerRadius = cornerRadius;
        }

        @Override
        protected void paintSafely(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            JTextComponent component = getComponent();

            g2.setColor(component.getBackground()); 
            g2.fillRoundRect(0, 0, component.getWidth(), component.getHeight(), cornerRadius, cornerRadius);

            g2.setColor(component.getForeground());
            g2.drawRoundRect(0, 0, component.getWidth() - 1, component.getHeight() - 1, cornerRadius, cornerRadius);

            g2.dispose();
            super.paintSafely(g);
        }

        @Override
        protected void installDefaults() {
            super.installDefaults();
            getComponent().setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        }
    }
}
