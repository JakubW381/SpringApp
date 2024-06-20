package com.example.SpringApp.Controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

import static java.lang.Math.clamp;

@RestController

public class ImageController {

    @GetMapping("/adjustBrightness")
    public String adjustBrightness(@RequestBody ImageDTO request) throws IOException {
        String base64Image = request.getBase64Image().replaceAll("\\s+","");
        String[] parts = base64Image.split(",");
        String imageType = parts[0].substring(parts[0].indexOf("/") + 1,parts[0].indexOf(";"));
        base64Image = parts[1];
        byte[] imageBytes = Base64.getDecoder().decode(base64Image);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(imageBytes);
        BufferedImage decodedImage = ImageIO.read(inputStream);
        inputStream.close();

        BufferedImage brightenedImage = changeBrightness(decodedImage, request.getBrightness());

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(brightenedImage,imageType,outputStream);
        byte[] brightenedImageEncoded = outputStream.toByteArray();
        outputStream.close();
        return "data:image/"+imageType+";base64,"+ Base64.getEncoder().encodeToString(brightenedImageEncoded);
    }
    @GetMapping("/adjustBrightness2")
    public ResponseEntity<byte[]> adjustBrightness2(@RequestBody ImageDTO request) throws IOException {
        String base64Image = request.getBase64Image().replaceAll("\\s+","");
        String[] parts = base64Image.split(",");
        String imageType = parts[0].substring(parts[0].indexOf("/") + 1,parts[0].indexOf(";"));
        base64Image = parts[1];
        byte[] imageBytes = Base64.getDecoder().decode(base64Image);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(imageBytes);
        BufferedImage decodedImage = ImageIO.read(inputStream);
        inputStream.close();

        BufferedImage brightenedImage = changeBrightness(decodedImage, request.getBrightness());

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(brightenedImage,imageType,outputStream);
        byte[] brightenedImageEncoded = outputStream.toByteArray();
        outputStream.close();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "image/" + imageType);
        headers.add("Content-Length", String.valueOf(brightenedImageEncoded.length));

        return new ResponseEntity<>(brightenedImageEncoded, headers, HttpStatus.OK);
    }

    private BufferedImage changeBrightness(BufferedImage inputImage,int brightness){
        BufferedImage result = new BufferedImage(
                inputImage.getWidth(),
                inputImage.getHeight(),
        BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < inputImage.getHeight(); y++){
            for (int x = 0; x < inputImage.getWidth(); x++){
                Color color = new Color(inputImage.getRGB(x,y));
                int r = clamp(color.getRed()+brightness,0,255);
                int g = clamp(color.getGreen()+brightness,0,255);
                int b = clamp(color.getBlue()+brightness,0,255);
                Color newColor = new Color(r,g,b);
                result.setRGB(x,y,newColor.getRGB());
            }
        }
        return result;
    }

}
