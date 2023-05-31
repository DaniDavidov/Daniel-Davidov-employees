package com.sirmasolutions.employees.web;

import com.sirmasolutions.employees.service.SeedService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

@Controller
public class InputController {

    private SeedService seedService;

    public InputController(SeedService seedService) {
        this.seedService = seedService;
    }

    @GetMapping("/")
    public String inputPage() {
        return "index";
    }

    @PostMapping("/")
    public String seedData(@RequestParam("file") MultipartFile file) {

        this.seedService.seedData(file);

        return "redirect:/";
    }
}
