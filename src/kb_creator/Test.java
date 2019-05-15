package kb_creator;

import com.intellij.refactoring.changeClassSignature.TypeParameterInfo;
import kb_creator.model.PropositionalLogic.NewConditional;
import nfc.model.Conditional;
import nfc.model.World;

public class Test {

    public static void main(String[] args) {
        //todo: wtf why this doesnt work
        //maybe better use set than list for stuff like that?
        World antecend = new World();
        antecend.addInt(1);

        antecend.addInt(2);
        antecend.addInt(3);

        World otherAntecend = new World();
        otherAntecend.addInt(1);
        otherAntecend.addInt(3);
        otherAntecend.addInt(2);

        World consequence = new World();
        consequence.addInt(2);

        Conditional conditional = new Conditional(consequence, antecend);
        Conditional otherConditional = new Conditional(consequence, otherAntecend);


        NewConditional newConditional = new NewConditional(conditional);

        NewConditional otherNewConditional = new NewConditional(otherConditional);

        System.out.println(newConditional + "equals: " + otherNewConditional + newConditional.equals(otherNewConditional));

    }
}
