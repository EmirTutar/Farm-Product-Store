import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import org.app.data.models.Product;
import org.app.data.dataCollection.TableViewCollection;
import org.app.fileHandling.IOClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testfx.framework.junit5.ApplicationTest;

import java.util.ArrayList;

@ExtendWith(MockitoExtension.class)
public class DDCTableViewCollectionTest extends ApplicationTest {

    private TableViewCollection tableViewCollection;

    @Mock
    private IOClient<Product> ioClientMock;

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
    public void testSetTableView() {
        sleep(4000); // Wait for TableView to be initialized (JavaFX thread issue
        TableView<Product> tableView = new TableView<>();
        Product product1 = new Product("Product1", "Category1", "subCategory1", "Specification1", 10.0);
        Product product2 = new Product("Product2", "Category2", "subCategory2", "Specification2", 20.0);
        tableViewCollection.addProducts(product1);
        tableViewCollection.addProducts(product2);

        tableViewCollection.setTableView(tableView);

        assertEquals(2, tableView.getItems().size());
    }

    @Test
    public void testSetProducts() {
        sleep(2000);
        ArrayList<Product> products = new ArrayList<>();
        products.add(new Product("Product1", "Category1", "subCategory1", "Specification1", 10.0));
        products.add(new Product("Product2", "Category2", "subCategory2", "Specification2", 20.0));
        tableViewCollection.setProducts(products);

        assertEquals(2, tableViewCollection.getProducts().size());
    }

    @Test
    public void testReloadProductsFlag() {
        tableViewCollection.setReloadProducts(true);
        assertTrue(tableViewCollection.isReloadProducts());

        tableViewCollection.setReloadProducts(false);
        assertFalse(tableViewCollection.isReloadProducts());
    }

    @Test
    public void testModifiedFlag() {
        tableViewCollection.setModified(true);
        assertTrue(tableViewCollection.isModified());

        tableViewCollection.setModified(false);
        assertFalse(tableViewCollection.isModified());
    }

    @Test
    public void testSetLoadedFile() {
        tableViewCollection.setLoadedFile("testFile");
        assertEquals("testFile", tableViewCollection.getLoadedFile());
    }
}
