package com.voidblue.finalexam.Model;

import lombok.Data;
import org.hibernate.annotations.GeneratorType;

import javax.persistence.*;

@Data
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String author;
    private Integer article;
    private String text;
    @Column(updatable = false)
    private String timeCreated;


}
