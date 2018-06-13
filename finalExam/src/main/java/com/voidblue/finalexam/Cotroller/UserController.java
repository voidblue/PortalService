package com.voidblue.finalexam.Cotroller;

import com.voidblue.finalexam.Utils.ResultMessageFactory;
import com.voidblue.finalexam.Utils.ResultMessage;
import com.voidblue.finalexam.Model.User;
import com.voidblue.finalexam.Dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private static final String IMAGE_PATH = System.getProperty("user.dir") + "/out/production/resources/static/api/user/image";
    @Autowired
    UserRepository userRepository;

    @GetMapping(value = "/{id}")
    public Optional<User> get(@PathVariable String id){
        return  userRepository.findById(id);
    }

    //TODO 남용하면 업데이트 처럼 동작 할 수 있을 거 같은데 어떻게 구문 하면 좋을지?
    @PostMapping
    public ResultMessage create(@RequestBody User user){
        System.out.println(user.toString() +"모델");
        userRepository.save(user);

        return  ResultMessageFactory.accept();
    }

    @PutMapping
    public ResultMessage update(@RequestBody User user, HttpServletRequest req){
        String token = req.getHeader("token");
        AuthContext.askAuthorityAndAct(user.getId(), token, ()->{
            userRepository.save(user);
        });
        return ResultMessageFactory.accept();
    }

//    @DeleteMapping("/{id}")
//    public ResultMessage delete(@PathVariable String id){
//        userRepository.deleteById(id);
//
//
//        return ResultMessageFactory.accept();
//    }

    @PostMapping("/image")
    public ResultMessage imageCreate(@RequestBody MultipartFile image){
        try {
            BufferedImage bufferedImage = ImageIO.read(image.getInputStream());
            ImageIO.write(bufferedImage, "jpg", new File(IMAGE_PATH + "/" + image.getOriginalFilename()));
        } catch (IOException e) {
            e.printStackTrace();
        }


        return ResultMessageFactory.accept();
    }

    @PutMapping("/image")
    public ResultMessage update(@RequestBody MultipartFile image, @PathVariable String id, HttpServletRequest req){
        String token = req.getHeader("token");
        ResultMessage resultMessage = AuthContext.askAuthorityAndAct(id, token, ()->{
            try {
                BufferedImage bufferedImage = ImageIO.read(image.getInputStream());
                ImageIO.write(bufferedImage, "jpg", new File(IMAGE_PATH + "/" + image.getOriginalFilename()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        return resultMessage;
    }

//    @DeleteMapping("/{id}/image.jpg")
//        //TODO 권한점검
//    public ResultMessage deleteImage(@PathVariable String id){
//        new File(IMAGE_PATH + "/" + id + "/image").delete();
//
//        return  ResultMessageFactory.accept();
//    }
}
