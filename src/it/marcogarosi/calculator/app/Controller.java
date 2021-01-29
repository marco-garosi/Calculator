package it.marcogarosi.calculator.app;

import it.marcogarosi.calculator.model.Operand;
import it.marcogarosi.calculator.model.Operation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class Controller {

    /**
     * The calculator "display"
     */
    @FXML
    private Label display;

    /**
     * Maximum number of digits that can be displayed.
     */
    private final int DISPLAY_NUMBER_MAX_LEN = 9;

    /**
     * The operation to execute
     */
    private Operation operation;

    /**
     * Current user input
     */
    private String input;

    /**
     * Support variable. It is true when last performed action is pressing "="
     */
    private boolean equalsWasPressed;

    /**
     * Creates a new Controller
     */
    public Controller() {
        input = "";
        operation = new Operation();
        equalsWasPressed = false;
    }

    /**
     * A digit button has been pressed. It composes the input
     * @param event The event containing a reference to the source (which button was actually pressed)
     */
    @FXML
    private void digitPressed(ActionEvent event) {
        Button button = (Button) event.getSource();

        // Last action performed is pressing the "=" button
        if (equalsWasPressed) {
            input = "";
            operation = new Operation();
            equalsWasPressed = false;
        }

        if (input.length() == DISPLAY_NUMBER_MAX_LEN)
            return;

        if (input.contains(".") && button.getId().equals("DOT"))
            return;

        input += button.getText();
        display.setText(input);
    }

    /**
     * Add the operator to the operation that has to be performed.
     * @param event The event containing a reference to the source (which button was actually pressed)
     */
    @FXML
    private void operatorPressed(ActionEvent event) {
        Button button = (Button) event.getSource();

        if (equalsWasPressed)
            equalsWasPressed = false;

        if (input == null || input.length() == 0) {
            operation.addOperand(Operand.valueOf(button.getId()));

            if (operation.getInput(0) != null)
                display.setText(operation.getInput(0).toString() + " " + button.getText());

            return;
        }

        addInput();

        // The operation can be performed because there are already two numbers
        if (operation.isFull()) {
            display.setText(operation.apply().toString());
            operation = new Operation(operation.apply());
        }

        operation.addOperand(Operand.valueOf(button.getId()));
        display.setText(display.getText() + " " + button.getText());

        input = "";
    }

    /**
     * Perform the operation. If no operator was specified by the user it displays the available input.
     * It stores the result in a new operation, so that the user can chain them without entering the
     * result every time.
     * @param event The event containing a reference to the source (which button was actually pressed)
     */
    @FXML
    private void equalsPressed(ActionEvent event) {

        equalsWasPressed = true;

        if (input == null || input.length() == 0) {
            if (operation.hasOperand()) {
                display.setText(operation.getInput(0).toString());
                operation = new Operation();
            }

            return;
        }

        addInput();
        input = "";

        if (!operation.isFull()) {
            display.setText(operation.getInput(0).toString());
            return;
        }

        display.setText(operation.apply().toString());
        operation = new Operation(operation.apply());
    }

    /**
     * Clear the screen and the input. It behaves as the "AC" button on common calculators
     * @param event The event containing a reference to the source (which button was actually pressed)
     */
    @FXML
    private void clearPressed(ActionEvent event) {
        input = "";
        operation = new Operation();
        equalsWasPressed = false;

        display.setText(input);
    }

    /**
     * Delete the last digit in the input and update the view
     * @param event The event containing a reference to the source (which button was actually pressed)
     */
    @FXML
    private void deleteDigit(ActionEvent event) {
        if (input == null || input.length() == 0)
            return;

        input = input.substring(0, input.length() - 1);
        display.setText(input);
    }

    /**
     * Add the input to the operation. It does not handle all the exceptions because many are non-critic and do
     * not impact on the result; those are much like "warnings" and in some cases they could be really helpful.
     * That is not the case, though, so they are "ignored"
     */
    private void addInput() {
        try {
            operation.addInput(Double.parseDouble(input));
        } catch (NullPointerException e) {
        } catch (NumberFormatException e) {
            operation.addInput((double) 0);
            display.setText("0");
        } catch (IllegalStateException e) {
        } catch (Exception e) {
        }
    }

}
