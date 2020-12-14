package io.open;

import dataModels.dataFormats.ParseItems;
import validations.ioExceptions.InvalidFileException;

import java.io.*;
import java.util.ArrayList;

public class OpenCsvBehavior extends OpenAbstract{
    @Override
    public <T> ArrayList<T> read(File file) {
        ArrayList<T> items = new ArrayList<>();
        try {
            FileReader fileReader = new FileReader(file.getPath());
            BufferedReader reader = new BufferedReader(fileReader);
            String line;
            while((line =reader.readLine()) != null){
                    items.add((T) ParseItems.parseItem(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return items;
    }
}
