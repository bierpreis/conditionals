package nfc.view.textArea;

import nfc.model.Conditional;
import nfc.model.ConditionalList;

import javax.swing.*;
import java.util.List;

public class CondPanel extends JPanel {
    private final CondTextField condTextField;
    private final JScrollPane scrollPane;


    private final InfoPanel infoPanel;
    private final JLabel numberOfConditionalsLabel;

    public CondPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        condTextField = new CondTextField();
        scrollPane = new JScrollPane(condTextField);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane);

        infoPanel = new InfoPanel();

        numberOfConditionalsLabel = new JLabel("0");
        add(infoPanel);

        revalidate();

    }


    public void printConditionals(List<Conditional> conditionalList, ViewOptions options) {
        condTextField.setText("");


        numberOfConditionalsLabel.setText(Integer.toString(conditionalList.size()));
        scrollPane.setViewportView(condTextField);

        condTextField.printConditionals(conditionalList, options);

        infoPanel.printInfo(condTextField.getDescription());
        revalidate();
    }

    public void printCnfcEq(List<ConditionalList> conditionalLists, ViewOptions options) {
        condTextField.setText("");

        condTextField.printCnfcEq(conditionalLists, options);

        infoPanel.printInfo(condTextField.getDescription());
        revalidate();
    }


    public String getContentAsString() {
        return condTextField.getContentAsString();
    }
}
