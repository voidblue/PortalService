package com.voidblue.finalexam.Cotroller;

import com.voidblue.finalexam.Dao.UserRepository;
import com.voidblue.finalexam.Model.Auth;
import com.voidblue.finalexam.Model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Optional;

@RestController
@RequestMapping("api/auth")
public class authContoller {
    @Autowired
    UserRepository userRepository;
    @PostMapping("/login")
    public String login(@RequestBody Auth auth, HttpServletResponse res){
        String jwtString = null;
        try{
            Optional<User> user = userRepository.findById(auth.getId());
            if(user.get().getPassword().equals(auth.getPassword())){
                jwtString = Jwts.builder()
                        .setHeaderParam("typ", "JWT")
                        .setHeaderParam("issueDate", System.currentTimeMillis())
                        .setSubject("")
                        .claim("id", user.get().getId())
                        .claim("nickname", user.get().getNickname())
                        .claim("imageName", user.get().getImageName())
                        .signWith(SignatureAlgorithm.HS512, "portalServiceFinalExam")
                        .compact();
            }else{
                res.setStatus(404);
            }
        }catch (EmptyResultDataAccessException e){
            res.setStatus(500);
        }

        return jwtString;

    }
}
