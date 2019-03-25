import Logic.Cardinality;

public class TestClass {
    public static void main(String[] args) {

        Cardinality c1 = new Cardinality(1, 1);

        Cardinality c2 = new Cardinality(1, 1);

        System.out.println("equals: " + c1.equals(c2));

        System.out.println(c1.hashCode());
        System.out.println(c2.hashCode());

    }
}
