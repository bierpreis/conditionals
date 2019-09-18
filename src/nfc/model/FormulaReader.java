package nfc.model;

import kb_creator.model.propositional_logic.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class FormulaReader {
    private Pattern disjunctionPattern; //todo
    private Pattern conjunctionPattern = Pattern.compile("!?\\D[2*]");
    private Pattern AtomPattern = Pattern.compile("\\D[1]");
    private Pattern NegationPattern = Pattern.compile("^![1].*");

    static private String testString = "!abc,abc";


    public static void main(String[] args) {
        AbstractFormula formula = getDisjunction(testString);

        System.out.println("formula: " + formula);


    }

    public AbstractFormula getFormula(String baseString) {
        baseString.matches()
    }

    private static AbstractFormula getNextConjunction(String string) {
        List<AbstractFormula> formulasToAdd = new ArrayList<>();
        while (string.length() != 0) {
            System.out.println("processing: " + string);
            if (string.matches("^!.*")) {
                string = string.replaceFirst("^!", "");

                if (string.matches("^a.*"))
                    formulasToAdd.add(new Negation(new Atom(Var.a)));
                else if (string.matches("^b.*"))
                    formulasToAdd.add(new Negation(new Atom(Var.b)));
                if (string.matches("^c.*"))
                    formulasToAdd.add(new Negation(new Atom(Var.c)));

                string = string.replaceFirst("\\D{1}", "");
            } else {

                if (string.matches("^a.*"))
                    formulasToAdd.add(new Atom(Var.a));
                else if (string.matches("^b.*"))
                    formulasToAdd.add(new Atom(Var.b));
                if (string.matches("^c.*"))
                    formulasToAdd.add(new Atom(Var.c));

                string = string.replaceFirst("\\D{1}", "");
            }
        }
        return new Conjunction(formulasToAdd);
    }

    public static AbstractFormula getDisjunction(String baseString) {
        String[] stringArray = baseString.split(",");
        List<AbstractFormula> formulasToAdd = new ArrayList<>();
        for (String string : stringArray) {
            formulasToAdd.add(getNextConjunction(string));
        }

        return new Disjunction(formulasToAdd);
    }

    public List<AbstractFormula> getFormulaListFromFile(String filePath) {
        File file = readFile(filePath);

        String[] formulaStringArray = getStringArrayFromFile(file);

        List<AbstractFormula> formulaList = new ArrayList<>(formulaStringArray.length);

        for (String string : formulaStringArray) {
            formulaList.add(getFormulaFromString(string));
        }

        return formulaList;
    }

    private File readFile(String filepath) {
        //todo: what happens when file not found? exception? null?
        File fileToReturn = new File(filepath);

        return fileToReturn;
    }

    private String[] getStringArrayFromFile(File fileToRead) {
        //todo: warning dialog when file not found?!
        Scanner fileScanner = null;
        try {
            fileScanner = new Scanner(fileToRead);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        StringBuilder sb = new StringBuilder();

        while (fileScanner.hasNextLine()) {
            sb.append(fileScanner.nextLine());
            sb.append("\n");
        }

        return sb.toString().split("\n");
    }

    private AbstractFormula getFormulaFromString(String string) {
        System.out.println(string);
        //todo
        //idea: recursion: split , ->disj
        //if matches (!)x[n] ->conj
        return new Tautology();
    }
}
