package com.voidblue.finalexam.Cotroller;

import com.voidblue.finalexam.Dao.CommentRepository;
import com.voidblue.finalexam.Model.Comment;
import com.voidblue.finalexam.Utils.ResultMessageFactory;
import com.voidblue.finalexam.Utils.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/comment")
public class CommentController {
    @Autowired
    CommentRepository commentRepository;

    @GetMapping("/{id}")
    public Optional<Comment> get(@PathVariable Integer id){
        return commentRepository.findById(id);
    }

    @GetMapping("/list")
    public List<Comment> getList(){
        return commentRepository.findAll();
    }

    @PostMapping
    public ResultMessage create(@RequestBody Comment comment){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.S");
        comment.setTimeCreated(simpleDateFormat.format(new Date()));

        commentRepository.save(comment);
        return ResultMessageFactory.accept();
    }

    @PutMapping
    public ResultMessage update(@RequestBody Comment comment){
        commentRepository.save(comment);
        return ResultMessageFactory.accept();
    }

    @DeleteMapping("/{id}")
    public ResultMessage delete(@PathVariable Integer id){
        commentRepository.deleteById(id);

        return ResultMessageFactory.accept();

    }


}
