package PROJECT;
import javax.swing.*;
import java.awt.*;

public class OfferGUI extends JFrame {
    private JTextField carNameField, seatsField, fareField, mobileNumberField;
    private JComboBox<String> emirateComboBox, mobilePrefixComboBox;
    private JButton submitButton, cancelButton;
    private RideSharePage parent;

    public OfferGUI(RideSharePage parent) {
        this.parent = parent;
        setTitle("Offer a Ride");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 500);
        setLayout(new BorderLayout(20, 20));

        // Colors for the dark theme
        Color bgColor = Color.BLACK;
        Color fgColor = Color.WHITE;
        Color buttonColor = new Color(0x1ABC9C); // Teal
        Color errorColor = new Color(0xE74C3C); // Red

        // Header Panel
        JLabel headerLabel = new JLabel("Offer a Ride", JLabel.CENTER);
        headerLabel.setFont(new Font("Roboto", Font.BOLD, 20));
        headerLabel.setForeground(fgColor);
        headerLabel.setBackground(bgColor);
        headerLabel.setOpaque(true);
        add(headerLabel, BorderLayout.NORTH);

        // Main Panel with input fields
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(bgColor);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        carNameField = createInputField(mainPanel, "Car Name:", bgColor, fgColor);
        seatsField = createInputField(mainPanel, "Number of Seats:", bgColor, fgColor);
        fareField = createInputField(mainPanel, "Fare per Kilometer:", bgColor, fgColor);

        // Emirate selection
        JLabel emirateLabel = new JLabel("Emirate:");
        styleLabel(emirateLabel, bgColor, fgColor);
        String[] emirates = {"(Select)", "Abu Dhabi", "Dubai", "Sharjah", "Ajman", "Umm Al Quwain", "Ras Al Khaimah", "Fujairah"};
        emirateComboBox = new JComboBox<>(emirates);
        styleComboBox(emirateComboBox, bgColor, fgColor);
        mainPanel.add(emirateLabel);
        mainPanel.add(emirateComboBox);

        // Mobile Number Panel
        JLabel mobileLabel = new JLabel("Mobile Number:");
        styleLabel(mobileLabel, bgColor, fgColor);
        JPanel mobilePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        mobilePanel.setBackground(bgColor);
        String[] mobileOptions = {"XXX", "050", "052", "053", "054", "055", "056", "058", "057"};
        mobilePrefixComboBox = new JComboBox<>(mobileOptions);
        styleComboBox(mobilePrefixComboBox, bgColor, fgColor);
        mobileNumberField = new JTextField(10);
        styleTextField(mobileNumberField, bgColor, fgColor);
        mobilePanel.add(mobilePrefixComboBox);
        mobilePanel.add(mobileNumberField);

        mainPanel.add(mobileLabel);
        mainPanel.add(mobilePanel);

        add(mainPanel, BorderLayout.CENTER);

        // Footer Panel with buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(bgColor);
        submitButton = new JButton("Submit");
        styleButton(submitButton, buttonColor, fgColor);
        submitButton.addActionListener(e -> submitOffer());
        cancelButton = new JButton("Cancel");
        styleButton(cancelButton, errorColor, fgColor);
        cancelButton.addActionListener(e -> {
            this.dispose();
            parent.setVisible(true); // Make RideSharePage visible again
        });

        buttonPanel.add(submitButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JTextField createInputField(JPanel parent, String labelText, Color bgColor, Color fgColor) {
        JLabel label = new JLabel(labelText);
        styleLabel(label, bgColor, fgColor);
        JTextField textField = new JTextField(20);
        styleTextField(textField, bgColor, fgColor);
        parent.add(label);
        parent.add(textField);
        return textField;
    }

    private void styleLabel(JLabel label, Color bgColor, Color fgColor) {
        label.setFont(new Font("Roboto", Font.PLAIN, 14));
        label.setForeground(fgColor);
        label.setBackground(bgColor);
        label.setOpaque(true);
    }

    private void styleTextField(JTextField textField, Color bgColor, Color fgColor) {
        textField.setFont(new Font("Roboto", Font.PLAIN, 14));
        textField.setForeground(fgColor);
        textField.setBackground(bgColor);
        textField.setCaretColor(fgColor);
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0xBDC3C7), 1),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)
        ));
    }

    private void styleComboBox(JComboBox<String> comboBox, Color bgColor, Color fgColor) {
        comboBox.setFont(new Font("Roboto", Font.PLAIN, 14));
        comboBox.setForeground(fgColor);
        comboBox.setBackground(bgColor);
        comboBox.setBorder(BorderFactory.createLineBorder(new Color(0xBDC3C7), 1));
    }

    private void styleButton(JButton button, Color bgColor, Color fgColor) {
        button.setFont(new Font("Roboto", Font.BOLD, 14));
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    }

    private void submitOffer() {
        if (carNameField.getText().isEmpty() ||
            seatsField.getText().isEmpty() ||
            fareField.getText().isEmpty() ||
            emirateComboBox.getSelectedIndex() == 0 ||
            mobilePrefixComboBox.getSelectedIndex() == 0 ||
            mobileNumberField.getText().length() != 7) {

            JOptionPane.showMessageDialog(this, "Please complete all fields correctly!", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            String carType = carNameField.getText();
            int numberOfSeats = Integer.parseInt(seatsField.getText());
            double farePerMeter = Double.parseDouble(fareField.getText());
            String location = (String) emirateComboBox.getSelectedItem();
            String mobileNumber = mobilePrefixComboBox.getSelectedItem() + mobileNumberField.getText();

            Offer offer = new Offer(carType, location, farePerMeter, numberOfSeats, mobileNumber, parent.getUsername());
            OfferManager.addOffer(offer);

            JOptionPane.showMessageDialog(this, "Offer successfully added!", "Success", JOptionPane.INFORMATION_MESSAGE);

            this.dispose();
            parent.setVisible(true); // Return to the parent RideSharePage
        }
    }
}
