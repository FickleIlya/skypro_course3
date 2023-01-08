package com.example.skyprocourse3.service;


import com.example.skyprocourse3.model.Faculty;
import com.example.skyprocourse3.repository.FacultyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class FacultyService {

    Logger logger = LoggerFactory.getLogger(FacultyService.class);
    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty createFaculty(Faculty faculty) {
        logger.info("Was invoked method for create faculty");
        return facultyRepository.save(faculty);
    }

    public Faculty updateFaculty(Faculty facultyToUpdate, Faculty updateBody) {
        logger.info("Was invoked method for update faculty");
        facultyToUpdate.setName(updateBody.getName());
        facultyToUpdate.setColor(updateBody.getColor());

        return facultyRepository.save(updateBody);
    }

    public void deleteFaculty(Long id) {
        logger.info("Was invoked method for delete faculty");
        facultyRepository.deleteById(id);
    }

    public Faculty getFacultyById(Long id) {
        logger.info("Was invoked method for get faculty by id");
        return facultyRepository.findById(id).orElse(null);
    }

    public Collection<Faculty> getAllFaculties() {
        logger.info("Was invoked method for get all faculties");
        return facultyRepository.findAll();
    }

    public Collection<Faculty> getFacultyByColor(String color) {
        logger.info("Was invoked method for get faculty by color");
        return facultyRepository.findByColor(color);
    }

    public Collection<Faculty> getFacultyByNameIgnoreCase(String name) {
        logger.info("Was invoked method for get faculty by name");
        return facultyRepository.findByNameIgnoreCase(name);
    }
}

