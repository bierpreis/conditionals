package kb_creator.gui.right_panel;


import javax.swing.*;
import java.awt.*;

public class MemoryPanel extends JPanel {
    private JLabel descriptionLabel;

    public MemoryPanel() {
        setLayout(new FlowLayout(FlowLayout.LEFT));
        setBorder(BorderFactory.createTitledBorder("JVM Memory"));
        descriptionLabel = new JLabel();
        add(descriptionLabel);
    }

    public void showFreeMemory() {
        long freeMemoryInMb = calculateFreeMemory();
        if (freeMemoryInMb < 400)
            descriptionLabel.setForeground(Color.RED);

        else descriptionLabel.setForeground(Color.BLACK);
        descriptionLabel.setText("Free Memory: " + freeMemoryInMb + "mb");
    }

    private long calculateFreeMemory() {
        return ((Runtime.getRuntime().maxMemory() - Runtime.getRuntime().totalMemory()) / 1_000_000);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        for (Component component : getComponents())
            component.setEnabled(enabled);

    }
}
