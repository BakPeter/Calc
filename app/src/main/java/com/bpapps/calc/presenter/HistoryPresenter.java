package com.bpapps.calc.presenter;

import com.bpapps.calc.contractsmvp.IHistoryContract;
import com.bpapps.calc.contractsmvp.IModel;
import com.bpapps.calc.model.HistoryEntry;
import com.bpapps.calc.model.Model;

import java.util.ArrayList;

public class HistoryPresenter implements IHistoryContract.Presenter {
    private IHistoryContract.View mView;
    private Model mModel;

    public HistoryPresenter(Model model) {
        mModel = model;
    }

    @Override
    public void attachView(IHistoryContract.View view) {
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
        detachView();
        detachModel();
    }

    @Override
    public ArrayList<HistoryEntry> getHistoryDataBase() {
        return mModel.getHistoryDataBase();
    }

    @Override
    public void deleteAllHistoryEntries() {
        mModel.clearHistoryDataBase();
        mView.onDataBaseUpdated();
    }

    @Override
    public boolean isDataBaseEmpty() {
        return mModel.isHistoryDataBaseEmpty();
    }
}
