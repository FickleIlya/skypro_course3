package com.example.skyprocourse3.controller;


import com.example.skyprocourse3.model.Faculty;
import com.example.skyprocourse3.model.Student;
import com.example.skyprocourse3.service.FacultyService;
import com.example.skyprocourse3.service.StudentService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(value="/api/v1/students", produces = MediaType.APPLICATION_JSON_VALUE)
public class StudentController {

    private final StudentService studentService;
    private final FacultyService facultyService;

    public StudentController(StudentService studentService, FacultyService facultyService) {
        this.studentService = studentService;
        this.facultyService = facultyService;
    }


    @GetMapping
    public ResponseEntity<Collection<Student>> getStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getStudentsCount() {
        return ResponseEntity.ok(studentService.getStudentsCount());
    }

    @GetMapping("/avgAge")
    public ResponseEntity<Integer> getStudentsAvgAge() {
        return ResponseEntity.ok(studentService.getStudentsAvgAge());
    }

    @GetMapping("/lastFiveStudents")
    public ResponseEntity<Collection<Student>> getLastFiveStudents() {
        return ResponseEntity.ok(studentService.getLastFiveStudents());
    }

    @PostMapping
    public ResponseEntity<Student> getStudent(@RequestBody Student student) {
        if (student.getAge() == null || student.getAge() < 0 || student.getName() == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(studentService.createStudent(student));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        Student student = studentService.getStudent(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(student);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student requestBody) {

        if (requestBody.getAge() == null || requestBody.getAge() < 0 || requestBody.getName() == null) {
            return ResponseEntity.badRequest().build();
        }

        Student foundStudent = studentService.getStudent(id);
        if (foundStudent == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(studentService.updateStudent(foundStudent, requestBody));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Student> deleteStudent(@PathVariable Long id) {
        if (studentService.getStudent(id) == null) {
            return ResponseEntity.notFound().build();
        }

        studentService.deleteStudent(id);
        return ResponseEntity.ok(studentService.getStudent(id));
    }

    @GetMapping("/age")
    public ResponseEntity<Collection<Student>> getStudentsByAge(@RequestParam Integer age) {
        if (age == null || age < 0) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(studentService.getStudentsByAge(age));
    }

    @GetMapping("/age/between")
    public ResponseEntity<Collection<Student>> getStudentsByAgeBetween(@RequestParam Integer minAge,
                                                                       @RequestParam Integer maxAge) {
        if (minAge == null || minAge < 0 || maxAge == null || maxAge < 0) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(studentService.getStudentsByAgeBetween(minAge, maxAge));
    }

    @GetMapping("/{id}/faculty")
    public ResponseEntity<Faculty> getFacultyByStudentId(@PathVariable Long id) {
        Student student = studentService.getStudent(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        Faculty faculty = facultyService.getFacultyById(student.getFaculty().getId());
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }
}
