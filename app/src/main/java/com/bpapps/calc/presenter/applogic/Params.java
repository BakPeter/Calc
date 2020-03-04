package com.bpapps.calc.presenter.applogic;

public class Params {
    private Double mNmu1;
    private Double mNum2;
    private @MathematicalOperation
    int mOperand;

    public Params() {
        init();
    }

    public Double getNmu1() {
        return mNmu1;
    }

    public void setNmu1(Double nmu1) {
        mNmu1 = nmu1;
    }

    public Double getNum2() {
        return mNum2;
    }

    public void setNum2(Double num2) {
        mNum2 = num2;
    }

    public int getOperand() {
        return mOperand;
    }

    public void setOperand(int operand) {
        mOperand = operand;
    }

    public void init() {
        mNmu1 = null;
        mNum2 = null;
        mOperand = MathematicalOperation.OPERATION_NOT_DEFINED;
    }
}
