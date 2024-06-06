import static org.junit.jupiter.api.Assertions.*;

import org.app.data.dataFormats.ItemsFormat;
import org.junit.jupiter.api.Test;
import org.app.data.dataFormats.ParseItems;
import org.app.data.models.Product;
import org.app.validation.ioExceptions.InvalidTypeException;

public class DDFParseItemsTest {

    @Test
    public void testParseItemValid() throws InvalidTypeException {
        String input = "1,Testproduct,Testcategory,Testsubcategory,Testspecs,19.99";
        Product product = ParseItems.parseItem(input);
        assertNotNull(product);
        assertEquals("Testproduct", product.getProductName());
        assertEquals("Testcategory", product.getCategory());
        assertEquals("Testsubcategory", product.getSubCategory());
        assertEquals("Testspecs", product.getSpecification());
        assertEquals(19.99, product.getPrice());
    }

    @Test
    public void testParseItemWithPipeInSpecs() throws InvalidTypeException {
        String input = "1,Testproduct,Testcategory,Testsubcategory,Specs|with|pipe,19.99";
        Product product = ParseItems.parseItem(input);
        assertNotNull(product);
        assertEquals("Testproduct", product.getProductName());
        assertEquals("Testcategory", product.getCategory());
        assertEquals("Testsubcategory", product.getSubCategory());
        assertEquals("Specs,with,pipe", product.getSpecification());
        assertEquals(19.99, product.getPrice());
    }

    @Test
    public void testParseItemInvalidFormat() {
        String input = "1,Testproduct,Testcategory"; // Invalid number of fields
        assertThrows(InvalidTypeException.class, () -> ParseItems.parseItem(input));
    }

    @Test
    public void testParseItemEmptyField() {
        String input = "1,,Testcategory,Testsubcategory,Testspecs,19.99"; // Empty product name
        assertThrows(InvalidTypeException.class, () -> ParseItems.parseItem(input));
    }

    @Test
    public void testParseItemInvalidID() {
        String input = "abc,Testproduct,Testcategory,Testsubcategory,Testspecs,19.99"; // Invalid ID
        assertThrows(InvalidTypeException.class, () -> ParseItems.parseItem(input));
    }

    @Test
    public void testParseItemInvalidPrice() {
        String input = "1,Testproduct,Testcategory,Testsubcategory,Testspecs,abc"; // Invalid price
        assertThrows(InvalidTypeException.class, () -> ParseItems.parseItem(input));
    }

    @Test
    public void testParseItemInvalidCategory() {
        String input = "1,Testproduct,123InvalidCategory,Testsubcategory,Testspecs,19.99"; // Invalid category
        assertThrows(InvalidTypeException.class, () -> ParseItems.parseItem(input));
    }

    @Test
    public void testParseItemInvalidSubCategory() {
        String input = "1,Testproduct,Testcategory,123InvalidSubcategory,Testspecs,19.99"; // Invalid subcategory
        assertThrows(InvalidTypeException.class, () -> ParseItems.parseItem(input));
    }

    @Test
    public void testParseItemInvalidSpecs() {
        String input = "1,Testproduct,Testcategory,Testsubcategory," + ItemsFormat.DELIMITER + ",19.99"; // Invalid specs
        assertThrows(InvalidTypeException.class, () -> ParseItems.parseItem(input));
    }
}
