package gui.textArea;

import Logic.Conditional;
import Logic.DataContainer;
import Logic.World;

import javax.swing.*;

public class CondTextField extends JTextArea {

    public CondTextField() {
        super(40, 40);
    }


    public void printWorlds() {
        setText("");
        for (World world : DataContainer.getWorldsList())
            append(world.getWorldsList().toString() + "\n");
    }

    public void printConditionals() {
        setText("");
        for (int i = 0; i < DataContainer.getConditionalSet().size(); i++)
            append(i + 1 + "    " + DataContainer.getConditionalSet().get(i).toString() + "\n");

        System.out.println("conditionals printed: " + DataContainer.getConditionalSet().size());
    }

    public void printCnfc() {
        setText("");
        for (Object object : DataContainer.getCnfc().values())
            append(((Conditional) object).toString() + ((Conditional) object).getCardinality() + "\n");
    }
}
