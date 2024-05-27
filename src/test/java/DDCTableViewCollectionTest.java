import static org.junit.jupiter.api.Assertions.*;

import javafx.collections.FXCollections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.app.data.models.Product;
import org.app.data.dataCollection.TableViewCollection;
import org.testfx.framework.junit5.ApplicationTest;

import java.util.ArrayList;

public class DDCTableViewCollectionTest extends ApplicationTest {

    private TableViewCollection tableViewCollection;

    @BeforeEach
    public void setUp() {
        tableViewCollection = TableViewCollection.getINSTANCE();
        tableViewCollection.setProducts(new ArrayList<>()); // Reset PRODUCTS for a clean test environment
    }

    @Test
    public void testSingletonInstance() {
        assertNotNull(tableViewCollection);
        assertSame(tableViewCollection, TableViewCollection.getINSTANCE());
    }

    @Test
    public void testAddAndDeleteProduct() {
        Product product = new Product("Product1", "Category", "subCategory", "Specification", 10.0);
        tableViewCollection.addProducts(product);
        assertTrue(tableViewCollection.getProducts().contains(product));

        tableViewCollection.deleteSelectedProducts(FXCollections.observableArrayList(product));
        assertFalse(tableViewCollection.getProducts().contains(product));
    }

    @Test
    public void testSaveData() {
        // Set up and simulation or isolation of IOClient needed
        tableViewCollection.saveData();
        assertFalse(tableViewCollection.isModified());
    }

    @Test
    public void testLoadProducts() {
        // Set up and simulate the IOClient for this test
        tableViewCollection.loadProducts("path/to/file");
        // Verification logic here
    }

    @Test
    public void testFilterTableView() {
        TableView<Product> tableView = new TableView<>();
        TextField filterTextField = new TextField();
        tableViewCollection.fillFilterComboBox(new ComboBox<>());
        tableViewCollection.filterTableView(tableView, filterTextField);

        filterTextField.setText("Product1"); // Assuming "Product1" is a product name
        assertEquals(1, tableView.getItems().size());
        assertEquals("Product1", tableView.getItems().get(0).getProductName());
    }
}
