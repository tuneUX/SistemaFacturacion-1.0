package def;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.extras.FlatAnimatedLafChange;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Style extends JButton {

    private Color[] colors = {Color.RED, Color.YELLOW, Color.WHITE, Color.MAGENTA, Color.CYAN, Color.PINK};
    private int index = 0;
    private Timer timer;
    private Color baseColor;

    public static void DarkTheme() {
        FlatAnimatedLafChange.showSnapshot();
        FlatDarkLaf.setup();
        FlatAnimatedLafChange.hideSnapshotWithAnimation();

        UIManager.put("Button.arc", 20);
        UIManager.put("Button.font", new Font("Segoe UI", Font.BOLD, 14));
        UIManager.put("Component.focusWidth", 2);
        UIManager.put("Component.innerFocusWidth", 1);
    }

    public Style(String text, Color baseColor) {
        super(text);
        this.baseColor = baseColor;
        setBackground(baseColor);
        setForeground(Color.WHITE);
        setFocusPainted(false);
        setContentAreaFilled(true);

        addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { startAnimation(); }
            public void mouseExited(MouseEvent e) { stopAnimation(); }
        });
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (timer != null && timer.isRunning()) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setColor(colors[index]);
            g2.setStroke(new BasicStroke(3));
            g2.drawRect(0, 0, getWidth()-1, getHeight()-1);
            g2.dispose();
        }
    }

    private void startAnimation() {
        if (timer == null) {
            timer = new Timer(300, e -> {
                index = (index + 1) % colors.length;
                setBorder(BorderFactory.createLineBorder(colors[index], 3));
                repaint();
            });
        }
        timer.start();
    }

    private void stopAnimation() {
        if (timer != null) {
            timer.stop();
        }
        repaint();
        setBorder(UIManager.getBorder("Button.border"));
    }
}
