package controller;

import data_access.CountryDAO;
import data_access.CustomerDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import static data_access.CountryDAO.getAllCountries;
import static data_access.CountryDAO.getAllDivisions;


/**
 * The controller for the <i>Modify Customer</i> view UI.
 * <p>
 * This class is tasked with the population of UI elements as well as their logic handling. The modify customer
 * screen also has a table view that must be populated and edited, that is done here.
 * </p>
 *
 * @author Zachary Zamiska
 * @version %I%, %G%
 */
public class ModifyCustomerController implements Initializable {

    int customerId;

    static AlertInterface alertPopup = (alert, title, header, content, wait) -> {
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        if (wait) {
            alert.showAndWait();
        }
    };

    @FXML
    private TableView<Customer> modifyCustomerTableView;
    @FXML
    private TableColumn<Customer, String> modifyCustomerAddressCol;
    @FXML
    private TableColumn<Customer, Integer> modifyCustomerDivisionCol;
    @FXML
    private TableColumn<Customer, String> modifyCustomerLocationCol;
    @FXML
    private TableColumn<Customer, String> modifyCustomerNameCol;
    @FXML
    private TableColumn<Customer, String> modifyCustomerZipCol;
    @FXML
    private TableColumn<Customer, Integer> modifyCustomerIdCol;

    @FXML
    private TextField modifyCustomerNameTxt;
    @FXML
    private TextField modifyCustomerPhoneTxt;
    @FXML
    private TextField modifyCustomerZipTxt;
    @FXML
    private TextField modifyCustomerAddressTxt;
    @FXML
    private ComboBox<Country> modifyCustomerCountryCBox;
    @FXML
    private ComboBox<FirstLevelDivision> modifyCustomerDivisionCBox;
    @FXML
    private TextField modifyCustomerIdText;


