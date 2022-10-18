package model;

import database.JDBC;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.Objects;
import java.util.Scanner;


/**
 * The main class for the program.
 *
 * @author Zachary Zamiska
 * @version JDK 11.0
 */

public class Main extends Application {

    public static LocalDateTime time = LocalDateTime.from(LocalDateTime.now().atZone(ZoneId.systemDefault()));

    public static void main(String[] args) {


        JDBC.openConnection();
        launch(args);
        JDBC.closeConnection();
    }


    /**
     * Method to change stages. This is a method to reduce redundancy when changing scenes with any action event handlers
     *
     * @param view  The string of the scene to load
     * @param event The event handler to use
     * @throws IOException This exception is thrown when there is no page to navigate to.
     */
    public static void SetStage(String view, ActionEvent event) throws IOException {
        Stage stage;
        Parent scene;

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(Main.class.getResource(view)));

        stage.setScene(new Scene(scene));

        stage.show();
    }


    /**
     * Programs start method. This is the programs' method that controls what happens as the program is launched(started).
     *
     * @param stage The parameter to put the stage that will be loaded initially.
     * @throws IOException THis exception is thrown if the load keyword in the root variable is null.
     */
    @Override
    public void start(Stage stage) throws IOException {

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/LoginScreen.fxml")));
        stage.setTitle("Screen");
        stage.setScene(new Scene(root));
        stage.show();

    }
}
