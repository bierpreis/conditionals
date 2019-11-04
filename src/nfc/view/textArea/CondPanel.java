package nfc.view.textArea;

import kb_creator.model.propositional_logic.AbstractFormula;
import nfc.model.WConditional;
import nfc.model.ConditionalList;
import nfc.model.WorldSet;

import javax.swing.*;
import java.util.List;
import java.util.Map;

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


    public void printConditionals(List<WConditional> conditionalList) {
        condTextField.setText("");


        numberOfConditionalsLabel.setText(Integer.toString(conditionalList.size()));
        scrollPane.setViewportView(condTextField);

        condTextField.printConditionals(conditionalList);

        infoPanel.printInfo(condTextField.getDescription());
        revalidate();
    }

    public void printCnfcEq(List<ConditionalList> conditionalLists) {
        condTextField.setText("");

        condTextField.printCnfcEq(conditionalLists);

        infoPanel.printInfo(condTextField.getDescription());
        revalidate();
    }

    public void printWorlds(List<WorldSet> worldsList) {
        condTextField.setText("");
        condTextField.printWorlds(worldsList);
        infoPanel.printInfo(condTextField.getDescription());
    }

    public void printWorldsAndFormulas(List<WorldSet> worldsList, Map<WorldSet, AbstractFormula> translationMap) {

        condTextField.setText("");
        System.out.println("worlds and formulas!");
        condTextField.printWorldsAndFormulas(worldsList, translationMap);
        infoPanel.printInfo(condTextField.getDescription());
    }


    public String getContentAsString() {
        return condTextField.getContentAsString();
    }

    public void printConditionalsWithCounters(List<WConditional> conditionals) {
        condTextField.setText("");
        condTextField.printConditionalsWithCounters(conditionals);
        infoPanel.printInfo(condTextField.getDescription());
    }
}
