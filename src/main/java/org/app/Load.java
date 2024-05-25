package org.app;

import org.app.data.dataCollection.CategoryCollection;
import org.app.data.dataCollection.TableViewCollection;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.app.validation.Alerts;
import java.io.IOException;

public class Load {

    /** Loads an FXML file */
    public static void window(String FXMLFilepath, String title, Stage stage){
        try {
            FXMLLoader loader = new FXMLLoader(Load.class.getResource(FXMLFilepath));
            Parent root = loader.load();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.getScene().getWindow().centerOnScreen();
            stage.show();
        } catch (IOException e){ e.printStackTrace(); }
    }

    /** Opens a new window for category creation */
    public static void openCategoryPopup(){
        try {
            Load.window("category.fxml", "Edit Category", new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /** Asks the user to save changes before the program exits */
    public static void exit(Stage stage){
        TableViewCollection collection = TableViewCollection.getINSTANCE();
        if(collection.isModified()){
            boolean response = Alerts.confirm("Do you want to save all changes?");
            if(response){
                collection.saveData();
            } else {
                Alerts.success("Changes were not saved");
            }
        }
        CategoryCollection categoryCollection = CategoryCollection.getInstance();
        if(categoryCollection.isModified()) categoryCollection.saveCategories();
        stage.close();
    }
}
