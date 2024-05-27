import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.app.data.dataFormats.ParseItems;
import org.app.data.models.Product;
import org.app.validation.ioExceptions.InvalidTypeException;

public class DFParseItemsTest {

    @Test
    public void testParseItemValid() throws InvalidTypeException {
        String input = "1,Test Product,Test Category,Test SubCategory,Test Specs,19.99";
        Product product = ParseItems.parseItem(input);
        assertNotNull(product);
        assertEquals("Test Product", product.getProductName());
        assertEquals(19.99, product.getPrice());
    }

    @Test
    public void testParseItemInvalidFormat() {
        String input = "1,Test Product,Test Category"; // Invalid number of fields
        assertThrows(InvalidTypeException.class, () -> ParseItems.parseItem(input));
    }
}
