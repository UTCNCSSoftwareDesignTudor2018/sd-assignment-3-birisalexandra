package BlogApp.BlogApp.data.entity;

import javax.persistence.*;

@Entity
@Table(name="article")
public class Article {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "article_id")
    private Integer articleId;

    @Column
    private String title;

    @Column
    private String abstractPart;

    @Column
    private String body;

    @OneToOne
    @JoinColumn(name="writer_id")
    private Writers writer;

    public Article() {}

    public Article(Integer articleId, String title, String abstractPart, String body, Writers writer) {
        this.articleId = articleId;
        this.title = title;
        this.abstractPart = abstractPart;
        this.body = body;
        this.writer = writer;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAbstractPart() {
        return abstractPart;
    }

    public void setAbstractPart(String abstractPart) {
        this.abstractPart = abstractPart;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Writers getWriter() {
        return writer;
    }

    public void setWriter(Writers writer) {
        this.writer = writer;
    }

    @Override
    public String toString() {
        return "Article{" +
                "articleId=" + articleId +
                ", title='" + title + '\'' +
                ", abstractPart='" + abstractPart + '\'' +
                ", body='" + body + '\'' +
                ", writer=" + writer +
                '}';
    }
}