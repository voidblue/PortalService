package com.voidblue.finalexam.Cotroller;

import com.voidblue.finalexam.Model.ResultMessage;
import com.voidblue.finalexam.Model.User;
import com.voidblue.finalexam.Dao.UserRepository;
import org.hibernate.boot.model.source.internal.hbm.ResultSetMappingBinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {

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
//                                ,@RequestParam MultipartFile profileImage){
//        //TODO 토근 권한 점검
//        String filename =  profileImage.getOriginalFilename();
//        try {
//            BufferedImage image= ImageIO.read(profileImage.getInputStream());
//            Image imageForResize = image.getScaledInstance(100,100, Image.SCALE_SMOOTH);
//            BufferedImage newImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
//            Graphics g = newImage.getGraphics();
//            g.drawImage(imageForResize, 0, 0, null);
//            g.dispose();
//            //TODO 상대경로 어떻게 깔끔하게 하는 법??
//            ImageIO.write(newImage,"jpg", new File(System.getProperty("user.dir") +"/src/main/resource/static" + filename));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        ResultMessage resultMessage = new ResultMessage();
        resultMessage.setResultCode(200);
        resultMessage.setMessage("승인");
        return resultMessage;
    }

    @PutMapping
    public ResultMessage update(@RequestBody User user){
        userRepository.save(user);
        //TODO 권한점검
        ResultMessage resultMessage = new ResultMessage();
        resultMessage.setResultCode(200);
        resultMessage.setMessage("승인");
        return resultMessage;
    }

    @DeleteMapping("/{id}")
    public ResultMessage delete(@PathVariable String id){
        userRepository.deleteById(id);

        ResultMessage resultMessage = new ResultMessage();
        resultMessage.setResultCode(200);
        resultMessage.setMessage("승인");
        return resultMessage;
    }
}
