package com.bpapps.calc.model;

import com.bpapps.calc.contractsmvp.IModel;
import com.bpapps.calc.model.db.CalculatorDataBase;

import java.util.ArrayList;

public class Model implements IModel {
    private CalculatorDataBase mDataBase;


    @Override
    public void setDataBase(CalculatorDataBase dataBase) {
        mDataBase = dataBase;
    }

    @Override
    public void addToHistoryDtaBase(HistoryEntry entry) {
        mDataBase.addToHistoryDataBase(entry);
    }

    @Override
    public void clearHistoryDataBase() {
        mDataBase.clearHistoryDataBase();
    }

    @Override
    public boolean isHistoryDataBaseEmpty() {
        return mDataBase.isHistoryDataBaseEmpty();
    }

    @Override
    public boolean isMemoryDataBaseEmpty() {
        return mDataBase.isMemoryDataBaseEmpty();
    }

    @Override
    public ArrayList<MemoryEntry> getMemoryDataBase() {
        return mDataBase.getMemoryData();
    }

    @Override
    public void clearMemoryDataBase() {
        mDataBase.clearMemoryDataBase();

    }

    @Override
    public void deleteMemoryItem(int itemId) {
        mDataBase.deleteMemoryItem(itemId);
    }

    @Override
    public void addToMemoryItem(int itemId) {
        mDataBase.addToMemoryItem(itemId);
    }

    @Override
    public void addToMemoryItem(double valueToAdd) {
        mDataBase.addToMemoryItem(valueToAdd);
    }

    @Override
    public void subtractFromMemoryItem(int itemId) {
        mDataBase.subtractFromMemoryItem(itemId);
    }

    @Override
    public void subtractFromMemoryItem(double valueToSubtract) {
        mDataBase.subtractFromMemoryItem(valueToSubtract);
    }

    @Override
    public void addToMemoryDataBase(MemoryEntry item) {
        mDataBase.addToMemoryDataBase(item);
    }

    @Override
    public MemoryEntry getMemoryItem(int id) {
        return mDataBase.getMemoryItem(id);
    }

    @Override
    public ArrayList<HistoryEntry> getHistoryDataBase() {
        return mDataBase.getHistoryData();
    }
}
