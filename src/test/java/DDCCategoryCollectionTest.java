import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.app.data.models.Category;
import org.app.data.dataCollection.CategoryCollection;
import org.testfx.framework.junit5.ApplicationTest;

import java.util.ArrayList;

public class DDCCategoryCollectionTest extends ApplicationTest {

    private CategoryCollection categoryCollection;

    @BeforeEach
    public void setUp() {
        categoryCollection = CategoryCollection.getInstance();
        categoryCollection.setCategoriesObjects(new ArrayList<>()); // Reset CATEGORIES for clean test environment
    }

    @Test
    public void testSingletonInstance() {
        assertNotNull(categoryCollection);
        assertSame(categoryCollection, CategoryCollection.getInstance());
    }

    @Test
    public void testAddCategory() {
        Category newCategory = new Category("New Category");
        categoryCollection.addCategory(newCategory);
        assertTrue(categoryCollection.getCategoryObjects().contains(newCategory));
        assertTrue(categoryCollection.getCategories().contains("New Category"));
    }

    @Test
    public void testSaveCategories() {
        // Sie benötigen eine Setup-Routine, um den IOClient entsprechend zu isolieren oder zu simulieren.
        categoryCollection.saveCategories();
        assertFalse(categoryCollection.isModified());
    }

    @Test
    public void testLoadCategories() {
        // Simulate or isolate IOClient behavior for this test
        categoryCollection.loadCategories();
        // Verification logic here
    }
}
