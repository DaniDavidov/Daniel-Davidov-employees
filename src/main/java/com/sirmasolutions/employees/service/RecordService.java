package com.sirmasolutions.employees.service;

import com.sirmasolutions.employees.model.entity.Project;
import com.sirmasolutions.employees.model.entity.Record;
import com.sirmasolutions.employees.model.entity.Team;
import com.sirmasolutions.employees.repository.ProjectRepository;
import com.sirmasolutions.employees.repository.RecordRepository;
import com.sirmasolutions.employees.repository.TeamRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class RecordService {

    private final RecordRepository recordRepository;
    private final TeamRepository teamRepository;
    private final ProjectRepository projectRepository;

    public RecordService(RecordRepository deadlineRepository, TeamRepository teamRepository, ProjectRepository projectRepository) {
        this.recordRepository = deadlineRepository;
        this.teamRepository = teamRepository;
        this.projectRepository = projectRepository;
    }


    public List<Record> findEmployeesWithLongestDeadline() {
        List<Record> records = this.recordRepository.findAllByOrderByProjectId();

        List<Team> teams = new ArrayList<>();
        for (int i = 0; i < records.size() - 1; i++) {
            for (int j = i + 1; j < records.size(); j++) {

                Record first = records.get(i);
                Record second = records.get(j);

                // checking if the two employees work on the same project
                if (Objects.equals(first.getProjectId(), second.getProjectId())) {

                    if (!hasOverlap(first, second)) {
                        continue;
                    }

                    long workingPeriod = calculateWorkingPeriod(first, second);

                    Optional<Team> teamOptional = this.teamRepository.findByEmp1IDAndEmp2ID(first.getEmpId(), second.getEmpId());
                    Team team = new Team(first.getEmpId(), second.getEmpId());

                    if (teamOptional.isEmpty()) {
                        this.teamRepository.save(team);
                    }
                    Optional<Project> projectOptional = this.projectRepository.findById(first.getProjectId());
                    if (projectOptional.isEmpty()) {

                    }
                    Project project = new Project(first.getProjectId(), team, workingPeriod);
                    this.projectRepository.save(project);
                }
            }
        }
        return records;
    }

    private long calculateWorkingPeriod(Record first, Record second) {
        LocalDate dateFromFirst = first.getDateFrom();
        LocalDate dateToFirst = first.getDateTo();
        LocalDate dateFromSecond = second.getDateFrom();
        LocalDate dateToSecond = second.getDateTo();

        //start date of the common project
        LocalDate startDate = null;

        //end date of the common project
        LocalDate endDate = null;

        int resultStartDate = dateFromFirst.compareTo(dateFromSecond);
        if (resultStartDate > 0) {
            startDate = dateFromFirst;
        } else {
            startDate = dateFromSecond;
        }

        int resultEndDate = dateToFirst.compareTo(dateToSecond);
        if (resultEndDate < 0) {
            endDate = dateToFirst;
        } else {
            endDate = dateToSecond;
        }

        return DAYS.between(startDate, endDate);
    }

    private boolean hasOverlap(Record first, Record second) {
        // in order to have overlap:
        // the first's dateFrom needs to be before the second's DateTo
        // and the first's dateTo needs to be after the second's dateFrom
        return (first.getDateFrom().isBefore(second.getDateTo())
                || first.getDateFrom().isEqual(second.getDateTo()))
                && (first.getDateTo().isAfter(second.getDateFrom())
                || first.getDateTo().isEqual(second.getDateFrom()));
    }
}
