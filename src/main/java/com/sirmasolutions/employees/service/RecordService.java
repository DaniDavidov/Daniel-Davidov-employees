package com.sirmasolutions.employees.service;

import com.sirmasolutions.employees.model.entity.TeamsWithDaysOfWork;
import com.sirmasolutions.employees.model.entity.Record;
import com.sirmasolutions.employees.model.entity.Team;
import com.sirmasolutions.employees.repository.RecordRepository;
import com.sirmasolutions.employees.repository.TeamRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class RecordService {

    private final RecordRepository recordRepository;
    private final TeamRepository teamRepository;

    public RecordService(RecordRepository deadlineRepository, TeamRepository teamRepository) {
        this.recordRepository = deadlineRepository;
        this.teamRepository = teamRepository;
    }

    public List<Long> findEmployeesWithLongestDeadline() {
        List<Record> records = this.recordRepository.findAllByOrderByProjectIdAscEmpIdAsc();

        for (int i = 0; i < records.size() - 1; i++) {
            for (int j = i + 1; j < records.size(); j++) {

                Record first = records.get(i);
                Record second = records.get(j);

                // checking if the two employees work on the same project
                if (Objects.equals(first.getProjectId(), second.getProjectId())) {

                    // the employees deadlines must overlap
                    if (!hasOverlap(first, second)) {
                        continue;
                    }

                    long workingPeriod = calculateWorkingPeriod(first, second);

                    Team team = new Team(first.getEmpId(), second.getEmpId(), first.getProjectId(), workingPeriod);
                    this.teamRepository.save(team);
                }
            }
        }

        // converting the string array to List with the IDs of the employees
        String[] mostWorkingTeam = findMostWorkingTeam();
        return Arrays.stream(mostWorkingTeam)
                .map(Long::parseLong)
                .collect(Collectors.toList());
    }

    public String[] findMostWorkingTeam() {
        List<Team> teams = this.teamRepository.findAll();

        Map<String, Long> employeesIDsByWorkingPeriod = new LinkedHashMap<>();

        for (Team team : teams) {
            Long emp1Id = team.getEmp1Id();
            Long emp2Id = team.getEmp2Id();
            Long workingPeriod = team.getWorkingPeriod();

            // the string will be the key for the HashMap and the working period will be the value to the key
            String employeesIDs = emp1Id + ", " + emp2Id;
            if (!employeesIDsByWorkingPeriod.containsKey(employeesIDs)) {
                employeesIDsByWorkingPeriod.put(employeesIDs, workingPeriod);
            } else {
                Long value = employeesIDsByWorkingPeriod.get(employeesIDs);
                employeesIDsByWorkingPeriod.put(employeesIDs, value + workingPeriod);
            }
        }

        // find the pair of workers with the greatest amount of working period
        Map.Entry<String, Long> highestEntry = employeesIDsByWorkingPeriod
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .orElse(null);

        // get the ID's of the employees
        String key = highestEntry.getKey();

        return key.split(", ");

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
