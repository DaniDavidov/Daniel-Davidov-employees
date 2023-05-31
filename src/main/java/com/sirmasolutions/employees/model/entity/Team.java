package com.sirmasolutions.employees.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "teams")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "emp1_ID", nullable = false)
    private Long emp1ID;

    @Column(name = "emp2_ID", nullable = false)
    private Long emp2ID;

    @Column(name = "working_period", nullable = false)
    private Long workingPeriod;

    @ManyToMany(mappedBy = "teams", targetEntity = Project.class)
    private List<Project> projects;

    public Team(Long emp1ID, Long emp2ID, long workingPeriod) {
        this.emp1ID = emp1ID;
        this.emp2ID = emp2ID;
        this.workingPeriod = workingPeriod;
        this.projects = new ArrayList<>();
    }
}
