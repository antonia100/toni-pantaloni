package toni.pantaloni.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import toni.pantaloni.exception.CategoryNotFoundException;
import toni.pantaloni.repository.CategoryRepository;
import toni.pantaloni.repository.dao.Category;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public Category getCategoryById(Long id) throws CategoryNotFoundException {
       Optional<Category> category = categoryRepository.findById(id);
       if(category.isPresent()){
           return category.get();
       }

       throw new CategoryNotFoundException();
    }

    public List<Category> getAllCategories(){
        return (List<Category>) categoryRepository.findAll();
    }

    public Category saveNewCategory(Category newCategory) {
        return categoryRepository.save(newCategory);
    }
}
