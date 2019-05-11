package kb_creator;

import kb_creator.model.PropositionalLogic.NewConditional;
import nfc.model.Conditional;
import nfc.model.World;

public class Test {

    public static void main(String[] args) {
        World antecend = new World();
        antecend.addInt(1);
        antecend.addInt(2);

        World consequence = new World();
        consequence.addInt(2);

        Conditional conditional = new Conditional(consequence, antecend);

        System.out.println("old: " + conditional);

        NewConditional newConditional = new NewConditional(conditional);

        System.out.println("new: " + newConditional);

    }
}
