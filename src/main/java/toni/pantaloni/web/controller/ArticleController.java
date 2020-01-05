package toni.pantaloni.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import toni.pantaloni.exception.ArticleNotFoundException;
import toni.pantaloni.exception.CategoryNotFoundException;
import toni.pantaloni.repository.dao.Article;
import toni.pantaloni.service.ArticleService;

import java.util.List;

@RestController
@RequestMapping(value = "/articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Article>> getAllArticles() {
        List<Article> articles = articleService.getAllArticles();
        return ResponseEntity.ok(articles);
    }

    @RequestMapping(value = "/{articleId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    Article getArticleById(@PathVariable Long articleId) throws ArticleNotFoundException {
        return articleService.getArticleById(articleId);
    }

    @RequestMapping(value = "/new", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody
    Article addNewArticle(@RequestBody Article newArticle) throws CategoryNotFoundException {
        return articleService.saveArticle(newArticle);
    }

}
