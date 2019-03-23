package gui;

import Logic.DataContainer;
import Logic.World;

import javax.swing.*;
import java.util.List;


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
}
