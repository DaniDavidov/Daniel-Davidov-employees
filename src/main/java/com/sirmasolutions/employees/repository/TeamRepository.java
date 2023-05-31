package com.sirmasolutions.employees.repository;

import com.sirmasolutions.employees.model.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

    Optional<Team> findByEmp1IDAndEmp2ID(Long emp1ID, Long emp2ID);
}
