package com.bpapps.calc.presenter.applogic;

import java.util.ArrayList;

public class Params {
    private ArrayList<Double> mNumbers = new ArrayList<>();
    private ArrayList<Integer> mOperands = new ArrayList<>();

    public Params() {
        init();
    }

    public void init() {
        mNumbers.clear();
        mOperands.clear();
    }

    public ArrayList<Double> getNumbers() {
        return mNumbers;
    }

    public ArrayList<Integer> getOperands() {
        return mOperands;
    }

    public void addNumber(double number) {
        mNumbers.add(number);
    }

    public void addOperand(@MathematicalOperation int operand) {
        mOperands.add(operand);
    }
}
