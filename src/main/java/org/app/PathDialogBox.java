package org.app;

import javafx.scene.control.TextInputDialog;
import org.app.validation.ioExceptions.*;

import java.io.File;
import java.util.Optional;

public class PathDialogBox {

    private TextInputDialog pathDialog = new TextInputDialog();

    public String getPathToSave(){
        pathDialog.setTitle("Save");
        pathDialog.setHeaderText("Give the file a name with .csv or .bin as the file extension: ");
        pathDialog.setContentText("File name: ");
        Optional<String> path = pathDialog.showAndWait();
        return path.orElse(null);
    }

    public String getPathToOpen(){
        pathDialog.setTitle("Open");
        pathDialog.setHeaderText("What is the name of the file you want to open?\nOnly open files from DataFraApp/");
        pathDialog.setContentText("File name: ");
        Optional<String> path = pathDialog.showAndWait();
        return path.orElse(null);
    }

    public void extensionCheck(String path) throws InvalidExtensionException {
        if(!path.contains(".")){
            throw new InvalidExtensionException("The file is missing an extension.");
        }
    }

    public void fileNotFound(String path) throws FileDontExistsException {
        if(!new File(path).exists()){
            throw new FileDontExistsException("The file: " + path + " was not found!");
        }
    }

    public void nullPathHandling(String path) throws NullPointerException{
        if(path.isBlank() || path.isEmpty()) {
            throw new NullPointerException("No file was provided.");
        }
    }
}
