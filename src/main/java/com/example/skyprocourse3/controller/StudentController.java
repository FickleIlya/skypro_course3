package com.example.skyprocourse3.controller;


import com.example.skyprocourse3.model.Student;
import com.example.skyprocourse3.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/v1")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }


    @GetMapping("/students")
    public ResponseEntity<Collection<Student>> getStudents() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @PostMapping("/students")
    public ResponseEntity<Student> getStudent(@RequestBody Student student) {
        if (student.getAge() == null || student.getAge() < 0 || student.getName() == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(studentService.createStudent(student));
    }

    @GetMapping("/students/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        Student student = studentService.getStudent(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(student);
    }

    @PutMapping("/students/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student student) {

        if (student.getAge() == null || student.getAge() < 0 || student.getName() == null) {
            return ResponseEntity.badRequest().build();
        }

        Student foundStudent = studentService.getStudent(id);
        if (foundStudent == null) {
            return ResponseEntity.notFound().build();
        }

        foundStudent.setName(student.getName());
        foundStudent.setAge(student.getAge());

        return ResponseEntity.ok(studentService.updateStudent(foundStudent));
    }

    @DeleteMapping("/students/{id}")
    public ResponseEntity<Student> deleteStudent(@PathVariable Long id) {
        if (studentService.getStudent(id) == null) {
            return ResponseEntity.notFound().build();
        }

        studentService.deleteStudent(id);
        return ResponseEntity.ok(studentService.getStudent(id));
    }


}
