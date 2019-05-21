package kb_creator.gui.leftpanel;

import javax.swing.*;

public class IterationStatusPanel extends JPanel {
    private JLabel speedDescriptionLabel;
    private JLabel speedLabel;
    private JLabel progressLabel;

    public IterationStatusPanel(){
        Box vBox = Box.createVerticalBox();
        add(vBox);

        speedDescriptionLabel = new JLabel("Speed: (KnowledgeBases/Second)");
        vBox.add(speedDescriptionLabel);

        speedLabel = new JLabel();
        vBox.add(speedLabel);

    }


    public void showOverallProgress(int finishedKBs) {
        if (finishedKBs != -1)
            progressLabel.setText("Now creating " + (finishedKBs + 1) + " element KBs");
        else progressLabel.setText("");
    }

    public void showSpeed(int speed) {
        speedLabel.setText(Integer.toString(speed));
    }
}
