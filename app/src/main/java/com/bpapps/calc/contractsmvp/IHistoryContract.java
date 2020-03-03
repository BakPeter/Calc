package com.bpapps.calc.contractsmvp;

import com.bpapps.calc.model.HistoryEntry;

import java.util.ArrayList;

public interface IHistoryContract {

    interface View extends IBaseView<IHistoryContract.Presenter> {
        void onDataBaseUpdated();

    }

    interface Presenter extends IBasePresenter<IHistoryContract.View> {

        ArrayList<HistoryEntry> getHistoryDataBase();

        void deleteAllHistoryEntries();

        boolean isDataBaseEmpty();

    }
}
