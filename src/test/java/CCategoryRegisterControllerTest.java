import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.app.controllers.CategoryRegisterController;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class CCategoryRegisterControllerTest extends ApplicationTest {

    private CategoryRegisterController controller;

    @Override
    public void start(Stage stage) throws IOException {
        // Lade die FXML-Datei für den CategoryRegisterController
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/app/category.fxml")); // Korrigieren Sie den Pfad zur FXML-Datei
        AnchorPane root = loader.load();
        controller = loader.getController();
        stage.setScene(new Scene(root));
        stage.show();
        stage.toFront();
    }

    @Test
    public void testControllerInitialization() {
        assertNotNull(controller, "Controller sollte nicht null sein");
        assertNotNull(controller.categoryTextField, "TextField für Kategorien sollte nicht null sein");
        assertNotNull(controller.subCategoryTextField, "TextField für Unterkategorien sollte nicht null sein");
        assertNotNull(controller.subCategoryListView, "ListView für Unterkategorien sollte nicht null sein");
        assertNotNull(controller.addNewCategoryButton, "Button für das Hinzufügen von Kategorien sollte nicht null sein");
        assertNotNull(controller.cancelButton, "Button für das Abbrechen sollte nicht null sein");
        assertNotNull(controller.addNewSubCategoryButton, "Button für das Hinzufügen von Unterkategorien sollte nicht null sein");
    }

    @Test
    public void testAddNewCategoryFunctionality() {
        // Eingabe in das Textfeld für Kategorie
        clickOn("#categoryTextField").write("Neuekategorie");
        clickOn("#subCategoryTextField").write("Neueunterkategorie");
        clickOn("#addNewCategoryButton"); // Annahme, dass der Button die fx:id "addNewCategoryButton" hat

        WaitForAsyncUtils.waitForFxEvents();

        assertTrue(controller.CATEGORY_COLLECTION.getCategories().contains("Neuekategorie"),
                "Kategorie sollte zur Liste hinzugefügt worden sein");
    }

    @Test
    public void testAddEmptyCategory() {
        clickOn("#categoryTextField").write("");
        clickOn("#addNewCategoryButton");

        WaitForAsyncUtils.waitForFxEvents();

        assertFalse(controller.CATEGORY_COLLECTION.getCategories().contains(""),
                "Leere Kategorie sollte nicht zur Liste hinzugefügt werden");
    }

    @Test
    public void testAddInvalidCategory() {
        clickOn("#categoryTextField").write("123InvalidCategory");
        clickOn("#addNewCategoryButton");

        WaitForAsyncUtils.waitForFxEvents();

        assertFalse(controller.CATEGORY_COLLECTION.getCategories().contains("123InvalidCategory"),
                "Ungültige Kategorie sollte nicht zur Liste hinzugefügt werden");
    }

    @Test
    public void testAddInvalidSubCategory() {
        // Zuerst Kategorie hinzufügen
        clickOn("#categoryTextField").write("NeueKategorie");
        clickOn("#addNewCategoryButton");

        // Versuche eine ungültige Unterkategorie hinzuzufügen
        clickOn("#subCategoryTextField").write("123InvalidSubCategory");
        clickOn("#addNewSubCategoryButton");

        WaitForAsyncUtils.waitForFxEvents();

        assertFalse(controller.subCategories.contains("123InvalidSubCategory"),
                "Ungültige Unterkategorie sollte nicht zur Liste hinzugefügt werden");
    }

    @Test
    public void testCancelCategoryCreation() {
        // Simuliere den Klick auf den Abbruch-Button
        clickOn("#cancelButton"); // Annahme, dass der Button die fx:id "cancelButton" hat

        // Überprüfe, ob das Fenster geschlossen wurde
        WaitForAsyncUtils.waitForFxEvents();

        // Es ist schwierig, das Schließen des Fensters direkt zu testen,
        // aber wir können überprüfen, ob die Anwendung weiter läuft und keine Fehler auftreten.
        assertFalse(controller.parentPane.getScene().getWindow().isShowing(), "Das Fenster sollte geschlossen werden");
    }

}
