import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.app.data.dataFormats.ItemsFormat;
import org.app.data.models.Product;
import org.app.validation.ioExceptions.InvalidTypeException;
import java.util.ArrayList;

public class DFItemsFormatTest {

    @Test
    public void testObjectFormatValidProduct() throws InvalidTypeException {
        Product product = new Product("Test Product", "Test Category", "Test SubCategory", "Specs, with comma", 19.99);
        String formatted = ItemsFormat.objectFormat(product);
        assertFalse(formatted.contains(","));
        assertTrue(formatted.contains("|"));
    }

    @Test
    public void testObjectFormatInvalidProduct() {
        Product product = new Product("Test Product", "Test Category", "Select again", "Specs", 19.99);
        assertThrows(InvalidTypeException.class, () -> ItemsFormat.objectFormat(product));
    }

    @Test
    public void testItemsText() throws InvalidTypeException {
        ArrayList<Product> products = new ArrayList<>();
        products.add(new Product("Test Product 1", "Category 1", "Subcategory 1", "Specs 1", 10.0));
        products.add(new Product("Test Product 2", "Category 2", "Subcategory 2", "Specs 2", 20.0));
        String result = ItemsFormat.itemsText(products);
        assertNotNull(result);
        assertTrue(result.contains("Test Product 1"));
        assertTrue(result.contains("Test Product 2"));
    }
}
