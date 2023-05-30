package com.sirmasolutions.employees.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.Set;

@Entity
@Table(name = "employees")
public class Employee extends BaseEntity {


    @OneToMany
    private Set<Project> project;
}
