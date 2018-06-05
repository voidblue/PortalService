package com.voidblue.finalexam.Cotroller;


import com.voidblue.finalexam.Dao.ThumbUpRepository;
import com.voidblue.finalexam.Model.ThumbUp;
import com.voidblue.finalexam.Utils.ResultMessage;
import com.voidblue.finalexam.Utils.ResultMessageFactory;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/like")
public class ThumbUpController {

    @Autowired
    ThumbUpRepository thumbUpRepository;


    @GetMapping("/{id}")
    public Optional<ThumbUp> get(@PathVariable Integer id){
        return thumbUpRepository.findById(id);
    }


    @GetMapping("/list")
    public List<ThumbUp> getList(){
        return thumbUpRepository.findAll();
    }

    @PostMapping
    public ResultMessage create(@RequestBody ThumbUp thumbUp){
        thumbUpRepository.save(thumbUp);
        return ResultMessageFactory.get200();
    }

    @DeleteMapping("/{id}")
    public ResultMessage delete(@PathVariable Integer id){
        thumbUpRepository.deleteById(id);
        return ResultMessageFactory.get200();
    }
}
