package com.bpapps.calc.presenter.applogic;

public class MathematicalCalculableOperationResult {
    private Double mNum1;
    private Double mNum2;
    private @MathematicalOperation
    int mOperand;
    private Double mResult;
    private MathematicalCalculableOperationException mException;

    public MathematicalCalculableOperationResult(Double num1, Double num2, @MathematicalOperation int operand, Double result, MathematicalCalculableOperationException exception) {
        mNum1 = num1;
        mNum2 = num2;
        mOperand = operand;
        mResult = result;
        mException = exception;
    }

    public Double getNum1() {
        return mNum1;
    }

    public Double getNum2() {
        return mNum2;
    }

    @MathematicalOperation
    public int getOperand() {
        return mOperand;
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

    public boolean isSingleNumOperand() {
        if (mOperand == MathematicalOperation.SQUARE_ROOT
                || mOperand == MathematicalOperation.POWER_2
                || mOperand == MathematicalOperation.INVERSE)
            return true;

        return false;
    }

    public boolean isUnaryOperand() {
        switch (mOperand) {
            case MathematicalOperation.PERCENTAGE:
                return true;
            default:
                return false;
        }
    }
}
