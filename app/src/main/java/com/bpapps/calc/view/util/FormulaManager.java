package com.bpapps.calc.view.util;

import java.util.ArrayList;

public class FormulaManager {
    private StringBuilder mFormula;
    private String mDelimiter;
    private double mValue;

    //+ - X / %
    private ArrayList<String> mOperandsSings;

    public FormulaManager(String delimiter, ArrayList<String> oneParamOperandSings) {
        mDelimiter = delimiter;
        mOperandsSings = oneParamOperandSings;
        init();
    }

    public void init() {
        mFormula = new StringBuilder();
        mValue = 0;
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
        if (operandSign.equals(mOperandsSings.get(mOperandsSings.size() - 1))) {
            if (mFormula.length() != 0) {
                mFormula.append(mDelimiter);
            }
            mFormula.append(number);
            mFormula.append(operandSign);
        } else {
            throw new IllegalArgumentException("operand " + operandSign + "not implemented");
        }

        return mFormula;
    }

    public void addBinaryOperand(String operandSign, String number) {
        if (mFormula.length() == 0) {
            mFormula.append(number);
            mFormula.append(mDelimiter);
            mFormula.append(operandSign);
        } else {
            mFormula.append(mDelimiter);
            if (number != null) {
                mFormula.append(number);
                mFormula.append(mDelimiter);
            }
            mFormula.append(operandSign);
        }
    }

    public void addEquals(String equalsSign, String currNumber) {
        if (currNumber != null && currNumber.length() > 0) {
            mFormula.append(mDelimiter);
            mFormula.append(currNumber);
        }

        mFormula.append(mDelimiter);
        mFormula.append(equalsSign);
    }
}
