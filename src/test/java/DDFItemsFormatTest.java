import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.app.data.dataFormats.ItemsFormat;
import org.app.data.models.Product;
import org.app.validation.ioExceptions.InvalidTypeException;
import java.util.ArrayList;

public class DDFItemsFormatTest {

    @Test
    public void testObjectFormatValidProduct() throws InvalidTypeException {
        Product product = new Product("Testproduct", "Testcategory", "Testsubcategory", "Specs,with,comma", 19.99);
        String formatted = ItemsFormat.objectFormat(product);

        // Prüfen, ob der formatierte String keine Kommas in den Spezifikationen hat
        assertFalse(formatted.contains("Specs,with,comma"), "Formatted string should not contain commas in specifications");
        assertTrue(formatted.contains("Specs|with|comma"), "Formatted string should replace comma with pipe in specifications");

        // Prüfen, ob der formatierte String die erwarteten Felder enthält
        assertTrue(formatted.contains("Testproduct"), "Formatted string should contain the product name");
        assertTrue(formatted.contains("Testcategory"), "Formatted string should contain the category");
        assertTrue(formatted.contains("Testsubcategory"), "Formatted string should contain the subcategory");
        assertTrue(formatted.contains("19.99"), "Formatted string should contain the price");
    }

    @Test
    public void testObjectFormatInvalidProduct() {
        Product product = new Product("Testproduct", "Testcategory", "Select again", "Specs", 19.99);
        assertThrows(InvalidTypeException.class, () -> ItemsFormat.objectFormat(product));
    }

    @Test
    public void testObjectFormatUnsupportedType() {
        String unsupportedObject = "UnsupportedType";
        assertThrows(InvalidTypeException.class, () -> ItemsFormat.objectFormat(unsupportedObject));
    }

    @Test
    public void testItemsText() throws InvalidTypeException {
        ArrayList<Product> products = new ArrayList<>();
        products.add(new Product("Testproduct1", "Category1", "Subcategory1", "Specs1", 10.0));
        products.add(new Product("Testproduct2", "Category2", "Subcategory2", "Specs2", 20.0));
        String result = ItemsFormat.itemsText(products);
        assertNotNull(result);
        assertTrue(result.contains("Testproduct1"));
        assertTrue(result.contains("Testproduct2"));
    }

    @Test
    public void testItemsTextWithCommaInSpecs() throws InvalidTypeException {
        ArrayList<Product> products = new ArrayList<>();
        products.add(new Product("Testproduct1", "Category1", "Subcategory1", "Specs,with,comma", 10.0));
        String result = ItemsFormat.itemsText(products);
        assertNotNull(result);
        assertTrue(result.contains("Testproduct1"));
        assertTrue(result.contains("Specs|with|comma"), "Formatted string should replace comma with pipe in specifications");
    }

    @Test
    public void testItemsTextWithInvalidProduct() {
        ArrayList<Product> products = new ArrayList<>();
        products.add(new Product("Testproduct1", "Category1", "Subcategory1", "Specs1", 10.0));
        products.add(new Product("Testproduct2", "Category2", "Select again", "Specs2", 20.0));
        assertThrows(InvalidTypeException.class, () -> ItemsFormat.itemsText(products));
    }
}
