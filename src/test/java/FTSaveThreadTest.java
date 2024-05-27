import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.app.fileHandling.fileThreads.SaveThread;
import org.app.fileHandling.FileInfo;
import org.app.validation.ioExceptions.InvalidExtensionException;
import org.app.validation.ioExceptions.InvalidTypeException;
import java.util.ArrayList;

public class FTSaveThreadTest {

    private SaveThread<Object> saveThread;
    private FileInfo fileInfo;

    @BeforeEach
    public void setUp() {
        fileInfo = new FileInfo("test.csv");
        saveThread = new SaveThread<>(fileInfo, new ArrayList<>());
    }

    @Test
    public void testSaveThreadInvalidExtension() {
        FileInfo wrongInfo = new FileInfo("test.invalid");
        SaveThread<Object> wrongThread = new SaveThread<>(wrongInfo, new ArrayList<>());
        assertThrows(InvalidExtensionException.class, wrongThread::call);
    }

    @Test
    public void testCall() throws InvalidTypeException {
        // Diese Methode kann nicht direkt getestet werden, ohne einen echten Thread zu starten
        assertDoesNotThrow(() -> {
            saveThread.call(); // Ausführung der call Methode, die normalerweise im Thread läuft
            // Da diese Methode keine Rückgabe liefert, prüfen wir nur, ob keine Exception auftritt
        });
    }
}
