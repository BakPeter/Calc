package com.bpapps.calc.presenter;

import com.bpapps.calc.contractsmvp.ICalculatorContract;
import com.bpapps.calc.contractsmvp.IModel;
import com.bpapps.calc.model.HistoryEntry;
import com.bpapps.calc.model.Model;
import com.bpapps.calc.presenter.applogic.MathematicalCalculableOperation;
import com.bpapps.calc.presenter.applogic.MathematicalCalculableOperationResult;
import com.bpapps.calc.presenter.applogic.Params;

import java.util.ArrayList;

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
    public Double calculateBinaryOperand(Params params) {
        ArrayList<Double> numbers = params.getNumbers();
        ArrayList<Integer> operands = params.getOperands();
        int numbersSize = numbers.size();
        int operandsSize = operands.size();

        MathematicalCalculableOperationResult result = new MathematicalCalculableOperationResult();
        result.setNumbers(numbers);
        result.setOperands(operands);

        if (numbersSize == 1) {
            result.setResult(numbers.get(0));
            result.setException(null);
        } else {
            if (numbersSize == operandsSize)
                operandsSize--;

            Double currResult = new Double(0);
            MathematicalCalculableOperationResult res;
            for (int i = 0; i < operandsSize; i++) {
                if (i == 0) {
                    res = mCalculator.calculate(
                            numbers.get(i),
                            numbers.get(i + 1),
                            operands.get(i));
                    currResult = res.getResult();
                } else {
                    res = mCalculator
                            .calculate(currResult,
                                    numbers.get(i + 1),
                                    operands.get(i));
                    currResult = res.getResult();
                }
                result.setResult(currResult);
                result.setException(res.getException());
            }
        }

        mView.onCalculated(result);

        return result.getResult();
    }

    @Override
    public void calculateUnaryOperand(Params params) {
        MathematicalCalculableOperationResult result =
                mCalculator.calculate(params.getNumbers().get(0), params.getOperands().get(0));

        result.setNumbers(params.getNumbers());
        result.setOperands(params.getOperands());
        mView.onCalculated(result);
    }

    @Override
    public void saveFormula(String formula, double value) {
        mModel.addToHistoryDtaBase(new HistoryEntry(formula, value));
    }
}
