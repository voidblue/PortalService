package com.voidblue.finalexam.Model;

import lombok.Data;
import lombok.ToString;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.lang.reflect.Field;

@Data
@Entity
public class User {
    @Id
    private String id;
    private String password;
    private String nickname;
    @Transient
    private String  token;
}
