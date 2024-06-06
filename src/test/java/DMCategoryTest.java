import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.app.data.models.Category;
import org.app.validation.customExceptions.InvalidTextInputException;
import java.util.ArrayList;

public class DMCategoryTest {

    private Category category;

    @BeforeEach
    public void setUp() {
        category = new Category("Test Category");
    }

    @Test
    public void testInitialSubCategoryListIsEmpty() {
        assertTrue(category.getSubCategories().isEmpty(), "SubCategory list should be empty initially");
    }

    @Test
    public void testAddSubCategory() throws InvalidTextInputException {
        category.addSubCategory("New SubCategory");
        assertTrue(category.getSubCategories().contains("New SubCategory"), "SubCategory list should contain 'New SubCategory'");
    }

    @Test
    public void testAddDuplicateSubCategoryThrowsException() {
        assertThrows(InvalidTextInputException.class, () -> {
            category.addSubCategory("SubCategory");
            category.addSubCategory("SubCategory");
        }, "Adding duplicate subcategory should throw InvalidTextInputException");
    }

    @Test
    public void testSetSubCategories() {
        ArrayList<String> subcategories = new ArrayList<>();
        subcategories.add("SubCategory1");
        subcategories.add("SubCategory2");
        category.setSubCategories(subcategories);
        assertEquals(2, category.getSubCategories().size(), "SubCategory list size should be 2");
        assertTrue(category.getSubCategories().containsAll(subcategories), "SubCategory list should contain all added subcategories");
    }

    @Test
    public void testToStringFormat() {
        assertDoesNotThrow(() -> category.toString(), "toString method should not throw any exceptions");
        String expectedOutput = "Category{name='Test Category', subCategories=[]}";
        assertEquals(expectedOutput, category.toString(), "toString output should match the expected format");
    }

    @Test
    public void testToStringWithSubCategories() throws InvalidTextInputException {
        category.addSubCategory("SubCategory1");
        category.addSubCategory("SubCategory2");
        String expectedOutput = "Category{name='Test Category', subCategories=[SubCategory1, SubCategory2]}";
        assertEquals(expectedOutput, category.toString(), "toString output with subcategories should match the expected format");
    }
}
