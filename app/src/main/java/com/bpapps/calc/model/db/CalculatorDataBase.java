package com.bpapps.calc.model.db;

import com.bpapps.calc.model.HistoryEntry;
import com.bpapps.calc.model.MemoryEntry;

import java.util.ArrayList;

public class CalculatorDataBase {
    private ArrayList<HistoryEntry> mHistoryData;
    private ArrayList<MemoryEntry> mMemoryData;

    public CalculatorDataBase() {
        mHistoryData = new ArrayList();
        mMemoryData = new ArrayList();

       // addMockDataBase();
    }

    private void addMockDataBase() {
        mMemoryData.add(new MemoryEntry(49.09));
        mMemoryData.add(new MemoryEntry(123.09));
        mMemoryData.add(new MemoryEntry(-98.0007));
        mMemoryData.add(new MemoryEntry(57.233));
    }

    public void addToHistoryDataBase(HistoryEntry entry) {
        mHistoryData.add(entry);
    }

    public ArrayList<HistoryEntry> getHistoryData() {
        return mHistoryData;
    }

    public void clearHistoryDataBase() {
        mHistoryData.clear();
    }

    public boolean isHistoryDataBaseEmpty() {
        return mHistoryData.size() == 0;
    }

    public ArrayList<MemoryEntry> getMemoryData() {

        return mMemoryData;
    }

    public boolean isMemoryDataBaseEmpty() {
        return mMemoryData.size() == 0;
    }

    public void clearMemoryDataBase() {
        mMemoryData.clear();
    }

    public void deleteMemoryItem(int position) {
        mMemoryData.remove(position);
    }

    public void addToMemoryItem(int itemId) {
        double valueToAdd = mMemoryData.get(itemId).getValue();
        mMemoryData.get(0).addToValue(valueToAdd);
    }

    public void subtractFromMemoryItem(int itemId) {
        double valueToSubtract = mMemoryData.get(itemId).getValue();
        mMemoryData.get(0).subtractFromValue(valueToSubtract);
    }

    public void subtractFromMemoryItem(double valueToSubtract) {
        if (mMemoryData.size() > 0) {
            mMemoryData.get(0).subtractFromValue(valueToSubtract);
        }
    }

    public void addToMemoryDataBase(MemoryEntry item) {

        mMemoryData.add(0, item);
    }

    public void addToMemoryItem(double valueToAdd) {
        if (mMemoryData.size() > 0) {
            mMemoryData.get(0).addToValue(valueToAdd);
        }
    }

    public MemoryEntry getMemoryItem(int id) {
        if (mMemoryData.size() > 0)
            return mMemoryData.get(id);

        return null;
    }
}
