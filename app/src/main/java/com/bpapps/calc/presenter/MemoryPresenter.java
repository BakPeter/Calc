package com.bpapps.calc.presenter;

import com.bpapps.calc.contractsmvp.IMemoryContract;
import com.bpapps.calc.contractsmvp.IModel;
import com.bpapps.calc.model.MemoryEntry;

import java.util.ArrayList;

public class MemoryPresenter
        implements IMemoryContract.Presenter {

    private IModel mModel;
    private IMemoryContract.View mView;

    public MemoryPresenter(IModel model) {
        attachModel(model);
    }

    @Override
    public void attachView(IMemoryContract.View view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    @Override
    public boolean isViewAttached() {
        return mView != null;
    }

    @Override
    public void attachModel(IModel model) {
        mModel = model;
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
    public boolean isDataBaseEmpty() {
        return mModel.isMemoryDataBaseEmpty();
    }

    @Override
    public ArrayList<MemoryEntry> getDataBase() {
        return mModel.getMemoryDataBase();
    }

    @Override
    public void clearDataBase() {
        mModel.clearMemoryDataBase();

        if (isViewAttached())
            mView.onDataBaseUpdated();
    }

    @Override
    public void deleteMemoryItem(int itemId) {
        mModel.deleteMemoryItem(itemId);
        mView.onDataBaseUpdated();
    }

    @Override
    public void addToMemoryItem(int itemId) {
        mModel.addToMemoryItem(itemId);
        mView.onDataBaseUpdated();
    }

    @Override
    public void addToMemoryItem(double valueToAdd) {
        mModel.addToMemoryItem(valueToAdd);
        mView.onDataBaseUpdated();
    }

    @Override
    public void subtractFromMemory(int itemId) {
        mModel.subtractFromMemoryItem(itemId);
        mView.onDataBaseUpdated();
    }

    @Override
    public void subtractFromMemory(double valueToSubtract) {
        mModel.subtractFromMemoryItem(valueToSubtract);
        mView.onDataBaseUpdated();
    }

    @Override
    public void addToDataBase(MemoryEntry item) {
        mModel.addToMemoryDataBase(item);
        if(isViewAttached())
            mView.onDataBaseUpdated();
    }

    @Override
    public MemoryEntry getMemoryItem(int id) {
        return mModel.getMemoryItem(id);
    }
}
