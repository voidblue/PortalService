package com.voidblue.finalexam;

import  static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.CoreMatchers.*;

import com.voidblue.finalexam.Model.Article;
import com.voidblue.finalexam.Model.Comment;
import com.voidblue.finalexam.Utils.ResultMessage;
import com.voidblue.finalexam.Utils.ResultMessageFactory;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class CommentTest {
    @Autowired
    TestRestTemplate restTemplate;

    private static final String PATH = "/api/comment";
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
        Integer id = 1;
        Comment comment = restTemplate.getForObject(PATH + "/" + id, Comment.class);

        assertThat(comment.getId() , is(id));
        validateComment("testuser", 1, "내용",comment);
//        assertThat(comment.getTimeCreated() , is(id));
    }

    @Test
    public void list(){
        List<Comment> comments = restTemplate.getForObject(PATH + "/list", List.class);

        assertThat(comments, not(comments.isEmpty()));
    }


    @Test
    public void Create(){
        notOwner(PATH, HttpMethod.POST);
        notoken(PATH, HttpMethod.POST);
        Comment commentForCreate = getValidComment();

        HttpEntity entity = new HttpEntity(commentForCreate, httpHeaders);
        ResponseEntity<ResultMessage> resultMessage = restTemplate.exchange(PATH ,HttpMethod.POST, entity, ResultMessage.class);

        Comment createdComment = restTemplate.getForObject(PATH + "/" + commentForCreate.getId(), Comment.class);
        System.out.println(resultMessage.getBody());
        assertThat(resultMessage.getBody().getResultCode(), is(200));
        validateComment(commentForCreate.getAuthor(), commentForCreate.getArticle(), commentForCreate.getText()
                ,createdComment);


    }

    @Test
    public void update(){
        Create();
        notOwner(PATH, HttpMethod.PUT);
        notoken(PATH, HttpMethod.PUT);

        Comment commentForUpdate = getValidComment();
        commentForUpdate.setText("수정됨");
        HttpEntity entity = new HttpEntity(commentForUpdate, httpHeaders);
        ResponseEntity<ResultMessage> resultMessage = restTemplate.exchange(PATH,HttpMethod.PUT, entity, ResultMessage.class);


        restTemplate.put(PATH + "/" , commentForUpdate);
        Comment updatedComment = restTemplate.getForObject(PATH + "/" + commentForUpdate.getId(), Comment.class);


        validateComment(commentForUpdate.getAuthor(), commentForUpdate.getArticle(), commentForUpdate.getText(),
                updatedComment);

    }
    @Test
    public void delete(){
        Create();
        Comment commentForDelete = getValidComment();

        notOwner(PATH + "/" + commentForDelete.getId() , HttpMethod.DELETE);
        notoken(PATH + "/" + commentForDelete.getId(), HttpMethod.DELETE);

        HttpEntity entity = new HttpEntity(commentForDelete, httpHeaders);
        ResponseEntity<ResultMessage> resultMessage = restTemplate.exchange(PATH + "/" + commentForDelete.getId(),HttpMethod.DELETE, entity, ResultMessage.class);

        assertThat(resultMessage.getBody(), is(ResultMessageFactory.accept()));
        assertThat(restTemplate.getForObject(PATH + "/" + commentForDelete.getId(), Comment.class), is(nullValue()));
    }


    public void notOwner(String url,HttpMethod httpMethod){
        Comment comment = getValidComment();
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


        HttpEntity entity = new HttpEntity(comment, headers);
        ResponseEntity<ResultMessage> resultMessage = restTemplate.exchange(url , httpMethod, entity, ResultMessage.class);

        assertThat(ResultMessageFactory.noAuthority(), is(resultMessage.getBody()));
    }

    public void notoken(String url, HttpMethod httpMethod){
        Comment comment = getValidComment();
        HttpHeaders notokenHeader = new HttpHeaders();
        HttpEntity entity = new HttpEntity(comment, notokenHeader);
        ResponseEntity<ResultMessage> resultMessage = restTemplate.exchange(url , httpMethod, entity, ResultMessage.class);
        assertThat(ResultMessageFactory.notLogined(), is(resultMessage.getBody()));
    }
    private void validateComment(String author, Integer article, String text, Comment comment) {
        assertThat(comment.getAuthor() , is(author));
        assertThat(comment.getArticle() , is(article));
        assertThat(comment.getText() , is(text));
    }

    private Comment getValidComment() {
        Comment comment = new Comment();
        comment.setId(2);
        comment.setArticle(1);
        comment.setAuthor("testid");
        comment.setText("내용");
        return comment;
    }


}
