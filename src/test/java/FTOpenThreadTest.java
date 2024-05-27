import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.app.fileHandling.fileThreads.OpenThread;
import org.app.fileHandling.FileInfo;
import org.app.validation.ioExceptions.InvalidExtensionException;
import org.app.validation.ioExceptions.InvalidTypeException;

import java.util.ArrayList;

public class FTOpenThreadTest {

    private OpenThread<Object> openThread;
    private FileInfo fileInfo;

    @BeforeEach
    public void setUp() {
        fileInfo = new FileInfo("test.csv"); // Beispiel für einen gültigen Dateipfad und eine gültige Erweiterung
        openThread = new OpenThread<>(fileInfo);
    }

    @Test
    public void testOpenThreadInvalidExtension() {
        FileInfo wrongInfo = new FileInfo("test.invalid");
        OpenThread<Object> wrongThread = new OpenThread<>(wrongInfo);
        assertThrows(InvalidExtensionException.class, wrongThread::call);
    }

    @Test
    public void testCall() throws InvalidTypeException {
        // Diese Methode kann nicht direkt getestet werden, ohne einen echten Thread zu starten
        // Annahme hier ist, dass keine Exception geworfen wird, was bedeutet, dass die Datei korrekt geöffnet wurde
        assertDoesNotThrow(() -> {
            ArrayList<Object> result = openThread.call(); // Ausführung der call Methode, die normalerweise im Thread läuft
            assertNotNull(result); // Stellen Sie sicher, dass das Ergebnis nicht null ist
        });
    }
}
