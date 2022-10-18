package controller;

import data_access.AppointmentDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.AlertInterface;
import model.Appointment;
import model.Main;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;

import static controller.LoginScreenController.initLogin;
import static model.Main.time;


/**
 * The controller for the <i>Main Screen</i> view UI.
 * <p>
 * This is one of the more important methods in the program it has many responsibilities.
 *     <ul>
 *         <li>This method populates and controls all of the UI elements for the view as well as the
 *         alerts/pop ups.</li>
 *         <li>Controls the filter radio button logic for the appointment data.</li>
 *     </ul>
 * </p>
 *
 * @author Zachary Zamiska
 * @version %I%, %G%
 */
public class MainScreenController implements Initializable {

    private Appointment appointmentData;

    static AlertInterface alertPopup = (alert, title, header, content, wait) -> {
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        if (wait) {
            alert.showAndWait();
        }
    };


    @FXML
    private TableView<Appointment> appointmentTableView;
    @FXML
    private TableColumn<Appointment, Integer> appointmentContactCol;
    @FXML
    private TableColumn<Appointment, Integer> appointmentCustomerIdCol;
    @FXML
    private TableColumn<Appointment, String> appointmentDescriptionCol;
    @FXML
    private TableColumn<Appointment, Timestamp> appointmentEndTimeCol;
    @FXML
    private TableColumn<Appointment, Integer> appointmentIdCol;
    @FXML
    private TableColumn<Appointment, String> appointmentLocationCol;
    @FXML
    private TableColumn<Appointment, Timestamp> appointmentStartTimeCol;
    @FXML
    private TableColumn<Appointment, String> appointmentTitleCol;
    @FXML
    private TableColumn<Appointment, String> appointmentTypeCol;
    @FXML
    private TableColumn<Appointment, Integer> appointmentUserIdCol;
    @FXML
    private RadioButton filterByMonthRBtn;
    @FXML
    private RadioButton filterByWeekRBtn;
    @FXML
    private RadioButton filterOffRBtn;

    /**
     * Method that this called when the user presses the <i>Add<i/> button on the <i>Main Screen</i> UI.
     *
     * <p>
     * When the add button is pressed this method takes the user to the <i>Add Appointment</i> view.
     * </p>
     *
     * @param event The action event handler for the method
     * @throws IOException If an input or output exception occurred.
     */
    @FXML
    public void onActionAppointmentAddBtn(ActionEvent event) throws IOException {
        Main.SetStage("/view/AddAppointment.fxml", event);
    }


    /**
     * Method that this called when the user presses the <i>Delete<i/> button on the <i>Main Screen</i> UI.
     *
     * <p>
     * When the user highlights an item in the appointment table view and selects this button
     * the item is deleted in the database through the <i>AppointmentDAO</i> file.
     * </p>
     *
     * <h3>Lambda Use #2:</h3>
     * <p>
     *     The use of the lambda helps with the ease of use for the programmer to program alerts.
     * </p>
     */
    @FXML
    public void onActionAppointmentDeleteBtn() {
        appointmentData = appointmentTableView.getSelectionModel().getSelectedItem();

        if (appointmentData != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alertPopup.AlertPopup(alert, "Delete Appointment?", "The following appointment will be cancelled: \n", "ID: " + appointmentData.getAppId() + "  |  Name: " + appointmentData.getAppTitle() + "  |  "
                    +  "Type: " + appointmentData.getAppType() + "  |  " + "Date: " + appointmentData.getAppStartDate().toLocalDateTime().toLocalDate() + "  |  "
                    + "Time: " + appointmentData.getAppStartDate().toLocalDateTime().toLocalTime().format(DateTimeFormatter.ofPattern("hh:mm a"))
                    + "\n\nPress ok to continue.", false);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                AppointmentDAO.deleteAppointment(appointmentData.getAppId());
                appointmentTableView.setItems(AppointmentDAO.getAllAppointments());
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alertPopup.AlertPopup(alert, "Error", "No appointment is highlighted.", "Please select an existing appointment to delete.", true);
        }


    }

