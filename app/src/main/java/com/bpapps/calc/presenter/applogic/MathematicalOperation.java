package com.bpapps.calc.presenter.applogic;

import androidx.annotation.IntDef;

import static com.bpapps.calc.presenter.applogic.MathematicalOperation.ADD;
import static com.bpapps.calc.presenter.applogic.MathematicalOperation.CHANGE_SIGN;
import static com.bpapps.calc.presenter.applogic.MathematicalOperation.DIVIDE;
import static com.bpapps.calc.presenter.applogic.MathematicalOperation.EQUALS;
import static com.bpapps.calc.presenter.applogic.MathematicalOperation.INVERSE;
import static com.bpapps.calc.presenter.applogic.MathematicalOperation.MULTIPLY;
import static com.bpapps.calc.presenter.applogic.MathematicalOperation.OPERATION_NOT_DEFINED;
import static com.bpapps.calc.presenter.applogic.MathematicalOperation.PERCENTAGE;
import static com.bpapps.calc.presenter.applogic.MathematicalOperation.POWER_2;
import static com.bpapps.calc.presenter.applogic.MathematicalOperation.SQUARE_ROOT;
import static com.bpapps.calc.presenter.applogic.MathematicalOperation.SUBTRACT;


@IntDef({
        OPERATION_NOT_DEFINED,
        ADD,
        SUBTRACT,
        CHANGE_SIGN,
        DIVIDE,
        EQUALS,
        INVERSE,
        MULTIPLY,
        PERCENTAGE,
        POWER_2,
        SQUARE_ROOT})
public @interface MathematicalOperation {
    int OPERATION_NOT_DEFINED = -1;
    int ADD = 1;
    int SUBTRACT = 2;
    int CHANGE_SIGN = 3;
    int DIVIDE = 4;
    int EQUALS = 5;
    int INVERSE = 6;
    int MULTIPLY = 7;
    int PERCENTAGE = 8;
    int POWER_2 = 9;
    int SQUARE_ROOT = 10;
}