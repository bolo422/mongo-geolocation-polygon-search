package com.bolo422.geolocation.geolocation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class ViewController {

    @GetMapping("/search")
    public String search() {
        return "search";
    }

    @GetMapping("/add")
    public String save() {
        return "add";
    }

    @GetMapping("/showAll")
    public String showAll() {
        return "showAll";
    }
}