    /**
     * Method that this called when the user presses the <i>Modify<i/> button on the <i>Main Screen</i> UI.
     *
     * <p>
     * This method does several things:
     *     <ul>
     *         <li>Checks if an item is selected in the appointment table view.</li>
     *         <li>If an item is selected, the method takes the data and sends it to the <i>Modify Appointment Controller</i>.</li>
     *         <li>Pre-initializes the <i>Modify Appointment Controller</i> and navigates to the <i>Modify Appointment</i> view.</li>
     *     </ul>
     * </p>
     *
     * @param event The action event handler for the method
     * @throws IOException If an input or output exception occurred.
     * @see ModifyAppointmentController
     */
    @FXML
    public void onActionAppointmentModifyBtn(ActionEvent event) throws IOException {

        appointmentData = appointmentTableView.getSelectionModel().getSelectedItem();

        if (appointmentData != null) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ModifyAppointment.fxml"));
            loader.load();
            ModifyAppointmentController MAController = loader.getController();
            MAController.sendAppointment(appointmentData);

            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent sceneLoad = loader.getRoot();
            stage.setScene(new Scene(sceneLoad));
            stage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alertPopup.AlertPopup(alert, "Error", "No appointment is highlighted", "Please select an existing appointment or create a new one.", true);
        }

    }

    /**
     * Method that this called when the user presses the <i>Customer Data<i/> button on the <i>Main Screen</i> UI.
     *
     * <p>
     * When the button is pressed this method takes the user to the <i>Customer Data</i> view.
     * </p>
     *
     * @param event The action event handler for the method
     * @throws IOException If an input or output exception occurred.
     */
    @FXML
    public void onActionCustomerDataBtn(ActionEvent event) throws IOException {
        Main.SetStage("/view/CustomerData.fxml", event);
    }

    /**
     * Method that this called when the user presses the <i>Logout<i/> button on the <i>Main Screen</i> UI.
     *
     * <p>
     * When the add button is pressed this method confirms that the user wants to take such action.
     * If yes, this method takes the user to the <i>Login Screen</i> view as well as resetting the
     * user authentication and current user variables..
     * </p>
     *
     * @param event The action event handler for the method
     * @throws IOException If an input or output exception occurred.
     */
    @FXML
    public void onActionLogoutBtn(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alertPopup.AlertPopup(alert, "Logout?", "Are you sure you would like to logout?", "Select ok to continue.", false);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Main.SetStage("/view/LoginScreen.fxml", event);
        }
    }

    /**
     * The <i>Main Screen</i> initialization method for the UI.
     *
     * <p>
     * This method runs when the Main Screen view is run. This method is tasked with checking if the user has any
     * appointments upcoming in the next 15 minutes as well as populating the appointment table view.
     * </p>
     *
     * @param url            The methods' URL to the world wide web if needed
     * @param resourceBundle The methods' resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        LoginScreenController.outputFile.close();


        appointmentTableView.setItems(AppointmentDAO.getAllAppointmentsModified());

        appointmentIdCol.setCellValueFactory(new PropertyValueFactory<>("appId"));
        appointmentTitleCol.setCellValueFactory(new PropertyValueFactory<>("appTitle"));
        appointmentDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("appDescription"));
        appointmentLocationCol.setCellValueFactory(new PropertyValueFactory<>("appLocation"));
        appointmentContactCol.setCellValueFactory(new PropertyValueFactory<>("appContactName"));
        appointmentTypeCol.setCellValueFactory(new PropertyValueFactory<>("appType"));
        appointmentStartTimeCol.setCellValueFactory(new PropertyValueFactory<>("appStartDate"));
        appointmentEndTimeCol.setCellValueFactory(new PropertyValueFactory<>("appEndDate"));
        appointmentCustomerIdCol.setCellValueFactory(new PropertyValueFactory<>("appCustomerId"));
        appointmentUserIdCol.setCellValueFactory(new PropertyValueFactory<>("appUserId"));

        boolean noApt = true;
        if (initLogin) {
            LocalDateTime plus15 = time.plusMinutes(15);

            for (Appointment a : appointmentTableView.getItems()) {

                if ((a.getAppStartDate().toLocalDateTime().isBefore(plus15)) && (a.getAppStartDate().toLocalDateTime().isAfter(Main.time))) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alertPopup.AlertPopup(alert, "Upcoming Appointments", "You have the following appointment upcoming: \n", "ID: " + a.getAppId() + "  |  Name: " + a.getAppTitle() + "  |  "
                            + "Date: " + a.getAppStartDate().toLocalDateTime().toLocalDate() + "  |  "
                            + "Time: " + a.getAppStartDate().toLocalDateTime().toLocalTime().format(DateTimeFormatter.ofPattern("hh:mm a"))
                            + "\n\nPress ok to continue.", true);

                    noApt = false;
                }
            }

            if (noApt) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alertPopup.AlertPopup(alert, "Upcoming Appointments", "You have no upcoming appointment withing the next 15 minutes", "Select ok to continue.", true);
            }
            initLogin = false;
        }

    }

    /**
     * Method that this called when the user presses the <i>Generate Appointment<i/> button on the <i>Main Screen</i> UI.
     *
     * <p>
     * When the Generate Appointment button is pressed this method takes the user to the <i>Report Creator</i> view.
     * </p>
     *
     * @param event The action event handler for the method
     * @throws IOException If an input or output exception occurred.
     */
    @FXML
    public void onActionCustomerGenReportBtn(ActionEvent event) throws IOException {
        Main.SetStage("/view/Report.fxml", event);
    }

    /**
     * Method that controls the filtering for the <i>Main Screen</i> views appointment table.
     *
     * <p>
     * This method is tasked with handling the UI events that pertain to the filter radio buttons as well as
     * their logic to actually filter.
     * </p>
     */
    @FXML
    public void onActionFilterRBtn() {
        // TODO: 6/22/2022 ADD IN THE CONTACT NAMES TO THE TABLE VIEW FOR FILTER AS WELL 
        if (filterByMonthRBtn.isSelected()) {
            appointmentTableView.setItems(AppointmentDAO.filterAppointmentsByWeek("MM"));

        } else if (filterByWeekRBtn.isSelected()) {
            appointmentTableView.setItems(AppointmentDAO.filterAppointmentsByWeek("YW"));
        } else if (filterOffRBtn.isSelected()) {
            appointmentTableView.setItems(AppointmentDAO.getAllAppointmentsModified());
        }
    }
}
