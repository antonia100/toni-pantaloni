package toni.pantaloni.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import toni.pantaloni.exception.CategoryNotFoundException;
import toni.pantaloni.repository.dao.Category;
import toni.pantaloni.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping(value = "/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @RequestMapping(value = "/{categoryId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    Category getCategoryById(@PathVariable Long categoryId) throws CategoryNotFoundException {
        return categoryService.getCategoryById(categoryId);
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    Category addNewCategory(@RequestBody Category newCategory) {
        return categoryService.saveNewCategory(newCategory);
    }

}
