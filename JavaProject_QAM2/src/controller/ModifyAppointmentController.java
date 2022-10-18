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

import static data_access.CountryDAO.getAllCountries;


/**
 * The controller for the <i>Modify Appointment</i> view UI.
 * <p>
 * This class is tasked with the population of UI elements as well as their logic handling.
 * This also receives <i>Main Screen</i> as well as sends it to the database from the <i>AppointmentDAO</i>.
 * </p>
 *
 * @author Zachary Zamiska
 * @version %I%, %G%
 * @see AppointmentDAO
 * @see MainScreenController
 */
public class ModifyAppointmentController implements Initializable {

    static AlertInterface alertPopup = (alert, title, header, content, wait) -> {
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        if (wait) {
            alert.showAndWait();
        }
    };

    private final ObservableList<Contact> contacts = ContactDAO.getAllContacts();
    private final ObservableList<Customer> customer = CustomerDAO.getAllCustomers();
    private int appointmentId;
    private Appointment modifAppt = null;
    private final ObservableList<Country> location = CountryDAO.getAllCountries();

    @FXML
    private ComboBox<LocalTime> modifyAppointmentStartTimeCBox;
    @FXML
    private ComboBox<LocalTime> modifyAppointmentEndTimeCBox;
    @FXML
    private ComboBox<Contact> modifyAppointmentContactCBox;
    @FXML
    private ComboBox<Customer> modifyAppointmentCustomerCBox;
    @FXML
    private TextArea modifyAppointmentDescriptionTxt;
    @FXML
    private DatePicker modifyAppointmentEndDateDP;
    @FXML
    private ComboBox<Country> modifyAppointmentLocationCBox;
    @FXML
    private TextField modifyAppointmentNameTxt;
    @FXML
    private DatePicker modifyAppointmentStartDateDP;
    @FXML
    private TextField modifyAppointmentTypeTxt;
    @FXML
    private Label modifyAppointmentUserLbl;


    /**
     * The <i>Modify Appointment</i> initialization method for the UI.
     *
     * <p>
     * This method runs when the Modify Appointment view is run. This method is tasked with setting and populating
     * the UI elements such as the text boxes and combo boxes. It also sets the times inside of the combo boxes.
     * </p>
     *
     * @param url            The methods' URL to the world wide web if needed
     * @param resourceBundle The methods' resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        modifyAppointmentUserLbl.setText(LoginScreenController.currentUser.getUserName());

        modifyAppointmentContactCBox.setItems(contacts);
        modifyAppointmentCustomerCBox.setItems(customer);
        modifyAppointmentLocationCBox.setItems(location);

        LocalTime start = LocalTime.of(6, 0);
        LocalTime end = LocalTime.of(23, 0);

        modifyAppointmentStartTimeCBox.setVisibleRowCount(5);
        modifyAppointmentEndTimeCBox.setVisibleRowCount(5);

        while (start.isBefore(end.plusSeconds(1))) {
            modifyAppointmentStartTimeCBox.getItems().add(start);
            modifyAppointmentEndTimeCBox.getItems().add(start);
            start = start.plusMinutes(5);
        }

    }


    /**
     * Method that this called when the user presses the <i>Modify<i/> button on the <i>Modify Appointment</i> UI.
     *
     * <p>
     * This button serves as the "Enter" or "OK" button for the modify appointment view:
     *     <ul>
     *         <li>It first checks to see if all of the fields are filled out. If not there is an alert.</li>
     *         <li>After it checks for this this method then grabs the values and sends them to the
     *         <i>AppointmentDAO</i> file to query the database.</li>
     *         <li>After appointment is modified, the user is sent back to the <i>Main Screen</i></li>
     *     </ul>
     * </p>
     *
     * @param event The action event handler.
     */
    @FXML
    public void onActionModifyBtn(ActionEvent event) {
        Exception exception = new NullPointerException();
        boolean run = false;

        try {
            String appointmentName = modifyAppointmentNameTxt.getText();
            if (modifyAppointmentNameTxt.getText().equals("")) {
                throw exception;
            }
            String appointmentDescription = modifyAppointmentDescriptionTxt.getText();
            if (modifyAppointmentDescriptionTxt.getText().equals("")) {
                throw exception;
            }
            String appointmentLocation = modifyAppointmentLocationCBox.getValue().getCountryName();
            String appointmentType = modifyAppointmentTypeTxt.getText();
            if (modifyAppointmentTypeTxt.getText().equals("")) {
                throw exception;
            }
            LocalDateTime startTime = LocalDateTime.of(modifyAppointmentStartDateDP.getValue(), modifyAppointmentStartTimeCBox.getValue());
            LocalDateTime endTime = LocalDateTime.of(modifyAppointmentEndDateDP.getValue(), modifyAppointmentEndTimeCBox.getValue());
            String appointmentUser = LoginScreenController.currentUser.getUserName();
            int appointmentContactId = modifyAppointmentContactCBox.getValue().getContactId();
            int appointmentCustomerId = modifyAppointmentCustomerCBox.getValue().getCustomerId();
            int appointmentUserId = LoginScreenController.currentUser.getUserId();

            if (startTime.isAfter(endTime)) {
                alertPopup.AlertPopup(new Alert(Alert.AlertType.ERROR), "Invalid Dates", "The appointment end date/time is before the start time.", "Select ok to continue.", true);
            }
            if (startTime.isBefore(Main.time)) {
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
                if (!(appointmentName.equals(a.getAppTitle())) && modifyAppointmentCustomerCBox.getValue().getCustomerId() == a.getAppCustomerId())
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
                AppointmentDAO.modifyAppointment(appointmentId, appointmentName, appointmentDescription, appointmentLocation, appointmentType, startTime, endTime, appointmentUser, appointmentCustomerId, appointmentUserId, appointmentContactId);
                Main.SetStage("/view/MainScreen.fxml", event);
            }


        } catch (Exception e) {
            System.out.println(e.getCause());
            alertPopup.AlertPopup(new Alert(Alert.AlertType.WARNING), "Modification Error", "Please fill in all of the required fields", "Select ok to continue.", true);

        }
    }


