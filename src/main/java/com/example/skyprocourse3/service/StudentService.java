package com.example.skyprocourse3.service;


import com.example.skyprocourse3.model.Faculty;
import com.example.skyprocourse3.model.Student;
import com.example.skyprocourse3.repository.FacultyRepository;
import com.example.skyprocourse3.repository.StudentRepository;
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

    public Student updateStudent(Student studentToUpdate, Student updateBody) {

        studentToUpdate.setName(updateBody.getName());
        studentToUpdate.setAge(updateBody.getAge());
        studentToUpdate.setFaculty(updateBody.getFaculty());
        return studentRepository.save(studentToUpdate);
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

    public Collection<Student> getStudentsByAge(Integer id) {
        return studentRepository.getStudentsByAge(id);
    }

    public Collection<Student> getStudentsByAgeBetween(Integer minAge, Integer maxAge) {
        return studentRepository.getStudentsByAgeBetween(minAge, maxAge);
    }

    public Collection<Student> getStudentsByFacultyId(Long id) {
        return studentRepository.getStudentsByFacultyId(id);
    }
}

