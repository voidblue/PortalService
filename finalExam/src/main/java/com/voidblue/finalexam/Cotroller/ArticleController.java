package com.voidblue.finalexam.Cotroller;


import com.voidblue.finalexam.Dao.ArticleRepository;
import com.voidblue.finalexam.Model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/article")
public class ArticleController {

    @Autowired
    ArticleRepository articleRepository;

    @GetMapping("/{id}")
    public Optional<Article> get(@PathVariable Integer id){
        return articleRepository.findById(id);
    }

    @GetMapping("/list")
    public List<Article> getList(){
        return articleRepository.findAll();
    }
}