    /**
     * The <i>Modify Customer</i> controller initialize method.
     *
     * <p>
     * This method runs when the view is initialized. It sets the views for the table view and combo boxes.
     * </p>
     *
     * @param url            The methods' URL to the world wide web if needed
     * @param resourceBundle The methods' resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        modifyCustomerTableView.setItems(CustomerDAO.getAllCustomersModified());

        modifyCustomerIdCol.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        modifyCustomerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        modifyCustomerAddressCol.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        modifyCustomerZipCol.setCellValueFactory(new PropertyValueFactory<>("customerZip"));
        modifyCustomerDivisionCol.setCellValueFactory(new PropertyValueFactory<>("customerDivisionName"));
        modifyCustomerLocationCol.setCellValueFactory(new PropertyValueFactory<>("customerCountryName"));

        modifyCustomerCountryCBox.setItems(CountryDAO.getAllCountries());
        modifyCustomerDivisionCBox.setItems(CountryDAO.getAllDivisions());

    }


    /**
     * Method that this called when the user presses the <i>Cancel<i/> button on the <i>Modify Customer</i> UI.
     *
     * <p>
     * This method first confirms that the user would like to cancel the modify customer process. If so, it returns the user to the <i>Customer Data</i> view.
     * </p>
     *
     * @param event The action event handler.
     * @throws IOException If an input or output exception occurred.
     */
    @FXML
    void onActionCancelBtn(ActionEvent event) throws IOException {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alertPopup.AlertPopup(alert, "Cancel Modification?", "Are you sure you would like cancel? All unsaved data will be deleted.", "Select ok to continue.", false);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            Main.SetStage("/view/CustomerData.fxml", event);
        }
    }


    /**
     * Method that is called when the user presses the <i>Modify<i/> button on the <i>Modify Customer</i> UI.
     *
     * <p>
     *     This button serves as the "Enter" or "OK" button for the modify customer view:
     *     <ul>
     *         <li>It first checks to see if all of the fields are filled out. If not there is an alert.</li>
     *         <li>After it checks for this this method then grabs the values and sends them to the
     *         <i>AppointmentDAO</i> file to query the database.</li>
     *     </ul>
     * </p>
     */
    @FXML
    void onActionModifyBtn() {
        Exception exception = new NullPointerException();

        try {
            String name = modifyCustomerNameTxt.getText();
            if (modifyCustomerNameTxt.getText().equals("")) {
                throw exception;
            }
            String address = modifyCustomerAddressTxt.getText();
            if (modifyCustomerAddressTxt.getText().equals("")) {
                throw exception;
            }
            String zip = modifyCustomerZipTxt.getText();
            if (modifyCustomerZipTxt.getText().equals("")) {
                throw exception;
            }
            String phone = modifyCustomerPhoneTxt.getText();
            if (modifyCustomerPhoneTxt.getText().equals("")) {
                throw exception;
            }
            int divId = modifyCustomerDivisionCBox.getValue().getDivisionId();

            String currentUser = LoginScreenController.currentUser.getUserName();

            CustomerDAO.modifyCustomer(customerId, name, address, zip, phone, currentUser, divId);
            modifyCustomerTableView.setItems(CustomerDAO.getAllCustomersModified());

        } catch (Exception e) {
            alertPopup.AlertPopup(new Alert(Alert.AlertType.WARNING), "Modification Error", "Please fill in all of the required fields", "Select ok to continue.", true);
        }

    }


    /**
     * Method that is called when user selects customer in table.
     *
     * <p>
     *     This method runs when the user selects a customer in the modify customer table view. It sets the input UI elements to the selected customers data.
     * </p>
     */
    public void onMouseClickedModCustomer() {

        try {
            Customer selected = modifyCustomerTableView.getSelectionModel().getSelectedItem();
            customerId = selected.getCustomerId();

            modifyCustomerIdText.setText(String.valueOf(customerId));
            modifyCustomerNameTxt.setText(selected.getCustomerName());
            modifyCustomerPhoneTxt.setText(selected.getCustomerPhone());
            modifyCustomerZipTxt.setText(selected.getCustomerZip());
            modifyCustomerAddressTxt.setText(selected.getCustomerAddress());

            for (Country c : getAllCountries()) {
                if (c.getCountryName().equals(selected.getCustomerCountryName())) {
                    modifyCustomerCountryCBox.setValue(c);
                }
            }

            for (FirstLevelDivision fld : getAllDivisions()) {
                if (fld.getDivisionId() == selected.getCustomerDivisionId()) {
                    modifyCustomerDivisionCBox.setValue(fld);
                }
            }
        } catch (NullPointerException e) {
            System.out.println("Come on don't click on this lame whitespace...");
        }

    }


    /**
     * Method that is called when the user selects a country.
     *
     * This method is called when a user selects a county from the combo box. When a country is selected it sets the
     * divisions to the corresponding locations in the country selected.
     *
     * @throws SQLException If an error occurs when the SQL query in <i>CustomerDAO </i> code.
     */
    @FXML
    void onActionSelectCountry() throws SQLException {
        modifyCustomerDivisionCBox.setItems(CountryDAO.getCountryDivisions(modifyCustomerCountryCBox.getValue().getCountryId()));
    }


    /**
     * Method that receives the data from the <i>Customer Data</i> view.
     *
     * <p>
     *     This method takes the selected customer from the the table view in the <i>Customer Date</i> view and insets
     *     them into the input fields in the UI.
     * </p>
     *
     * @param customerData Data from the <i>Customer Data</i> view
     * @see CustomerDataController
     */
    public void sendCustomer(Customer customerData) {
        customerId = customerData.getCustomerId();

        modifyCustomerIdText.setText(String.valueOf(customerId));
        modifyCustomerNameTxt.setText(customerData.getCustomerName());
        modifyCustomerPhoneTxt.setText(customerData.getCustomerPhone());
        modifyCustomerZipTxt.setText(customerData.getCustomerZip());
        modifyCustomerAddressTxt.setText(customerData.getCustomerAddress());

        for (Country c : getAllCountries()) {
            if (c.getCountryName().equals(customerData.getCustomerCountryName())) {
                modifyCustomerCountryCBox.setValue(c);
            }
        }

        for (FirstLevelDivision fld : getAllDivisions()) {
            if (fld.getDivisionId() == customerData.getCustomerDivisionId()) {
                modifyCustomerDivisionCBox.setValue(fld);
            }
        }
    }
}
