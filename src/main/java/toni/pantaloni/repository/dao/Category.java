package toni.pantaloni.repository.dao;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

/**
 * The persistence class for the categories database table.
 */
@Entity(name = "Category")
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue
    private Long id;
    @Column(unique = true)
    @Size(min = 3, max = 25)
    private String title;
    @OneToMany(
            cascade = CascadeType.ALL
//            orphanRemoval = true
    )
    @JoinColumn(name = "category_id")
    private List<Article> articles;

    public Category() {
    }

    public Category(String title){
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }
}
