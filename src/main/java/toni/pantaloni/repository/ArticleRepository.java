package toni.pantaloni.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import toni.pantaloni.repository.dao.Article;

import java.util.List;

@Component
public interface ArticleRepository extends CrudRepository<Article, Long> {

    List<Article> getArticleByTitleContaining(String partialTitle);
}
