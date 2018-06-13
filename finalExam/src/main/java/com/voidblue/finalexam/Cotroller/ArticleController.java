package com.voidblue.finalexam.Cotroller;


import com.voidblue.finalexam.Dao.ArticleRepository;
import com.voidblue.finalexam.Model.Article;
import com.voidblue.finalexam.Utils.ResultMessageFactory;
import com.voidblue.finalexam.Utils.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/article")
//TODO 이미지 부분도 처리해야 함
public class ArticleController {
    private static final String IMAGE_PATH = System.getProperty("user.dir") + "/src/main/resources/static/api/article/image";

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
    public ResultMessage create(@RequestBody Article article, HttpServletRequest req){
        String token = req.getHeader("token");
        System.out.println(token);
        System.out.println(article);
        ResultMessage resultMessage = AuthContext.askAuthorityAndAct(article.getAuthor(), token, () -> {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            System.out.println(simpleDateFormat.format(new Date()));
            article.setTimeCreated(simpleDateFormat.format(new Date()));
            articleRepository.save(article);
        });
        return  resultMessage;
    }

    @PutMapping
    public ResultMessage update(@RequestBody Article article, HttpServletRequest req){
        String token = req.getHeader("token");
        ResultMessage resultMessage = AuthContext.askAuthorityAndAct(article.getAuthor(), token, () -> {
            articleRepository.save(article);
        });
        return  resultMessage;
    }

    @DeleteMapping("/{id}")
    public ResultMessage delete(@PathVariable Integer id, HttpServletRequest req){
        String token = req.getHeader("token");
        Optional<Article> optArticle = articleRepository.findById(id);
        Article article;
        ResultMessage resultMessage = null;
        //TODO 옵셔널을 써서 오히려 복잡해졌는데??
        if(optArticle == null){
            resultMessage = ResultMessageFactory.isEmpty();
        }else {
            article = optArticle.get();
            resultMessage = AuthContext.askAuthorityAndAct(article.getAuthor(), token, () -> {
                articleRepository.deleteById(id);
            });

        }

        return  resultMessage;
    }

    //이미지 권한검사 불가 ㅠㅠ
    @PostMapping("/image")
    public ResultMessage imageCreate(@RequestBody MultipartFile image){
        try {
            BufferedImage bufferedImage = ImageIO.read(image.getInputStream());
            ImageIO.write(bufferedImage, "jpg", new File(IMAGE_PATH + "/" + image.getOriginalFilename()));
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        return ResultMessageFactory.accept();
    }

}
