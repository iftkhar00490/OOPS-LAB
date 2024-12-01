package PROJECT;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class RoundedButton extends JButton {

    // Constructor to set the text of the button
    public RoundedButton(String text) {
        super(text);
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setForeground(Color.WHITE);  // Set text color (white for visibility on dark background)
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (getModel().isPressed()) {
            g.setColor(new Color(0, 0, 0, 150));  // Color when button is pressed
        } else {
            g.setColor(new Color(0, 0, 0, 200));  // Default color when not pressed
        }
        g.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);  // Rounded corners
        super.paintComponent(g);
    }

    @Override
    public void addActionListener(ActionListener l) {
        super.addActionListener(l);  // Add action listener as usual
    }
}
