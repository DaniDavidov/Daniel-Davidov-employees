package com.sirmasolutions.employees.service;

import com.sirmasolutions.employees.model.entity.Record;
import com.sirmasolutions.employees.model.entity.Team;
import com.sirmasolutions.employees.repository.RecordRepository;
import com.sirmasolutions.employees.repository.TeamRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
public class RecordService {

    private final RecordRepository recordRepository;
    private final TeamRepository teamRepository;

    public RecordService(RecordRepository deadlineRepository, TeamRepository teamRepository) {
        this.recordRepository = deadlineRepository;
        this.teamRepository = teamRepository;
    }


    @Transactional
    public List<Record> findEmployeesWithLongestDeadline() {
        List<Record> records = this.recordRepository.findAllByOrderByProjectId();

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
