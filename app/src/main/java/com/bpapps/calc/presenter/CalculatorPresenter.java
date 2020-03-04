package com.bpapps.calc.presenter;

import com.bpapps.calc.contractsmvp.ICalculatorContract;
import com.bpapps.calc.contractsmvp.IModel;
import com.bpapps.calc.model.HistoryEntry;
import com.bpapps.calc.model.Model;
import com.bpapps.calc.presenter.applogic.MathematicalCalculableOperation;
import com.bpapps.calc.presenter.applogic.MathematicalCalculableOperationResult;
import com.bpapps.calc.presenter.applogic.MathematicalOperation;
import com.bpapps.calc.presenter.applogic.Params;

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
    public void calculateBinaryOperand(Params params
    ) {
        MathematicalCalculableOperationResult result = mCalculator.calculate(params.getNmu1(), params.getNum2(), params.getOperand());
        mView.onCalculated(result);
    }

    @Override
    public void calculateUnaryOperand(Params params) {
        MathematicalCalculableOperationResult result = mCalculator.calculate(params.getNmu1(), params.getOperand());
        mView.onCalculated(result);
    }

    @Override
    public void saveFormula(String formula, double value) {
        mModel.addToHistoryDtaBase(new HistoryEntry(formula, value));
    }
}
