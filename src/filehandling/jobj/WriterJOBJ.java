package filehandling.jobj;

import dataModels.Items;
import filehandling.WriterAbstract;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class WriterJOBJ extends WriterAbstract {
    private ArrayList<Items> items;
    private File filePath;
    public WriterJOBJ(ArrayList<Items> items,File filePath){
        this.items = items;
        this.filePath = filePath;
    }
    @Override
    public void write(ArrayList<Items> items, File filePath) {
        try{
            FileOutputStream file = new FileOutputStream(filePath);
            ObjectOutputStream objectOut = new ObjectOutputStream(file);
            for(Items item:items){
                objectOut.writeObject(item);
            }
            objectOut.close();
        }catch (IOException e){
            e.getStackTrace();
        }
    }

    @Override
    protected Void call() {
        try{
            Thread.sleep(2000);
        }catch (InterruptedException ignored){}
        write(items,filePath);
        return null;
    }
}
