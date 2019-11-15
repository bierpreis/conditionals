package nfc_creator.model;

import kb_creator.model.propositional_logic.*;
import kb_creator.model.propositional_logic.signature.AbstractSignature;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

public class FormulaReader {
    private Pattern simpleDisjunctionPattern = Pattern.compile("([!]?[abc]){1,3},([ ]?[!]?[!abc,=()]*){1,7}");
    private Pattern compoundDisjunctionPattern = Pattern.compile("\\(.*\\),\\(.*\\)");

    private Pattern conjunctionPattern = Pattern.compile("((!?[a-c]){2,3}){1,3}");
    private Pattern rightCompoundConjunctionPattern = Pattern.compile("(!?[a-c]){1,2}[!]?\\(.*\\)");

    private Pattern atomPattern = Pattern.compile("[a-c]");
    private Pattern tautologyPattern = Pattern.compile("\\(true\\)");
    private Pattern negatedAtomPattern = Pattern.compile("[!][a-c]");

    private Pattern doubleEqualityPattern = Pattern.compile("([!]?[abc]){1,2}==([!]?[abc]){1,2}");
    private Pattern tripleEqualityPattern = Pattern.compile("[!]?[abc]==[!]?[abc]==[!]?[abc]");

    private Pattern compoundNegationPattern = Pattern.compile("!\\(.*\\)$");


    private AbstractFormula getFormulaFromString(String string) {

        //remove possible leading space chars
        string = string.replaceAll("^[ ]+", "");

        if (negatedAtomPattern.matcher(string).matches())
            return getAtomNegation(string);
        if (compoundNegationPattern.matcher(string).matches())
            return getCompoundNegation(string);
        if (simpleDisjunctionPattern.matcher(string).matches() || compoundDisjunctionPattern.matcher(string).matches())
            return getDisjunction(string);
        if (conjunctionPattern.matcher(string).matches())
            return getConjunction(string);
        if (atomPattern.matcher(string).matches())
            return getAtom(string);
        if (tautologyPattern.matcher(string).matches())
            return new Tautology();
        if (doubleEqualityPattern.matcher(string).matches() || tripleEqualityPattern.matcher(string).matches())
            return getEquality(string);
        if (rightCompoundConjunctionPattern.matcher(string).matches())
            return getRightCompoundConjunction(string);
        throw new RuntimeException("Invalid Formula String: " + string);
    }

    private AbstractFormula getRightCompoundConjunction(String string) {
        string = string.replaceAll("\\)$", "");
        String[] splitString = string.split("\\(");

        if (splitString.length != 2)
            throw new RuntimeException("Invalid Compound Conjunction: " + string);
        return getFormulaFromString(splitString[0]).and(getFormulaFromString(splitString[1]));
    }

    private AbstractFormula getEquality(String string) {
        String[] splitString = string.split("==");
        List<AbstractFormula> formulas = new ArrayList<>(splitString.length);

        for (String subString : splitString) {
            formulas.add(getFormulaFromString(subString));
        }
        return new Equality(formulas);
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

                string = string.replaceFirst("\\D", "");
            } else {

                if (string.matches("^a.*"))
                    formulasToAdd.add(new Atom(Var.a));
                else if (string.matches("^b.*"))
                    formulasToAdd.add(new Atom(Var.b));
                if (string.matches("^c.*"))
                    formulasToAdd.add(new Atom(Var.c));

                string = string.replaceFirst("\\D", "");
            }
        }
        return new Conjunction(formulasToAdd);
    }

    private AbstractFormula getDisjunction(String baseString) {

        baseString = baseString.replace("^\\(", "");
        baseString = baseString.replace("\\)$", "");

        String[] stringArray = baseString.split(",");
        List<AbstractFormula> formulasToAdd = new ArrayList<>();
        for (String string : stringArray) {
            formulasToAdd.add(getFormulaFromString(string));
        }
        return new Disjunction(formulasToAdd);
    }

    public Map<Integer, AbstractFormula> getFormulaMapFromFile(AbstractSignature signature) {
        File file = new File("src/resources/" + signature.toString().replaceAll(",", "") + ".txt");

        String[] formulaStringArray = getStringArrayFromFile(file);

        Map<Integer, AbstractFormula> formulaMap = new HashMap<>(formulaStringArray.length);

        for (String string : formulaStringArray) {
            String[] stringArray = string.split(": ");
            if (!string.matches("^//.*"))
                formulaMap.put(getNumberFromString(stringArray[0]), getFormulaFromString(stringArray[1]));
        }

        return formulaMap;
    }

    private Integer getNumberFromString(String string) {
        Scanner scanner = new Scanner(string);
        return scanner.nextInt();
    }

    private String[] getStringArrayFromFile(File fileToRead) {
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
