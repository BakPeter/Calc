package com.bpapps.calc.contractsmvp;

import com.bpapps.calc.presenter.applogic.MathematicalCalculableOperationResult;
import com.bpapps.calc.presenter.applogic.MathematicalOperation;

public interface ICalculatorContract {

    interface View extends IBaseView<ICalculatorContract.Presenter> {

        void updateViewAfterCalculation(MathematicalCalculableOperationResult result);

        void saveHistoryEntry();

    }

    interface Presenter extends IBasePresenter<ICalculatorContract.View> {
        void calculate(Double num1, Double num2, @MathematicalOperation int operand);

        void calculate(Double num, @MathematicalOperation int operand);

        void saveFormula(String formula, double value);
    }
}
