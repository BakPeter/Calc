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
import com.bpapps.calc.view.adapters.HistoryRecyclerViewAdapter;
import com.bpapps.calc.view.adapters.MemoryRecyclerViewAdapter;
import com.bpapps.calc.view.interfaces.IOnHistoryDataBaseChangedListener;


public class CalculatorFragment extends Fragment
        implements View.OnClickListener, ICalculatorContract.View, HistoryRecyclerViewAdapter.IMemoryItemClicked, MemoryRecyclerViewAdapter.IOnMemoryItemClickCallBack {

    private static final String TAG = CalculatorFragment.class.getSimpleName();

    private static final int MAX_NUMBER_OF_DIGITS = 10;
    private static final int MAX_NUMBER_OF_OPERANDS_IN_FORMULA = 8;
    private static final String DELIMITER = " ";

    private ICalculatorContract.Presenter mCalculatorPresenter;
    private IMemoryContract.Presenter mMemoryPresenter;

    public void setOnDataBaseChangedListener(IOnHistoryDataBaseChangedListener onDataBaseChangedListener) {
        mOnDataBaseChangedListener = onDataBaseChangedListener;
    }

    private IOnHistoryDataBaseChangedListener mOnDataBaseChangedListener;

    private TextView mTextViewFormulaShower;
    private TextView mTextViewInputAndResultShower;

    private StringBuilder mFormula;

    private boolean mCalculate = false;
    private double mNum1;
    private double mNum2;
    private @MathematicalOperation
    int mOperand = MathematicalOperation.OPERATION_NOT_DEFINED;

    private boolean mFirstNumber = true;
    private boolean mNewInput = true;

    public static CalculatorFragment getInstance() {
        return new CalculatorFragment();
    }

    public CalculatorFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_clculator, container, false);

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

        view.findViewById(R.id.button_math_operation_add).setOnClickListener(this);
        view.findViewById(R.id.button_math_operation_change_sign).setOnClickListener(this);
        view.findViewById(R.id.button_math_operation_divide).setOnClickListener(this);
        view.findViewById(R.id.button_math_operation_equals).setOnClickListener(this);
        view.findViewById(R.id.button_math_operation_inverse).setOnClickListener(this);
        view.findViewById(R.id.button_math_operation_multiply).setOnClickListener(this);
        view.findViewById(R.id.button_math_operation_percentage).setOnClickListener(this);
        view.findViewById(R.id.button_math_operation_power_2).setOnClickListener(this);
        view.findViewById(R.id.button_math_operation_square_root).setOnClickListener(this);
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


            case R.id.button_math_operation_inverse:
                onClickOneParamOperand(
                        getResources().getString(R.string.inverse_sign_text_format),
                        getResources().getString(R.string.inverse_num_sign),
                        MathematicalOperation.INVERSE);
                break;
            case R.id.button_math_operation_percentage:
                onClickPercentageOperand();
                break;
            case R.id.button_math_operation_power_2:
                onClickOneParamOperand(
                        getResources().getString(R.string.power_2_text_format),
                        getResources().getString(R.string.power_2_sign),
                        MathematicalOperation.POWER_2);
                break;
            case R.id.button_math_operation_square_root:
                onClickOneParamOperand(
                        getResources().getString(R.string.square_root_text_format),
                        getResources().getString(R.string.square_root_sign),
                        MathematicalOperation.SQUARE_ROOT);
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

    private void clearMemoryDataBase() {
        mMemoryPresenter.clearDataBase();
    }

    private void memoryRecall() {
        MemoryEntry item = mMemoryPresenter.getMemoryItem(0);
        clearAll();
        updateTextViewResultInputShower(item.getValue() + "");
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

    private void onClickPercentageOperand() {
        //TODO private void onClickPercentageOperand()

    }

    private void onClickOneParamOperand(String operandFormatString, String operandSign, @MathematicalOperation int operand) {
        updateFormulaOneParamOperand(operandFormatString, operand);

        if (!mFirstNumber)
            mCalculatorPresenter.calculate(mNum1, operand);
        else
            mCalculatorPresenter.calculate(mNum2, operand);
    }

    private void updateFormulaOneParamOperand(String operandFormatString, @MathematicalOperation int operand) {
        String text;
        if (mFirstNumber) {
            mFirstNumber = false;
            mNum1 = getNumberFromInput();
            text = String.format(operandFormatString, mNum1);
            mFormula = new StringBuilder(text);
        } else if (isFormulaContainsTwoParamsOperand()) {
            mNum2 = getNumberFromInput();
            mFormula.append(String.format(operandFormatString, mNum2));
        } else {
            mNum2 = getNumberFromInput();
            text = String.format(operandFormatString, mFormula.toString());
            mFormula = new StringBuilder(text);
        }

        updateTextViewFormulaShower(mFormula);
    }

    private boolean isFormulaContainsTwoParamsOperand() {
        String formula = mFormula.toString();

        Resources resources = getResources();
        if (formula.contains(resources.getString(R.string.add_sign)))
            return true;
        if (formula.contains(resources.getString(R.string.subtract_sign)))
            return true;
        if (formula.contains(resources.getString(R.string.multiply_sign)))
            return true;
        if (formula.contains(resources.getString(R.string.divide_sign)))
            return true;


        return false;
    }

    private void onClickTwoParamsOperandButton(String operandSign, @MathematicalOperation int operand) {
        updateFormula(operandSign);

        if (mCalculate)
            calculate();
        else
            mCalculate = true;

        updateOperand(operand);

        mNewInput = true;
    }

    private void onClickEqualsButton() {
        if (mCalculate) {
            updateFormula(getResources().getString(R.string.equals_sign));
            calculate();

            saveHistoryEntry();

            mNewInput = true;
            mFirstNumber = true;
            mFormula = null;
            mCalculate = false;
        }
    }

    private void updateFormula(String operand) {
        if (mFirstNumber) {
            mFirstNumber = false;

            mNum1 = getNumberFromInput();

            mFormula = new StringBuilder();

            mFormula.append(mNum1);
            mFormula.append(DELIMITER);
            mFormula.append(operand);
        } else {
            mNum2 = getNumberFromInput();

            mFormula.append(DELIMITER);

            int len = mFormula.length();
            char ch1 = mFormula.toString().charAt(len - 2);
            char ch2 = getResources().getString(R.string.right_bracket).charAt(0);
            if (ch1 == ch2) {
                mFormula.append(operand);
                mFormula.append(DELIMITER);
            } else {
                mFormula.append(mNum2);
                mFormula.append(DELIMITER);
                mFormula.append(operand);
            }
        }

        updateTextViewFormulaShower(mFormula);
    }

    private void updateOperand(@MathematicalOperation int operand) {
        mOperand = operand;
    }

    private void calculate() {
        mCalculatorPresenter.calculate(mNum1, mNum2, mOperand);
    }

    private Double getNumberFromInput() {
        return Double.parseDouble(getCurrInput().toString());
    }

    private void buttonDigitClicked(String digitStr) {
        StringBuilder currInput;
        if (mNewInput) {
            currInput = new StringBuilder();
            mNewInput = false;
        } else {
            currInput = new StringBuilder(mTextViewInputAndResultShower.getText().toString());
        }

        if (currInput.length() > MAX_NUMBER_OF_DIGITS) {
            String msg = getResources().getString(R.string.max_number_of_digits_msg);
            Toast.makeText(requireActivity(), String.format(msg, MAX_NUMBER_OF_DIGITS), Toast.LENGTH_LONG).show();
            return;
        }

        currInput.append(digitStr);

        updateTextViewResultInputShower(currInput);
    }


    @Override
    public void updateViewAfterCalculation(@NonNull MathematicalCalculableOperationResult result) {
        if (result.isOperationSuccessful()) {
            if (result.isSingleNumOperand()) {
                if (mFirstNumber) {
                    mCalculatorPresenter.calculate(mNum1, result.getResult(), mOperand);
                } else {
                    mNum1 = result.getResult();
                    updateTextViewResultInputShower(mNum1 + "");
                }
            } else {
                mNum1 = result.getResult();
                updateTextViewResultInputShower(mNum1 + "");
                mNewInput = true;
            }
        } else {
            updateTextViewResultInputShower(result.getException().getMessage());
        }
    }

    @Override
    public void saveHistoryEntry() {
        mCalculatorPresenter.saveFormula(mFormula.toString(), mNum1);
        mOnDataBaseChangedListener.onDataBaseChanged();
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
        StringBuilder newInput = getCurrInput();
        String dot = getResources().getString(R.string.dot);

        if (!newInput.toString().contains(dot)) {
            newInput.append(getResources().getString(R.string.dot));
            updateTextViewResultInputShower(newInput);
            mNewInput = false;
        }
    }

    private void clearAll() {
        updateTextViewFormulaShower("");
        updateTextViewResultInputShower(getResources().getString(R.string.digit_0));
        mNewInput = true;
        mFirstNumber = true;
        mFormula = null;
        mCalculate = false;
    }

    private void clearAllInputEntry() {
        updateTextViewResultInputShower(getResources().getString(R.string.digit_0));
        mNewInput = true;
    }

    private void deleteLastCharacter() {
        if (!mNewInput) {
            StringBuilder newInputString = getCurrInput();

            if (newInputString.length() > 1) {
                newInputString.deleteCharAt(newInputString.length() - 1);
                updateTextViewResultInputShower(newInputString);
            } else {
                updateTextViewResultInputShower(getResources().getString(R.string.digit_0));
                mNewInput = true;
            }
        }
    }

    private StringBuilder getCurrInput() {
        return new StringBuilder(mTextViewInputAndResultShower.getText());
    }

    private void changeInputSign() {
        StringBuilder newInput = getCurrInput();
        String minusStr = getResources().getString(R.string.minus_sign);
        if (newInput.substring(0, 1).equals(minusStr)) {
            newInput.deleteCharAt(0);
        } else {
            newInput.insert(0, minusStr);
        }

        updateTextViewResultInputShower(newInput);
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
        mFormula = new StringBuilder(entry.getFormula());
        updateTextViewFormulaShower(mFormula);
        updateTextViewResultInputShower(entry.getValue() + "");

        if (mFirstNumber) {
            mNum1 = entry.getValue();
            mFirstNumber = false;
        } else
            mNum2 = entry.getValue();
    }

    @Override
    public void onClick(MemoryEntry item) {
        updateTextViewResultInputShower(item.getValue() + "");
    }
}