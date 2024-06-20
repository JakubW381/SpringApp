package com.example.SpringApp.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("rectangles")
public class RectangleController {
    //Rectangle rectangle;
    private final List<Rectangle> rectangles = new ArrayList<>();
//    @GetMapping("/rectangleAdd")
//    public void Rectadd() {
//        rectangles.add(new Rectangle(40,15,20,42,"Orange"));
//        rectangles.add(new Rectangle(13,12,19,11,"Blue"));
//    }

    public RectangleController() {
        Rectangle rectangle = new Rectangle(5,5,10,12,"Red");
        rectangles.add(new Rectangle(40,15,20,42,"Orange"));
        rectangles.add(new Rectangle(13,12,19,11,"Blue"));
        rectangles.add(rectangle);
    }
    @PostMapping("/add")
    public ResponseEntity<String> addRectangle(@RequestBody Rectangle newRectangle) {
        rectangles.add(newRectangle);
        return new ResponseEntity<>("Rectangle added successfully", HttpStatus.CREATED);
    }
    //nie działa
    //curl -X POST http://localhost:8080/rectangles/add -H "Content-Type: application/json" -d '{"x": 7, "y": 7, "width": 14, "height": 20, "color": "Green"}'

//    @GetMapping({"/JSON", "/"})
//    public Rectangle getRectangle(){
//        return rectangle;
//    }

    @GetMapping("/list/{index}")
         Rectangle getRectIndex(@PathVariable int index){
        if (index < 0 || index >= rectangles.size()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return rectangles.get(index);
        }
    @GetMapping("/list")
    List<Rectangle> getRectList(){
        return rectangles;
    }
    @GetMapping("/list/svg")
    public ResponseEntity<String> listToSvg(){
        StringBuilder builder = new StringBuilder();
        builder.append("<svg width=\"800\" height=\"600\" xmlns=\"http://www.w3.org/2000/svg\">");
        for (Rectangle rect : rectangles) {
            builder.append("<rect x=\"").append(rect.getX())
                    .append("\" y=\"").append(rect.getY())
                    .append("\" width=\"").append(rect.getWidth())
                    .append("\" height=\"").append(rect.getHeight())
                    .append("\" fill=\"").append(rect.getColor())
                    .append("\" />");
        }
        builder.append("</svg>");

        return ResponseEntity.ok(builder.toString());
    }
    @GetMapping("/list/{index}/svg")
    public ResponseEntity<String> indexToSvg(@PathVariable int index){
        if (index < 0 || index >= rectangles.size()) {
            return ResponseEntity.badRequest().build();
        }
        StringBuilder builder = new StringBuilder();
        builder.append("<svg width=\"800\" height=\"600\" xmlns=\"http://www.w3.org/2000/svg\">");
            builder.append("<rect x=\"").append(rectangles.get(index).getX())
                    .append("\" y=\"").append(rectangles.get(index).getY())
                    .append("\" width=\"").append(rectangles.get(index).getWidth())
                    .append("\" height=\"").append(rectangles.get(index).getHeight())
                    .append("\" fill=\"").append(rectangles.get(index).getColor())
                    .append("\" />");
        builder.append("</svg>");

        return ResponseEntity.ok(builder.toString());
    }

    @PutMapping("/update/{index}")

    public ResponseEntity<String> changeRectangle(@PathVariable int index, @RequestBody Rectangle rect){
        if (index < 0 || index >= rectangles.size()) {
            return ResponseEntity.badRequest().build();
        }
        rectangles.set(index,rect);
        return ResponseEntity.ok("Rectangle "+index+" updated succesfully");
    }
    @DeleteMapping("/remove/{index}")
    public ResponseEntity<String> removeRectangle(@PathVariable int index){
        if (index < 0 || index >= rectangles.size()) {
            return ResponseEntity.badRequest().build();
        }
        rectangles.remove(index);
        return ResponseEntity.ok("Rectangle "+index+" removed succesfully");
    }

}
