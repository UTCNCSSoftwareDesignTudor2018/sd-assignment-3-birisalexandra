package BlogApp.BlogApp.controller;

import BlogApp.BlogApp.business.ArticleService;
import BlogApp.BlogApp.data.entity.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ArticleController {

    @Autowired
    ArticleService articleService;

    public void insertArticle(Article a) {
        articleService.insertNewArticle(a);
    }

    public List<Article> getAllArticles() {
        return articleService.getAllArticles();
    }

    public Article findByTitle(String title) {
        return articleService.findByTitle(title);
    }

    public void deleteArticle(Article a) {
        articleService.deleteArticle(a);
    }
}