    /**
     * Method that this called when the user presses the <i>Cancel<i/> button on the <i>Modify Appointment</i> UI.
     *
     * <p>
     * After warning the user that all data will be lost and confirming the choice with said user, they are sent to
     * the <i>Main Screen</i> view again.
     * </p>
     *
     * @param event The action event handler.
     * @throws IOException If an input or output exception occurred.
     */
    @FXML
    public void onActionCancelBtn(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alertPopup.AlertPopup(alert, "Cancel Modification?", "All unsaved data will be lost", "Select ok to continue.", false);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Main.SetStage("/view/MainScreen.fxml", event);
        }
    }


    /**
     * Method that receives the appointment data from the main screen and assigns the UI elements to match said data.
     *
     * @param appointment The appointment that is being sent over from the <i>Main Screen</i>
     * @see MainScreenController
     */
    @FXML
    public void sendAppointment(Appointment appointment) {

        modifAppt = appointment;
        LocalDateTime startTimeDate = appointment.getAppStartDate().toLocalDateTime();
        LocalDateTime endDateTime = appointment.getAppEndDate().toLocalDateTime();

        /*This section guarantees no matter how many customers there are and how many get deleted the combo box will list the correct one.*/
        /*START*/
        int count = 0;

        for (Customer c : customer) {
            count++;
            if (c.getCustomerId() == appointment.getAppCustomerId()) {
                modifyAppointmentCustomerCBox.getSelectionModel().select(count - 1);
            }
        }
        /*END*/


        appointmentId = appointment.getAppId();
        modifyAppointmentNameTxt.setText(appointment.getAppTitle());
        modifyAppointmentDescriptionTxt.setText(appointment.getAppDescription());
        modifyAppointmentTypeTxt.setText(appointment.getAppType());


        for (Country c : getAllCountries()) {
            if (c.getCountryName().equals(appointment.getAppLocation())) {
                modifyAppointmentLocationCBox.setValue(c);
            }
        }

        modifyAppointmentContactCBox.getSelectionModel().select(appointment.getAppContactId() - 1);
        modifyAppointmentStartDateDP.setValue(startTimeDate.toLocalDate());
        modifyAppointmentStartTimeCBox.getSelectionModel().select(startTimeDate.toLocalTime());
        modifyAppointmentEndDateDP.setValue(endDateTime.toLocalDate());
        modifyAppointmentEndTimeCBox.getSelectionModel().select(endDateTime.toLocalTime());
    }



}
