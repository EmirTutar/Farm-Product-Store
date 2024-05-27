import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.app.fileHandling.save.SaveBinBehavior;

import java.io.File;
import java.util.ArrayList;

public class SaveBinBehaviorTest {

    private SaveBinBehavior<Object> saveBinBehavior;
    private String testPath;

    @BeforeEach
    public void setUp() {
        saveBinBehavior = new SaveBinBehavior<>();
        testPath = "test_data.bin"; // Der Pfad zur Testdatei
    }

    @Test
    public void testWriteValidData() {
        ArrayList<Object> testData = new ArrayList<>();
        testData.add("Test Data");
        // Diese Methode sollte normalerweise eine tatsächliche Datei schreiben, hier setzen wir einen Test ohne Dateischreiben voraus
        assertDoesNotThrow(() -> saveBinBehavior.write(testPath, testData));
        // Überprüfung, dass die Datei tatsächlich Daten enthält, könnte in einem Integrationstest durchgeführt werden
    }

    @Test
    public void testWriteWithIOException() {
        // Setzen eines ungültigen Dateipfads, um IOException zu simulieren
        saveBinBehavior = new SaveBinBehavior<>();
        assertDoesNotThrow(() -> saveBinBehavior.write("/invalid_path/test_data.bin", new ArrayList<>()));
        // Hier könnten Sie überprüfen, ob eine Log-Nachricht geschrieben wurde oder ähnliches
    }
}
