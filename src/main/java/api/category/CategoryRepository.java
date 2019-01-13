package api.category;

import application.model.Category;

import java.util.List;

public class CategoryRepository {

    private ICategoryContext context;

    public CategoryRepository(ICategoryContext context) {
        this.context = context;
    }

    public List<Category> getAll(){
        return context.getAll();
    }
}
