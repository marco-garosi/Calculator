package it.marcogarosi.calculator.model;

import java.util.function.BiFunction;

/**
 * Represents the operands +, -, * and /.
 * Each one specifies its own Function from Double, Double to Double via the
 * BiFunction functional interface.
 * Separate classes could be created instead of this enum, but I wanted to try
 * something different.
 */
public enum Operand {

    SUM((a, b) -> a + b),
    SUBTRACTION((a, b) -> a - b),
    MULTIPLICATION((a, b) -> a * b),
    DIVISION((a, b) -> a / b);

    /**
     * The operation that the operand will perform
     */
    private final BiFunction<Double, Double, Double> operation;

    /**
     * Creates the operand memorizing its operation
     * @param operation The operation that the operand will perform
     */
    private Operand(BiFunction<Double, Double, Double> operation) {
        this.operation = operation;
    }

    /**
     * Applies the operation (function) to the operands
     * @param a First input
     * @param b Second input
     * @return The result of the operation
     */
    public Double apply(Double a, Double b) {
        return operation.apply(a, b);
    }

    /**
     * Returns a text-based representation of the operand
     * @return The text-base representation of the operand
     */
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
