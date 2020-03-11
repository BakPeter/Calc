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
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;


public class CalculatorFragment extends Fragment
        implements View.OnClickListener, ICalculatorContract.View, HistoryRecyclerViewAdapter.IMemoryItemClicked, MemoryRecyclerViewAdapter.IOnMemoryItemClickCallBack {

    private static final String TAG = CalculatorFragment.class.getSimpleName();

    private static final int MAX_NUMBER_OF_DIGITS = 25;
    //private static final int MAX_NUMBER_OF_OPERANDS_IN_FORMULA = 8;
    private static final String DELIMITER = " ";

    private ICalculatorContract.Presenter mCalculatorPresenter;
    private IMemoryContract.Presenter mMemoryPresenter;

    private IOnHistoryDataBaseChangedListener mOnHistoryDataBaseChangedListener;

    public void setOnHistoryDataBaseChangedListener(IOnHistoryDataBaseChangedListener onHistoryDataBaseChangedListener) {
        mOnHistoryDataBaseChangedListener = onHistoryDataBaseChangedListener;
    }


    private TextView mTextViewFormulaShower;
    private TextView mTextViewInputAndResultShower;
    private Snackbar mSnackbar;

    private FormulaManager mFormulaManager;
    private StringBuilder mInput;
    private Params mParams = new Params();
    private boolean mIsPrevOperandUnary;


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
        setPrevOperandUnary(false);

        initTextViews(view);

        initButtons(view);

        initSnackBar(view);

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
        oneParamOperands.add(resources.getString(R.string.percentage_sign));

        mFormulaManager = new FormulaManager(DELIMITER, oneParamOperands);

        setPrevOperandUnary(false);
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

    void initSnackBar(View view) {
        String text = String.format(
                getResources().getString(R.string.number_of_digits_out_of_allowed_bound_format_msg),
                MAX_NUMBER_OF_DIGITS);

        mSnackbar = Snackbar.make(view, text, BaseTransientBottomBar.LENGTH_INDEFINITE);
        mSnackbar.setAction("OK", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSnackbar.dismiss();
            }
        });
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
                onClickUnaryParamOperand(
                        getResources().getString(R.string.percentage_sign_text_format),
                        getResources().getString(R.string.percentage_sign),
                        MathematicalOperation.PERCENTAGE);
                break;

            case R.id.button_math_operation_add:
                onClickBinaryOperandButton(getResources().getString(R.string.add_sign), MathematicalOperation.ADD);
                break;
            case R.id.button_math_operation_subtract:
                onClickBinaryOperandButton(getResources().getString(R.string.subtract_sign), MathematicalOperation.SUBTRACT);
                break;
            case R.id.button_math_operation_divide:
                onClickBinaryOperandButton(getResources().getString(R.string.divide_sign), MathematicalOperation.DIVIDE);
                break;
            case R.id.button_math_operation_multiply:
                onClickBinaryOperandButton(getResources().getString(R.string.multiply_sign), MathematicalOperation.MULTIPLY);
                break;

            case R.id.button_math_operation_equals:
                onClickEqualsButton();
                break;


            case R.id.button_digit_0:
                onClickDigitButton(getResources().getString(R.string.digit_0));
                break;
            case R.id.button_digit_1:
                onClickDigitButton(getResources().getString(R.string.digit_1));
                break;
            case R.id.button_digit_2:
                onClickDigitButton(getResources().getString(R.string.digit_2));
                break;
            case R.id.button_digit_3:
                onClickDigitButton(getResources().getString(R.string.digit_3));
                break;
            case R.id.button_digit_4:
                onClickDigitButton(getResources().getString(R.string.digit_4));
                break;
            case R.id.button_digit_5:
                onClickDigitButton(getResources().getString(R.string.digit_5));
                break;
            case R.id.button_digit_6:
                onClickDigitButton(getResources().getString(R.string.digit_6));
                break;
            case R.id.button_digit_7:
                onClickDigitButton(getResources().getString(R.string.digit_7));
                break;
            case R.id.button_digit_8:
                onClickDigitButton(getResources().getString(R.string.digit_8));
                break;
            case R.id.button_digit_9:
                onClickDigitButton(getResources().getString(R.string.digit_9));
                break;
        }
    }

    private void onClickUnaryParamOperand(String operandFormatString, String operandSign, @MathematicalOperation int operand) {
        StringBuilder currInput = getCurrInput();
        deleteZerozAtEnd(currInput);

        mFormulaManager.addUnaryOperand(operandFormatString, operandSign, currInput.toString());

        Params params = new Params();
        params.addNumber(Double.parseDouble(currInput.toString()));
        params.addOperand(operand);

        mCalculatorPresenter.calculateUnaryOperand(params);
    }


    private void onClickBinaryOperandButton(String operandSign, @MathematicalOperation int operand) {
        StringBuilder currInput = getCurrInput();
        deleteZerozAtEnd(currInput);

        if (mIsPrevOperandUnary)
            mFormulaManager.addBinaryOperand(operandSign, null);
        else
            mFormulaManager.addBinaryOperand(operandSign, currInput.toString());

        if (!mIsPrevOperandUnary) {
            mParams.addNumber(Double.parseDouble(currInput.toString()));
        }

        mParams.addOperand(operand);
        mCalculatorPresenter.calculateBinaryOperand(mParams);
    }


    @Override
    public void onCalculated(@NonNull MathematicalCalculableOperationResult result) {
        if (result.isOperationSuccessful()) {
            Double resultValue = result.getResult();
            if (result.isUnaryOperation()) {
                addParamAfterUnaryOperandCalculation(result);
                setPrevOperandUnary(true);
            } else {
                setPrevOperandUnary(false);
            }

            updateTextViewResultInputShower(resultValue.toString());
            mFormulaManager.setValue(resultValue);
        } else {
            updateTextViewResultInputShower(result.getException().getMessage());

            initFormula();
            mParams.init();
        }

        initInput();
        updateTextViewFormulaShower(mFormulaManager.getFormula());
    }

    private void addParamAfterUnaryOperandCalculation(MathematicalCalculableOperationResult result) {
        ArrayList<Double> numbers = mParams.getNumbers();

        if (mIsPrevOperandUnary) {
            numbers.remove(numbers.size() - 1);
        }

        mParams.addNumber(result.getResult());
    }

    private void onClickEqualsButton() {
        StringBuilder currNumber = getCurrInput();
        deleteZerozAtEnd(currNumber);

        if (mParams.getNumbers().size() == 0) {
            mFormulaManager.addEquals(getResources().getString(R.string.equals_sign), currNumber.toString());
            mFormulaManager.setValue(Double.parseDouble(currNumber.toString()));
            updateTextViewFormulaShower(mFormulaManager.getFormula());
        } else if (mParams.getNumbers().size() == 1 && mParams.getOperands().size() == 0) {
            mFormulaManager.addEquals(getResources().getString(R.string.equals_sign), null);
            mFormulaManager.setValue(Double.parseDouble(currNumber.toString()));
            updateTextViewFormulaShower(mFormulaManager.getFormula());
        } else if (mParams.getNumbers().size() == mParams.getOperands().size()) {
            mFormulaManager.addEquals(getResources().getString(R.string.equals_sign), currNumber.toString());
            mParams.addNumber(Double.parseDouble(currNumber.toString()));
            mCalculatorPresenter.calculateBinaryOperand(mParams);
        } else if (mParams.getNumbers().size() > mParams.getOperands().size()) {
            mFormulaManager.addEquals(getResources().getString(R.string.equals_sign), null);
            mCalculatorPresenter.calculateBinaryOperand(mParams);
        }

        saveHistoryEntry();

        initFormula();

        initInput();
        mParams.init();

        setPrevOperandUnary(false);
    }


    private void clearMemoryDataBase() {
        mMemoryPresenter.clearDataBase();
        Toast.makeText(requireContext(), getString(R.string.memory_deleted_msg), Toast.LENGTH_LONG).show();
    }

    private void memoryRecall() {
        MemoryEntry item = mMemoryPresenter.getMemoryItem(0);
        if (item == null) {
            Toast.makeText(requireContext(), getString(R.string.memory_empty_msg), Toast.LENGTH_LONG).show();
        } else {
            initInput();
            String num = item.getValue() + "";
            updateTextViewResultInputShower(num);
            mInput.append(num);
        }
    }

    private void addToMemory() {
        String currInput = getCurrInput().toString();

        mMemoryPresenter.addToMemoryItem(Double.parseDouble(currInput));
        Toast.makeText(requireContext(), getString(R.string.memory_added_msg), Toast.LENGTH_LONG).show();
    }

    private void subtractFromMemory() {
        String currInput = getCurrInput().toString();

        mMemoryPresenter.subtractFromMemory(Double.parseDouble(currInput));
        Toast.makeText(requireContext(), getString(R.string.memory_subtracted_msg), Toast.LENGTH_LONG).show();
    }

    private void addToMemoryDataBase() {
        String currInput = getCurrInput().toString();
        MemoryEntry item = new MemoryEntry(Double.parseDouble(currInput));

        mMemoryPresenter.addToDataBase(item);
        Toast.makeText(requireContext(), getString(R.string.memory_added_new_item_msg), Toast.LENGTH_LONG).show();
    }

    private void onClickDigitButton(String digitStr) {
        if (mInput.length() > MAX_NUMBER_OF_DIGITS) {
            mSnackbar.show();
            return;
        }

        StringBuilder minusZero = new StringBuilder(getResources().getString(R.string.minus_sign));
        if (mInput.length() == 1) {
            if (mInput.substring(0, 1).equals(getResources().getString(R.string.digit_0)) &&
                    digitStr.equals(getResources().getString(R.string.digit_0)))
                return;

            if (mInput.substring(0, 1).equals(minusZero.toString()))
                mInput.deleteCharAt(0);
        }

        if (mInput.length() == 2) {
            minusZero.append(getResources().getString(R.string.digit_0));
            if (mInput.substring(0, 2).equals(minusZero.toString()))
                mInput.deleteCharAt(1);
        }

        mInput.append(digitStr);
        updateTextViewResultInputShower(mInput);
    }


    @Override
    public void saveHistoryEntry() {
        mCalculatorPresenter.saveFormula(mFormulaManager.getFormula().toString(), mFormulaManager.getValue());
        if (mOnHistoryDataBaseChangedListener != null)
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
            int len = mInput.length();
            if (len == 0) {
                mInput.append(getResources().getString(R.string.digit_0));
            } else if (len == 1) {
                if (mInput.substring(0, 1).equals(getResources().getString(R.string.minus_sign))) {
                    mInput.append(getResources().getString(R.string.digit_0));
                }
            }

            mInput.append(dot);
        }

        updateTextViewResultInputShower(mInput);
    }

    private void clearAll() {
        initFormula();
        initInput();
        mParams.init();

        updateTextViewFormulaShower("");
        updateTextViewResultInputShower(getResources().getString(R.string.digit_0));
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

    private void changeInputSign() {
        //TODO bug fix private void changeInputSign()
        String minusSign = getResources().getString(R.string.minus_sign);
        if (mInput.length() > 0) {
            if (mInput.substring(0, 1).equals(minusSign)) {
                mInput.deleteCharAt(0);
                // mInput.append(getResources().getString(R.string.digit_0));
            } else {
                mInput.insert(0, minusSign);
            }
        } else {
            mInput.append(minusSign);
            mInput.append(getResources().getString(R.string.digit_0));
        }

        updateTextViewResultInputShower(mInput);
    }

    private StringBuilder getCurrInput() {
        String currInput = mTextViewInputAndResultShower.getText().toString();
        if(currInput.equals("Cannot divide by zero"))
            return new StringBuilder(getResources().getString(R.string.digit_0));
        else
            return new StringBuilder(currInput);
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
        initFormula();
        initInput();
        mParams.init();
        updateTextViewFormulaShower(entry.getFormula());
        updateTextViewResultInputShower(entry.getValue() + "");
    }

    @Override
    public void onClick(MemoryEntry item) {
        String num = item.getValue() + "";
        updateTextViewResultInputShower(num);
        initInput();
        mInput.append(num);
    }

    private void initInput() {
        mInput.setLength(0);
    }

    public void setPrevOperandUnary(boolean prevOperandUnary) {
        mIsPrevOperandUnary = prevOperandUnary;
    }

    private void deleteZerozAtEnd(StringBuilder number) {
        Resources resources = getResources();
        int indexOfDot = number.indexOf(resources.getString(R.string.dot));

        if (indexOfDot != -1) {
            if (number.length() - 1 == indexOfDot) {
                number.deleteCharAt(number.length() - 1);
                return;
            }

            char digit0 = resources.getString(R.string.digit_0).charAt(0);
            for (int i = number.length() - 1; i > indexOfDot; i--) {
                if (number.charAt(i) == digit0) {
                    number.deleteCharAt(i);
                } else {
                    break;
                }
            }

            indexOfDot = number.indexOf(resources.getString(R.string.dot));
            if (indexOfDot == number.length() - 1) {
                number.deleteCharAt(indexOfDot);
            }
        }
    }
}