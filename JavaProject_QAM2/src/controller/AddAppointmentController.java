package controller;

import data_access.AppointmentDAO;
import data_access.ContactDAO;
import data_access.CountryDAO;
import data_access.CustomerDAO;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;
import java.util.ResourceBundle;


/**
 * The controller for the Add Appointment view UI.
 * <p>
 * This class is in charge of intake and displaying the data for the appointments in the application.
 * </p>
 * <p>
 * This class also handles the user input and the logic behind the said input.
 * </p>
 *
 * @author Zachary Zamiska
 * @version JDK 11.0
 */
public class AddAppointmentController implements Initializable {


    ObservableList<Contact> contacts = ContactDAO.getAllContacts();
    ObservableList<Customer> customer = CustomerDAO.getAllCustomers();
    ObservableList<Country> location = CountryDAO.getAllCountries();

    /**
     * <h3>Lambda Use #2:</h3>
     * <p>
     * This is the definition of the alert lambda.
     * </p>
     */
    static AlertInterface alertPopup = (alert, title, header, content, wait) ->
    {
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        if (wait) {
            alert.showAndWait();
        }
    };

    @FXML
    private ComboBox<LocalTime> addAppointmentStartTimeCBox;
    @FXML
    private ComboBox<LocalTime> addAppointmentEndTimeCBox;
    @FXML
    private ComboBox<Contact> addAppointmentContactCBox;
    @FXML
    private ComboBox<Customer> addAppointmentCustomerCBox;
    @FXML
    private TextArea addAppointmentDescriptionTxt;
    @FXML
    private DatePicker addAppointmentEndDateDP;
    @FXML
    private ComboBox<Country> addAppointmentLocationCBox;
    @FXML
    private TextField addAppointmentNameTxt;
    @FXML
    private DatePicker addAppointmentStartDateDP;
    @FXML
    private TextField addAppointmentTypeTxt;
    @FXML
    private Label addAppointmentUserLbl;


