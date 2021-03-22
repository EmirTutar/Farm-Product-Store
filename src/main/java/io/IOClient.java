package io;

import dataModels.data.Component;
import dataModels.data.ConfigItem;
import dataModels.dataCollection.ListViewCollection;
import dataModels.dataCollection.TableViewCollection;
import io.fileThreads.OpenThread;
import io.fileThreads.SaveThread;
import javafx.concurrent.WorkerStateEvent;
import javafx.scene.control.Label;
import org.app.controllers.LoginController;
import validations.Alerts;


import java.util.ArrayList;

public class IOClient<T> {

    private ArrayList<T> listToWrite;
    private FileInfo fileInfo;
    private OpenThread<T> openThread;
    private SaveThread<T> saveThread;
    private Label label;
    public IOClient(FileInfo fileInfo, ArrayList<T> listToWrite) {
        this.fileInfo = fileInfo;
        this.listToWrite = listToWrite;
        this.saveThread = new SaveThread<>(this.fileInfo, this.listToWrite);
    }

    public IOClient(FileInfo fileInfo,Label lbl) {
        this.fileInfo = fileInfo;
        this.label = lbl;
        this.openThread = new OpenThread<>(this.fileInfo);
    }

    public IOClient(FileInfo fileInfo){
        this.fileInfo = fileInfo;
        this.openThread = new OpenThread<>(this.fileInfo);
    }

    public void runSaveThread() {
        saveThread.setOnSucceeded(this::saveDone);
        saveThread.setOnFailed(this::saveFailed);
        Thread th = new Thread(saveThread);
        th.setDaemon(true);
        th.start();
    }

    private void saveDone(WorkerStateEvent e) {
        saveThread.call();
    }

    private void saveFailed(WorkerStateEvent event) {
        Throwable e = event.getSource().getException();
        Alerts.warning(e.getMessage());
    }

    public void runOpenThread() {
        openThread.setOnSucceeded(this::openDone);
        openThread.setOnFailed(this::openFailed);
        Thread th = new Thread(openThread);
        th.setDaemon(true);
        th.start();
    }

    private void openDone(WorkerStateEvent e) {

        if (LoginController.getCurUser().equals("Admin")) {
            ArrayList<Component> list = (ArrayList<Component>) openThread.call();
            TableViewCollection.getComponents().clear();
            TableViewCollection.setComponents(list, true);
        } else {
            ArrayList<ConfigItem> list = (ArrayList<ConfigItem>) openThread.call();
            ListViewCollection.loadingConfig(list,this.label);
        }
    }

    private void openFailed(WorkerStateEvent event) {
        Throwable e = event.getSource().getException();
        Alerts.warning(e.getMessage());
    }

}