package com.voidblue.finalexam;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import com.voidblue.finalexam.Model.Article;
import com.voidblue.finalexam.Utils.ResultMessage;
import org.hamcrest.collection.IsEmptyCollection;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ArticleTest {
    @Autowired
    TestRestTemplate restTemplate;
    private static final String PATH = "/api/article";

    @Test
    public void get(){
        int id = 1;

        Article article = restTemplate.getForObject(PATH + "/" + id, Article.class);

        assertThat(article.getId(), is(1));
        validateArticle(article, "testid", "테스트",  "제목", "2000-01-01 00:00:00.0");
    }

    @Test
    public void list(){
        List<Article> articles =  restTemplate.getForObject(PATH + "/list", List.class);
        assertThat(articles, not(IsEmptyCollection.empty()));
    }

    @Test
    public void create(){
        //지금 create의 반환값이 ResultMessage인데 이거를 Article로 변경하지 않으면 생성하고 바로 수정하는 테스트는 안되고(id셋팅을 무시함)
        Article articleForCreate = getArticle();
        ResultMessage resultMessage = restTemplate.postForObject(PATH + "/", articleForCreate, ResultMessage.class);
        Article createdArticle = restTemplate.getForObject(PATH + "/" + articleForCreate.getId(), Article.class);

        assertThat(resultMessage.getResultCode(), is(200));
        validateArticle(createdArticle, articleForCreate.getAuthor(), articleForCreate.getText(), articleForCreate.getTitle(), createdArticle.getTimeCreated());

    }

    @Test
    public void update(){
        //넣은 id값을 가져오는 것이 지금은 불가능, 기존에 있는 것만 수정해보기
        Article articleForCreate = getArticle();
        restTemplate.postForObject(PATH  + "/", articleForCreate, ResultMessage.class);
        Article articleForUpdate = restTemplate.getForObject(PATH + "/" + 1, Article.class);

        articleForUpdate.setTitle("수정됨");

        restTemplate.put(PATH  + "/", articleForUpdate);
        Article updatedArticle = restTemplate.getForObject(PATH + "/" + 1, Article.class);

        validateArticle(updatedArticle, articleForCreate.getAuthor(), articleForCreate.getText(), "수정됨", articleForCreate.getTimeCreated());

        articleForUpdate.setTitle("제목");
        restTemplate.put(PATH  + "/", articleForUpdate);
        updatedArticle = restTemplate.getForObject(PATH + "/" + 1, Article.class);

        validateArticle(updatedArticle, articleForCreate.getAuthor(), articleForCreate.getText(), "제목", articleForCreate.getTimeCreated());
    }

    @Test
    public void delete(){
        Article articleForCreate = getArticle();
        ResultMessage resultMessage = restTemplate.postForObject(PATH + "/", articleForCreate, ResultMessage.class);
        Article createdArticle = restTemplate.getForObject(PATH + "/" + articleForCreate.getId(), Article.class);

        restTemplate.delete(PATH + "/" + createdArticle.getId());
        assertThat(restTemplate.getForObject(PATH + "/" + createdArticle.getId(), Article.class), is(nullValue()));



    }

    private Article getArticle() {
        Article article = new Article();
        article.setId(1);
        article.setAuthor("testid");
        article.setText("테스트");
        article.setTitle("제목");
        article.setTimeCreated("2000-01-01 00:00:00.0");
        return article;
    }

    private void validateArticle(Article article, String author, String text, String title, String timeCreated) {
        assertThat(article.getAuthor(), is(author));
        assertThat(article.getText(), is(text));
        assertThat(article.getTitle(), is(title));
    }

}
