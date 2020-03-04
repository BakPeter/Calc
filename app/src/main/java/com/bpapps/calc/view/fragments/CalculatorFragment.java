package com.bpapps.calc.view.fragments;


import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bpapps.calc.CalculatorObjectsContainer;
import com.bpapps.calc.R;
import com.bpapps.calc.contractsmvp.ICalculatorContract;
import com.bpapps.calc.contractsmvp.IMemoryContract;
import com.bpapps.calc.model.HistoryEntry;
import com.bpapps.calc.model.MemoryEntry;
import com.bpapps.calc.presenter.applogic.MathematicalCalculableOperationResult;
import com.bpapps.calc.presenter.applogic.MathematicalOperation;
import com.bpapps.calc.presenter.applogic.Params;
import com.bpapps.calc.view.adapters.HistoryRecyclerViewAdapter;
import com.bpapps.calc.view.adapters.MemoryRecyclerViewAdapter;
import com.bpapps.calc.view.interfaces.IOnHistoryDataBaseChangedListener;
import com.bpapps.calc.view.util.FormulaManager;

import java.util.ArrayList;


public class CalculatorFragment extends Fragment
        implements View.OnClickListener, ICalculatorContract.View, HistoryRecyclerViewAdapter.IMemoryItemClicked, MemoryRecyclerViewAdapter.IOnMemoryItemClickCallBack {

    private static final String TAG = CalculatorFragment.class.getSimpleName();

    private static final int MAX_NUMBER_OF_DIGITS = 15;
    //private static final int MAX_NUMBER_OF_OPERANDS_IN_FORMULA = 8;
    private static final String DELIMITER = " ";

    private ICalculatorContract.Presenter mCalculatorPresenter;
    private IMemoryContract.Presenter mMemoryPresenter;

    public void setOnHistoryDataBaseChangedListener(IOnHistoryDataBaseChangedListener onHistoryDataBaseChangedListener) {
        mOnHistoryDataBaseChangedListener = onHistoryDataBaseChangedListener;
    }

    private IOnHistoryDataBaseChangedListener mOnHistoryDataBaseChangedListener;

    private TextView mTextViewFormulaShower;
    private TextView mTextViewInputAndResultShower;

    private FormulaManager mFormulaManager;
    private StringBuilder mInput;

    private Params mParams = new Params();
    private boolean mPrevOperandUnary = false;


    public static CalculatorFragment getInstance() {
        return new CalculatorFragment();
    }

    public CalculatorFragment() {

        mInput = new StringBuilder();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_clculator, container, false);

        initFormula();

        initTextViews(view);

        initButtons(view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        setCalculatorPresenter(CalculatorObjectsContainer.getInstance().getCalculatorPresenter());
        setMemoryPresenter(CalculatorObjectsContainer.getInstance().getMemoryPresenter());
    }

    @Override
    public void onDestroy() {
        mCalculatorPresenter.detachView();
        super.onDestroy();
    }

    void initFormula() {
        ArrayList<String> oneParamOperands = new ArrayList<>();
        Resources resources = getResources();
        oneParamOperands.add(resources.getString(R.string.add_sign));
        oneParamOperands.add(resources.getString(R.string.minus_sign));
        oneParamOperands.add(resources.getString(R.string.multiply_sign));
        oneParamOperands.add(resources.getString(R.string.divide_sign));

        mFormulaManager = new FormulaManager(DELIMITER, oneParamOperands);
    }

    private void initTextViews(@NonNull View view) {
        mTextViewFormulaShower = view.findViewById(R.id.text_view_formula_shower);
        mTextViewFormulaShower.setText("");

        mTextViewInputAndResultShower = view.findViewById(R.id.text_view_input_and_result_shower);
        mTextViewInputAndResultShower.setText(getResources().getString(R.string.digit_0));
    }

    private void initButtons(@NonNull View view) {
        view.findViewById(R.id.button_memory_clear).setOnClickListener(this);
        view.findViewById(R.id.button_memory_add).setOnClickListener(this);
        view.findViewById(R.id.button_memory_recall).setOnClickListener(this);
        view.findViewById(R.id.button_memory_subtract).setOnClickListener(this);
        view.findViewById(R.id.button_memory_store).setOnClickListener(this);

        view.findViewById(R.id.button_input_manipulation_add_dot).setOnClickListener(this);
        view.findViewById(R.id.button_input_manipulation_clear_all).setOnClickListener(this);
        view.findViewById(R.id.button_input_manipulation_clear_all_input_entry).setOnClickListener(this);
        view.findViewById(R.id.button_input_manipulation_delete_last_character).setOnClickListener(this);

        view.findViewById(R.id.button_math_operation_percentage).setOnClickListener(this);

        view.findViewById(R.id.button_math_operation_add).setOnClickListener(this);
        view.findViewById(R.id.button_math_operation_change_sign).setOnClickListener(this);
        view.findViewById(R.id.button_math_operation_divide).setOnClickListener(this);
        view.findViewById(R.id.button_math_operation_equals).setOnClickListener(this);
        view.findViewById(R.id.button_math_operation_multiply).setOnClickListener(this);
        view.findViewById(R.id.button_math_operation_subtract).setOnClickListener(this);

        view.findViewById(R.id.button_digit_0).setOnClickListener(this);
        view.findViewById(R.id.button_digit_1).setOnClickListener(this);
        view.findViewById(R.id.button_digit_2).setOnClickListener(this);
        view.findViewById(R.id.button_digit_3).setOnClickListener(this);
        view.findViewById(R.id.button_digit_4).setOnClickListener(this);
        view.findViewById(R.id.button_digit_5).setOnClickListener(this);
        view.findViewById(R.id.button_digit_6).setOnClickListener(this);
        view.findViewById(R.id.button_digit_7).setOnClickListener(this);
        view.findViewById(R.id.button_digit_8).setOnClickListener(this);
        view.findViewById(R.id.button_digit_9).setOnClickListener(this);
    }

    @Override
    public void onClick(@NonNull View v) {
        int id = v.getId();

        switch (id) {
            case R.id.button_memory_clear:
                clearMemoryDataBase();
                break;
            case R.id.button_memory_add:
                addToMemory();
                break;
            case R.id.button_memory_recall:
                memoryRecall();
                break;
            case R.id.button_memory_subtract:
                subtractFromMemory();
                break;
            case R.id.button_memory_store:
                addToMemoryDataBase();
                break;


            case R.id.button_input_manipulation_add_dot:
                addDot();
                break;
            case R.id.button_input_manipulation_clear_all:
                clearAll();
                break;
            case R.id.button_input_manipulation_clear_all_input_entry:
                clearAllInputEntry();
                break;
            case R.id.button_input_manipulation_delete_last_character:
                deleteLastCharacter();
                break;
            case R.id.button_math_operation_change_sign:
                changeInputSign();
                break;

            case R.id.button_math_operation_percentage:
                onClickOneParamOperand(
                        getResources().getString(R.string.percentage_sign_text_format),
                        getResources().getString(R.string.percentage_sign),
                        MathematicalOperation.PERCENTAGE);
                break;

            case R.id.button_math_operation_add:
                onClickTwoParamsOperandButton(getResources().getString(R.string.add_sign), MathematicalOperation.ADD);
                break;
            case R.id.button_math_operation_subtract:
                onClickTwoParamsOperandButton(getResources().getString(R.string.subtract_sign), MathematicalOperation.SUBTRACT);
                break;
            case R.id.button_math_operation_divide:
                onClickTwoParamsOperandButton(getResources().getString(R.string.divide_sign), MathematicalOperation.DIVIDE);
                break;
            case R.id.button_math_operation_multiply:
                onClickTwoParamsOperandButton(getResources().getString(R.string.multiply_sign), MathematicalOperation.MULTIPLY);
                break;

            case R.id.button_math_operation_equals:
                onClickEqualsButton();
                break;


            case R.id.button_digit_0:
                buttonDigitClicked(getResources().getString(R.string.digit_0));
                break;
            case R.id.button_digit_1:
                buttonDigitClicked(getResources().getString(R.string.digit_1));
                break;
            case R.id.button_digit_2:
                buttonDigitClicked(getResources().getString(R.string.digit_2));
                break;
            case R.id.button_digit_3:
                buttonDigitClicked(getResources().getString(R.string.digit_3));
                break;
            case R.id.button_digit_4:
                buttonDigitClicked(getResources().getString(R.string.digit_4));
                break;
            case R.id.button_digit_5:
                buttonDigitClicked(getResources().getString(R.string.digit_5));
                break;
            case R.id.button_digit_6:
                buttonDigitClicked(getResources().getString(R.string.digit_6));
                break;
            case R.id.button_digit_7:
                buttonDigitClicked(getResources().getString(R.string.digit_7));
                break;
            case R.id.button_digit_8:
                buttonDigitClicked(getResources().getString(R.string.digit_8));
                break;
            case R.id.button_digit_9:
                buttonDigitClicked(getResources().getString(R.string.digit_9));
                break;
        }
    }

    private void onClickOneParamOperand(String operandFormatString, String operandSign, @MathematicalOperation int operand) {
        String currNumber = getCurrInput().toString();

        if (operandSign.equals(getResources().getString(R.string.percentage_sign))) {
            mFormulaManager.appendWithoutDelimiter(currNumber);
            mFormulaManager.appendWithDelimiter(operandSign);
        }

        if (mParams.getNmu1() == null) {
            mParams.setNmu1(Double.parseDouble(getCurrInput().toString()));
            mParams.setOperand(operand);
            mCalculatorPresenter.calculateUnaryOperand(mParams);
        } else {
            double num1 = mParams.getNmu1();
            @MathematicalOperation int prevOperand = mParams.getOperand();

            Params params = new Params();
            params.setNmu1(Double.parseDouble(getCurrInput().toString()));
            params.setOperand(operand);
            mCalculatorPresenter.calculateUnaryOperand(params);

            mParams.setNum2(mParams.getNmu1());
            mParams.setNmu1(num1);
            mParams.setOperand(prevOperand);
            mCalculatorPresenter.calculateBinaryOperand(mParams);
        }
    }

    private void onClickTwoParamsOperandButton(String operandSign, @MathematicalOperation int operand) {
        String currNumber = getCurrInput().toString();
        if (mPrevOperandUnary) {
            mFormulaManager.appendWithoutDelimiter(operandSign);
        } else {
            mFormulaManager.appendWithoutDelimiter(currNumber);
            mFormulaManager.appendWithoutDelimiter(operandSign);
        }

        if (mParams.getNmu1() == null) {
            mParams.setNmu1(Double.parseDouble(getCurrInput().toString()));
            mParams.setOperand(operand);

            updateTextViewFormulaShower(mFormulaManager.getFormula());
        } else {

            if (mPrevOperandUnary) {
                mParams.setOperand(operand);
                updateTextViewFormulaShower(mFormulaManager.getFormula());
            } else {
                mParams.setNum2(Double.parseDouble(getCurrInput().toString()));

                if (mParams.getOperand() != MathematicalOperation.OPERATION_NOT_DEFINED)
                    mCalculatorPresenter.calculateBinaryOperand(mParams);

                mParams.setOperand(operand);
            }
        }

        initInput();
    }

    private void clearMemoryDataBase() {
        mMemoryPresenter.clearDataBase();
        Toast.makeText(requireContext(), "Memory deleted", Toast.LENGTH_LONG).show();

    }

    private void memoryRecall() {
        MemoryEntry item = mMemoryPresenter.getMemoryItem(0);
        if (item == null) {
            Toast.makeText(requireContext(), "Memory empty", Toast.LENGTH_LONG).show();
        } else {
            initInput();
            updateTextViewResultInputShower(item.getValue() + "");
        }
    }

    private void addToMemory() {
        String currInput = getCurrInput().toString();

        mMemoryPresenter.addToMemoryItem(Double.parseDouble(currInput));
    }

    private void subtractFromMemory() {
        String currInput = getCurrInput().toString();

        mMemoryPresenter.subtractFromMemory(Double.parseDouble(currInput));
    }

    private void addToMemoryDataBase() {
        String currInput = getCurrInput().toString();
        MemoryEntry item = new MemoryEntry(Double.parseDouble(currInput));

        mMemoryPresenter.addToDataBase(item);
    }

    private void onClickEqualsButton() {
        String currNumber = getCurrInput().toString();

        if (mParams.getOperand() != MathematicalOperation.OPERATION_NOT_DEFINED) {
            mFormulaManager.appendWithDelimiter(currNumber);
            mParams.setNum2(Double.parseDouble(currNumber));
            mCalculatorPresenter.calculateBinaryOperand(mParams);
        }

        if (mFormulaManager.getFormula().length() == 0) {
            mFormulaManager.appendWithDelimiter(currNumber);
        }

        mFormulaManager.appendWithoutDelimiter(getResources().getString(R.string.equals_sign));

        saveHistoryEntry();

        updateTextViewFormulaShower(mFormulaManager.getFormula());

        mFormulaManager.init();
        initInput();
        mParams.init();
        mPrevOperandUnary = false;
    }


    private void buttonDigitClicked(String digitStr) {
        mInput.append(digitStr);
        updateTextViewResultInputShower(mInput);
    }


    @Override
    public void onCalculated(@NonNull MathematicalCalculableOperationResult result) {
        mParams.init();

        if (result.isOperationSuccessful()) {
            updateTextViewResultInputShower(result.getResult().toString());
            updateTextViewFormulaShower(mFormulaManager.getFormula());

            mParams.setNmu1(result.getResult());

            if (result.isUnaryOperand()) {
                mPrevOperandUnary = true;
            }
        } else {
            updateTextViewResultInputShower(result.getException().getMessage());
            updateTextViewFormulaShower(mFormulaManager.getFormula());
            initFormula();
            initInput();
            mPrevOperandUnary = false;
        }
    }

    @Override
    public void saveHistoryEntry() {
        mCalculatorPresenter.saveFormula(mFormulaManager.getFormula().toString(), mFormulaManager.getValue());
        mOnHistoryDataBaseChangedListener.onDataBaseChanged();
    }

    @Override
    public void setCalculatorPresenter(ICalculatorContract.Presenter calculatorPresenter) {
        mCalculatorPresenter = calculatorPresenter;

        mCalculatorPresenter.attachView(this);
    }

    public void setMemoryPresenter(IMemoryContract.Presenter memoryPresenter) {
        mMemoryPresenter = memoryPresenter;
    }

    private void addDot() {
        String dot = getResources().getString(R.string.dot);
        if (!mInput.toString().contains(dot)) {
            if (mInput.length() == 0) {
                mInput.append(getResources().getString(R.string.digit_0));
            }

            mInput.append(dot);
        }

        updateTextViewResultInputShower(mInput);
    }

    private void clearAll() {
        mFormulaManager.init();
        initInput();

        updateTextViewFormulaShower("");
        updateTextViewResultInputShower(getResources().getString(R.string.digit_0));
        mPrevOperandUnary = false;
    }

    private void clearAllInputEntry() {
        initInput();

        updateTextViewResultInputShower(getResources().getString(R.string.digit_0));
    }

    private void deleteLastCharacter() {
        int len = mInput.length();
        if (len > 0)
            mInput.deleteCharAt(len - 1);

        if (mInput.length() == 0)
            updateTextViewResultInputShower(getResources().getString(R.string.digit_0));
        else
            updateTextViewResultInputShower(mInput);
    }

    private StringBuilder getCurrInput() {
        return new StringBuilder(mTextViewInputAndResultShower.getText());
    }

    private void changeInputSign() {
        String minusSign = getResources().getString(R.string.minus_sign);

        if (mInput.length() > 0) {
            if (mInput.substring(0, 1).equals(minusSign)) {
                mInput.deleteCharAt(0);
            } else {
                mInput.insert(0, minusSign);
            }
        } else {
            mInput.append(minusSign);
//            mInput.append(getResources().getString(R.string.digit_0));
        }

        updateTextViewResultInputShower(mInput);
    }

    private void updateTextViewResultInputShower(String text) {
        mTextViewInputAndResultShower.setText(text);
    }

    private void updateTextViewResultInputShower(StringBuilder text) {
        mTextViewInputAndResultShower.setText(text);
    }


    private void updateTextViewFormulaShower(String text) {
        mTextViewFormulaShower.setText(text);
    }

    private void updateTextViewFormulaShower(StringBuilder text) {
        mTextViewFormulaShower.setText(text);
    }

    @Override
    public void onClick(HistoryEntry entry) {
        updateTextViewFormulaShower(entry.getFormula());
        updateTextViewResultInputShower(entry.getValue() + "");
    }

    @Override
    public void onClick(MemoryEntry item) {
        updateTextViewResultInputShower(item.getValue() + "");
    }


    private void initInput() {
        mInput.setLength(0);
    }
}