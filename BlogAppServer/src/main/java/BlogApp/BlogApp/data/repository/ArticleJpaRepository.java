package BlogApp.BlogApp.data.repository;

import BlogApp.BlogApp.data.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleJpaRepository extends JpaRepository<Article, Integer> {

    public Article findByTitle(String title);
}
