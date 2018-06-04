package com.voidblue.finalexam;

import  static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.CoreMatchers.*;

import com.voidblue.finalexam.Model.Comment;
import com.voidblue.finalexam.Utils.ResultMessage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
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
        Comment commentForCreate = getValidComment();
        ResultMessage resultMessage = restTemplate.postForObject(PATH + "/" , commentForCreate, ResultMessage.class);

        Comment createdComment = restTemplate.getForObject(PATH + "/" + commentForCreate.getId(), Comment.class);

        assertThat(resultMessage.getResultCode(), is(200));
        validateComment(commentForCreate.getAuthor(), commentForCreate.getArticle(), commentForCreate.getText()
                ,createdComment);
    }

    @Test
    public void update(){
        Comment commentForCreate = getValidComment();
        ResultMessage resultMessage = restTemplate.postForObject(PATH + "/" , commentForCreate, ResultMessage.class);

        Comment commentForUpdate = restTemplate.getForObject(PATH + "/" + commentForCreate.getId(), Comment.class);


        commentForUpdate.setText("수정된내용");
        restTemplate.put(PATH + "/" , commentForUpdate);
        Comment updatedComment = restTemplate.getForObject(PATH + "/" + commentForUpdate.getId(), Comment.class);


        validateComment(commentForUpdate.getAuthor(), commentForUpdate.getArticle(), commentForUpdate.getText(),
                updatedComment);

    }
    @Test
    public void delete(){
        Comment commentForCreate = getValidComment();
        ResultMessage resultMessage = restTemplate.postForObject(PATH + "/" , commentForCreate, ResultMessage.class);

        Comment createdComment = restTemplate.getForObject(PATH + "/" + commentForCreate.getId(), Comment.class);

        restTemplate.delete(PATH +"/"+ createdComment.getId());

        assertThat(restTemplate.getForObject(PATH + "/" + commentForCreate.getId(), Comment.class), is(nullValue()));
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
        comment.setAuthor("testuser");
        comment.setText("내용");
        return comment;
    }


}
