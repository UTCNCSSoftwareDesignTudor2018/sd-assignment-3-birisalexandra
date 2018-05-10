package BlogApp.BlogApp.business;

import BlogApp.BlogApp.data.entity.Article;
import BlogApp.BlogApp.data.repository.ArticleJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleService {

    @Autowired
    ArticleJpaRepository articleJpaRepository;

    public void insertNewArticle(Article a) {
        articleJpaRepository.save(a);
    }

    public void deleteArticle(Article a) {
        articleJpaRepository.delete(a);
    }

    public void updateArticle(Article a, String body) {
        a.setBody(body);
        articleJpaRepository.save(a);
    }

    public List<Article> getAllArticles() {
        return articleJpaRepository.findAll();
    }

    public Article findByTitle(String title) {
        return articleJpaRepository.findByTitle(title);
    }
}
