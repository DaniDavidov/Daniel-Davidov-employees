package com.sirmasolutions.employees.web;

import com.sirmasolutions.employees.model.entity.Record;
import com.sirmasolutions.employees.model.entity.Team;
import com.sirmasolutions.employees.repository.TeamRepository;
import com.sirmasolutions.employees.service.RecordService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class RecordController {

    private final RecordService recordService;
    private final TeamRepository teamRepository;

    public RecordController(RecordService deadlineService, TeamRepository teamRepository) {
        this.recordService = deadlineService;
        this.teamRepository = teamRepository;
    }


    @GetMapping("/results")
    public String longestDeadline() {

        List<Long> employees = this.recordService.findEmployeesWithLongestDeadline();

        List<Team> teamData = this.teamRepository.findAllByEmp1IdAndEmp2Id(employees.get(0), employees.get(1));


        return "results";
    }
}
