package com.example.skyprocourse3.repository;

import com.example.skyprocourse3.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
