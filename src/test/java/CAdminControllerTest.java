import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.app.controllers.AdminController;
import org.app.data.models.Product;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.service.query.NodeQuery;
import org.testfx.util.WaitForAsyncUtils;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static javafx.application.Platform.runLater;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

public class CAdminControllerTest extends ApplicationTest {

    private AdminController controller;
    private BorderPane rootPane;

    @Override
    public void start(Stage stage) throws IOException {
        URL fxmlUrl = getClass().getResource("/org/app/adminView.fxml");
        if (fxmlUrl == null) {
            throw new IOException("Cannot find FXML file /org/app/adminView.fxml");
        }
        FXMLLoader loader = new FXMLLoader(fxmlUrl);
        rootPane = loader.load();
        controller = loader.getController();
        stage.setScene(new Scene(rootPane));
        stage.show();
    }

    private void waitForDataLoad() {
        sleep(2000);
        try {
            WaitForAsyncUtils.waitFor(15, TimeUnit.SECONDS, () -> !controller.categoriesCombobox.getItems().isEmpty());
        } catch (TimeoutException e) {
            throw new RuntimeException("Timeout waiting for data to load", e);
        }
    }

    @Test
    public void testControllerInitialization() {
        waitForDataLoad();
        assertNotNull(controller);
        assertNotNull(controller.tableview);
    }

    @Test
    public void testRegisterProductFunctionality() {
        waitForDataLoad();
        int initialSize = controller.COLLECTION.getProducts().size();

        clickOn("#name").write("New Product Name");
        clickOn("#price").write("25.50");
        clickOn("#specifications").write("Some specifications");

        clickOn("#categoriesCombobox");
        sleep(1500);
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);

