import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.app.data.models.Product;
import org.app.validation.customExceptions.InvalidTextInputException;
import org.app.validation.customExceptions.EmptyFieldException;

public class MProductTest {

    private Product product;

    @BeforeEach
    public void setUp() {
        product = new Product("Initial Product", "Initial Category", "Initial SubCategory", "Initial Specs", 100.0);
    }

    @Test
    public void testProductIDAutoIncrement() {
        Product newProduct = new Product("New Product", "New Category", "New SubCategory", "New Specs", 200.0);
        assertNotEquals(product.getProductID(), newProduct.getProductID());
    }

    @Test
    public void testSetAndGetProperties() throws InvalidTextInputException, EmptyFieldException {
        product.setProductName("Updated Product");
        assertEquals("Updated Product", product.getProductName());

        product.setCategory("Updated Category");
        assertEquals("Updated Category", product.getCategory());

        product.setSubCategory("Updated SubCategory");
        assertEquals("Updated SubCategory", product.getSubCategory());

        product.setSpecification("Updated Specs");
        assertEquals("Updated Specs", product.getSpecification());

        product.setPrice(150.0);
        assertEquals(150.0, product.getPrice());
    }

    @Test
    public void testCsvFormat() {
        String expectedFormat = String.format("%d,Initial Product,Initial Category,Initial SubCategory,Initial Specs,100.0", product.getProductID());
        assertEquals(expectedFormat, product.csvFormat(","));
    }

    @Test
    public void testToString() {
        assertNotNull(product.toString());
        assertTrue(product.toString().contains("Initial Product"));
        assertTrue(product.toString().contains("100.0"));
    }

    @Test
    public void testSerializationMethods() throws InvalidTextInputException {
        product.setProductName("Serializable Product");
        String output = product.toString();
        assertTrue(output.contains("Serializable Product"));
    }
}
