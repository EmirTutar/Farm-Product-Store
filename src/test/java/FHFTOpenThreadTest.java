import static java.lang.Thread.sleep;
import static org.junit.jupiter.api.Assertions.*;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.app.fileHandling.fileThreads.OpenThread;
import org.app.fileHandling.FileInfo;
import org.app.validation.ioExceptions.InvalidExtensionException;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class FHFTOpenThreadTest {

    private OpenThread<Object> openThread;
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
        fileInfo = new FileInfo("DataFraApp/OpenCsvBehaviorTestFile.csv"); // Example of a valid file path and extension
        openThread = new OpenThread<>(fileInfo);
    }

    @Test
    public void testOpenThreadInvalidExtension() {
        FileInfo wrongInfo = new FileInfo("test.invalid");
        OpenThread<Object> wrongThread = new OpenThread<>(wrongInfo);

        // Test for InvalidExtensionException
        InvalidExtensionException exception = assertThrows(InvalidExtensionException.class, () -> {
            // Directly call the method without Platform.runLater
            wrongThread.call();
        });

        assertEquals("File type not supported.\nOnly open *.bin & *.csv", exception.getMessage());
    }

    @Test
    public void testCall() {
        assertDoesNotThrow(() -> {
            Platform.runLater(() -> {
                try {
                    ArrayList<Object> result = openThread.call(); // Execute the call method, which normally runs in the thread
                    assertNotNull(result); // Ensure the result is not null
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        });
    }

    @Test
    public void testOpenThreadBinExtension() {
        FileInfo binInfo = new FileInfo("DataFraApp/Database/products.bin");
        OpenThread<Object> binThread = new OpenThread<>(binInfo);

        assertDoesNotThrow(() -> {
            // Directly call the method to test .bin extension handling
            ArrayList<Object> result = binThread.call(); // Execute the call method, which normally runs in the thread
            assertNotNull(result); // Ensure the result is not null
        });
    }
}
