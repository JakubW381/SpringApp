package com.example.SpringApp.Controller;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("product")
public class HelloController {
    @GetMapping
    public String hello(){
        return "Hello World!";
    }
}
