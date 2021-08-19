package ru.otus.dataprocessor;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.Map;

public class FileSerializer implements Serializer {
    private final String fileName;

    public FileSerializer(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void serialize(Map<String, Double> data) {
        try (OutputStreamWriter fileOutputStream = new OutputStreamWriter(new FileOutputStream(fileName))) {

            Gson gson = new Gson();

            Type typeOfMap = new TypeToken<Map<String, Double>>(){}.getType();
            String json = gson.toJson(data, typeOfMap);
            fileOutputStream.write(json);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //формирует результирующий json и сохраняет его в файл
    }
}
