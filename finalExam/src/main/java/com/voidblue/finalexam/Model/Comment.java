package com.voidblue.finalexam.Model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Comment {
    @Id
    private Integer id;
    private String author;
    private Integer article;
    private String text;
    @Column(updatable = false)
    private String timeCreated;

}
