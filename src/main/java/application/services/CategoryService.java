package application.services;

import api.interfaces.ICategoryService;
import com.google.gson.*;
import application.model.Category;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CategoryService implements ICategoryService {
    private URL url = new URL("http://localhost:8090/category");
    private Gson jsonConverter = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create();

    public CategoryService() throws MalformedURLException {

    }

    public List<Category> getAll() {
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            JsonArray jsonArray = jsonConverter.fromJson(new InputStreamReader(connection.getInputStream()), JsonArray.class);

            List<Category> categories = new ArrayList<>();

            for (JsonElement element : jsonArray) {
                categories.add(jsonConverter.fromJson(element, Category.class));
            }


            return categories;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
