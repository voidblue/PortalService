package com.voidblue.finalexam.Model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Date;

@Data
@Entity
public class Article {
    @Id
    private Integer id;
    private String author;
    private String title;
    private String text;
    private String timeCreated;

}
