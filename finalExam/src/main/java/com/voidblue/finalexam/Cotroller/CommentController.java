package com.voidblue.finalexam.Cotroller;

import com.voidblue.finalexam.Dao.CommentRepository;
import com.voidblue.finalexam.Model.Comment;
import com.voidblue.finalexam.Utils.ResultMessageFactory;
import com.voidblue.finalexam.Utils.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
    public List<Comment> getList(@RequestParam(defaultValue = "-1") Integer article){
        if(!article.equals("-1")){
            return commentRepository.findAllByArticle(article);
        }
        return commentRepository.findAll();
    }

    @PostMapping
    public ResultMessage create(@RequestBody Comment comment, HttpServletRequest req){
        String token = req.getHeader("token");

        ResultMessage resultMessage = AuthContext.askAuthorityAndAct(comment.getAuthor(), token, ()->{
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.S");
                comment.setTimeCreated(simpleDateFormat.format(new Date()));
                commentRepository.save(comment);

        });


        return resultMessage;
    }

    @PutMapping
    public ResultMessage update(@RequestBody Comment comment, HttpServletRequest req){
        String token  = req.getHeader("token");
        commentRepository.save(comment);
        ResultMessage resultMessage = AuthContext.askAuthorityAndAct(comment.getAuthor(), token, ()->{
            commentRepository.save(comment);
        });
        return resultMessage;
    }

    @DeleteMapping("/{id}")
    public ResultMessage delete(@PathVariable Integer id, HttpServletRequest req){
        String token = req.getHeader("token");
        Optional<Comment> optComment = commentRepository.findById(id);
        Comment comment;
        ResultMessage resultMessage;
        if(optComment == null){
            resultMessage = ResultMessageFactory.isEmpty();
        }else {
            comment = optComment.get();
            resultMessage = AuthContext.askAuthorityAndAct(comment.getAuthor(), token, () -> {
                commentRepository.deleteById(id);
            });
        }
        return resultMessage;

    }


}
