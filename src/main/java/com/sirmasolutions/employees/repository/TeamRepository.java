package com.sirmasolutions.employees.repository;

import com.sirmasolutions.employees.model.entity.TeamsWithDaysOfWork;
import com.sirmasolutions.employees.model.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

    Optional<Team> findByEmp1IdAndEmp2Id(Long emp1ID, Long emp2ID);


    List<Team> findAllByOrderByEmp1IdAscEmp2IdAsc();


    List<Team> findAllByEmp1IdAndEmp2Id(Long emp1Id, Long emp2Id);
}
