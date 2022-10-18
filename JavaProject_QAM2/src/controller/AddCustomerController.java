package controller;

import data_access.AppointmentDAO;
import data_access.CountryDAO;
import data_access.CustomerDAO;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import model.AlertInterface;
import model.Country;
import model.FirstLevelDivision;
import model.Main;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * The controller for the Customer view UI.
 * <p>
 * This class is in charge of intake and displaying the data for the appointments in the application.
 * </p>
 * <p>
 * This class also handles the user input and the logic behind the said input.
 * </p>
 *
 * @author Zachary Zamiska
 * @version %I%, %G%
 */
public class AddCustomerController implements Initializable {

    ObservableList<Country> location = CountryDAO.getAllCountries();


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
    private TextField addCustomerAddressTxt;
    @FXML
    private ComboBox<Country> addCustomerCountryCBox;
    @FXML
    private ComboBox<FirstLevelDivision> addCustomerDivisionCBox;
    @FXML
    private TextField addCustomerNameTxt;
    @FXML
    private TextField addCustomerPhoneTxt;
    @FXML
    private TextField addCustomerZipTxt;


    /**
     * Method that this called when the user presses the <i>Add<i/> button on the <i>Add Customer</i> UI.
     *
     * <p>
     * When the user presses the add button:
     *     <ul>
     *         <li>Checks if the user has inputted data in the fields.</li>
     *         <li>If the data is correct the method takes the data and creates a new customer in the SQL database.</li>
     *     </ul>
     * </p>
     *
     * @param event The action event handler.
     */
    @FXML
    void onActionAddBtn(ActionEvent event) {

        Exception exception = new NullPointerException();

        try {
            String customerName = addCustomerNameTxt.getText();
            if (addCustomerNameTxt.getText().isEmpty()) {
                throw exception;
            }
            String customerAddress = addCustomerAddressTxt.getText();
            if (addCustomerAddressTxt.getText().isEmpty()) {
                throw exception;
            }
            String customerZip = addCustomerZipTxt.getText();
            if (addCustomerZipTxt.getText().isEmpty()) {
                throw exception;
            }
            String customerPhone = addCustomerPhoneTxt.getText();
            if (addCustomerPhoneTxt.getText().isEmpty()) {
                throw exception;
            }
            int customerDivisionId = addCustomerDivisionCBox.getValue().getDivisionId();
            String currentUser = LoginScreenController.currentUser.getUserName();


            CustomerDAO.insertCustomer(customerName, customerAddress, customerZip, customerPhone, customerDivisionId, currentUser);

            Main.SetStage("/view/CustomerData.fxml", event);
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alertPopup.AlertPopup(alert, "Empty Fields", "Please fill out all of the required fields", "Select ok to continue", true);
        }
    }


    /**
     * Method that this called when the user presses the <i>Cancel<i/> button on the <i>Add Customer</i> UI.
     *
     * @param event The action event handler.
     * @throws IOException If an input or output exception occurred.
     */
    @FXML
    void onActionCancelBtn(ActionEvent event) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alertPopup.AlertPopup(alert, "Cancel Add Customer", "Are you sure you would like to cancel adding customer? All data will be lost", "Select ok to continue", false);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Main.SetStage("/view/CustomerData.fxml", event);
        }


    }


    /**
     * Method when a choice is selected in the Country combo box.
     *
     * <p>
     * Controls the combo boxes so user can not select the division before the country is selected.
     * </p>
     *
     * @throws SQLException If an error occurs when the SQL query in <i>CountryDAO</i> code.
     * @see CountryDAO
     */
    @FXML
    void onActionSelectCountry() throws SQLException {
        addCustomerDivisionCBox.setDisable(false);
        addCustomerDivisionCBox.setItems(CountryDAO.getCountryDivisions(addCustomerCountryCBox.getValue().getCountryId()));
    }


    /**
     * The <i>Add Customer</i> initialization method for the UI.
     *
     * <p>
     * This method runs when the Add Appointment view is run. This method is tasked with setting the Country combo box with the correct items.
     * </p>
     *
     * @param url            The methods' URL to the world wide web.
     * @param resourceBundle The methods' resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        addCustomerDivisionCBox.disableProperty();
        addCustomerCountryCBox.setItems(location);
    }
}
