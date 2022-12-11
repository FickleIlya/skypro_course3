package com.example.skyprocourse3.controller;

import com.example.skyprocourse3.model.Faculty;
import com.example.skyprocourse3.model.Student;
import com.example.skyprocourse3.service.FacultyService;
import com.example.skyprocourse3.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/v1/faculties")
public class FacultyController {
    private final FacultyService facultyService;
    private final StudentService studentService;

    public FacultyController(FacultyService facultyService, StudentService studentService) {
        this.facultyService = facultyService;
        this.studentService = studentService;
    }

    @GetMapping
    public ResponseEntity<Collection<Faculty>> getFaculty() {
        return ResponseEntity.ok(facultyService.getAllFaculties());
    }

    @PostMapping
    public ResponseEntity<Faculty> createFaculty(@RequestBody Faculty faculty) {
        if (faculty.getName() == null || faculty.getColor() == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(facultyService.createFaculty(faculty));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Faculty> getFacultyById(@PathVariable Long id) {
        Faculty faculty = facultyService.getFacultyById(id);
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(faculty);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Faculty> updateFaculty(@PathVariable Long id, @RequestBody Faculty requestBody) {
        if (requestBody.getName() == null || requestBody.getColor() == null) {
            return ResponseEntity.badRequest().build();
        }

        Faculty foundFaculty = facultyService.getFacultyById(id);
        if (foundFaculty == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(facultyService.updateFaculty(foundFaculty, requestBody));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Faculty> deleteFaculty(@PathVariable Long id) {
        Faculty faculty = facultyService.getFacultyById(id);
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }

        facultyService.deleteFaculty(id);
        return ResponseEntity.ok(faculty);
    }

    @GetMapping("/color")
    public ResponseEntity<Collection<Faculty>> getFacultyByColor(@RequestParam String color) {
        if (color == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(facultyService.getFacultyByColor(color));
    }

    @GetMapping("/name")
    public ResponseEntity<Collection<Faculty>> getFacultyByNameIgnoreCase(@RequestParam String name) {
        if (name == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(facultyService.getFacultyByNameIgnoreCase(name));
    }

    @GetMapping("/{id}/students")
    public ResponseEntity<Collection<Student>> getFacultyStudents(@PathVariable Long id) {
        Faculty faculty = facultyService.getFacultyById(id);
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(studentService.getStudentsByFacultyId(id));
    }
}
