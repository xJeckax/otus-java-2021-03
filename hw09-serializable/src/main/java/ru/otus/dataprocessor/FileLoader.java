package ru.otus.dataprocessor;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import ru.otus.model.Measurement;

import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;

public class FileLoader implements Loader {
    private final String fileName;

    public FileLoader(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    @Override
    public List<Measurement> load() {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(fileName).getFile());

        Gson gson = new Gson();
        List<Measurement> measurementList = null;
        try (JsonReader reader = new JsonReader(new FileReader(file))) {
            Measurement[] measurements = gson.fromJson(reader, Measurement[].class);
            measurementList = Arrays.asList(measurements);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return measurementList;
    }
}
