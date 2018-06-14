package com.voidblue.finalexam.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.ToString;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.lang.reflect.Field;

@Data
@Entity
public class User {
    @Id
    private String id;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private String nickname;
    private String imageName;
}
