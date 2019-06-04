package kb_creator.gui.writerPanel;

import javax.swing.*;
import java.text.NumberFormat;
import java.util.Locale;

public class QueuePanel extends JPanel {
    private JLabel inconsistentLabel;
    private JLabel consistentLabel;


    public QueuePanel() {
        setBorder(BorderFactory.createTitledBorder("Queue for Writing"));

        Box vBox = Box.createVerticalBox();
        add(vBox);


        consistentLabel = new JLabel();
        vBox.add(consistentLabel);


        inconsistentLabel = new JLabel();
        vBox.add(inconsistentLabel);

        showConsistentQueue(0);
        showInconsistentQueue(0);
    }

    public void showConsistentQueue(int consistentQueue) {
        NumberFormat formatter = NumberFormat.getInstance(new Locale("de_DE"));
        consistentLabel.setText("Consistent Queue length: " + formatter.format(consistentQueue));


    }


    public void showInconsistentQueue(int inConsistentQueue) {
        NumberFormat formatter = NumberFormat.getInstance(new Locale("de_DE"));
        inconsistentLabel.setText("Inconsistent Queue length: " + formatter.format(inConsistentQueue));


    }
}
