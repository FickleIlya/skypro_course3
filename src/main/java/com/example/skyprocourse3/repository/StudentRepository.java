package com.example.skyprocourse3.repository;

import com.example.skyprocourse3.model.Faculty;
import com.example.skyprocourse3.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Collection<Student> getStudentsByAge(Integer age);
    Collection<Student> getStudentsByAgeBetween(Integer minAge, Integer maxAge);

    Collection<Student> getStudentsByFacultyId(Long id);

    @Query(value="SELECT name FROM Student")
    Collection<String> getStudentsNames();

    @Query(value="select round(avg(age), 0) as avgAge from Student", nativeQuery = true)
    Integer getStudentsAvgAge();

    @Query(value="select * from student order by id desc limit 5", nativeQuery = true)
    Collection<Student> getLastFiveStudents();
}
