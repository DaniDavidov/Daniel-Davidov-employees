package com.sirmasolutions.employees.service;

import com.sirmasolutions.employees.repository.EmployeeRepository;
import com.sirmasolutions.employees.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class SeedService {

    private EmployeeRepository employeeRepository;
    private ProjectRepository projectRepository;

    public SeedService(EmployeeRepository employeeRepository, ProjectRepository projectRepository) {
        this.employeeRepository = employeeRepository;
        this.projectRepository = projectRepository;
    }

    public void seedData(File file) {

    }
}
