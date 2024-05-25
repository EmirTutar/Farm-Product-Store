package org.app.controllers;

import org.app.data.dataCollection.CategoryCollection;
import org.app.data.models.Product;
import org.app.fileHandling.FileInfo;
import org.app.fileHandling.IOClient;
import javafx.collections.ObservableList;
import org.app.Load;
import org.app.PathDialogBox;
import org.app.data.dataCollection.TableViewCollection;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.app.validation.Alerts;
import org.app.validation.NumberConversion;
import org.app.validation.customExceptions.InvalidNumberFormat;
import org.app.validation.ioExceptions.*;
import org.app.validation.Validator;
import org.app.validation.customExceptions.EmptyFieldException;
import org.app.validation.customExceptions.InvalidTextInputException;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AdminController implements Initializable {

    @FXML
    private BorderPane adminPane;
    @FXML
    private TextField name, price, txtFilter;
    @FXML
    private TextArea specifications;
    @FXML
    private ComboBox<String> categoriesCombobox;
    @FXML
    private ComboBox<String> subcategoryCombobox;
    @FXML
    private ComboBox<String> filterComboBox;
    @FXML
    private TableView<Product> tableview;
    @FXML
    private TableColumn<Product, String> categoryCol;
    @FXML
    private TableColumn<Product, String> subcategoryCol;
    @FXML
    private TableColumn<Product, Double> priceCol;
    @FXML
    private Label filenameLabel;
    public static Label filenameLabelStatic;

    private TableSelectionModel<Product> tableSelectionModel;
    private final TableViewCollection COLLECTION = TableViewCollection.getINSTANCE();
    private final CategoryCollection CATEGORY_COLLECTION = CategoryCollection.getInstance();
    private String openedFile;
    private final PathDialogBox PATH_DIALOG_BOX = new PathDialogBox();

    private final NumberConversion.StringToDouble STR_2_DOUBLE = new NumberConversion.StringToDouble();

    private void setOpenedFile(String openedFile) {
        this.openedFile = openedFile;
    }

    private String getOpenedFile() {
        return openedFile;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String file = "DataFraApp/Database/products.bin";
        COLLECTION.loadProducts(file);
        COLLECTION.setTableView(tableview);
        COLLECTION.fillFilterComboBox(filterComboBox);
        COLLECTION.filterTableView(tableview, txtFilter);

        CATEGORY_COLLECTION.loadCategories();
        CATEGORY_COLLECTION.setComboBoxes(categoriesCombobox, subcategoryCombobox);
        CATEGORY_COLLECTION.updateCategoriesOnChange(categoriesCombobox, subcategoryCombobox);
        CATEGORY_COLLECTION.updateSubCategoriesOnChange(categoriesCombobox);
        CATEGORY_COLLECTION.updateSubCategoriesOnTableView(tableview);

        tableSelectionModel = tableview.getSelectionModel();
        tableSelectionModel.setSelectionMode(SelectionMode.MULTIPLE);
        filenameLabelStatic = filenameLabel;

        categoryCol.setCellFactory(ComboBoxTableCell.forTableColumn(CATEGORY_COLLECTION.getCategories()));
        subcategoryCol.setCellFactory(ComboBoxTableCell.forTableColumn(CATEGORY_COLLECTION.getSubCategories()));
        priceCol.setCellFactory(TextFieldTableCell.forTableColumn(STR_2_DOUBLE));

        price.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*(\\.\\d*)?")) {
                price.setText(newValue.replaceAll("[^\\d*(\\.\\d*)?]", ""));
            }
        });
    }

    @FXML
    void registerProduct() {
        try {
            String product_name = Validator.validateName(name.getText());
            String category = Validator.validateCategory(categoriesCombobox.getValue());
            String subcategory = Validator.validateCategory(subcategoryCombobox.getValue());
            String specs = Validator.validateSpecs(specifications.getText());
            Double product_price = Validator.validatePrice(price.getText());

            Product product = new Product(product_name, category, subcategory, specs, product_price);
            COLLECTION.addProducts(product);
            reset();

            Alerts.success("New Product Created");
        } catch (InvalidTextInputException | EmptyFieldException | InvalidNumberFormat e) {
            Alerts.warning(e.getMessage());
        }
    }

    private void reset() {
        // Resets the fields
        name.setText("");
        specifications.setText("");
        price.setText("");
    }

    @FXML
    void open() {
        boolean doOpen = Alerts.confirm("Do you want to replace the data in the table with the data from the file you are about to upload?");
        if (doOpen) {
            String path = "DataFraApp/" + PATH_DIALOG_BOX.getPathToOpen();
            try {
                PATH_DIALOG_BOX.nullPathHandling(path);
                PATH_DIALOG_BOX.extensionCheck(path);
                PATH_DIALOG_BOX.fileNotFound(path);
                FileInfo file = new FileInfo(path);
                IOClient<Product> io = new IOClient<>(file);
                io.runOpenThread("Opening file...");
                setOpenedFile(file.getFullPath());
            } catch (FileDontExistsException | NullPointerException | InvalidExtensionException e) {
                Alerts.warning(e.getMessage());
            }
        } else {
            Alerts.success("Data was not replaced.");
        }
    }

    private String getPath() {
        if (getOpenedFile() == null) {
            return PATH_DIALOG_BOX.getPathToSave();
        }

        boolean newFile = Alerts.confirm("Do you want to save the file as a new file?");
        if (newFile) {
            return PATH_DIALOG_BOX.getPathToSave();
        } else {
            return getOpenedFile();
        }
    }

    @FXML
    void save() {
        ArrayList<Product> components = new ArrayList<>(COLLECTION.getProducts());
        if (!components.isEmpty()) {
            String path = "DataFraApp/" + getPath();
            try {
                PATH_DIALOG_BOX.nullPathHandling(path);
                PATH_DIALOG_BOX.extensionCheck(path);
            } catch (NullPointerException | InvalidExtensionException e) {
                Alerts.warning(e.getMessage());
                return;
            }
            FileInfo file = new FileInfo(path);
            IOClient<Product> io = new IOClient<>(file, components);
            io.runSaveThread("Saving file...");
        } else {
            Alerts.warning("Nothing was saved.");
        }
    }

    @FXML
    void editName(TableColumn.CellEditEvent<Product, String> event) {
        try {
            event.getRowValue().setProductName(event.getNewValue());
            COLLECTION.setModified(true);
            tableview.refresh();
        } catch (IllegalArgumentException e) {
            Alerts.warning(e.getMessage());
            tableview.refresh();
        }
    }

    @FXML
    void editSpecs(TableColumn.CellEditEvent<Product, String> event) {
        try {
            event.getRowValue().setSpecification(event.getNewValue());
            COLLECTION.setModified(true);
            tableview.refresh();
        } catch (IllegalArgumentException e) {
            Alerts.warning(e.getMessage());
            tableview.refresh();
        }
    }

    @FXML
    void editCategory(TableColumn.CellEditEvent<Product, String> event) {
        try {
            event.getRowValue().setCategory(event.getNewValue());
            event.getRowValue().setSubCategory("Select again");
            Alerts.success("You have changed the parent category.\nPlease select a new subcategory.");
            COLLECTION.setModified(true);
            tableview.refresh();
        } catch (Exception e) {
            Alerts.warning(e.getMessage());
            tableview.refresh();
        }
    }

    @FXML
    void editSubCategory(TableColumn.CellEditEvent<Product, String> event) {
        try {
            event.getRowValue().setSubCategory(event.getNewValue());
            COLLECTION.setModified(true);
            tableview.refresh();
        } catch (Exception e) {
            Alerts.warning(e.getMessage());
            tableview.refresh();
        }
    }

    @FXML
    void editPrice(TableColumn.CellEditEvent<Product, Double> event) {
        try {
            event.getRowValue().setPrice(event.getNewValue());
            COLLECTION.setModified(true);
            tableview.refresh();
        } catch (IllegalArgumentException e) {
            Alerts.warning(e.getMessage());
            tableview.refresh();
        } catch (NullPointerException ignored) {
            tableview.refresh();
        }
    }

    @FXML
    void delete() {
        ObservableList<Product> selectedRows = tableSelectionModel.getSelectedItems();
        boolean doRemove = Alerts.confirm("Are you sure you want to delete the selected item(s)?");
        if (doRemove) {
            COLLECTION.deleteSelectedProducts(selectedRows);
            tableview.refresh();
        } else {
            tableSelectionModel.clearSelection();
        }
    }

    @FXML
    void logOut() {
        if (COLLECTION.isModified()) {
            boolean response = Alerts.confirm("Do you want to save all changes?");
            if (response) {
                COLLECTION.saveData();
            } else {
                Alerts.success("Changes were not saved");
                COLLECTION.setReloadProducts(true);
                COLLECTION.getProducts().clear();
                COLLECTION.setModified(false);
            }
        } else if (CATEGORY_COLLECTION.isModified()) {
            CATEGORY_COLLECTION.saveCategories();
        }
        CATEGORY_COLLECTION.getCategories().clear();
        Stage stage = (Stage) adminPane.getScene().getWindow();
        Load.window("loginView.fxml", "Login", stage);
    }

    @FXML
    void showCategoryRegister() {
        Load.openCategoryPopup();
    }
}
