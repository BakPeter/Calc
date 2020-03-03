package com.bpapps.calc;

import com.bpapps.calc.contractsmvp.ICalculatorContract;
import com.bpapps.calc.contractsmvp.IHistoryContract;
import com.bpapps.calc.model.Model;
import com.bpapps.calc.model.db.CalculatorDataBase;
import com.bpapps.calc.presenter.CalculatorPresenter;
import com.bpapps.calc.presenter.HistoryPresenter;
import com.bpapps.calc.presenter.MemoryPresenter;

public class CalculatorObjectsContainer {
    private static CalculatorObjectsContainer sInstance;

    private Model mModel;
    private CalculatorPresenter mCalculatorPresenter;
    private HistoryPresenter mHistoryPresenter;
    private MemoryPresenter mMemoryPresenter;


    public static CalculatorObjectsContainer getInstance() {
        if (sInstance == null)
            sInstance = new CalculatorObjectsContainer();

        return sInstance;
    }

    public static void free() {
        if (sInstance != null) {
            sInstance.mModel.setDataBase(null);
            sInstance.mModel = null;

            sInstance.mCalculatorPresenter = null;

            sInstance.mHistoryPresenter = null;

            sInstance.mMemoryPresenter = null;
        }
        sInstance = null;
    }

    public CalculatorObjectsContainer() {
        mModel = new Model();
        mModel.setDataBase(new CalculatorDataBase());

        mCalculatorPresenter = new CalculatorPresenter(mModel);

        mHistoryPresenter = new HistoryPresenter(mModel);

        mMemoryPresenter = new MemoryPresenter(mModel);
    }

    public Model getModel() {
        return mModel;
    }

    public ICalculatorContract.Presenter getCalculatorPresenter() {
        return mCalculatorPresenter;
    }

    public IHistoryContract.Presenter getHistoryPresenter() {
        return mHistoryPresenter;
    }

    public MemoryPresenter getMemoryPresenter() {
        return mMemoryPresenter;
    }
}
