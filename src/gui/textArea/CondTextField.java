package gui.textArea;


import Logic.Conditional;
import Logic.DataContainer;
import Logic.World;

import javax.swing.*;
import java.util.List;

public class CondTextField extends JTextArea {

    public CondTextField() {
        super(40, 40);
    }


    public int printWorlds() {
        setText("");
        int numberCounter = 0;
        for (World world : DataContainer.getWorldsList()) {
            append(world.getWorldsList().toString() + "\n");
            numberCounter++;

        }
        return numberCounter;
    }

    public int printConditionals() {
        setText("");
        int numberOfConditionals = 0;
        for (int i = 0; i < DataContainer.getConditionalSet().size(); i++) {
            append(i + 1 + "    " + DataContainer.getConditionalSet().get(i).toString() + "\n");
            numberOfConditionals++;
        }
        return numberOfConditionals;
    }

    public int printCnfc() {
        int numberOfNfc = 0;
        setText("");
        for (int i = 0; i < DataContainer.getCnfc().size(); i++) {
            List<Conditional> currentList = DataContainer.getCnfc().get(i);
            append(currentList.toString() + "\n");
            numberOfNfc++;
        }
        return numberOfNfc;
    }
}
