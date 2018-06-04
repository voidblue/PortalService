package com.voidblue.finalexam.Cotroller;


import com.voidblue.finalexam.Dao.ArticleRepository;
import com.voidblue.finalexam.Model.Article;
import com.voidblue.finalexam.Model.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public ResultMessage create(@RequestBody Article article){
        articleRepository.save(article);
        ResultMessage resultMessage = new ResultMessage();
        resultMessage.setResultCode(200);
        resultMessage.setMessage("승인");
        return resultMessage;
    }

    @PutMapping
    public ResultMessage update(@RequestBody Article article){
        articleRepository.save(article);
        ResultMessage resultMessage = new ResultMessage();
        resultMessage.setResultCode(200);
        resultMessage.setMessage("승인");
        return resultMessage;
    }

    @DeleteMapping("/{id}")
    public ResultMessage delete(@PathVariable Integer id){
        articleRepository.deleteById(id);

        ResultMessage resultMessage = new ResultMessage();
        resultMessage.setResultCode(200);
        resultMessage.setMessage("승인");
        return resultMessage;
    }
}
