package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;

@Controller
public class NewsController {

    @GetMapping("/news")
    public String showNewsPage(){
        return "news";
    }
}
