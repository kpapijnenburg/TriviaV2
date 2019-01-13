package api.category;

import application.model.Category;

import java.util.List;

public interface ICategoryContext {
    List<Category> getAll();
}
