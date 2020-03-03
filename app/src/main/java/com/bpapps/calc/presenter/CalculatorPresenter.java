package com.bpapps.calc.presenter;

import com.bpapps.calc.contractsmvp.ICalculatorContract;
import com.bpapps.calc.contractsmvp.IModel;
import com.bpapps.calc.model.HistoryEntry;
import com.bpapps.calc.model.Model;
import com.bpapps.calc.presenter.applogic.MathematicalCalculableOperation;
import com.bpapps.calc.presenter.applogic.MathematicalCalculableOperationResult;
import com.bpapps.calc.presenter.applogic.MathematicalOperation;

public class CalculatorPresenter implements ICalculatorContract.Presenter {
    private ICalculatorContract.View mView;
    private Model mModel;

    private MathematicalCalculableOperation mCalculator = new MathematicalCalculableOperation();

    public CalculatorPresenter(Model model) {
        attachModel(model);
    }

    @Override
    public void attachView(ICalculatorContract.View view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    @Override
    public boolean isViewAttached() {
        return mView == null;
    }

    @Override
    public void attachModel(IModel model) {
        mModel = (Model) model;
    }

    @Override
    public void detachModel() {
        mModel = null;
    }

    @Override
    public void detachAll() {
        detachModel();
        detachView();
    }

    @Override
    public void calculate(Double num1, Double num2, int operand) {
        MathematicalCalculableOperationResult result = mCalculator.calculate(num1, num2, operand);
        mView.updateViewAfterCalculation(result);
    }

    @Override
    public void calculate(Double num, @MathematicalOperation int operand) {
        MathematicalCalculableOperationResult result = mCalculator.calculate(num, operand);
        mView.updateViewAfterCalculation(result);
    }

    @Override
    public void saveFormula(String formula, double value) {
        mModel.addToHistoryDtaBase(new HistoryEntry(formula, value));
    }
}
