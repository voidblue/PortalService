package com.voidblue.finalexam.Model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class Article {
    @Id
    private Integer id;
    private String author;
    private String title;
    private String text;
    @Column(updatable = false)
    private String timeCreated;
    @Transient
    private String  token;

}
