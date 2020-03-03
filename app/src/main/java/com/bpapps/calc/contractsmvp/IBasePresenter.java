package com.bpapps.calc.contractsmvp;

public interface IBasePresenter<T> {
    void attachView(T view);

    void detachView();

    boolean isViewAttached();

    void attachModel(IModel model);

    void detachModel();

    void detachAll();
}
