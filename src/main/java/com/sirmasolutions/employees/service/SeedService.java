package com.sirmasolutions.employees.service;

import com.sirmasolutions.employees.model.entity.Record;
import com.sirmasolutions.employees.repository.RecordRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.LocalDate;

@Service
public class SeedService {

    private final RecordRepository recordRepository;

    public SeedService(RecordRepository deadlineRepository) {
        this.recordRepository = deadlineRepository;
    }

    public void seedData(MultipartFile file) {
        String line = "";
        try {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {

                int counter = 0;
                while ((line = reader.readLine()) != null) {

                    if (counter == 0) {
                        counter++;
                        continue;
                    }

                    String[] data = line.split(", ");
                    Long empId = Long.valueOf(data[0]);
                    Long projectId = Long.parseLong(data[1]);
                    LocalDate dateFrom = LocalDate.parse(data[2]);
                    LocalDate dateTo;

                    if (data[3].equals("NULL")) {
                        dateTo = LocalDate.now();
                    } else {
                        dateTo = LocalDate.parse(data[3]);
                    }

                    Record record = new Record(projectId, empId, dateFrom, dateTo);
                    recordRepository.save(record);
                }

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
