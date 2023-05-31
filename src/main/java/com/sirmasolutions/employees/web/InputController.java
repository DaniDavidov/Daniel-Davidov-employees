package com.sirmasolutions.employees.web;

import com.sirmasolutions.employees.service.RecordService;
import com.sirmasolutions.employees.service.SeedService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class InputController {

    private SeedService seedService;
    private final RecordService recordService;

    public InputController(SeedService seedService, RecordService recordService) {
        this.seedService = seedService;
        this.recordService = recordService;
    }

    @GetMapping("/")
    public String inputPage() {
        this.recordService.cleanUpDatabase();
        return "index";
    }

    @PostMapping("/")
    public String seedData(@RequestParam("file") MultipartFile file) {

        this.seedService.seedData(file);

        return "redirect:/results";
    }
}
