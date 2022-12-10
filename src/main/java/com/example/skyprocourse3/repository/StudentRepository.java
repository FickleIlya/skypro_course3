package com.example.skyprocourse3.repository;

import com.example.skyprocourse3.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Collection<Student> getStudentsByAge(Integer age);
}
