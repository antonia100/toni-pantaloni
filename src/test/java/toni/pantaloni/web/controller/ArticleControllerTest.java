package toni.pantaloni.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import toni.pantaloni.PantaloniApplication;
import toni.pantaloni.repository.ArticleRepository;
import toni.pantaloni.repository.CategoryRepository;
import toni.pantaloni.repository.dao.Article;
import toni.pantaloni.repository.dao.Category;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PantaloniApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ArticleControllerTest {

    private static final String ARTICLES_URI = "/articles";
    private static final String ADD_NEW_ARTICLE_URI = ARTICLES_URI + "/new";

    private static final String ARTICLE_TITLE = "Test Article title";
    private static final String ARTICLE_TITLE_JSON_PATH_VALUE = "title";
    private static final String ARTICLE_TITLE_JSON_ARRAY_PATH_VALUE = "[0]." + ARTICLE_TITLE_JSON_PATH_VALUE;
    private static final String ARTICLE_ID_URI_QUERY = "/1";
    private static final String NON_EXISTING_ARTICLE_ID_URI_QUERY = "/2";

    private static final String CATEGORY_TITLE = "Category title";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void givenArticles_whenGetArticles_thenShouldReturnCorrect() throws Exception {
        addTestArticle();

        mvc.perform(get(ARTICLES_URI))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath(ARTICLE_TITLE_JSON_ARRAY_PATH_VALUE, is(ARTICLE_TITLE)));
    }

    @Test
    public void givenArticle_whenGettingArticleById_thenShouldReturnCorrect() throws Exception {
        addTestArticle();

        mvc.perform(get(ARTICLES_URI + ARTICLE_ID_URI_QUERY))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath(ARTICLE_TITLE_JSON_PATH_VALUE, is(ARTICLE_TITLE)));
    }

    @Test
    public void givenNonExistingArticleId_whenGettingArticleById_thenShouldReturnNotFound() throws Exception {
        addTestArticle();

        mvc.perform(get(ARTICLES_URI + NON_EXISTING_ARTICLE_ID_URI_QUERY))
                .andExpect(status().isNotFound());
    }

    @Test
    public void givenValidArticle_whenAddingNew_thenShouldReturnOk() throws Exception {

        Article newArticle = new Article();
        newArticle.setTitle(ARTICLE_TITLE);

        Category category = addDummyCategory();
        newArticle.setCategory(category);

        mvc.perform(post(ADD_NEW_ARTICLE_URI)
                .content(asJsonString(newArticle))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath(ARTICLE_TITLE_JSON_PATH_VALUE, is(ARTICLE_TITLE)));
    }

    @Test
    public void givenArticleWithMissingCategory_whenAddingNew_thenShouldReturnCategoryNotFound() throws Exception {

        Article newArticle = new Article();
        newArticle.setTitle(ARTICLE_TITLE);

        mvc.perform(post(ADD_NEW_ARTICLE_URI)
                .content(asJsonString(newArticle))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isNotFound());
    }

    private void addTestArticle() {
        Article testArticle = new Article();
        testArticle.setTitle(ARTICLE_TITLE);
        articleRepository.save(testArticle);
    }

    private Category addDummyCategory() {
        Category category = new Category();
        category.setTitle(CATEGORY_TITLE);
        categoryRepository.save(category);
        return category;
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
