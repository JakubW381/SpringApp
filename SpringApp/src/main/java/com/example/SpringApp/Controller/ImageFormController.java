package com.example.SpringApp.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

@RestController
@RequestMapping("imageform")
public class ImageFormController {


    @GetMapping("/index")
    public String indexHtml(){
        return "index";
    }
    @PostMapping("/upload")
    public String uploadImage(@RequestParam("file") MultipartFile file) throws IOException {

            BufferedImage bufferedImage = ImageIO.read(file.getInputStream());

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", outputStream);
            String image = Base64.getEncoder().encodeToString(outputStream.toByteArray());

            return "image";
    }

}
