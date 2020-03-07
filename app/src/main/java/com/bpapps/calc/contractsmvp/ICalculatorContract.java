package com.bpapps.calc.contractsmvp;

import com.bpapps.calc.presenter.applogic.MathematicalCalculableOperationResult;
import com.bpapps.calc.presenter.applogic.Params;

public interface ICalculatorContract {

    interface View extends IBaseView<ICalculatorContract.Presenter> {

        void onCalculated(MathematicalCalculableOperationResult result);

        void saveHistoryEntry();

    }

    interface Presenter extends IBasePresenter<ICalculatorContract.View> {
        Double calculateBinaryOperand(Params params);

        void calculateUnaryOperand(Params params);

        void saveFormula(String formula, double value);
    }
}
