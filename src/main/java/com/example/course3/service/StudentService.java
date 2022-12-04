package com.example.course3.service;


import com.example.course3.model.Student;
import com.example.course3.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student updateStudent(Student student) {
        return studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    public Student getStudent(Long id) {
        return studentRepository.findById(id).orElse(null);
    }

    public Collection<Student> getAllStudents() {
        return studentRepository.findAll();
    }

}
