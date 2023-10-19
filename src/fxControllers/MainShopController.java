package fxControllers;

import hibernateControllers.CustomHib;
import hibernateControllers.GenericHib;
import hibernateControllers.UserHib;
import hibernateControllers.WarehouseHib;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.util.converter.LocalDateStringConverter;
import model.*;
import jakarta.persistence.EntityManagerFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainShopController implements Initializable {

    @FXML
    public ListView<Product> productList;
    @FXML
    public ListView<Product> currentOrder;
    @FXML
    public Tab usersTab;
    @FXML
    public Tab warehouseTab;
    @FXML
    public ListView<Warehouse> warehouseList;
    @FXML
    public TextField addressWarehouseField;
    @FXML
    public TextField titleWarehouseField;
    @FXML
    public Tab ordersTab;
    @FXML
    public Tab productsTab;
    @FXML
    public TableView<Customer> customerTable;
    @FXML
    public TableView<Manager> managerTable;
    @FXML
    public TabPane tabPane;
    @FXML
    public Tab primaryTab;
    @FXML
    public ListView<Product> productListManager;
    @FXML
    public TextField productTitleField;
    @FXML
    public TextArea productDescriptionField;
    @FXML
    public ComboBox<ProductType> productType;
    @FXML
    public ComboBox<Warehouse> warehouseComboBox;
    @FXML
    public DatePicker plantDateField;
    @FXML
    public TextField weightField;
    @FXML
    public TextArea chemicalDescriptionField;
    @FXML
    public TextField productManufacturerField;
    @FXML
    public TableColumn<Customer, Integer> idColumn;
    @FXML
    public TableColumn<Customer, String> nameColumn;
    @FXML
    public TableColumn<Customer, String> surnameColumn;
    @FXML
    public TableColumn<Customer, String> addressColumn;
    @FXML
    public TableColumn<Customer, LocalDate> birthdayColumn;
    public TableColumn<Customer, String> cardNumber;
    @FXML
    public Button updateCustomer;
    @FXML
    public Button deleteCustomer;
    @FXML
    public TableColumn<Manager, Integer> managerIdColumn;
    @FXML
    public TableColumn<Manager, String> managerNameColumn;
    @FXML
    public TableColumn<Manager, String> managerSurnameColumn;
    @FXML
    public TableColumn<Manager, LocalDate> managerBirthdayColumn;
    @FXML
    public TableColumn<Manager, String> managerEmployeeId;
    @FXML
    public TableColumn<Manager, LocalDate> managerEmploymentDate;
    @FXML
    public TableColumn<Manager, String> managerMedCertificate;
    @FXML
    public Button deleteManager;
    @FXML
    public Button updateManager;
    @FXML
    public TextField productDimensions;
    @FXML
    public TextField productCategory;
    @FXML
    public TextField productTypes;
    @FXML
    public TextField productCollection;
    @FXML
    public TextField productQuantity;
    @FXML
    public TextField productMaterial;
    @FXML
    public TextField productWeight;
    @FXML
    public TextField productRarity;
    @FXML
    public TextArea commentBody;
    @FXML
    public TextField commentTitle;


    private EntityManagerFactory entityManagerFactory;
    private User currentUser;
    private GenericHib genericHib;

    private CustomHib productHib;

    private WarehouseHib warehouseHib;

    private UserHib customerHib;
    private UserHib managerHib;
    private GenericHib cartHib;
    private GenericHib commentHib;

    public void setData(EntityManagerFactory entityManagerFactory, User currentUser) {
        this.entityManagerFactory = entityManagerFactory;
        this.currentUser = currentUser;
        limitAccess();
        loadData();
    }

    private void loadData() {
        genericHib = new GenericHib(entityManagerFactory);
        productHib = new CustomHib(entityManagerFactory);
        cartHib = new GenericHib(entityManagerFactory);
        commentHib = new GenericHib(entityManagerFactory);
        productList.getItems().clear();
        productList.getItems().addAll(genericHib.getAllRecords(Product.class));
        customerHib = new UserHib(entityManagerFactory);
        customerTable.getItems().clear();
        customerTable.getItems().addAll(customerHib.getAllCustomers());
        managerHib = new UserHib(entityManagerFactory);
        managerTable.getItems().clear();
        managerTable.getItems().addAll(managerHib.getAllManagers());
        setUsersTableValues();
    }

    private void limitAccess() {
        if (currentUser.getClass() == Manager.class) {
            Manager manager = (Manager) currentUser;
            if (!manager.isAdmin()) {
                managerTable.setDisable(true);
            }
        } else if (currentUser.getClass() == Customer.class) {
            Customer customer = (Customer) currentUser;
            //tabPane.getTabs().remove(usersTab);
            //tabPane.getTabs().remove(warehouseTab);
        } else {
            JavaFxCustomUtils.generateAlert(Alert.AlertType.WARNING, "WTF", "WTF", "WTF");
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        productType.getItems().addAll(ProductType.values());
    }

    public void leaveComment() {
    }

    public void addToCart() {
    }

    public void loadTabValues() {
        if (productsTab.isSelected()) {
            loadProductListManager();
            //List<Warehouse> record = genericHib.getAllRecords(Warehouse.class);
            warehouseComboBox.getItems().addAll(genericHib.getAllRecords(Warehouse.class));
        } else if (warehouseTab.isSelected()) {
            loadWarehouseList();
        }
    }

    public void enableProductFields() {
        if (productType.getSelectionModel().getSelectedItem() == ProductType.ACTIONFIGURE) {
            productDimensions.setVisible(true);
            productCategory.setVisible(true);
            productTypes.setVisible(false);
            productCollection.setVisible(false);
            productQuantity.setVisible(false);
            productMaterial.setVisible(false);
            productWeight.setVisible(false);
            productRarity.setVisible(false);

        }
        else if (productType.getSelectionModel().getSelectedItem() == ProductType.TRADINGCARDS) {
            productDimensions.setVisible(false);
            productCategory.setVisible(false);
            productTypes.setVisible(true);
            productCollection.setVisible(true);
            productQuantity.setVisible(true);
            productMaterial.setVisible(false);
            productWeight.setVisible(false);
            productRarity.setVisible(false);
        } else if (productType.getSelectionModel().getSelectedItem() == ProductType.KEYCHAIN){
            productDimensions.setVisible(false);
            productCategory.setVisible(false);
            productTypes.setVisible(false);
            productCollection.setVisible(false);
            productQuantity.setVisible(false);
            productMaterial.setVisible(true);
            productWeight.setVisible(false);
            productRarity.setVisible(false);
        }
        else{
            productDimensions.setVisible(false);
            productCategory.setVisible(false);
            productTypes.setVisible(false);
            productCollection.setVisible(false);
            productQuantity.setVisible(false);
            productMaterial.setVisible(false);
            productWeight.setVisible(true);
            productRarity.setVisible(true);
        }
    }

    //----------------------Product functionality-------------------------------//

    private void loadProductListManager() {
        productListManager.getItems().clear();
        productListManager.getItems().addAll(genericHib.getAllRecords(Product.class));
    }

    public void loadProductListData() {
        Product selectedProduct = productListManager.getSelectionModel().getSelectedItem();
        productTitleField.setText(selectedProduct.getTitle());
        productDescriptionField.setText(selectedProduct.getDescription());
        productManufacturerField.setText(selectedProduct.getManufacturer());
        warehouseComboBox.setValue(selectedProduct.getWarehouse());
        if (selectedProduct.getProductType() == ProductType.ACTIONFIGURE){
            productType.setValue(ProductType.ACTIONFIGURE);
            productDimensions.setText(((ActionFigure) selectedProduct).getDimensions());
            productCategory.setText(((ActionFigure) selectedProduct).getCategory());
        }
        else if (selectedProduct.getProductType() == ProductType.TRADINGCARDS){
            productType.setValue(ProductType.TRADINGCARDS);
            productTypes.setText(((TradingCards) selectedProduct).getType());
            productCollection.setText(((TradingCards) selectedProduct).getCollection());
            productQuantity.setText(String.valueOf(((TradingCards) selectedProduct).getQuantityInPack()));
        }
        else if (selectedProduct.getProductType() == ProductType.KEYCHAIN){
            productType.setValue(ProductType.KEYCHAIN);
            productMaterial.setText(((KeyChain) selectedProduct).getMaterial());
        }
        else if (selectedProduct.getProductType() == ProductType.OTHER){
            productType.setValue(ProductType.OTHER);
            productWeight.setText(String.valueOf(((Other) selectedProduct).getWeight()));
            productRarity.setText(((Other) selectedProduct).getRarity());
        }
    }

    public void addNewProduct() {
        Warehouse selectedWarehouse = warehouseComboBox.getSelectionModel().getSelectedItem();
        Warehouse warehouse = genericHib.getEntityById(Warehouse.class, selectedWarehouse.getId());
        if (productType.getSelectionModel().getSelectedItem() == ProductType.ACTIONFIGURE) {
            genericHib.create(new ActionFigure(productTitleField.getText(), productDescriptionField.getText(), productManufacturerField.getText(), warehouse, productDimensions.getText(), productCategory.getText()));
        }
        else if (productType.getSelectionModel().getSelectedItem() == ProductType.TRADINGCARDS){
            try {
                String productQuantityText = productQuantity.getText();
                int productQuantityInt = Integer.parseInt(productQuantityText);
                genericHib.create(new TradingCards(productTitleField.getText(), productDescriptionField.getText(), productManufacturerField.getText(), warehouse, productTypes.getText(), productCollection.getText(), productQuantityInt));
            }
            catch (NumberFormatException e){
                e.printStackTrace();
            }
        }
        else if (productType.getSelectionModel().getSelectedItem() == ProductType.KEYCHAIN){
            genericHib.create(new KeyChain(productTitleField.getText(), productDescriptionField.getText(), productManufacturerField.getText(), warehouse, productMaterial.getText()));
        }
        else{
            try {
                String productWeightText = productWeight.getText();
                Double productWeightDouble = Double.parseDouble(productWeightText);
                genericHib.create(new Other(productTitleField.getText(), productDescriptionField.getText(), productManufacturerField.getText(), warehouse, productWeightDouble, productRarity.getText()));
            }
            catch (NumberFormatException e){
                e.printStackTrace();
            }
        }
        loadProductListManager();
    }

    public void updateProduct() {
        Product selectedProduct = productListManager.getSelectionModel().getSelectedItem();
        selectedProduct.setTitle(productTitleField.getText());
        selectedProduct.setDescription(productDescriptionField.getText());
        selectedProduct.setManufacturer(productManufacturerField.getText());
        selectedProduct.setWarehouse(warehouseComboBox.getValue());
        if (selectedProduct.getProductType() == ProductType.ACTIONFIGURE){
            ((ActionFigure) selectedProduct).setDimensions(productDimensions.getText());
            ((ActionFigure) selectedProduct).setCategory(productCategory.getText());
        }
        else if (selectedProduct.getProductType() == ProductType.TRADINGCARDS){
            String productQuantityText = productQuantity.getText();
            int productQuantityInt = Integer.parseInt(productQuantityText);
            ((TradingCards) selectedProduct).setType(productTypes.getText());
            ((TradingCards) selectedProduct).setCollection(productCollection.getText());
            ((TradingCards) selectedProduct).setQuantityInPack(productQuantityInt);
        }
        else if (selectedProduct.getProductType() == ProductType.KEYCHAIN){
            ((KeyChain) selectedProduct).setMaterial(productMaterial.getText());
        }
        else if (selectedProduct.getProductType() == ProductType.OTHER){
            String productWeightText = productWeight.getText();
            Double productWeightDouble = Double.parseDouble(productWeightText);
            ((Other) selectedProduct).setWeight(productWeightDouble);
            ((Other) selectedProduct).setRarity(productRarity.getText());
        }
        if ((selectedProduct.getProductType() == ProductType.ACTIONFIGURE && productType.getSelectionModel().getSelectedItem() != ProductType.ACTIONFIGURE)
                || (selectedProduct.getProductType() == ProductType.TRADINGCARDS && productType.getSelectionModel().getSelectedItem() != ProductType.TRADINGCARDS)
                || (selectedProduct.getProductType() == ProductType.KEYCHAIN && productType.getSelectionModel().getSelectedItem() != ProductType.KEYCHAIN)
                || (selectedProduct.getProductType() == ProductType.OTHER && productType.getSelectionModel().getSelectedItem() != ProductType.OTHER)){
            JavaFxCustomUtils.generateAlert(Alert.AlertType.ERROR, "Product update", "Wrong product type", "You can't change product type");
        }
        else{
            genericHib.update(selectedProduct);
            loadProductListManager();
        }
    }

    public void deleteProduct() {
        Product selectedProduct = productListManager.getSelectionModel().getSelectedItem();
        //genericHib.delete(selectedProduct);
        productHib.deleteProduct(selectedProduct.getId());
        loadProductListManager();
    }

    //----------------------Warehouse functionality-----------------------------//

    private void loadWarehouseList() {
        warehouseHib = new WarehouseHib(entityManagerFactory);
        warehouseList.getItems().clear();
        warehouseList.getItems().addAll(warehouseHib.getAllWarehouse());
    }

    public void addNewWarehouse() {
        warehouseHib.createWarehouse(new Warehouse(titleWarehouseField.getText(), addressWarehouseField.getText()));
        loadWarehouseList();
    }

    public void updateWarehouse() {
        Warehouse selectedWarehouse = warehouseList.getSelectionModel().getSelectedItem();
        selectedWarehouse.setTitle(titleWarehouseField.getText());
        selectedWarehouse.setAddress(addressWarehouseField.getText());
        warehouseHib.updateWarehouse(selectedWarehouse);
        loadWarehouseList();
    }

    public void removeWarehouse() {

        Warehouse selectedWarehouse = warehouseList.getSelectionModel().getSelectedItem();
        warehouseHib.deleteWarehouse(selectedWarehouse.getId());
        loadWarehouseList();
    }

    public void loadWarehouseData() {
        Warehouse selectedWarehouse = warehouseList.getSelectionModel().getSelectedItem();
        titleWarehouseField.setText(selectedWarehouse.getTitle());
        addressWarehouseField.setText(selectedWarehouse.getAddress());
    }

    //----------------------Users functionality-------------------------------//

    private void setUsersTableValues(){
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        birthdayColumn.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
        cardNumber.setCellValueFactory(new PropertyValueFactory<>("cardNo"));
        managerIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        managerNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        managerSurnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
        managerBirthdayColumn.setCellValueFactory(new PropertyValueFactory<>("birthDate"));
        managerEmployeeId.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        managerEmploymentDate.setCellValueFactory(new PropertyValueFactory<>("employmentDate"));
        managerMedCertificate.setCellValueFactory(new PropertyValueFactory<>("medCertificate"));
        makeCustomerTableEditable();
        makeManagerTableEditable();
    }

    private void makeCustomerTableEditable(){
        customerTable.setEditable(true);
        setupCellFactoryAndHandlerForString(nameColumn, "Name");
        setupCellFactoryAndHandlerForString(surnameColumn, "Surname");
        setupCellFactoryAndHandlerForString(addressColumn, "Address");
        setupCellFactoryAndHandlerForLocalDate(birthdayColumn, "BirthDate");
        setupCellFactoryAndHandlerForString(cardNumber, "CardNo");
    }

    private void makeManagerTableEditable(){
        managerTable.setEditable(true);
        setupCellFactoryAndHandlerForString(managerNameColumn, "Name");
        setupCellFactoryAndHandlerForString(managerSurnameColumn, "Surname");
        setupCellFactoryAndHandlerForLocalDate(managerBirthdayColumn, "BirthDate");
        setupCellFactoryAndHandlerForString(managerEmployeeId, "EmployeeId");
        setupCellFactoryAndHandlerForLocalDate(managerEmploymentDate, "EmploymentDate");
        setupCellFactoryAndHandlerForString(managerMedCertificate, "MedCertificate");
    }

    private <T> void setupCellFactoryAndHandlerForLocalDate(TableColumn<T, LocalDate> column, String setter) {
        column.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateStringConverter()));
        column.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<T, LocalDate>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<T, LocalDate> event) {
                T item = event.getRowValue();
                try {
                    item.getClass().getMethod("set" + setter, LocalDate.class).invoke(item, event.getNewValue());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private <T> void setupCellFactoryAndHandlerForString(TableColumn<T, String> column, String setter) {
        column.setCellFactory(TextFieldTableCell.forTableColumn());
        column.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<T, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<T, String> event) {
                T item = event.getRowValue();
                try {
                    item.getClass().getMethod("set" + setter, String.class).invoke(item, event.getNewValue());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public void updateCustomerData() {
        Customer customer = customerTable.getSelectionModel().getSelectedItem();
        customerTable.getItems().clear();
        customerHib.updateUser(customer);
        customerTable.getItems().setAll(customerHib.getAllCustomers());
    }

    public void deleteCustomer() {
        Customer customer = customerTable.getSelectionModel().getSelectedItem();
        customerTable.getItems().clear();
        customerHib.deleteUser(customer.getId());
        customerTable.getItems().setAll(customerHib.getAllCustomers());
    }

    public void deleteManager() {
        Manager manager = managerTable.getSelectionModel().getSelectedItem();
        managerTable.getItems().clear();
        managerHib.deleteUser(manager.getId());
        managerTable.getItems().setAll(managerHib.getAllManagers());
    }

    public void updateManagerData() {
        Manager manager = managerTable.getSelectionModel().getSelectedItem();
        managerTable.getItems().clear();
        managerHib.updateUser(manager);
        managerTable.getItems().setAll(managerHib.getAllManagers());
    }

    //----------------------Cart functionality-------------------------------//
    //public ListView<Product> productList;
    public void addProductToCart() {
        currentOrder.getItems().add(productList.getSelectionModel().getSelectedItem());
    }

    public void deleteProductFromCart() {
        currentOrder.getItems().remove(productList.getSelectionModel().getSelectedItem());
    }


    public void onCartSubmit() {
        ObservableList<Product> observableList = currentOrder.getItems();
        List<Product> productList = new ArrayList<>(observableList);
        Cart cart = new Cart(LocalDate.now(), productList);
        cartHib.create(cart);
        //commentHib.create(new Comment(commentTitle.getText(), commentBody.getText(), LocalDate.now(), cart));
    }
}
