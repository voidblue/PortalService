package com.voidblue.finalexam;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import com.voidblue.finalexam.Model.ThumbUp;
import com.voidblue.finalexam.Utils.ResultMessage;
import com.voidblue.finalexam.Utils.ResultMessageFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ThumbUpTest {

    private static final String PATH = "/api/like";
    @Autowired
    TestRestTemplate restTemplate;



    @Test
    public void get(){
        Integer id = 1;
        ThumbUp thumbUp = restTemplate.getForObject(PATH + "/" + id, ThumbUp.class);

        assertThat(thumbUp.getUser(), is("testid"));
        assertThat(thumbUp.getArticle(), is(1));

    }

    @Test
    public void getList(){
        List<ThumbUp> thumbUps = restTemplate.getForObject(PATH + "/list" , List.class);

        assertThat(thumbUps, is(not(thumbUps.isEmpty())));
    }

    @Test
    public void create(){
        ThumbUp thumbUp = getThumbUp();

        ResultMessage resultMessage = restTemplate.postForObject(PATH + "/", thumbUp, ResultMessage.class);
        assertThat(resultMessage, is(ResultMessageFactory.accept()));

        ThumbUp createdThumbUp = restTemplate.getForObject(PATH + "/"  + thumbUp.getId(), ThumbUp.class);

        validateThumbUp(thumbUp.getArticle(), thumbUp.getUser(), createdThumbUp);
    }

    @Test
    public void delete(){
        ThumbUp thumbUp = getThumbUp();

        ThumbUp createdThumbUp = restTemplate.getForObject(PATH + "/"  + thumbUp.getId(), ThumbUp.class);

        restTemplate.delete(PATH + "/" + createdThumbUp.getId());

        ThumbUp deletedThumbUp = restTemplate.getForObject(PATH + "/" + createdThumbUp.getId(), ThumbUp.class);

        assertThat(deletedThumbUp, is(nullValue()));
    }

    public ThumbUp getThumbUp() {
        ThumbUp thumbUp = new ThumbUp();
        thumbUp.setId(2);
        thumbUp.setArticle(1);
        thumbUp.setUser("testid");
        return thumbUp;
    }

    public void validateThumbUp(Integer article, String user,ThumbUp createdThumbUp) {
        assertThat(createdThumbUp.getArticle(), is(article));
        assertThat(createdThumbUp.getUser(), is(user));
    }


}