package nfc.model;

import kb_creator.model.propositional_logic.AbstractFormula;
import kb_creator.model.propositional_logic.Tautology;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FormulaReader {


    public static void main(String[] args) {
        String filePath = "/home/bierpreis/test";
        FormulaReader formulaReader = new FormulaReader();

        List<AbstractFormula> formulaList = formulaReader.getFormulaListFromFile(filePath);

        for (AbstractFormula formula : formulaList)
            System.out.println(formula);


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
