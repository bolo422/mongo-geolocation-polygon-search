package com.bolo422.geolocation.geolocation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/edit")
    public String edit() {
        return "edit";
    }

    @GetMapping("/addRadius")
    public String addRadius() {
        return "addRadius";
    }

}
