package org.app.data.dataCollection;

import org.app.data.models.Product;
import org.app.fileHandling.FileInfo;
import org.app.fileHandling.IOClient;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.*;
import java.util.ArrayList;

/**
 * The class collects data for the TableView and its methods allow for data manipulation.
 */
public class TableViewCollection {

    private final ObservableList<Product> PRODUCTS = FXCollections.observableArrayList();
    private boolean reloadProducts = true;
    private boolean modified = false;
    private String filterChoice = "Name";
    private String loadedFile;
    private static TableViewCollection INSTANCE;

    /**
     * When an object of this class is created, it always monitors if the data in the TableView has been changed.
     */
    private TableViewCollection() {
        PRODUCTS.addListener((ListChangeListener<Product>) change -> setModified(true));
    }

    /**
     * Uses a single instance of this class so that the data in the TableView synchronizes.
     */
    public static TableViewCollection getINSTANCE() {
        if (INSTANCE == null) INSTANCE = new TableViewCollection();
        return INSTANCE;
    }

    /**
     * Loads all products from a file and adds them to the ObservableList: <b>products</b>
     */
    public void loadProducts(String filePath) {
        IOClient<Product> open = new IOClient<>(new FileInfo(filePath));
        loadedFile = filePath;
        if (reloadProducts) {
            open.runOpenThread("Loading products...");
            reloadProducts = false;
        }
    }

    /**
     * Deletes all selected products from the table
     */
    public void deleteSelectedProducts(ObservableList<Product> selectedProducts) {
        if (selectedProducts.size() >= 1) {
            PRODUCTS.removeAll(selectedProducts);
            setModified(true);
        }
    }

    /**
     * Adds a new product to the table
     */
    public void addProducts(Product product) {
        for (Product p : getProducts()) {
            if (product.getProductID() == p.getProductID()) {
                PRODUCTS.remove(p);
                break;
            }
        }
        PRODUCTS.add(product);
        setModified(true);
    }

    /**
     * Updates the file when the user logs out or the program exits
     */
    public void saveData() {
        ArrayList<Product> data = new ArrayList<>(getProducts());
        IOClient<Product> save = new IOClient<>(new FileInfo(loadedFile), data);
        save.runSaveThread("Saving file...");
        setModified(false);
    }

    /**
     * Displays all products in the table
     */
    public void setTableView(TableView<Product> tableView) {
        tableView.setItems(getProducts());
    }

    /**
     * Allows filtering the table by product name, price, category, etc.
     */
    public void fillFilterComboBox(ComboBox<String> filterOptions) {
        String[] filterCats = {"Product ID", "Name", "Category", "Specifications", "Price"};
        ObservableList<String> filterCategories = FXCollections.observableArrayList(filterCats);
        filterOptions.setItems(filterCategories);
        filterOptions.setValue("Name");
        filterOptions.setOnAction(e -> filterChoice = filterOptions.getValue());
    }

    /**
     * Filters and searches the table
     */
    public void filterTableView(TableView<Product> tableView, TextField filterTextField) {
        FilteredList<Product> filteredList = new FilteredList<>(PRODUCTS, product -> true);
        filterTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate((product) -> {
                String nr = Integer.toString(product.getProductID());
                String name = product.getProductName().toLowerCase();
                String category = product.getCategory().toLowerCase();
                String specs = product.getSpecification();
                String price = Double.toString(product.getPrice());
                String filter = newValue.toLowerCase();

                switch (filterChoice) {
                    case "Product ID":
                        if (nr.equals(filter)) {
                            return true;
                        }
                        break;
                    case "Name":
                        if (name.contains(filter)) {
                            return true;
                        }
                        break;
                    case "Category":
                        if (category.contains(filter)) {
                            return true;
                        }
                        break;
                    case "Specifications":
                        if (specs.contains(filter)) {
                            return true;
                        }
                        break;
                    case "Price":
                        if (price.contains(filter)) {
                            return true;
                        }
                        break;
                }
                return newValue.isEmpty();
            });
        });

        SortedList<Product> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedList);
    }

    /**
     * Getter and Setter methods
     */
    public void setProducts(ArrayList<Product> items) {
        for (Product i : items) {
            for (Product p : PRODUCTS) {
                while (i.getProductID() == p.getProductID()) {
                    i.setProductID(p.getProductID() + 1);
                }
            }
            PRODUCTS.add(i);
        }
    }

    public ObservableList<Product> getProducts() {
        return PRODUCTS;
    }

    public void setReloadProducts(boolean isProductsReloaded) {
        reloadProducts = isProductsReloaded;
    }

    public void setModified(boolean isModified) {
        modified = isModified;
    }

    public void setLoadedFile(String loadedFile1) {
        loadedFile = loadedFile1;
    }

    public boolean isModified() {
        return modified;
    }
}
