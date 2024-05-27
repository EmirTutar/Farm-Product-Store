import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.app.data.models.Category;
import org.app.validation.customExceptions.InvalidTextInputException;
import java.util.ArrayList;

public class MCategoryTest {

    private Category category;

    @BeforeEach
    public void setUp() {
        category = new Category("Test Category");
    }

    @Test
    public void testInitialSubCategoryListIsEmpty() {
        assertTrue(category.getSubCategories().isEmpty());
    }

    @Test
    public void testAddSubCategory() throws InvalidTextInputException {
        category.addSubCategory("New SubCategory");
        assertTrue(category.getSubCategories().contains("New SubCategory"));
    }

    @Test
    public void testAddDuplicateSubCategoryThrowsException() {
        assertThrows(InvalidTextInputException.class, () -> {
            category.addSubCategory("SubCategory");
            category.addSubCategory("SubCategory");
        });
    }

    @Test
    public void testSetSubCategories() {
        ArrayList<String> subcategories = new ArrayList<>();
        subcategories.add("SubCategory1");
        subcategories.add("SubCategory2");
        category.setSubCategories(subcategories);
        assertEquals(2, category.getSubCategories().size());
        assertTrue(category.getSubCategories().containsAll(subcategories));
    }

    @Test
    public void testToStringFormat() {
        assertDoesNotThrow(() -> category.toString());
        String expectedOutput = "Category{name='Test Category', subCategories=[]}";
        assertEquals(expectedOutput, category.toString());
    }
}
