package com.sirmasolutions.employees.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "records")
public class Record {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "project_id", nullable = false)
    private Long projectId;

    @Column(name = "emp_id", nullable = false)
    private Long empId;

    @Column(name = "date_from", nullable = false)
    private LocalDate dateFrom;

    @Column(name = "date_to")
    private LocalDate dateTo;

    public Record(Long projectId, Long empId, LocalDate dateFrom, LocalDate dateTo) {
        this.projectId = projectId;
        this.empId = empId;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }
}
