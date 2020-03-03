package com.bpapps.calc;

import android.app.Application;

public class CalcApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        CalculatorObjectsContainer.getInstance();
    }

    @Override
    public void onTerminate() {
        CalculatorObjectsContainer.free();

        super.onTerminate();
    }
}
