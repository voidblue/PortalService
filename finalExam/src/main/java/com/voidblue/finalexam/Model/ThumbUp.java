package com.voidblue.finalexam.Model;


import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class ThumbUp {
    @Id
    private Integer id;
    private String user;
    private Integer article;
}
