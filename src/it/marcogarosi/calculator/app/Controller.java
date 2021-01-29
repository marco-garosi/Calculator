package it.marcogarosi.calculator.app;

import it.marcogarosi.calculator.model.Operand;
import it.marcogarosi.calculator.model.Operation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class Controller {

    @FXML
    private Label display;

    private final int DISPLAY_NUMBER_MAX_LEN = 9;

    private Operation operation;
    private String input;
    private boolean equalsWasPressed;

    public Controller() {
        input = "";
        operation = new Operation();
        equalsWasPressed = false;
    }

    @FXML
    private void digitPressed(ActionEvent event) {
        Button button = (Button) event.getSource();

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

        if (operation.isFull()) {
            display.setText(operation.apply().toString());
            operation = new Operation(operation.apply());
        }

        operation.addOperand(Operand.valueOf(button.getId()));
        display.setText(display.getText() + " " + button.getText());

        input = "";
    }

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

    @FXML
    private void clearPressed(ActionEvent event) {
        input = "";
        operation = new Operation();
        equalsWasPressed = false;

        display.setText(input);
    }

    @FXML
    private void deleteDigit(ActionEvent event) {
        if (input == null || input.length() == 0)
            return;

        input = input.substring(0, input.length() - 1);
        display.setText(input);
    }

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
