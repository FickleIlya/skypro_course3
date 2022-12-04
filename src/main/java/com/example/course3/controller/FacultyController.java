package com.example.course3.controller;

import com.example.course3.model.Faculty;
import com.example.course3.service.FacultyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/v1")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping("/faculties")
    public ResponseEntity<Collection<Faculty>> getFaculty() {
        return ResponseEntity.ok(facultyService.getAllFaculties());
    }

    @PostMapping("/faculties")
    public ResponseEntity<Faculty> createFaculty(@RequestBody Faculty faculty) {
        if (faculty.getName() == null || faculty.getColor() == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(facultyService.createFaculty(faculty));
    }

    @GetMapping("/faculties/{id}")
    public ResponseEntity<Faculty> getFacultyById(@PathVariable Long id) {
        Faculty faculty = facultyService.getFaculty(id);
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(faculty);
    }

    @PutMapping("/faculties/{id}")
    public ResponseEntity<Faculty> updateFaculty(@PathVariable Long id, @RequestBody Faculty faculty) {
        if (faculty.getName() == null || faculty.getColor() == null) {
            return ResponseEntity.badRequest().build();
        }

        Faculty foundFaculty = facultyService.getFaculty(id);
        if (foundFaculty == null) {
            return ResponseEntity.notFound().build();
        }

        foundFaculty.setName(faculty.getName());
        foundFaculty.setColor(faculty.getColor());

        return ResponseEntity.ok(facultyService.updateFaculty(foundFaculty));
    }

    @DeleteMapping("/faculties/{id}")
    public ResponseEntity<Faculty> deleteFaculty(@PathVariable Long id) {
        Faculty faculty = facultyService.getFaculty(id);
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }

        facultyService.deleteFaculty(id);
        return ResponseEntity.ok(faculty);
    }

}
