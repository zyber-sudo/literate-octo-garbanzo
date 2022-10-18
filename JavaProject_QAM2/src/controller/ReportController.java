package controller;

import data_access.AppointmentDAO;
import data_access.ContactDAO;
import data_access.UserDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Appointment;
import model.Contact;
import model.Main;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.Month;
import java.util.ResourceBundle;

public class ReportController implements Initializable {

    ObservableList<String> reportTypes = FXCollections.observableArrayList();
    ObservableList<String> months = FXCollections.observableArrayList();
    ObservableList<String> types = FXCollections.observableArrayList();
    ObservableList<Appointment> appList = FXCollections.observableArrayList();
    Contact contact;

    @FXML
    private ComboBox<String> reportTypeCBox;
    @FXML
    private ComboBox<Contact> reportContactCBox;
    @FXML
    private Label contactLbl;
    @FXML
    private ComboBox<User> reportUserCBox;
    @FXML
    private Label userLbl;


    @FXML
    private TableView<Appointment> appointmentTableView;
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
    private Button reportGenerateBtn;
    @FXML
    private ComboBox<String> monthCBox;
    @FXML
    private ComboBox<String> typeCBox;
    @FXML
    private Label titleLbl;
    @FXML
    private Label descriptionLbl;
    @FXML
    private Label monthLbl;
    @FXML
    private Label typeLbl;
    @FXML
    private Label appCountLbl;
    @FXML
    private Label appCountNumLbl;


    /**
     * Class for controlling the report view.
     *
     * @author Zachary Zamiska
     * @version JDK 11.0
     */

    /**
     * The initialize method called when the <i>Report</i> view is loaded.
     *
     * @param url            The methods' URL to the world wide web if needed
     * @param resourceBundle The methods' resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        reportContactCBox.setVisible(false);
        contactLbl.setVisible(false);

        reportUserCBox.setVisible(false);
        userLbl.setVisible(false);

        titleLbl.setVisible(false);
        descriptionLbl.setVisible(false);
        monthLbl.setVisible(false);
        typeLbl.setVisible(false);
        appCountLbl.setVisible(false);
        appCountNumLbl.setVisible(false);
        reportGenerateBtn.setVisible(false);
        monthCBox.setVisible(false);
        typeCBox.setVisible(false);

        reportContactCBox.setItems(ContactDAO.getAllContacts());
        reportUserCBox.setItems(UserDAO.getAllUsers.getAll());

        for (int i = 1; i < 12; i++) {
            months.add(String.valueOf(Month.of(i)));
        }
        monthCBox.setItems(months);

        for (Appointment a : AppointmentDAO.getAllAppointments()) {
            if (!(types.contains(a.getAppType()))) {
                types.add(a.getAppType());
            }
        }
        typeCBox.setItems(types);

        reportTypes.add("Appointment Count by Month and Type");
        reportTypes.add("Appointments by Contact");
        reportTypes.add("Appointments by User");
        reportTypeCBox.setItems(reportTypes);
    }

    /**
     * The method for the back button in the <i>Report</i> view.
     *
     * @param event The Action Event handler for the <i>Back Button</i>
     * @throws IOException If an input or output exception occurred
     */
    @FXML
    public void onActionBackBtn(ActionEvent event) throws IOException {
        Main.SetStage("/view/MainScreen.fxml", event);
    }

