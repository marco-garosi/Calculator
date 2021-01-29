package it.marcogarosi.calculator.model;

public class Operation {

    /**
     * The two inputs
     */
    private final Double[] inputs;

    /**
     * The operand to be applied to the inputs
     */
    private Operand operand;

    /**
     * Creates a new empty operation
     */
    public Operation() {
        this.inputs = new Double[2];
    }

    /**
     * Creates a new operation with one or both the inputs
     * @param inputs The inputs to be stored in the operation. There could be either one or two inputs
     *               (but no more and no less)
     */
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

    /**
     * The operation is ready to be performed. It is full in the sense that it already has all the components it needs
     * @return True if there are all components
     */
    public boolean isFull() {
        return operand != null && inputs[0] != null && inputs[1] != null;
    }

    /**
     * Store the passed input
     * @param input The input to store
     */
    public void addInput(Double input) {
        if (inputs[0] != null && inputs[1] != null)
            throw new IllegalStateException("There are already two inputs");

        if (inputs[0] == null)
            inputs[0] = input;
        else
            inputs[1] = input;
    }

    /**
     * Returns the i-th input
     * @param i The index of the input (it must be in the inclusive range [0, 1])
     * @return The i-th input
     */
    public Double getInput(int i) {
        if (i < 0 || i >= inputs.length)
            throw new IllegalArgumentException("Index must be in range [0, " + inputs.length + "]");

        return inputs[i];
    }

    /**
     * Adds the operand to the operation. If there's already one, it overwrites it
     * @param operand The new operand
     */
    public void addOperand(Operand operand) {
        this.operand = operand;
    }

    /**
     * Tells if an operand is set
     * @return True if an operand's set, false otherwise
     */
    public boolean hasOperand() {
        return this.operand != null;
    }

    /**
     * Computes the result applying the specified operation. It doesn't store the result so that
     * it is easier to maintain the object's components "synchronized".
     * Operations are fast and lightweight.
     * @return The result
     */
    public Double apply() {
        if (inputs[0] == null || inputs[1] == null)
            throw new IllegalStateException("Missing input(s)");

        if (operand == null)
            throw new IllegalStateException("Missing operand");

        if (operand == Operand.DIVISION && inputs[1] == 0)
            throw new ArithmeticException("Cannot divide by 0");

        return operand.apply(inputs[0], inputs[1]);
    }

    /**
     * Returns a text-based representation of the operation
     * @return The text-base representation of the operation
     */
    @Override
    public String toString() {
        return "" + inputs[0] + " " + operand + " " + inputs[1];
    }

}
