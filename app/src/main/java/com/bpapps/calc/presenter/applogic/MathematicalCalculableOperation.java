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
                    num1,
                    num2,
                    MathematicalOperation.DIVIDE,
                    null,
                    new MathematicalCalculableOperationException("Invalid input(null) for one or both numbers"));
        } else if (num2 == 0) {
            return new MathematicalCalculableOperationResult(
                    num1,
                    num2,
                    MathematicalOperation.DIVIDE,
                    null,
                    new MathematicalCalculableOperationException("Cannot divide by zero"));
        } else {
            return new MathematicalCalculableOperationResult(
                    num1,
                    num2,
                    MathematicalOperation.DIVIDE,
                    num1 / num2,
                    null);
        }
    }

    private MathematicalCalculableOperationResult multiply(Double num1, Double num2) {
        if (num1 == null || num2 == null) {
            return new MathematicalCalculableOperationResult(
                    num1,
                    num2,
                    MathematicalOperation.MULTIPLY,
                    null,
                    new MathematicalCalculableOperationException("Invalid input(null) for one or both numbers"));
        } else {
            return new MathematicalCalculableOperationResult(
                    num1,
                    num2,
                    MathematicalOperation.MULTIPLY,
                    num1 * num2,
                    null);
        }
    }

    private MathematicalCalculableOperationResult subtract(Double num1, Double num2) {
        if (num1 == null || num2 == null) {
            return new MathematicalCalculableOperationResult(
                    num1,
                    num2,
                    MathematicalOperation.SUBTRACT,
                    null,
                    new MathematicalCalculableOperationException("Invalid input(null) for one or both numbers"));
        } else {
            return new MathematicalCalculableOperationResult(
                    num1,
                    num2,
                    MathematicalOperation.SUBTRACT,
                    num1 - num2,
                    null);
        }
    }

    private MathematicalCalculableOperationResult add(Double num1, Double num2) {
        if (num1 == null || num2 == null) {
            return new MathematicalCalculableOperationResult(
                    num1,
                    num2,
                    MathematicalOperation.ADD,
                    null,
                    new MathematicalCalculableOperationException("Invalid input(null) for one or both numbers"));
        } else {
            return new MathematicalCalculableOperationResult(
                    num1,
                    num2,
                    MathematicalOperation.ADD,
                    num1 + num2,
                    null);
        }
    }

    public MathematicalCalculableOperationResult calculate(Double num, @MathematicalOperation int operand) {
        switch (operand) {
            case MathematicalOperation.CHANGE_SIGN:
                return changeSign(num);
            case MathematicalOperation.INVERSE:
                return inverse(num);
            case MathematicalOperation.PERCENTAGE:
                return percentage(num);
            case MathematicalOperation.POWER_2:
                return pow2(num);
            case MathematicalOperation.SQUARE_ROOT:
                return squareRoot(num);
            default:
                return null;
        }
    }

    private MathematicalCalculableOperationResult squareRoot(Double num) {
        if (num < 0) {
            return new MathematicalCalculableOperationResult(
                    num,
                    null,
                    MathematicalOperation.SQUARE_ROOT,
                    null,
                    new MathematicalCalculableOperationException("Invalid input"));
        } else {
            return new MathematicalCalculableOperationResult(
                    num,
                    null,
                    MathematicalOperation.SQUARE_ROOT,
                    Math.sqrt(num),
                    null);
        }
    }

    private MathematicalCalculableOperationResult pow2(Double num) {
        return new MathematicalCalculableOperationResult(
                num,
                null,
                MathematicalOperation.POWER_2,
                num * num,
                null);
    }

    private MathematicalCalculableOperationResult percentage(Double num) {
        return new MathematicalCalculableOperationResult(
                num,
                null,
                MathematicalOperation.PERCENTAGE,
                num / 100.0,
                null);
    }

    private MathematicalCalculableOperationResult inverse(Double num) {
        if (num == 0) {
            return new MathematicalCalculableOperationResult(
                    num,
                    null,
                    MathematicalOperation.INVERSE,
                    null,
                    new MathematicalCalculableOperationException("Cannot divide by zero"));
        } else {
            return new MathematicalCalculableOperationResult(
                    num,
                    null,
                    MathematicalOperation.INVERSE,
                    1.0 / num,
                    null);
        }
    }

    private MathematicalCalculableOperationResult changeSign(Double num) {
        return new MathematicalCalculableOperationResult(
                num,
                null,
                MathematicalOperation.CHANGE_SIGN,
                (-1.0) * num,
                null);
    }
}
