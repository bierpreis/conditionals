package view.textArea;

import model.Conditional;
import model.ConditionalList;
import model.World;

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

    public void printWorlds(List<World> worldsList) {
        condTextField.setText("");
        condTextField.printWorlds(worldsList);


        infoPanel.printInfo(condTextField.getDescription());
        revalidate();
    }

    public void printConditionals(List<Conditional> conditionalList) {
        condTextField.setText("");


        numberOfConditionalsLabel.setText(Integer.toString(conditionalList.size()));
        scrollPane.setViewportView(condTextField);

        condTextField.printConditionals(conditionalList);

        infoPanel.printInfo(condTextField.getDescription());
        revalidate();
    }

    public void printCnfc(List<ConditionalList> conditionalLists) {
        condTextField.setText("");

        condTextField.printCnfc(conditionalLists);

        infoPanel.printInfo(condTextField.getDescription());
        revalidate();
    }
}
