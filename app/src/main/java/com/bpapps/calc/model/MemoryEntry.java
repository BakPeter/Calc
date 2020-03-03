package com.bpapps.calc.model;

public class MemoryEntry {
    private double mValue;
    private int mId;

    public MemoryEntry(double value) {
        mValue = value;
    }

    public double getValue() {
        return mValue;
    }

    public void addToValue(double value) {
        mValue += value;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public void subtractFromValue(double valueToSubtract) {
        mValue -= valueToSubtract;
    }
}
