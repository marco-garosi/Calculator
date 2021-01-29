package it.marcogarosi.calculator.model;

import java.util.function.BiFunction;

public enum Operand {

    SUM((a, b) -> a + b),
    SUBTRACTION((a, b) -> a - b),
    MULTIPLICATION((a, b) -> a * b),
    DIVISION((a, b) -> a / b);

    private final BiFunction<Double, Double, Double> operation;

    private Operand(BiFunction<Double, Double, Double> operation) {
        this.operation = operation;
    }

    public Double apply(Double a, Double b) {
        return operation.apply(a, b);
    }

    @Override
    public String toString() {
        switch(this) {
            case SUM:
                return "+";
            case SUBTRACTION:
                return "-";
            case MULTIPLICATION:
                return "*";
            case DIVISION:
                return "/";
            default:
                return "";
        }
    }

}
