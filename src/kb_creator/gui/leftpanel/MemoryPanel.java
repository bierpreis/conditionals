package kb_creator.gui.leftpanel;

import com.intellij.ui.JBColor;

import javax.swing.*;
import java.awt.*;

public class MemoryPanel extends JPanel {
    JLabel descriptionLabel;

    public MemoryPanel() {
        setBorder(BorderFactory.createTitledBorder("JVM Memory"));
        descriptionLabel = new JLabel();
        add(descriptionLabel);
    }

    public void showFreeMemory() {
        long freeMemoryInMb = calculateFreeMemory();
        if (freeMemoryInMb < 400)
            descriptionLabel.setForeground(JBColor.RED);
        else descriptionLabel.setForeground(JBColor.BLACK);
        descriptionLabel.setText("Free Memory: " + freeMemoryInMb + "mb");
    }

    private long calculateFreeMemory() {
        return ((Runtime.getRuntime().maxMemory() - Runtime.getRuntime().totalMemory()) / 1_000_000);
    }
}
