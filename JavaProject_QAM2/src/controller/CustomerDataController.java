package controller;

import data_access.AppointmentDAO;
import data_access.CustomerDAO;
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
import model.Customer;
import model.Main;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * The controller for the <i>Customer Data Controller</i> view UI.
 * <p>
 * This class is in charge of intake and displaying the data for the appointments in the application
 * for the CustomerData.fxml file.
 * </p>
 * <p>
 * This class also handles the user input and the logic behind the said input. As well
 * as the alert handling.
 * </p>
 *
 * @author Zachary Zamiska
 * @version %I%, %G%
 */
public class CustomerDataController implements Initializable {

    private Customer customerData;

    static AlertInterface alertPopup = (alert, title, header, content, wait) ->
    {

        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

    };

    @FXML
    private TableView<Customer> customerTableView;
    @FXML
    private TableColumn<Customer, String> customerAddressCol;
    @FXML
    private TableColumn<Customer, String> customerDivisionCol;
    @FXML
    private TableColumn<Customer, String> customerLocationCol;
    @FXML
    private TableColumn<Customer, String> customerNameCol;
    @FXML
    private TableColumn<Customer, String> customerZipCol;


    /**
     * Method that this called when the user presses the <i>Add<i/> button on the <i>Customer Data</i> UI.
     *
     * <p>
     *     When the user presses the add button the view is changed to the AddCustomer view through
     *     the SetStage method in main.
     * </p>
     *
     * @param event The action event handler.
     * @throws IOException If an input or output exception occurred.
     * @see Main
     */
    @FXML
    void onActionAddBtn(ActionEvent event) throws IOException {

        Main.SetStage("/view/AddCustomer.fxml", event);
    }


    /**
     * Method that this called when the user presses the <i>Back<i/> button on the <i>Customer Data</i> UI.
     *
     * <p>
     *     When the user presses the back button the view is changed to the MainScreen view through
     *     the SetStage method in main.
     * </p>
     *
     * @param event The action event handler.
     * @throws IOException If an input or output exception occurred.
     * @see Main
     */
    @FXML
    void onActionBackBtn(ActionEvent event) throws IOException {

        Main.SetStage("/view/MainScreen.fxml", event);

    }


    /**
     * Method that this called when the user presses the <i>Delete<i/> button on the <i>Customer Data</i> UI.
     *
     * <p>
     *     This method does the following:
     *     <ul>
     *         <li>Checks if the user has selected an item from the customer data table view.</li>
     *         <li>If a user is selected, it checks if the user has any outstanding appointments.</li>
     *         <li>If the user does not have any appointments, the method confirms the delete action with the user.</li>
     *         <li>After confirmation the method rust the delete query in the database through the CustomerDAO class.</li>
     *     </ul>
     * </p>
     */
    @FXML
    void onActionDeleteBtn() {
        customerData = customerTableView.getSelectionModel().getSelectedItem();
        boolean matched = false;


        if (customerData == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alertPopup.AlertPopup(alert, "Error", "No customer is highlighted.", "Please select an existing customer to delete.", true);
        } else {
            for (Appointment a : AppointmentDAO.getAllAppointments()) {
                if (a.getAppCustomerId() == customerData.getCustomerId()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("You can not delete a customer with existing appointments.");
                    alert.setContentText("Please cancel or edit all appointments with customer.");

                    alert.showAndWait();
                    matched = true;
                    break;
                }
            }

            if (!matched) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alertPopup.AlertPopup(alert, "Delete Customer", "Are you sure you would like to delete the following customer: \n \n" + customerData.getCustomerName() + "\n", "Select ok to continue.", false);
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    CustomerDAO.deleteCustomer(customerData.getCustomerId());
                    customerTableView.setItems(CustomerDAO.getAllCustomersModified());
                }
            }
        }
    }


    /**
     * Method that this called when the user presses the <i>Modify<i/> button on the <i>Customer Data</i> UI.
     *
     * <p>
     *     This method does the following:
     *     <ul>
     *         <li>Checks if the user has selected an item from the customer data table view.</li>
     *         <li>If user is selected, the customers data is sent to the ModifyCustomer view.</li>
     *         <li>After data is sent the ModifyCustomer view is loaded.</li>
     *     </ul>
     * </p>
     *
     * @param event The action event handler.
     * @throws IOException If an input or output exception occurred.
     * @see Main
     */
    @FXML
    void onActionModifyBtn(ActionEvent event) throws IOException {
        customerData = customerTableView.getSelectionModel().getSelectedItem();

        if (customerData == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No customer is highlighted.");
            alert.setContentText("Please select an existing customer to modify.");

            alert.showAndWait();
        } else {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ModifyCustomer.fxml"));
            loader.load();
            ModifyCustomerController MCController = loader.getController();
            MCController.sendCustomer(customerData);

            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent sceneLoad = loader.getRoot();
            stage.setScene(new Scene(sceneLoad));
            stage.show();
        }

    }


    /**
     * The <i>Customer Data</i> initialization method for the UI.
     *
     * <p>
     * This method runs when the Customer Data view is run. This method is tasked with setting and populating the customer view table.
     * </p>
     *
     * @param url            The methods' URL to the world wide web if needed
     * @param resourceBundle The methods' resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerTableView.setItems(CustomerDAO.getAllCustomersModified());

        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customerAddressCol.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
        customerZipCol.setCellValueFactory(new PropertyValueFactory<>("customerZip"));
        customerDivisionCol.setCellValueFactory(new PropertyValueFactory<>("customerDivisionName"));
        customerLocationCol.setCellValueFactory(new PropertyValueFactory<>("customerCountryName"));
    }
}
