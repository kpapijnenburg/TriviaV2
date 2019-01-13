package api.category;

import api.interfaces.ICategoryService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import application.model.Category;

import java.util.List;

@RestController
public class CategoryController implements ICategoryService {
    private CategoryRepository repository = new CategoryRepository(new CategoryContext());

    @RequestMapping(value = "/category" , method = RequestMethod.GET)
    public List<Category> getAll() {
        return repository.getAll();
    }
}
