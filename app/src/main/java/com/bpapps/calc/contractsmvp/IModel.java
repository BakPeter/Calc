package com.bpapps.calc.contractsmvp;

import com.bpapps.calc.model.HistoryEntry;
import com.bpapps.calc.model.MemoryEntry;
import com.bpapps.calc.model.db.CalculatorDataBase;

import java.util.ArrayList;

public interface IModel {

    ArrayList<HistoryEntry> getHistoryDataBase();

    void setDataBase(CalculatorDataBase dataBase);

    void addToHistoryDtaBase(HistoryEntry entry);

    void clearHistoryDataBase();

    boolean isHistoryDataBaseEmpty();

    ////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////
    ////////////////////////////////////////////////////////////////////////////////////

    boolean isMemoryDataBaseEmpty();

    ArrayList<MemoryEntry> getMemoryDataBase();

    void clearMemoryDataBase();

    void deleteMemoryItem(int itemId);

    void addToMemoryItem(int itemId);

    void addToMemoryItem(double valueToAdd);

    void subtractFromMemoryItem(int itemId);

    void subtractFromMemoryItem(double valueToSubtract);

    void addToMemoryDataBase(MemoryEntry item);

    MemoryEntry getMemoryItem(int id);
}
