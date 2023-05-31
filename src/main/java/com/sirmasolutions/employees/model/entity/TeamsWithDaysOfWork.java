package com.sirmasolutions.employees.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TeamsWithDaysOfWork {

    private Long emp1Id;

    private Long emp2Id;

    private Long workingPeriod;
}
