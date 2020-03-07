package com.bpapps.calc.presenter.applogic;

import java.util.ArrayList;

public class MathematicalCalculableOperationResult {

    private ArrayList<Double> mNumbers;
    private ArrayList<Integer> mOperands;
    private Double mResult;
    private MathematicalCalculableOperationException mException;

    public MathematicalCalculableOperationResult() {
    }

    public MathematicalCalculableOperationResult(
            ArrayList<Double> numbers,
            ArrayList<Integer> operands,
            Double result,
            MathematicalCalculableOperationException exception) {

        mNumbers = numbers;
        mOperands = operands;
        mResult = result;
        mException = exception;
    }


    public Double getResult() {
        return mResult;
    }

    public MathematicalCalculableOperationException getException() {
        return mException;
    }

    public boolean isOperationSuccessful() {
        return mException == null;
    }

    public boolean isUnaryOperation() {
        if (mNumbers.size() == 1
                && mOperands.get(0) == MathematicalOperation.PERCENTAGE) {
            return true;
        } else {
            return false;
        }
    }

    public void setNumbers(ArrayList<Double> numbers) {
        mNumbers = numbers;
    }

    public void setOperands(ArrayList<Integer> operands) {
        mOperands = operands;
    }

    public void setResult(Double result) {
        mResult = result;
    }

    public void setException(MathematicalCalculableOperationException exception) {
        mException = exception;
    }

    public void addToResult(double result) {
        if (mResult == null)
            mResult = result;
        else
            mResult += result;
    }
}
