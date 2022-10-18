package controller;

import data_access.UserDAO;
import database.JDBC;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.Main;
import model.User;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * The controller for the Add Appointment view UI.
 * <p>
 * This class is the gatekeeper to the application and byh extension the companies information. It is tasked with handling the user authentication
 * as well as setting the current user for the rest of the program to use.
 * </p>
 * <p>
 * This controller also handle the logging of sign in events whether successful or not for security analysis.
 * </p>
 *
 * @author Zachary Zamiska
 * @version %I%, %G%
 */
public class LoginScreenController implements Initializable {

    ResourceBundle rb = ResourceBundle.getBundle("language_files/rb", Locale.getDefault());
    static public User currentUser;
    static PrintWriter outputFile;
    static boolean initLogin = true;
    int attemptNum = 0;

    @FXML
    private Button exitButtonLbl;
    @FXML
    private Label locationLbl;
    @FXML
    private Button loginButtonLbl;
    @FXML
    private PasswordField loginPasswordTxt;
    @FXML
    private Label loginTitle;
    @FXML
    private TextField loginUserNameTxt;
    @FXML
    private Label passwordLbl;
    @FXML
    private Label userIdLbl;
    @FXML
    private Label userLocationLbl;
    @FXML
    private Label thatsNotRight;


    /**
     * Method that this called when the user presses the <i>Exit<i/> button on the <i>Login Screen</i> UI.
     *
     * <p>
     *     Method that first confirms that the user wants to exit. If yes, the method closes database connection,
     *     output file connection, and ends the program.
     * </p>
     */
    @FXML
    public void onActionExitBtn() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(rb.getString("QuitProgramHeader"));
        alert.setHeaderText(rb.getString("QuitProgramMessage"));
        alert.setContentText(rb.getString("QuitProgramConfirm"));

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            JDBC.closeConnection();
            outputFile.close();
            System.exit(0);
        }
    }


    /**
     * Method that this called when the user presses the <i>Login<i/> button on the <i>Login Screen</i> UI.
     *
     * <p>
     *     This methods heavy lifting is done by the authentication method below.
     * </p>
     *
     * @param event The Action Event handler for the <i>Login Button</i>
     * @throws IOException If an input or output exception occurred
     */
    @FXML
    public void onActionLoginBtn(ActionEvent event) throws IOException {
        userAuthentication(event);
    }



    /**
     * The classes and view initialization method.
     *
     * <p>
     *     This method does the following:
     *     <ul>
     *         <li>Sets the log in UI language based on the users computer language settings.</li>
     *         <li>Set's the initial login boolean to true to prevent the upcoming appointment dialog box popping up every new view.</li>
     *         <li>Creates/Amends the log in activity log with a new session header.</li>
     *     </ul>
     * </p>
     *
     * @param url            The methods' URL to the world wide web if needed
     * @param resourceBundle The methods' resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        textSet();
        initLogin = true;

        String filename = "login_activity.txt";
        FileWriter fwriter;
        try {
            fwriter = new FileWriter(filename, true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        outputFile = new PrintWriter(fwriter);
        outputFile.println("\n *****NEW PROGRAM SESSION  |  " + Main.time.format(DateTimeFormatter.ofPattern("LL-dd-yyyy  hh:mm a")) + "*****  \n");
    }


    /**
     * The method tasked with language control.
     *
     * <p>
     *     This method controls what language file to use depending on the local computer language settings.
     *     Once the language is ascertained it sets the labels, buttons, and alerts to said language.
     * </p>
     *
     */
    public void textSet() {

        loginTitle.setText(rb.getString("AptManager"));
        loginButtonLbl.setText(rb.getString("Login"));
        userIdLbl.setText(rb.getString("UserID"));
        exitButtonLbl.setText(rb.getString("Exit"));
        passwordLbl.setText(rb.getString("Password"));
        locationLbl.setText(rb.getString("Location"));
        thatsNotRight.setText(rb.getString("NotRightPass"));

        //  This does not require a different language:
        userLocationLbl.setText(String.valueOf(ZoneId.systemDefault()));


    }


    /**
     * The user authentication method.
     *
     * <p>
     *     This method controls the username and password check for the log in form. This method also writes to the login activity file
     *     for each attempted log in.
     * </p>
     *
     * @param actionEvent The Action Event handler for the method
     * @throws IOException If an input or output exception occurred
     */
    private void userAuthentication(ActionEvent actionEvent) throws IOException {

        ObservableList<User> userList = UserDAO.getAllUsers.getAll();

        try {
            for (User user : userList) {
                if (loginUserNameTxt.getText().equals(user.getUserName())) {
                    currentUser = user;
                    outputFile.println("\n Log in attempt: \n");
                    outputFile.print("Username: " + currentUser.getUserName() + " | ");
                    outputFile.print("Date: " + LocalDate.now());
                    outputFile.print(" | " + "Time: " + LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm a")) + "\n");
                }

            }
            if (currentUser.getUserPassword().equals(loginPasswordTxt.getText())) {
                outputFile.print("Password entered: " + loginPasswordTxt.getText());
                outputFile.print(" >>>  LOG IN SUCCESSFUL" + "\n");

                Main.SetStage("/view/MainScreen.fxml", actionEvent);
            } else {
                attemptNum++;
                outputFile.print("Password entered: " + loginPasswordTxt.getText());
                outputFile.print(" >>>  LOG IN FAILED  |   ATTEMPT NUMBER FOR SESSION: " + attemptNum + "\n");

                currentUser = null;
                thatsNotRight.setVisible(true);
            }
        } catch (NullPointerException e) {
            thatsNotRight.setVisible(true);
            attemptNum++;
            outputFile.println("\n Log in attempt: \n");
            outputFile.print("Username: " + loginUserNameTxt.getText() + " | ");
            outputFile.print("Date: " + LocalDate.now());
            outputFile.print(" | " + "Time: " + LocalTime.now().format(DateTimeFormatter.ofPattern("hh:mm a")) + "\n");
            outputFile.print("Password entered: " + loginPasswordTxt.getText());
            outputFile.print(" >>>  LOG IN FAILED  |   ATTEMPT NUMBER FOR SESSION: " + attemptNum + "\n");
        }
    }

}
