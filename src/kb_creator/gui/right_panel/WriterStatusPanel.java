package kb_creator.gui.right_panel;

import kb_creator.model.writer.WriterStatus;

import javax.swing.*;
import java.awt.*;
import java.text.NumberFormat;
import java.util.Locale;


public class WriterStatusPanel extends JPanel {

    private JLabel statusLabel;

    private JLabel inconsistentLabel;
    private JLabel consistentLabel;


    public WriterStatusPanel() {
        Box vBox = Box.createVerticalBox();
        add(vBox);

        vBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        setBorder(BorderFactory.createTitledBorder("Knowledge Base Writer"));
        setLayout(new FlowLayout(FlowLayout.LEFT));


        statusLabel = new JLabel();
        vBox.add(statusLabel);

        //placeholder for empty line
        vBox.add(new JLabel(" "));


        consistentLabel = new JLabel();
        vBox.add(consistentLabel);


        inconsistentLabel = new JLabel();
        vBox.add(inconsistentLabel);

        showConsistentQueue(0);
        showInconsistentQueue(0);
        showStatus(WriterStatus.NOT_STARTED);
    }


    public void showConsistentQueue(int consistentQueue) {
        NumberFormat formatter = NumberFormat.getInstance(new Locale("de_DE"));
        consistentLabel.setText("Consistent Queue length: " + formatter.format(consistentQueue));
    }


    public void showInconsistentQueue(int inConsistentQueue) {
        NumberFormat formatter = NumberFormat.getInstance(new Locale("de_DE"));
        inconsistentLabel.setText("Inconsistent Queue length: " + formatter.format(inConsistentQueue));
    }


    public void showStatus(WriterStatus status) {
        statusLabel.setText("Status: " + status.toString());
    }


    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        for (Component component : getComponents())
            component.setEnabled(enabled);

        statusLabel.setEnabled(enabled);
        consistentLabel.setEnabled(enabled);
        inconsistentLabel.setEnabled(enabled);
    }
}
