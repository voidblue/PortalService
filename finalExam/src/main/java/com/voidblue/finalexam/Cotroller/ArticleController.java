package com.voidblue.finalexam.Cotroller;


import com.voidblue.finalexam.Dao.ArticleRepository;
import com.voidblue.finalexam.Model.Article;
import com.voidblue.finalexam.Utils.ResultMessageFactory;
import com.voidblue.finalexam.Utils.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/article")
//TODO 이미지 부분도 처리해야 함
public class ArticleController {

    @Autowired
    ArticleRepository articleRepository;

    @GetMapping("/{id}")
    public Optional<Article> get(@PathVariable Integer id){
        return articleRepository.findById(id);
    }

    @GetMapping("/list")
    //TODO 파라미터 및 페이징 추가할것
    public List<Article> getList(){
        return articleRepository.findAll();
    }

    @PostMapping
    public ResultMessage create(@RequestBody Article article){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.S");
        article.setTimeCreated(simpleDateFormat.format(new Date()));
        articleRepository.save(article);

        return  ResultMessageFactory.get200();
    }

    @PutMapping
    public ResultMessage update(@RequestBody Article article){
        articleRepository.save(article);
        return  ResultMessageFactory.get200();
    }

    @DeleteMapping("/{id}")
    public ResultMessage delete(@PathVariable Integer id){
        articleRepository.deleteById(id);

        return  ResultMessageFactory.get200();
    }


}
