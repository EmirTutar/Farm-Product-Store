import static org.junit.jupiter.api.Assertions.*;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.app.fileHandling.FileInfo;
import org.app.fileHandling.fileThreads.SaveThread;
import org.app.validation.ioExceptions.InvalidExtensionException;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class FHFTSaveThreadTest {

    private SaveThread<Object> saveThread;
    private FileInfo fileInfo;

    @BeforeAll
    public static void initToolkit() throws InterruptedException {
        // Ensure that JavaFX toolkit is initialized only once
        CountDownLatch latch = new CountDownLatch(1);
        Platform.startup(() -> {
            new JFXPanel(); // Initialize the JavaFX environment
            latch.countDown();
        });
        latch.await(5, TimeUnit.SECONDS);
    }

    @BeforeEach
    public void setUp() {
        fileInfo = new FileInfo("OpenCsvBehaviorTestFile.csv"); // Example of a valid file path and extension
        saveThread = new SaveThread<>(fileInfo, new ArrayList<>());
    }

    @Test
    public void testSaveThreadInvalidExtension() {
        FileInfo wrongInfo = new FileInfo("test.invalid");
        SaveThread<Object> wrongThread = new SaveThread<>(wrongInfo, new ArrayList<>());

        // Test for InvalidExtensionException
        InvalidExtensionException exception = assertThrows(InvalidExtensionException.class, () -> {
            // Directly call the method without Platform.runLater
            wrongThread.call();
        });

        assertEquals("File type not supported.\nOnly open *.bin & *.csv", exception.getMessage());
    }

    @Test
    public void testCallCsv() {
        assertDoesNotThrow(() -> {
            // Directly call the method without Platform.runLater
            Void result = saveThread.call(); // Execute the call method, which normally runs in the thread
            assertNull(result); // Ensure the result is null as expected
        });
    }

    @Test
    public void testSaveThreadBinExtension() {
        FileInfo binInfo = new FileInfo("test.bin");
        SaveThread<Object> binThread = new SaveThread<>(binInfo, new ArrayList<>());

        assertDoesNotThrow(() -> {
            // Directly call the method to test .bin extension handling
            Void result = binThread.call(); // Execute the call method, which normally runs in the thread
            assertNull(result); // Ensure the result is null as expected
        });
    }
}
