import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.app.fileHandling.save.SaveAbstract;
import org.app.fileHandling.save.SaveBinBehavior;
import org.app.fileHandling.save.SaveCsvBehavior;
import org.app.validation.ioExceptions.InvalidTypeException;
import org.app.data.models.Product;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FHSaveTest {

    private SaveAbstract<Product> saveBinBehavior;
    private SaveAbstract<Product> saveCsvBehavior;
    private List<Product> testList;
    private String binFilePath;
    private String csvFilePath;

    @BeforeEach
    public void setUp() {
        saveBinBehavior = new SaveBinBehavior<>();
        saveCsvBehavior = new SaveCsvBehavior<>();
        testList = new ArrayList<>();
        testList.add(new Product("Testproduct1", "Category1", "Subcategory1", "specs1", 10.0));
        testList.add(new Product("Testproduct2", "Category2", "Subcategory2", "specs2", 20.0));
        binFilePath = "src/test/testFiles/testOutput.bin";
        csvFilePath = "src/test/testFiles/testOutput.csv";
    }

    @Test
    public void testWriteBinFile() {
        assertDoesNotThrow(() -> saveBinBehavior.write(binFilePath, new ArrayList<>(testList)));
        // Verify that the file was created and is not empty
        File file = new File(binFilePath);
        assertTrue(file.exists());
        assertTrue(file.length() > 0);
    }

    @Test
    public void testWriteCsvFile() throws InvalidTypeException {
        assertDoesNotThrow(() -> saveCsvBehavior.write(csvFilePath, new ArrayList<>(testList)));
        // Verify that the file was created and is not empty
        File file = new File(csvFilePath);
        assertTrue(file.exists());
        assertTrue(file.length() > 0);

        // Additional verification for content
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();
            assertNotNull(line);
            assertTrue(line.contains("Testproduct1") || line.contains("Testproduct2"));
        } catch (IOException e) {
            fail("Failed to read the CSV file: " + e.getMessage());
        }
    }

    @Test
    public void testWriteInvalidCsvFile() {
        assertThrows(InvalidTypeException.class, () -> saveCsvBehavior.write("invalidPath/invalid.csv", null));
    }

    @Test
    public void testWriteInvalidBinFile() {
        // Testing invalid path for SaveBinBehavior
        assertDoesNotThrow(() -> saveBinBehavior.write("invalidPath/invalid.bin", new ArrayList<>(testList)));
    }
}
