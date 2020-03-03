package com.bpapps.calc.model;

public class HistoryEntry {
    private String mFormula;
    private double mValue;

    public HistoryEntry(String formula, double value) {
        mFormula = formula;
        mValue = value;
    }

    public String getFormula() {
        return mFormula;
    }

    public double getValue() {
        return mValue;
    }
}
