package com.sirmasolutions.employees.web;

import com.sirmasolutions.employees.model.entity.Record;
import com.sirmasolutions.employees.service.RecordService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class RecordController {

    private final RecordService recordService;

    public RecordController(RecordService deadlineService) {
        this.recordService = deadlineService;
    }


    @GetMapping("/results")
    public String longestDeadline() {
        List<Record> employeesWithLongestDeadline =
                this.recordService.findEmployeesWithLongestDeadline();

        return "index";
    }
}
