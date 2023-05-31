package com.sirmasolutions.employees.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "projects")
public class Project {

    @Id
    private Long id;

    @ManyToOne
    private Team team;

    @Column(name = "working_period", nullable = false)
    private Long workingPeriod;
}
