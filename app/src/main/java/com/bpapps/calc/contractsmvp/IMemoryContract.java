package com.bpapps.calc.contractsmvp;

import com.bpapps.calc.model.MemoryEntry;

import java.util.ArrayList;

public interface IMemoryContract {

    interface View extends IBaseView<IMemoryContract.Presenter> {

        void onDataBaseUpdated();

    }

    interface Presenter extends IBasePresenter<IMemoryContract.View> {

        boolean isDataBaseEmpty();

        ArrayList<MemoryEntry> getDataBase();

        void clearDataBase();

        void deleteMemoryItem(int itemId);

        void addToMemoryItem(int itemId);

        void addToMemoryItem(double valueToAdd);

        void subtractFromMemory(int itemId);

        void subtractFromMemory(double valueToSubtract);

        void addToDataBase(MemoryEntry item);

        MemoryEntry getMemoryItem(int i);
    }
}
