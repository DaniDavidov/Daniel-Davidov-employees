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
@Table(name = "teams")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "emp1_ID", nullable = false)
    private Long emp1Id;

    @Column(name = "emp2_ID", nullable = false)
    private Long emp2Id;

    @Column(name = "project_id", nullable = false)
    private Long projectId;

    @Column(name = "working_period", nullable = false)
    private Long workingPeriod;


    public Team(Long emp1Id, Long emp2Id, Long projectId, long workingPeriod) {
        this.emp1Id = emp1Id;
        this.emp2Id = emp2Id;
        this.projectId = projectId;
        this.workingPeriod = workingPeriod;
    }
}
