package com.voidblue.finalexam;


import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import com.voidblue.finalexam.Model.Comment;
import com.voidblue.finalexam.Utils.ResultMessage;
import com.voidblue.finalexam.Model.User;
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

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserTest {
    @Autowired
    private TestRestTemplate restTemplate;
    private static final String PATH = "/api/user";

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
        String id = "kimqwe12300";
        String nickname = "kim";
        String password = "1234";


        User user = restTemplate.getForObject(PATH + "/" + id, User.class);

        validateUser(id, nickname, password, user);


    }

    @Test
    public void create(){
        User userForCreate = getValidUser();
        ResultMessage resultMessage = restTemplate.postForObject(PATH + "/", userForCreate, ResultMessage.class);

        User createdUser = restTemplate.getForObject(PATH+ "/" + userForCreate.getId(), User.class);


        validateUser(userForCreate.getId(), userForCreate.getNickname(), userForCreate.getPassword(), createdUser);

        //TODO 이미지 넣고 저장하는거는 되는데 테스트는 어떻게??
        //getFOR
    }


    @Test
    public void update(){
        User userForUpdate = getValidUser();
        userForUpdate.setPassword("4321");

        notoken(PATH , HttpMethod.PUT);
        notOwner(PATH, HttpMethod.PUT);

        HttpEntity entity = new HttpEntity(userForUpdate, httpHeaders);
        ResponseEntity<ResultMessage> resultMessage= restTemplate.exchange(PATH, HttpMethod.PUT, entity ,ResultMessage.class);

        User updatedUser = restTemplate.getForObject(PATH + "/" + userForUpdate.getId(), User.class);

        assertThat(resultMessage.getBody(), is(ResultMessageFactory.accept()));
        validateUser(userForUpdate.getId(), userForUpdate.getNickname(), userForUpdate.getPassword(), updatedUser);
    }

    @Test
    public void delete(){
        User user = restTemplate.getForObject(PATH + "/"+ getValidUser().getId(), User.class);
        restTemplate.delete(PATH + "/" + user.getId());

        User deletedUser = restTemplate.getForObject(PATH + "/" + user.getId(), User.class);

        assertThat(deletedUser, is(nullValue()));

    }

    public void notOwner(String url,HttpMethod httpMethod){
        User user = getValidUser();
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


        HttpEntity entity = new HttpEntity(user, headers);
        ResponseEntity<ResultMessage> resultMessage = restTemplate.exchange(url , httpMethod, entity, ResultMessage.class);

        assertThat(ResultMessageFactory.noAuthority(), is(resultMessage.getBody()));
    }

    public void notoken(String url, HttpMethod httpMethod){
        User user = getValidUser();
        HttpHeaders notokenHeader = new HttpHeaders();
        HttpEntity entity = new HttpEntity(user, notokenHeader);
        ResponseEntity<ResultMessage> resultMessage = restTemplate.exchange(url , httpMethod, entity, ResultMessage.class);
        assertThat(ResultMessageFactory.notLogined(), is(resultMessage.getBody()));
    }

    private void validateUser(String id, String nickname, String password, User user) {
        assertThat(user.getId(), is(id));
        assertThat(user.getNickname(), is(nickname));
        assertThat(user.getPassword(), is(password));
    }


    private User getValidUser() {
        User user = new User();
        user.setId("testid");
        user.setPassword("1234");
        user.setNickname("tester");
        return user;
    }
}
