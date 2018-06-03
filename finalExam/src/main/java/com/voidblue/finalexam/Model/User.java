package com.voidblue.finalexam.Model;

import lombok.Data;
import lombok.ToString;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.lang.reflect.Field;

@Data
@Entity
public class User {
    @Id
    private String id;
    private String password;
    private String nickname;
    private String ImageUrl;


    public MultiValueMap toMultiValueMap(){
        MultiValueMap user = new LinkedMultiValueMap();
        for(Field f : this.getClass().getDeclaredFields()){
            try {
                user.add(f.getName(), f.get(this));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return user;
    }
}
