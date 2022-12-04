package com.example.course3.service;


import com.example.course3.model.Faculty;
import com.example.course3.repository.FacultyRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class FacultyService {

    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty createFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public Faculty updateFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public void deleteFaculty(Long id) {
        facultyRepository.deleteById(id);
    }

    public Faculty getFaculty(Long id) {
        return facultyRepository.findById(id).orElse(null);
    }

    public Collection<Faculty> getAllFaculties() {
        return facultyRepository.findAll();
    }

}
