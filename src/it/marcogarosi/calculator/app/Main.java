package it.marcogarosi.calculator.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("calculator.fxml"));

        primaryStage.setTitle("Calculator");

        primaryStage.setScene(new Scene(root, 300, 500));
        primaryStage.setResizable(false);

        primaryStage.getIcons().add(new Image("file:assets/calculator.png"));

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
