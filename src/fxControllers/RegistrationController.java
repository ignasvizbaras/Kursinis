package fxControllers;

import hibernateControllers.UserHib;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Customer;
import model.Manager;
import model.User;
import jakarta.persistence.EntityManagerFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RegistrationController implements Initializable {

    @FXML
    public TextField loginField;
    @FXML
    public PasswordField passwordField;
    @FXML
    public PasswordField repeatPasswordField;
    @FXML
    public TextField nameField;
    @FXML
    public TextField surnameField;
    @FXML
    public RadioButton customerCheckbox;
    @FXML
    public ToggleGroup userType;
    @FXML
    public RadioButton managerCheckbox;
    @FXML
    public TextField addressField;
    @FXML
    public TextField cardNoField;
    @FXML
    public DatePicker birthDateField;
    @FXML
    public TextField employeeIdField;
    @FXML
    public TextField medCertificateField;
    @FXML
    public DatePicker employmentDateField;
    @FXML
    public CheckBox isAdminCheck;

    private EntityManagerFactory entityManagerFactory;
    private UserHib userHib;

    public void setData(EntityManagerFactory entityManagerFactory, boolean showManagerFields) {
        this.entityManagerFactory = entityManagerFactory;
        disableFields(showManagerFields);
    }

//    public void setData(EntityManagerFactory entityManagerFactory, Manager manager) {
//        this.entityManagerFactory = entityManagerFactory;
//        toggleFields(customerCheckbox.isSelected(), manager);
//    }

    private void disableFields(boolean showManagerFields) {
        if (!showManagerFields) {
            employeeIdField.setVisible(false);
            medCertificateField.setVisible(false);
            employmentDateField.setVisible(false);
            isAdminCheck.setVisible(false);
        }
    }

    private void toggleFields(boolean isManager) {
        if (isManager) {
            addressField.setVisible(false);
            cardNoField.setVisible(false);
            employeeIdField.setVisible(true);
            medCertificateField.setVisible(true);
            employmentDateField.setVisible(true);
            isAdminCheck.setVisible(true);
        } else {
            addressField.setVisible(true);
            cardNoField.setVisible(true);
            employeeIdField.setVisible(false);
            medCertificateField.setVisible(false);
            employmentDateField.setVisible(false);
            isAdminCheck.setVisible(false);
        }
    }


    public void createUser() {
        userHib = new UserHib(entityManagerFactory);
        User user = null;
        if (customerCheckbox.isSelected()) {
            user = new Customer(loginField.getText(), passwordField.getText(), birthDateField.getValue(), nameField.getText(), surnameField.getText(), addressField.getText(), cardNoField.getText());
        } else {
            user = new Manager(loginField.getText(), passwordField.getText(), birthDateField.getValue(), nameField.getText(), surnameField.getText(), employeeIdField.getText(), medCertificateField.getText(), employmentDateField.getValue(), isAdminCheck.isSelected());
        }
        try{
            userHib.createUser(user);
            JavaFxCustomUtils.generateAlert(Alert.AlertType.INFORMATION, "Registration", "Registration", "Registration completed successfully");
            returnToLogin();
        } catch (Exception e){
            JavaFxCustomUtils.generateAlert(Alert.AlertType.ERROR, "Registration", "Registration", "Registration went wrong. Try again");
        }

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Arba cia kazka su laukais darau
        userType.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                RadioButton selectedRadioButton = (RadioButton) newValue;
                if (selectedRadioButton == customerCheckbox) {
                    toggleFields(false);
                } else if (selectedRadioButton == managerCheckbox) {
                    toggleFields(true);
                }
            }
        });
    }

    public void returnToLogin() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(RegistrationController.class.getResource("../view/login.fxml"));
        Parent parent = fxmlLoader.load();
        LoginController loginController = fxmlLoader.getController();

        Scene scene = new Scene(parent);
        Stage stage = (Stage) loginField.getScene().getWindow();
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }
}
