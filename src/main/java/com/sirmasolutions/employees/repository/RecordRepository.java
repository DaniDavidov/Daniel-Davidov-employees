package com.sirmasolutions.employees.repository;

import com.sirmasolutions.employees.model.entity.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {

    List<Record> findAllByOrderByProjectId();
}
