package nfc.model;

import kb_creator.model.propositional_logic.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormulaReader {
    static Pattern worldPattern = Pattern.compile("!?[abc]{1}");


    public static void main(String[] args) {
        String test = "!abc";
        System.out.println("formula: " + getNextAtom(test));
/*
        String filePath = "/home/bierpreis/test";
        FormulaReader formulaReader = new FormulaReader();

        List<AbstractFormula> formulaList = formulaReader.getFormulaListFromFile(filePath);

        for (AbstractFormula formula : formulaList)
            System.out.println(formula);


Matcher matcher = worldPattern.matcher(test);
        System.out.println(matcher.matches());
*/

        String output = "";
        if (test.matches("^!.*"))
            output += "negation";
        test = test.replaceFirst("^!?[abc]{1}", "");
        System.out.println("output: " + output);
        System.out.println("test: " + test);
    }

    private static AbstractFormula getNextAtom(String string) {
        AbstractFormula formulaToReturn = null;
        if (string.matches("^!.*")) {
            string = string.replaceFirst("^!", "");

            if (string.matches("^a.*"))
                formulaToReturn = new Negation(new Atom(Var.a));
            else if (string.matches("^b.*"))
                formulaToReturn = new Negation(new Atom(Var.b));
            if (string.matches("^c.*"))
                formulaToReturn = new Negation(new Atom(Var.c));
            return formulaToReturn;
        } else {

            if (string.matches("^a.*"))
                formulaToReturn = new Atom(Var.a);
            else if (string.matches("^b.*"))
                formulaToReturn = new Atom(Var.b);
            if (string.matches("^c.*"))
                formulaToReturn = new Atom(Var.c);
            
            return formulaToReturn;
        }
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
