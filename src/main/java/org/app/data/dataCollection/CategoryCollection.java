package org.app.data.dataCollection;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import org.app.data.models.Category;
import org.app.data.models.Product;
import org.app.fileHandling.FileInfo;
import org.app.fileHandling.IOClient;

import java.util.ArrayList;

public class CategoryCollection {

    public static final ObservableList<Category> CATEGORIES = FXCollections.observableArrayList();
    private final ObservableList<String> mainCategories = FXCollections.observableArrayList();
    private final ObservableList<String> subCategories = FXCollections.observableArrayList();
    private final FileInfo categoryFile = new FileInfo("DataFraApp/Database/categories.bin");
    private boolean modified = false;

    public static CategoryCollection instance;

    private CategoryCollection() {
    }

    public static CategoryCollection getInstance() {
        if (instance == null) {
            instance = new CategoryCollection();
        }
        return instance;
    }

    /**
     * Adds new categories
     */
    public void addCategory(Category toAdd) {
        mainCategories.add(toAdd.getName());
        CATEGORIES.add(toAdd);
    }

    /**
     * When the list of categories is changed, all combo boxes must be updated
     */
    public void updateCategoriesOnChange(ComboBox<String> categoryOptions, ComboBox<String> subCategoryOptions) {
        CATEGORIES.addListener((ListChangeListener<Category>) change -> setComboBoxes(categoryOptions, subCategoryOptions));
    }

    /**
     * When the main category is changed, the subcategories must be updated
     */
    public void updateSubCategoriesOnChange(ComboBox<String> categoryOptions) {
        categoryOptions.valueProperty().addListener((observableValue, oldValue, newValue) -> loadSubCategories(newValue));
    }

    /**
     * Opens the categories file
     */
    public void loadCategories() {
        IOClient<Category> openFile = new IOClient<>(categoryFile);
        openFile.runOpenThread("Loading categories...");
    }

    /**
     * Saves the categories file
     */
    public void saveCategories() {
        IOClient<Category> saveFile = new IOClient<>(categoryFile, new ArrayList<>(CATEGORIES));
        saveFile.runSaveThread("Saving new categories...");
    }

    /**
     * Sets values to category combo boxes
     */
    public void setComboBoxes(ComboBox<String> categoryOptions, ComboBox<String> subCategoryOptions) {
        categoryOptions.setItems(mainCategories);
        subCategoryOptions.setItems(subCategories);
    }

    /**
     * Fills the subcategory observable list with values based on the main category
     */
    public void loadSubCategories(String value) {
        subCategories.clear();
        for (Category category : CATEGORIES) {
            if (category.getName().equals(value)) {
                subCategories.addAll(category.getSubCategories());
            }
        }
    }

    /**
     * Fills the subcategories combo box with the correct values on the TableView
     */
    public void updateSubCategoriesOnTableView(TableView<Product> tableView) {
        tableView.setRowFactory(tv -> {
            TableRow<Product> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1) {
                    if (row.getItem() != null) {
                        Product product = row.getItem();
                        loadSubCategories(product.getCategory());
                    }
                }
            });
            return row;
        });
    }

    /**
     * Getter - Setter methods
     */

    public ObservableList<String> getCategories() {
        return mainCategories;
    }

    public ObservableList<String> getSubCategories() {
        return subCategories;
    }

    public ObservableList<Category> getCategoryObjects() {
        return CATEGORIES;
    }

    public void setCategoriesObjects(ArrayList<Category> list) {
        CATEGORIES.clear();
        CATEGORIES.addAll(list);
        list.forEach(category -> mainCategories.add(category.getName()));
    }

    public boolean isModified() {
        return modified;
    }

    public void setModified(boolean modified) {
        this.modified = modified;
    }
}
