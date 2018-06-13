package com.voidblue.finalexam;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import com.voidblue.finalexam.Model.Article;
import com.voidblue.finalexam.Utils.ResultMessage;
import com.voidblue.finalexam.Utils.ResultMessageFactory;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.hamcrest.collection.IsEmptyCollection;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ArticleTest {
    @Autowired
    TestRestTemplate restTemplate;
    private static final String PATH = "/api/article";
    String jwtString;
    HttpHeaders httpHeaders;
    @Before
    public void setup(){
        httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        jwtString = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("issueDate", System.currentTimeMillis())
                .setSubject("")
                .claim("id", "testid")
                .claim("nickname", "")
                .signWith(SignatureAlgorithm.HS512, "portalServiceFinalExam")
                .compact();
        httpHeaders.add("token", jwtString);

    }

    @Test
    public void get(){
        int id = 0;

        Article article = restTemplate.getForObject(PATH + "/" + id, Article.class);

        assertThat(article.getId(), is(0));
        validateArticle(article, "testid", "내용",  "제목");
    }

    @Test
    public void list(){
        List<Article> articles =  restTemplate.getForObject(PATH + "/list", List.class);
        assertThat(articles, not(IsEmptyCollection.empty()));
    }

    @Test
    public void create(){
        Article articleForCreate = getArticle();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        System.out.println(simpleDateFormat.format(new Date()));
        articleForCreate.setTimeCreated(simpleDateFormat.format(new Date()));

        HttpEntity<ResultMessage> entity = new HttpEntity(articleForCreate, httpHeaders);

        ResponseEntity<ResultMessage> resultMessage = restTemplate.exchange(PATH , HttpMethod.POST, entity, ResultMessage.class);

        Article createdArticle = restTemplate.getForObject(PATH + "/" + articleForCreate.getId(), Article.class);



        System.out.println(resultMessage);
        assertThat(resultMessage.getBody().getResultCode(), is(200));
        validateArticle(createdArticle, articleForCreate.getAuthor(), articleForCreate.getText(), articleForCreate.getTitle());
    }

    @Test
    public void update(){
        create();
        Article articleForUpdate = getArticle();

        articleForUpdate.setTitle("수정됨");
        HttpEntity entity = new HttpEntity(articleForUpdate, httpHeaders);
        ResponseEntity<ResultMessage> resultMessage = restTemplate.exchange(PATH , HttpMethod.PUT, entity, ResultMessage.class);
        Article updatedArticle = restTemplate.getForObject(PATH + "/" + articleForUpdate.getId(), Article.class);

        assertThat(resultMessage.getBody().getResultCode(),is(200));
        validateArticle(updatedArticle, articleForUpdate.getAuthor(), articleForUpdate.getText(), "수정됨");

    }

    @Test
    public void delete(){
        create();
        Article articleForDelete = getArticle();

        HttpEntity<ResultMessage> entity = new HttpEntity(null, httpHeaders);
        ResponseEntity<ResultMessage> resultMessage = restTemplate.exchange(PATH + "/" + articleForDelete.getId(), HttpMethod.DELETE, entity, ResultMessage.class);
        assertThat(resultMessage.getBody().getResultCode(),is(200));
        assertThat(restTemplate.getForObject(PATH + "/" + articleForDelete.getId(), Article.class), is(nullValue()));
    }


    @Test
    public void notoken(){
        Article article = getArticle();
        ResponseEntity<ResultMessage> resultMessage = restTemplate.postForEntity(PATH, article, ResultMessage.class);

        assertThat(ResultMessageFactory.notLogined(), is(resultMessage.getBody()));
    }

    @Test
    public void notOwner(){
        Article article = getArticle();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        jwtString = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("issueDate", System.currentTimeMillis())
                .setSubject("")
                .claim("id", "다른아이디")
                .claim("nickname", "")
                .signWith(SignatureAlgorithm.HS512, "portalServiceFinalExam")
                .compact();
        headers.add("token", jwtString);


        HttpEntity entity = new HttpEntity(article, headers);
        ResponseEntity<ResultMessage> resultMessage = restTemplate.exchange(PATH + "/" + article.getId() , HttpMethod.DELETE, entity, ResultMessage.class);

        assertThat(ResultMessageFactory.noAuthority(), is(resultMessage.getBody()));
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

    private void validateArticle(Article article, String author, String text, String title) {
        assertThat(article.getAuthor(), is(author));
        assertThat(article.getText(), is(text));
        assertThat(article.getTitle(), is(title));
    }

}
