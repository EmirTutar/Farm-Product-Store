import javafx.scene.control.ComboBox;
import org.app.data.dataCollection.CategoryCollection;
import org.app.data.models.Category;
import org.app.fileHandling.IOClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testfx.framework.junit5.ApplicationTest;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class DDCCategoryCollectionTest extends ApplicationTest {

    @InjectMocks
    private CategoryCollection categoryCollection;

    @Mock
    private IOClient<Category> ioClientMock;

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
    public void testUpdateCategoriesOnChange() {
        ComboBox<String> categoryOptions = new ComboBox<>();
        ComboBox<String> subCategoryOptions = new ComboBox<>();
        categoryCollection.updateCategoriesOnChange(categoryOptions, subCategoryOptions);

        Category newCategory = new Category("New Category");
        categoryCollection.addCategory(newCategory);

        // Ensure combo boxes are updated when categories change
        assertTrue(categoryOptions.getItems().contains("New Category"));
    }

    @Test
    public void testUpdateSubCategoriesOnChange() {
        ComboBox<String> categoryOptions = new ComboBox<>();
        categoryCollection.updateSubCategoriesOnChange(categoryOptions);

        Category newCategory = new Category("New Category");
        newCategory.addSubCategory("Sub Category");
        categoryCollection.addCategory(newCategory);

        // Simulate category change
        categoryOptions.getSelectionModel().select("New Category");
        assertTrue(categoryCollection.getSubCategories().contains("Sub Category"));
    }

    @Test
    public void testLoadSubCategories() {
        Category newCategory = new Category("New Category");
        newCategory.addSubCategory("Sub Category");
        categoryCollection.addCategory(newCategory);

        categoryCollection.loadSubCategories("New Category");
        assertTrue(categoryCollection.getSubCategories().contains("Sub Category"));
    }

}
