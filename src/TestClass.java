import Logic.World;

public class TestClass {
    public static void main(String[] args) {

        World world1 = new World();
        world1.addInt(4);


        World world2 = new World();
        world2.addInt(2);

        world1.isEquivalent(world2);

    }


}
