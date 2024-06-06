import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.app.data.models.Product;
import org.app.validation.customExceptions.InvalidTextInputException;
import org.app.validation.customExceptions.EmptyFieldException;

import java.io.*;

public class DMProductTest {

    private Product product;

    @BeforeEach
    public void setUp() {
        product = new Product("Initialproduct", "Initialcategory", "Initialsubcategory", "Initialspecs", 100.0);
    }

    @Test
    public void testProductIDAutoIncrement() {
        Product newProduct = new Product("Newproduct", "Newcategory", "Newsubcategory", "Newspecs", 200.0);
        assertNotEquals(product.getProductID(), newProduct.getProductID());
    }

    @Test
    public void testSetAndGetProperties() throws InvalidTextInputException, EmptyFieldException {
        product.setProductName("Updatedproduct");
        assertEquals("Updatedproduct", product.getProductName());

        product.setCategory("Updatedcategory");
        assertEquals("Updatedcategory", product.getCategory());

        product.setSubCategory("Updatedsubcategory");
        assertEquals("Updatedsubcategory", product.getSubCategory());

        product.setSpecification("Updatedspecs");
        assertEquals("Updatedspecs", product.getSpecification());

        product.setPrice(150.0);
        assertEquals(150.0, product.getPrice());
    }

    @Test
    public void testCsvFormat() {
        String expectedFormat = String.format("%d,Initialproduct,Initialcategory,Initialsubcategory,Initialspecs,100.0", product.getProductID());
        assertEquals(expectedFormat, product.csvFormat(","));
    }

    @Test
    public void testToString() {
        assertNotNull(product.toString());
        assertTrue(product.toString().contains("Initialproduct"));
        assertTrue(product.toString().contains("100.0"));
    }

    @Test
    public void testSerializationMethods() throws InvalidTextInputException, IOException, ClassNotFoundException {
        product.setProductName("Serializableproduct");

        // Serialize the product
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(product);

        // Deserialize the product
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        Product deserializedProduct = (Product) objectInputStream.readObject();

        assertEquals(product.getProductID(), deserializedProduct.getProductID());
        assertEquals("Serializableproduct", deserializedProduct.getProductName());
    }

    @Test
    public void testSetInvalidProductName() {
        assertThrows(InvalidTextInputException.class, () -> product.setProductName("123Invalid"));
    }

    @Test
    public void testSetEmptyProductName() {
        assertThrows(EmptyFieldException.class, () -> product.setProductName(""));
    }

    @Test
    public void testSetInvalidCategory() {
        assertThrows(InvalidTextInputException.class, () -> product.setCategory("invalidCategory1"));
    }

    @Test
    public void testSetEmptyCategory() {
        assertThrows(EmptyFieldException.class, () -> product.setCategory(""));
    }

    @Test
    public void testSetInvalidSubCategory() {
        assertThrows(InvalidTextInputException.class, () -> product.setSubCategory("invalidSubCategory1"));
    }

    @Test
    public void testSetEmptySubCategory() {
        assertThrows(EmptyFieldException.class, () -> product.setSubCategory(""));
    }

    @Test
    public void testSetEmptySpecification() {
        assertThrows(EmptyFieldException.class, () -> product.setSpecification(""));
    }

    @Test
    public void testSetProductID() {
        int oldId = product.getProductID();
        product.setProductID();
        assertNotEquals(oldId, product.getProductID());
        assertEquals(oldId + 1, product.getProductID());
    }
}
