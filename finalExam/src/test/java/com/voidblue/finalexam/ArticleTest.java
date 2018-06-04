package com.voidblue.finalexam;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import com.voidblue.finalexam.Model.Article;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

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
        assertThat(article.getAuthor(), is("testid"));
        assertThat(article.getText(), is("테스트"));
        assertThat(article.getTimeCreated(), is("2000-01-01 00:00:00.0"));
        assertThat(article.getTitle(), is("테스트") );
    }
}
