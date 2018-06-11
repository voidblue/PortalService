package com.voidblue.finalexam.Cotroller;

import com.voidblue.finalexam.Dao.ThumbUpRepository;
import com.voidblue.finalexam.Model.Auth;
import com.voidblue.finalexam.Model.ThumbUp;
import com.voidblue.finalexam.Utils.ResultMessage;
import com.voidblue.finalexam.Utils.ResultMessageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
    public ResultMessage create(@RequestBody ThumbUp thumbUp, HttpServletRequest req){
        String token = req.getHeader("token");
        ResultMessage resultMessage = AuthContext.askAuthorityAndAct(thumbUp.getUser(), token, ()->{thumbUpRepository.save(thumbUp);});
        return resultMessage;
    }

    @PutMapping
    public ResultMessage update(@RequestBody ThumbUp thumbUp, HttpServletRequest req){
        String token = req.getHeader("token");
        ResultMessage resultMessage = AuthContext.askAuthorityAndAct(thumbUp.getUser(), token, ()->{thumbUpRepository.save(thumbUp);});
        return resultMessage;
    }

//    @DeleteMapping("/{id}")
//    public ResultMessage delete(@PathVariable Integer id, HttpServletRequest req){
//        Optional<ThumbUp> thumbUp = thumbUpRepository.findById(id);
//        String token = req.getHeader("token");
//        ResultMessage resultMessage = AuthContext.askAuthorityAndAct(thumbUp.getUser(), token, ()->{thumbUpRepository.save(thumbUp);});
//        return resultMessage;
//    }
}
