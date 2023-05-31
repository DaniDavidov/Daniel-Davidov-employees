package com.sirmasolutions.employees.model.dto;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SeedPojo {

    @CsvBindByName(column = "EmpId")
    private Long empId;

    @CsvBindByName(column = "ProjectId")
    private Long projectId;

    @CsvBindByName(column = "DateFrom")
    @CsvDate(value = "yyyy-MM-dd")
    private LocalDate dateFrom;

    @CsvBindByName(column = "DateTo")
    @CsvDate(value = "yyyy-MM-dd")
    private LocalDate dateTo;
}