    /**
     * Method that this called when the user presses the <i>Add<i/> button on the <i>Add Appointment</i> UI.
     * <p>
     * When a user presses the add button:
     * <ul>
     *     <li>The method first checks that all the required input fields have a value inputted.</li>
     *     <li>Due to the nature of the input areas MOST of the value types need not be checked.</li>
     *     <li>If all values are correct the method add the values to instantiate a new Appointment object in database.</li>
     *     <li>Time logic and alerts are also handled with this method.</li>
     * </ul>
     *
     *
     * @param event The action event handler.
     */
    @FXML
    public void onActionAddBtn(ActionEvent event) {

        Exception exception = new NullPointerException();
        boolean run = false;

        try {

            String appointmentName = addAppointmentNameTxt.getText();
            if (addAppointmentNameTxt.getText().isEmpty()) {
                throw exception;
            }
            String appointmentDescription = addAppointmentDescriptionTxt.getText();
            if (addAppointmentDescriptionTxt.getText().isEmpty()) {
                throw exception;
            }
            String appointmentLocation = addAppointmentLocationCBox.getValue().getCountryName();
            if (addAppointmentLocationCBox.getSelectionModel().isEmpty()) {
                throw exception;
            }
            String appointmentType = addAppointmentTypeTxt.getText();
            if (addAppointmentTypeTxt.getText().isEmpty()) {
                throw exception;
            }
            LocalDateTime startTime = LocalDateTime.of(addAppointmentStartDateDP.getValue(), addAppointmentStartTimeCBox.getValue());
            LocalDateTime endTime = LocalDateTime.of(addAppointmentEndDateDP.getValue(), addAppointmentEndTimeCBox.getValue());
            String appointmentUser = LoginScreenController.currentUser.getUserName();
            int appointmentContactId = addAppointmentContactCBox.getValue().getContactId();
            if (addAppointmentContactCBox.getSelectionModel().isEmpty()) {
                throw exception;
            }
            int appointmentCustomerId = addAppointmentCustomerCBox.getValue().getCustomerId();
            if (addAppointmentCustomerCBox.getSelectionModel().isEmpty()) {
                throw exception;
            }
            int userId = LoginScreenController.currentUser.getUserId();

            if (startTime.isAfter(endTime)) {
                alertPopup.AlertPopup(new Alert(Alert.AlertType.ERROR), "Invalid Dates", "The appointment end date/time is before the start time.", "Select ok to continue.", true);
            } else if (startTime.isBefore(Main.time)) {
                alertPopup.AlertPopup(new Alert(Alert.AlertType.ERROR), "Invalid Dates", "The appointment start date/time has passed already", "Select ok to continue.", true);
            } else if ((startTime.toLocalTime().isBefore(LocalTime.of(8, 00)))
                    || (startTime.toLocalTime().isAfter(LocalTime.of(22, 00)))
                    || (startTime.toLocalDate().getDayOfWeek().equals(DayOfWeek.SATURDAY))
                    || (startTime.toLocalDate().getDayOfWeek().equals(DayOfWeek.SUNDAY))) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alertPopup.AlertPopup(alert, "Outside Work Hours", "The appointment start date/time is outside work hours", "Are you sure you would like to schedule?", false);
                Optional<ButtonType> result = alert.showAndWait();

                if (result.isPresent() && result.get() == ButtonType.OK) {
                    run = true;
                }
            } else {
                run = true;
            }




            for (Appointment a: AppointmentDAO.getAllAppointments())
            {
                if(addAppointmentCustomerCBox.getValue().getCustomerId() == a.getAppCustomerId())
                {
                    if (a.getAppStartDate().toLocalDateTime().isBefore(startTime) && a.getAppEndDate().toLocalDateTime().isAfter(startTime))
                    {
                        alertPopup.AlertPopup(new Alert(Alert.AlertType.ERROR), "Overlap Error", "This customer has already started an appointment at this time.", "Select ok to continue.", true);

                        run = false;
                    }

                    if (a.getAppEndDate().toLocalDateTime().isAfter(endTime) && a.getAppStartDate().toLocalDateTime().isBefore(endTime))
                    {
                        alertPopup.AlertPopup(new Alert(Alert.AlertType.ERROR), "Overlap Error", "This customer would still be in a meeting at this time.", "Select ok to continue.", true);

                        run = false;
                    }

                    if (a.getAppStartDate().toLocalDateTime().isAfter(startTime.minusSeconds(1)) && a.getAppEndDate().toLocalDateTime().isBefore(endTime.plusSeconds(1)))
                    {
                        alertPopup.AlertPopup(new Alert(Alert.AlertType.ERROR), "Overlap Error", "This customer already has an appointment at this time.", "Select ok to continue.", true);

                        run = false;
                    }
                }
            }

            if (run)
            {
                AppointmentDAO.insertAppointment(appointmentName, appointmentDescription, appointmentLocation, appointmentType, startTime, endTime, appointmentUser, appointmentCustomerId, userId, appointmentContactId);
                Main.SetStage("/view/MainScreen.fxml", event);
            }

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alertPopup.AlertPopup(alert, "Error", "Please fill in all of the required fields", "Select ok to continue.", true);
        }

    }


    /**
     * Method that this called when the user presses the <i>Cancel<i/> button on the <i>Add Appointment</i> UI.
     *
     * <p>
     * When the user presses the cancel button this method does the following:
     *     <ul>
     *         <li>Alerts the user that they will lose any unsaved data if they proceed.</li>
     *         <li>If the user continues, it navigates them back to the Main Screen UI.</li>
     *     </ul>
     * </p>
     *
     * @param event The action event handler.
     * @throws IOException An input/output exception if FXML view file does not exist.
     * @see MainScreenController
     */
    @FXML
    public void onActionCancelBtn(ActionEvent event) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alertPopup.AlertPopup(alert, "Confirm Cancel?", "Are you sure you would like to cancel? All unsaved progress will be lost", "Select ok to continue.", false);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && (result.get() == ButtonType.OK)) {
            Main.SetStage("/view/MainScreen.fxml", event);
        }

    }


    /**
     * The initialize method for the UI view <i><b>Add Appointment</b></i>.
     *
     * <p>
     * This method runs when the Add Appointment view is run.
     * This method is tasked with setting the data type and the UI elements with the correct types.
     * As well as handling the time UI elements.
     * </p>
     *
     * @param url            Uniform Resource Locator, a pointer to a "resource" on the World Wide Web.
     * @param resourceBundle Resource bundle for the method.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        addAppointmentUserLbl.setText(LoginScreenController.currentUser.getUserName());

        addAppointmentContactCBox.setItems(contacts);
        addAppointmentCustomerCBox.setItems(customer);
        addAppointmentLocationCBox.setItems(location);

        LocalTime start = LocalTime.of(4, 0);
        LocalTime end = LocalTime.of(23, 0);
        addAppointmentStartTimeCBox.setVisibleRowCount(5);
        addAppointmentEndTimeCBox.setVisibleRowCount(5);

        while (start.isBefore(end.plusSeconds(1))) {
            addAppointmentStartTimeCBox.getItems().add(start);
            addAppointmentEndTimeCBox.getItems().add(start);
            start = start.plusMinutes(5);
        }

    }

}
