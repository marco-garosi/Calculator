package it.marcogarosi.calculator.model;

public class Operation {

    private final Double[] inputs;
    private Operand operand;

    public Operation() {
        this.inputs = new Double[2];
    }

    public Operation(Double... inputs) {
        if (inputs == null || inputs.length > 2)
            throw new IllegalArgumentException("There should be one or two inputs");

        if (inputs.length == 1) {
            this.inputs = new Double[2];
            addInput(inputs[0]);
        } else {
            this.inputs = inputs;
        }
    }

    public boolean isFull() {
        return operand != null && inputs[0] != null && inputs[1] != null;
    }

    public void addInput(Double input) {
        if (inputs[0] != null && inputs[1] != null)
            throw new IllegalStateException("There are already two inputs");

        if (inputs[0] == null)
            inputs[0] = input;
        else
            inputs[1] = input;
    }

    public Double getInput(int i) {
        if (i < 0 || i >= inputs.length)
            throw new IllegalArgumentException("Index must be in range [0, " + inputs.length + "]");

        return inputs[i];
    }

    public void addOperand(Operand operand) {
        this.operand = operand;
    }

    public boolean hasOperand() {
        return this.operand != null;
    }

    public Double apply() {
        if (inputs[0] == null || inputs[1] == null)
            throw new IllegalStateException("Missing input(s)");

        if (operand == null)
            throw new IllegalStateException("Missing operand");

        if (operand == Operand.DIVISION && inputs[1] == 0)
            throw new ArithmeticException("Cannot divide by 0");

        return operand.apply(inputs[0], inputs[1]);
    }

    @Override
    public String toString() {
        return "" + inputs[0] + " " + operand + " " + inputs[1];
    }

}
