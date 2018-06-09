package com.voidblue.finalexam.Cotroller;

import com.voidblue.finalexam.Utils.ResultMessageFactory;
import com.voidblue.finalexam.Utils.ResultMessage;
import com.voidblue.finalexam.Model.User;
import com.voidblue.finalexam.Dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private static final String IMAGE_PATH = System.getProperty("user.dir") + "/src/main/resources/static/api/user";
    @Autowired
    UserRepository userRepository;

    @GetMapping(value = "/{id}")
    public Optional<User> get(@PathVariable String id){

        return userRepository.findById(id);
    }

    //TODO 남용하면 업데이트 처럼 동작 할 수 있을 거 같은데 어떻게 구문 하면 좋을지?
    @PostMapping
    public ResultMessage create(@RequestBody User user){
        System.out.println(user.toString() +"모델");
        userRepository.save(user);

        return  ResultMessageFactory.get200();
    }

    @PutMapping
    public ResultMessage update(@RequestBody User user){
        userRepository.save(user);
        //TODO 권한점검
        return ResultMessageFactory.get200();
    }

    @DeleteMapping("/{id}")
    public ResultMessage delete(@PathVariable String id){
        userRepository.deleteById(id);


        return ResultMessageFactory.get200();
    }

    @PostMapping("/{id}/image.jpg")
    public ResultMessage imgaeCreate(@RequestBody MultipartFile image, @PathVariable String id){
        try {
            image.transferTo(new File(IMAGE_PATH + "/" + id  + "/image.jpg"));
        } catch (InvalidPathException e){
            File imageDir = new File(IMAGE_PATH  + "/" + id);
            imageDir.mkdir();
            try {
                image.transferTo(new File(IMAGE_PATH  + "/" + id + "/image.jpg"));
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        } catch (IOException e2) {
            e2.printStackTrace();
        }


        return ResultMessageFactory.get200();
    }

    @PutMapping("/{id}/image.jpg")
        //TODO 권한점검
    public ResultMessage update(@RequestBody MultipartFile image, @PathVariable String id){
        try {
            image.transferTo(new File(IMAGE_PATH + "/" + id +  "/image"));
        } catch (IOException e) {
            e.printStackTrace();
        }


        return  ResultMessageFactory.get200();
    }

    @DeleteMapping("/{id}/image.jpg")
        //TODO 권한점검
    public ResultMessage deleteImage(@PathVariable String id){
        new File(IMAGE_PATH + "/" + id + "/image").delete();

        return  ResultMessageFactory.get200();
    }
}
