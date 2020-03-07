package com.bpapps.calc.presenter.applogic;

public class MathematicalCalculableOperation {
    public MathematicalCalculableOperationResult calculate(Double num1, Double num2, @MathematicalOperation int operand) {
        switch (operand) {
            case MathematicalOperation.ADD:
                return add(num1, num2);
            case MathematicalOperation.SUBTRACT:
                return subtract(num1, num2);
            case MathematicalOperation.MULTIPLY:
                return multiply(num1, num2);
            case MathematicalOperation.DIVIDE:
                return divide(num1, num2);
            default:
                return null;
        }
    }

    private MathematicalCalculableOperationResult divide(Double num1, Double num2) {
        if (num1 == null || num2 == null) {
            return new MathematicalCalculableOperationResult(
                    null,
                    null,
                    null,
                    new MathematicalCalculableOperationException("Invalid input(null) for one or both numbers"));
        } else if (num2 == 0) {
            return new MathematicalCalculableOperationResult(
                    null,
                    null,
                    null,
                    new MathematicalCalculableOperationException("Cannot divide by zero"));
        } else {
            return new MathematicalCalculableOperationResult(
                    null,
                    null,
                    num1 / num2,
                    null);
        }
    }

    private MathematicalCalculableOperationResult multiply(Double num1, Double num2) {
        if (num1 == null || num2 == null) {
            return new MathematicalCalculableOperationResult(
                    null,
                    null,
                    null,
                    new MathematicalCalculableOperationException("Invalid input(null) for one or both numbers"));
        } else {
            return new MathematicalCalculableOperationResult(
                    null,
                    null,
                    num1 * num2,
                    null);
        }
    }

    private MathematicalCalculableOperationResult subtract(Double num1, Double num2) {
        if (num1 == null || num2 == null) {
            return new MathematicalCalculableOperationResult(
                    null,
                    null,
                    null,
                    new MathematicalCalculableOperationException("Invalid input(null) for one or both numbers"));
        } else {
            return new MathematicalCalculableOperationResult(
                    null,
                    null,
                    num1 - num2,
                    null);
        }
    }

    private MathematicalCalculableOperationResult add(Double num1, Double num2) {
        if (num1 == null || num2 == null) {
            return new MathematicalCalculableOperationResult(
                    null,
                    null,
                    null,
                    new MathematicalCalculableOperationException("Invalid input(null) for one or both numbers"));
        } else {
            return new MathematicalCalculableOperationResult(
                    null,
                    null,
                    num1 + num2,
                    null);
        }
    }

    public MathematicalCalculableOperationResult calculate(Double num, @MathematicalOperation int operand) {
        switch (operand) {
            case MathematicalOperation.PERCENTAGE:
                return percentage(num);
            default:
                return null;
        }
    }


    private MathematicalCalculableOperationResult percentage(Double num) {
        return new MathematicalCalculableOperationResult(
                null,
                null,
                num / 100.0,
                null);
    }

}
