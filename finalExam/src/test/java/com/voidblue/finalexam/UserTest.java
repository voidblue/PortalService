package com.voidblue.finalexam;


import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import com.voidblue.finalexam.Model.ResultMessage;
import com.voidblue.finalexam.Model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.MultiValueMap;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserTest {
    @Autowired
    private TestRestTemplate restTemplate;
    private static final String PATH = "/api/user";

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
        System.out.println(userForCreate.toMultiValueMap() + "테스트");

        //TODO MulitValueMap말고 객체에서 바로 json으로 보내는 방법이 있을것 같은데 그냥 쓰면  적용이 안됨
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        HttpEntity request =  new HttpEntity(userForCreate.toMultiValueMap(), headers);
        ResultMessage resultMessage = restTemplate.postForObject(PATH + "/", userForCreate, ResultMessage.class);
        System.out.println(resultMessage.toString());

        User createdUser = restTemplate.getForObject(PATH+ "/" + userForCreate.getId(), User.class);
        validateUser(userForCreate.getId(), userForCreate.getNickname(), userForCreate.getPassword(), createdUser);
//        assertThat(resultMessage.getResultCode(), is(200));
        //TODO 이미지 넣고 저장하는거는 되는데 테스트는 어떻게??
        //getFOR
    }


    @Test
    public void update(){
        User user = getValidUser();
        user.setPassword("4321");
        restTemplate.put(PATH + "/", user);

        User updatedUser = restTemplate.getForObject(PATH + "/" + user.getId(), User.class);

        validateUser(user.getId(), user.getNickname(), user.getPassword(), updatedUser);
    }

    @Test
    public void delete(){
        User user = restTemplate.getForObject(PATH + "/"+ getValidUser().getId(), User.class);
        restTemplate.delete(PATH + "/" + user.getId());

        User deletedUser = restTemplate.getForObject(PATH + "/" + user.getId(), User.class);

        assertThat(deletedUser, is(nullValue()));

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
        user.setImageUrl("/testImage.png");
        return user;
    }
}