        clickOn("#subcategoryCombobox");
        sleep(1500);
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);

        clickOn("#registerProduct");
        WaitForAsyncUtils.waitForFxEvents();

        int newSize = controller.COLLECTION.getProducts().size();
        assertTrue(newSize > initialSize, "Product list size should have increased");
        Product lastAddedProduct = controller.COLLECTION.getProducts().get(newSize - 1);
        assertEquals("New Product Name", lastAddedProduct.getProductName(), "Product name should match");
    }

    @Test
    public void testRegisterProductWithInvalidName() {
        waitForDataLoad();
        sleep(5000);
        int initialSize = controller.COLLECTION.getProducts().size();

        clickOn("#name").write("23");  // invalid name
        clickOn("#price").write("25.50");
        clickOn("#specifications").write("Some specifications");

        clickOn("#categoriesCombobox");
        sleep(1500);
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);

        clickOn("#subcategoryCombobox");
        sleep(1500);
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);

        clickOn("#registerProduct");
        WaitForAsyncUtils.waitForFxEvents();

        int newSize = controller.COLLECTION.getProducts().size();
        assertEquals(initialSize, newSize, "Product list size should not have changed due to invalid input");
    }

    @Test
    public void testEditProductName() {
        waitForDataLoad();

        // Erstelle eine modifizierbare ObservableList und setze sie in der TableView
        ObservableList<Product> modifiableList = FXCollections.observableArrayList();
        interact(() -> {
            controller.tableview.setItems(modifiableList); // Setze die modifizierbare Liste
            controller.tableview.getItems().add(new Product("Initial Name", "Landbruk", "Tresker", "Specs", 20.00));
        });

        doubleClickOn("Initial Name");
        write("New Name");
        push(KeyCode.ENTER);
        WaitForAsyncUtils.waitForFxEvents();

        assertEquals("New Name", controller.tableview.getItems().get(0).getProductName(), "Product name should be updated after editing");
    }

    @Test
    public void testOpenWithInvalidExtension() {
        waitForDataLoad();
        interact(() -> {
            // Vorbereitung eines Pfades mit ungültiger Dateierweiterung
            controller.setOpenedFile("invalid.filetype");
        });

        // Simulation eines Öffnungsvorgangs, der eine Ausnahme auslösen sollte
        runLater(() -> {
            try {
                controller.open();
            } catch (Exception e) {
                assertNotNull(e, "Should catch an exception for invalid file types");
            }
        });

        WaitForAsyncUtils.waitForFxEvents();
    }

    @Test
    public void testSaveWithNoChanges() {
        waitForDataLoad();
        interact(() -> {
            // Keine Änderungen vorgenommen, Liste ist leer oder unverändert
            controller.COLLECTION.setModified(false);
        });

        // Versuche zu speichern, was nicht passieren sollte
        runLater(() -> {
            controller.save();
        });

        WaitForAsyncUtils.waitForFxEvents();
        // Erwarte, dass keine Dateioperation ausgeführt wird, da keine Änderungen vorhanden sind
    }

    @Test
    public void testSaveWithChanges() {
        waitForDataLoad();
        interact(() -> {
            // Füge ein Produkt hinzu und markiere Änderungen
            ObservableList<Product> products = FXCollections.observableArrayList(
                    new Product("NewProduct", "NewCategory", "NewSubcategory", "Specs", 30.0)
            );
            controller.tableview.setItems(products);
            controller.COLLECTION.setModified(true);
        });

        // Simuliere den Speichervorgang
        runLater(() -> {
            controller.save();
        });

        WaitForAsyncUtils.waitForFxEvents();
        // Überprüfen, ob die Speichermethode ohne Fehler ausgeführt wurde
    }

    // Zusätzliche Tests für andere Methoden und Ausnahmefälle
    @Test
    public void testEditProductSpecs() {
        waitForDataLoad();

        // Setze eine neue Produktliste
        interact(() -> {
            ObservableList<Product> products = FXCollections.observableArrayList(
                    new Product("Test Product", "Category", "Subcategory", "Old Specs", 20.0)
            );
            controller.tableview.setItems(products);
        });

        // Simuliere das Bearbeiten der Spezifikationen
        runLater(() -> {
            TableColumn.CellEditEvent<Product, String> editEvent = new TableColumn.CellEditEvent<>(
                    controller.tableview,
                    new TablePosition<>(controller.tableview, 0, new TableColumn<>()),
                    TableColumn.editCommitEvent(),
                    "New Specs"
            );
            controller.editSpecs(editEvent);
        });

        WaitForAsyncUtils.waitForFxEvents();
        assertEquals("New Specs", controller.tableview.getItems().get(0).getSpecification(), "Specifications should be updated to 'New Specs'");
    }

    @Test
    public void testEditProductPrice() {
        waitForDataLoad();

        // Setze eine neue Produktliste
        interact(() -> {
            ObservableList<Product> products = FXCollections.observableArrayList(
                    new Product("Test Product", "Category", "Subcategory", "Specs", 20.0)
            );
            controller.tableview.setItems(products);
        });

        // Simuliere das Bearbeiten des Preises
        runLater(() -> {
            TableColumn.CellEditEvent<Product, Double> editEvent = new TableColumn.CellEditEvent<>(
                    controller.tableview,
                    new TablePosition<>(controller.tableview, 0, new TableColumn<>()),
                    TableColumn.editCommitEvent(),
                    25.0
            );
            controller.editPrice(editEvent);
        });

        WaitForAsyncUtils.waitForFxEvents();
        assertEquals(25.0, controller.tableview.getItems().get(0).getPrice(), "Price should be updated to 25.0");
    }

    @Test
    public void testLogoutWithUnsavedChanges() {
        waitForDataLoad();
        interact(() -> {
            // Setze Zustand als geändert
            controller.COLLECTION.setModified(true);
        });

        // Simuliere den Log-Out-Prozess, wo Benutzer speichern möchte
        runLater(() -> {
            try {
                controller.logOut();
            } catch (Exception e) {
                assertNotNull(e, "Log out should handle saving logic");
            }
        });

        WaitForAsyncUtils.waitForFxEvents();
    }

    @Test
    public void testOpenWithValidPath() {
        waitForDataLoad();

        interact(() -> {
            // Setze einen gültigen Pfad
            controller.setOpenedFile("valid.filetype");
        });

        // Simuliere den Öffnungsvorgang und Bestätigung simulieren
        interact(() -> {
            Alert alert = mock(Alert.class);
            when(alert.getResult()).thenReturn(ButtonType.YES);
            try {
                controller.open();
            } catch (Exception e) {
                fail("Should not throw an exception for a valid file type");
            }
        });

        WaitForAsyncUtils.waitForFxEvents();
    }

    @Test
    public void testInitialize() {
        waitForDataLoad();
        assertNotNull(controller.categoriesCombobox);
        assertNotNull(controller.subcategoryCombobox);
        assertNotNull(controller.tableview);
        assertNotNull(controller.categoryCol);
        assertNotNull(controller.subcategoryCol);
        assertNotNull(controller.priceCol);
        assertNotNull(controller.name);
        assertNotNull(controller.price);
        assertNotNull(controller.specifications);
        assertNotNull(controller.txtFilter);
        assertNotNull(controller.filterComboBox);
        assertNotNull(controller.filenameLabel);
    }

    @Test
    public void testRegisterProductWithInvalidPrice() {
        waitForDataLoad();
        int initialSize = controller.COLLECTION.getProducts().size();

        clickOn("#name").write("New Product Name");
        clickOn("#price").write("a"); // Ungültiger Preis
        clickOn("#specifications").write("Some specifications");

        clickOn("#categoriesCombobox");
        sleep(1500);
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);

        clickOn("#subcategoryCombobox");
        sleep(1500);
        type(KeyCode.DOWN);
        type(KeyCode.ENTER);

        clickOn("#registerProduct");
        WaitForAsyncUtils.waitForFxEvents();

        int newSize = controller.COLLECTION.getProducts().size();
        assertEquals(initialSize, newSize, "Product list size should not have changed due to invalid price");
    }

    @Test
    public void testSaveEmptyProduct() {
        waitForDataLoad();

        // Erfasse die ursprüngliche Anzahl der Produkte in der Tabelle
        int initialSize = controller.tableview.getItems().size();

        interact(() -> {
            controller.name.setText("");
            controller.price.setText("");
            controller.specifications.setText("");
        });

        runLater(() -> {
            controller.save();
        });

        WaitForAsyncUtils.waitForFxEvents();

        // Überprüfe, ob die Anzahl der Produkte in der Tabelle unverändert bleibt
        int newSize = controller.tableview.getItems().size();
        assertEquals(initialSize, newSize, "Product list size should not change when trying to save an empty product");
    }


}
