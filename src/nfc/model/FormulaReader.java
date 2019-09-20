package nfc.model;

import kb_creator.model.propositional_logic.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class FormulaReader {
    private Pattern disjunctionPattern = Pattern.compile(".*,.*");
    private Pattern conjunctionPattern = Pattern.compile("!?\\D{2,3}"); //todo: test with ab
    private Pattern atomPattern = Pattern.compile("\\D{1}");


    private Pattern negatedAtomPattern = Pattern.compile("^![1]\\D{1}");

    private Pattern compoundNegationPattern = Pattern.compile("!{1}\\({1}.*\\){1}$");

    static private String testString = "!(abc),abc";

    private final String filePath = "/resources/";


    public static void main(String[] args) {



        FormulaReader formulaReader = new FormulaReader();

        formulaReader.getFormulaListFromFile("src/resources/ab.txt");


    }

    //todo: equality
    public AbstractFormula getFormulaFromString(String baseString) {
        if (negatedAtomPattern.matcher(baseString).matches())
            return getAtomNegation(baseString);
        else if (compoundNegationPattern.matcher(baseString).matches())
            return getCompoundNegation(baseString);
        else if (disjunctionPattern.matcher(baseString).matches())
            return getDisjunction(baseString);
        else if (conjunctionPattern.matcher(baseString).matches())
            return getConjunction(baseString);
        if (atomPattern.matcher(baseString).matches())
            return getAtom(baseString);
        else throw new RuntimeException("Invalid Formula String: " + baseString);
    }

    private AbstractFormula getAtom(String string) {
        Var var;
        switch (string) {
            case "a":
                var = Var.a;
                break;
            case "b":
                var = Var.b;
                break;
            case "c":
                var = Var.c;
                break;
            default:
                throw new RuntimeException("Unknown String For Atom: " + string);
        }

        return new Atom(var);
    }

    private AbstractFormula getCompoundNegation(String string) {
        string = string.replaceFirst("^!", "");
        string = string.replaceFirst("\\(", "");
        string = string.replaceFirst("\\)", "");
        return new Negation(getFormulaFromString(string));
    }

    private AbstractFormula getAtomNegation(String string) {
        return new Negation(getFormulaFromString(string.replaceFirst("^!", "")));
    }

    private AbstractFormula getConjunction(String string) {
        List<AbstractFormula> formulasToAdd = new ArrayList<>();
        while (string.length() != 0) {
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

    //todo: remove brackets maybe?
    public AbstractFormula getDisjunction(String baseString) {
        String[] stringArray = baseString.split(",");
        List<AbstractFormula> formulasToAdd = new ArrayList<>();
        for (String string : stringArray) {
            formulasToAdd.add(getFormulaFromString(string));
        }

        return new Disjunction(formulasToAdd);
    }

    //todo: put here the simple list and then replace some with the ones from file. compare numbers to be sure it is correct.
    public List<AbstractFormula> getFormulaListFromFile(String filePath) {
        File file = readFile(filePath);

        String[] formulaStringArray = getStringArrayFromFile(file);

        List<AbstractFormula> formulaList = new ArrayList<>(formulaStringArray.length);

        for (String string : formulaStringArray) {
            if (!string.matches("^//")) //todo: test comment out
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
}