    /**
     * The method for when the user changes the type or report combo box.
     */
    @FXML
    public void onActionTypeCBox() {

        if (reportTypeCBox.getSelectionModel().isSelected(0)) {


            reportContactCBox.setVisible(false);
            contactLbl.setVisible(false);

            reportUserCBox.setVisible(false);
            userLbl.setVisible(false);

            appointmentTableView.setVisible(false);

            titleLbl.setVisible(true);
            descriptionLbl.setVisible(true);
            monthLbl.setVisible(true);
            typeLbl.setVisible(true);
            appCountLbl.setVisible(true);
            appCountNumLbl.setVisible(true);
            reportGenerateBtn.setVisible(true);
            monthCBox.setVisible(true);
            typeCBox.setVisible(true);
        } else if (reportTypeCBox.getSelectionModel().isSelected(1)) {


            reportContactCBox.setVisible(true);
            contactLbl.setVisible(true);

            reportUserCBox.setVisible(false);
            userLbl.setVisible(false);

            appointmentTableView.setVisible(true);

            titleLbl.setVisible(false);
            descriptionLbl.setVisible(false);
            monthLbl.setVisible(false);
            typeLbl.setVisible(false);
            appCountLbl.setVisible(false);
            appCountNumLbl.setVisible(false);
            reportGenerateBtn.setVisible(false);
            monthCBox.setVisible(false);
            typeCBox.setVisible(false);
        } else if (reportTypeCBox.getSelectionModel().isSelected(2)) {


            reportContactCBox.setVisible(false);
            contactLbl.setVisible(false);

            reportUserCBox.setVisible(true);
            userLbl.setVisible(true);

            appointmentTableView.setVisible(true);

            titleLbl.setVisible(false);
            descriptionLbl.setVisible(false);
            monthLbl.setVisible(false);
            typeLbl.setVisible(false);
            appCountLbl.setVisible(false);
            appCountNumLbl.setVisible(false);
            reportGenerateBtn.setVisible(false);
            monthCBox.setVisible(false);
            typeCBox.setVisible(false);
        }

    }

    /**
     * The method for when the user picks a contact from the contact combo box.
     */
    @FXML
    public void onActionReportContactCBox() {
        appointmentTableView.getItems().clear();
        contact = reportContactCBox.getValue();
        for (Appointment a : AppointmentDAO.getAllAppointments()) {
            if (a.getAppContactId() == contact.getContactId()) {
                appList.add(a);
            }
        }

        appointmentTableView.setItems(appList);

        appointmentIdCol.setCellValueFactory(new PropertyValueFactory<>("appId"));
        appointmentTitleCol.setCellValueFactory(new PropertyValueFactory<>("appTitle"));
        appointmentDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("appDescription"));
        appointmentLocationCol.setCellValueFactory(new PropertyValueFactory<>("appLocation"));
        appointmentTypeCol.setCellValueFactory(new PropertyValueFactory<>("appType"));
        appointmentStartTimeCol.setCellValueFactory(new PropertyValueFactory<>("appStartDate"));
        appointmentEndTimeCol.setCellValueFactory(new PropertyValueFactory<>("appEndDate"));
        appointmentCustomerIdCol.setCellValueFactory(new PropertyValueFactory<>("appCustomerId"));
        appointmentUserIdCol.setCellValueFactory(new PropertyValueFactory<>("appUserId"));

    }

    /**
     * The method for when the user picks a user from the user combo box.
     */
    @FXML
    public void onActionReportUserCBox() {
        appointmentTableView.getItems().clear();
        User user = reportUserCBox.getValue();
        for (Appointment a : AppointmentDAO.getAllAppointments()) {
            if (a.getAppUserId() == user.getUserId()) {
                appList.add(a);
            }
        }

        appointmentTableView.setItems(appList);

        appointmentIdCol.setCellValueFactory(new PropertyValueFactory<>("appId"));
        appointmentTitleCol.setCellValueFactory(new PropertyValueFactory<>("appTitle"));
        appointmentDescriptionCol.setCellValueFactory(new PropertyValueFactory<>("appDescription"));
        appointmentLocationCol.setCellValueFactory(new PropertyValueFactory<>("appLocation"));
        appointmentTypeCol.setCellValueFactory(new PropertyValueFactory<>("appType"));
        appointmentStartTimeCol.setCellValueFactory(new PropertyValueFactory<>("appStartDate"));
        appointmentEndTimeCol.setCellValueFactory(new PropertyValueFactory<>("appEndDate"));
        appointmentCustomerIdCol.setCellValueFactory(new PropertyValueFactory<>("appCustomerId"));
        appointmentUserIdCol.setCellValueFactory(new PropertyValueFactory<>("appUserId"));

    }

    /**
     * Method for when the user presses the <i>Generate Button</i>.
     */
    @FXML
    public void onActionReportGenerateBtn() {
        int count = 0;

        try {
            String monthPick = monthCBox.getValue();
            String typePick = typeCBox.getValue();

            for (Appointment a : AppointmentDAO.getAllAppointments()) {
                if (a.getAppType().equals(typePick) && (a.getAppStartDate().toLocalDateTime().getMonth().toString().equals(monthPick.toUpperCase()))) {
                    count++;
                }
            }
            appCountNumLbl.setText(String.valueOf(count));
        } catch (Exception e) {
            System.out.println("Caught");
        }
    }

}
