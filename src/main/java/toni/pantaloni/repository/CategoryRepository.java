package toni.pantaloni.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import toni.pantaloni.repository.dao.Category;

@Component
public interface CategoryRepository extends CrudRepository<Category, Long> {

    boolean existsByTitle(String title);

}
