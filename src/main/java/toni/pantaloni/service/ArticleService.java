package toni.pantaloni.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import toni.pantaloni.exception.ArticleNotFoundException;
import toni.pantaloni.exception.CategoryNotFoundException;
import toni.pantaloni.repository.ArticleRepository;
import toni.pantaloni.repository.CategoryRepository;
import toni.pantaloni.repository.dao.Article;

import java.util.List;
import java.util.Optional;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public Article getArticleById(Long articleId) throws ArticleNotFoundException {

        Optional<Article> article = articleRepository.findById(articleId);
        if (article.isPresent()) {
            return article.get();
        }

        throw new ArticleNotFoundException();
    }

    public List<Article> getAllArticles() {
        return (List<Article>) articleRepository.findAll();
    }

    public Article saveArticle(Article newArticle) throws CategoryNotFoundException {

        if (articleContainsValidCategory(newArticle)) {
            //TODO update category's articles
            return articleRepository.save(newArticle);
        }
        throw new CategoryNotFoundException();
    }

    private boolean articleContainsValidCategory(Article article){
        return articleHasCategory(article) && categorySuppliedInArticleExists(article);
    }

    private boolean articleHasCategory(Article article) {
        return article.getCategory() != null;
    }

    private boolean categorySuppliedInArticleExists(Article article) {
        return categoryRepository.existsByTitle(article.getCategory().getTitle());
    }

    //TODO implement in end point
    public List<Article> getArticlesByPartialTitleMatch(String partialTitle) {
        return articleRepository.getArticleByTitleContaining(partialTitle);
    }

}

