package util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class JsonDB {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static final String DATA_DIR = "SistemaHorarios/data/";
    // SistemaHorarios\data\
    static {
        System.out.println("JsonDB using data directory: " + new File(DATA_DIR).getAbsolutePath());
    }


    public static synchronized <T> void save(String fileName, List<T> data) {
        File directory = new File(DATA_DIR);
        if (!directory.exists()) directory.mkdirs();
        
        try (FileWriter writer = new FileWriter(DATA_DIR + fileName)) {
            gson.toJson(data, writer);
        } catch (IOException e) {
            System.err.println("Error guardando JSON: " + e.getMessage());
        }
    }

    public static <T> List<T> load(String fileName, Class<T> clazz) {
        File file = new File(DATA_DIR + fileName);
        if (!file.exists()) return new ArrayList<>();
        if (file.length() == 0) return new ArrayList<>();

        try (Reader reader = new FileReader(file)) {
            Type type = TypeToken.getParameterized(List.class, clazz).getType();
            return gson.fromJson(reader, type);
        } catch (IOException | JsonSyntaxException e) {
            System.err.println("Error cargando JSON, devolviendo lista vacía: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}