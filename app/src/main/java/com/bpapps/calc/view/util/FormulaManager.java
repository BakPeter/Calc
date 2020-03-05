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

    public StringBuilder getFormula() {
        return mFormula;
    }

    public double getValue() {
        return mValue;
    }

    public void setValue(double value) {
        mValue = value;
    }

    public StringBuilder addUnaryOperand(String operandFormatString, String operandSign, String number) {
        if(operandSign.equals("%")) {
            if(mFormula.length() == 0) {
                mFormula.append(number);
                mFormula.append(operandSign);
            } else {
                mFormula.append(operandSign);
            }
        } else {
            throw  new IllegalArgumentException("operand " + operandSign + "not implemented");
        }

        return mFormula;
    }

    public void addBinaryOperand(String operandSign, String number) {
        if(mFormula.length() == 0) {
            mFormula.append(number);
            mFormula.append(mDelimiter);
            mFormula.append(operandSign);
        } else {
            mFormula.append(mDelimiter);
            mFormula.append(number);
            mFormula.append(mDelimiter);
            mFormula.append(operandSign);
        }
    }

    public boolean containsBinaryOperand() {
       String formula = mFormula.toString();

       for(int i=0; i<formula.length(); i++) {
           if(formula.charAt(i) == '%')
               return true;
       }
        return false;
    }
}
