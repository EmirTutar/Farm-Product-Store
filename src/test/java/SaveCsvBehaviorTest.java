import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.app.fileHandling.save.SaveCsvBehavior;
import org.app.validation.ioExceptions.InvalidTypeException;

import java.io.File;
import java.util.ArrayList;

public class SaveCsvBehaviorTest {

    private SaveCsvBehavior<Object> saveCsvBehavior;
    private String testPath;

    @BeforeEach
    public void setUp() {
        saveCsvBehavior = new SaveCsvBehavior<>();
        testPath = "test_data.csv"; // Der Pfad zur Testdatei
    }

    @Test
    public void testWriteValidData() throws InvalidTypeException {
        ArrayList<Object> testData = new ArrayList<>();
        testData.add("Test Data");
        assertDoesNotThrow(() -> saveCsvBehavior.write(testPath, testData));
        // Dieser Test würde normalerweise die Datei schreiben und dann lesen, um die Korrektheit zu überprüfen
    }

    @Test
    public void testWriteEmptyData() {
        ArrayList<Object> emptyData = new ArrayList<>();
        InvalidTypeException thrown = assertThrows(InvalidTypeException.class, () -> saveCsvBehavior.write(testPath, emptyData));
        assertTrue(thrown.getMessage().contains("The file you want to upload is empty!"));
    }

    @Test
    public void testIOExceptionHandling() {
        // Setzen eines ungültigen Dateipfads, um IOException zu simulieren
        assertDoesNotThrow(() -> saveCsvBehavior.write("/invalid_path/test_data.csv", new ArrayList<>()));
    }
}
