package com.bpapps.calc.view.util;

import java.util.ArrayList;

public class FormulaManager {
    private StringBuilder mFormula;
    private String mDelimiter;
    private double mValue;

    //+ - X /
    private ArrayList<String> mOneParamOperandSings;

    public FormulaManager(String delimiter, ArrayList<String> oneParamOperandSings) {
        mDelimiter = delimiter;
        mOneParamOperandSings = oneParamOperandSings;
        init();
    }

    public void init() {
        mFormula = new StringBuilder();
        mValue = 0;
    }

    public StringBuilder appendWithDelimiter(String valueToAppend) {
        appendWithoutDelimiter(valueToAppend);
        mFormula.append(mDelimiter);

        return mFormula;
    }

    public StringBuilder appendWithoutDelimiter(String valueToAppend) {
        if (mFormula.length() != 0)
            mFormula.append(mDelimiter);

        mFormula.append(valueToAppend);

        return mFormula;
    }

    public StringBuilder getFormulaNoDelimiters() {
        StringBuilder formula = new StringBuilder(mFormula);
        int len = mFormula.length();

        for (int i = 0; i < len; i++) {
            if (mFormula.substring(i, i + 1).equals(mDelimiter))
                mFormula.deleteCharAt(i);
        }


        return formula;
    }

    public StringBuilder getFormula() {
        return mFormula;
    }

    public double getValue() {
        return mValue;
    }
}
