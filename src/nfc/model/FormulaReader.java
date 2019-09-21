package nfc.model;

import kb_creator.model.propositional_logic.*;
import kb_creator.model.propositional_logic.signature.AbstractSignature;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

public class FormulaReader {
    private Pattern disjunctionPattern = Pattern.compile(".*,.*");
    private Pattern conjunctionPattern = Pattern.compile("(!?\\D{2,3}){1,3}"); //todo: this triggers a==b. but how?
    private Pattern atomPattern = Pattern.compile("\\D{1}");
    private Pattern tautologyPattern = Pattern.compile("\\(true\\)");
    private Pattern negatedAtomPattern = Pattern.compile("^![1]\\D{1}");
    private Pattern equalityPattern = Pattern.compile(".*==.*");
    private Pattern compoundNegationPattern = Pattern.compile("!{1}\\({1}.*\\){1}$");


    private AbstractFormula getFormulaFromString(String string) {
        if (negatedAtomPattern.matcher(string).matches())
            return getAtomNegation(string);
        if (compoundNegationPattern.matcher(string).matches())
            return getCompoundNegation(string);
        if (disjunctionPattern.matcher(string).matches())
            return getDisjunction(string);
        if (conjunctionPattern.matcher(string).matches())
            return getConjunction(string);
        if (atomPattern.matcher(string).matches())
            return getAtom(string);
        if (tautologyPattern.matcher(string).matches())
            return new Tautology();
        if (equalityPattern.matcher(string).matches())
            return getEquality(string);
        throw new RuntimeException("Invalid Formula String: " + string);
    }

    //todo: rest abc signature
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
    private AbstractFormula getDisjunction(String baseString) {
        String[] stringArray = baseString.split(",");
        List<AbstractFormula> formulasToAdd = new ArrayList<>();
        for (String string : stringArray) {
            formulasToAdd.add(getFormulaFromString(string));
        }

        return new Disjunction(formulasToAdd);
    }

    public Map<Integer, AbstractFormula> getFormulaMapFromFile(AbstractSignature signature) {
        File file = readFile("src/resources/" + signature.toString().replaceAll(",", "") + ".txt");

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
