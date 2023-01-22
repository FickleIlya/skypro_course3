package com.example.skyprocourse3.service;


import com.example.skyprocourse3.model.Student;
import com.example.skyprocourse3.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Stream;

@Service
public class StudentService {

    public Integer indx = 0;

    Logger logger = LoggerFactory.getLogger(StudentService.class);
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student) {
        logger.info("Was invoked method for create student");
        return studentRepository.save(student);
    }

    public Student updateStudent(Student studentToUpdate, Student updateBody) {
        logger.info("Was invoked method for update student");

        studentToUpdate.setName(updateBody.getName());
        studentToUpdate.setAge(updateBody.getAge());
        studentToUpdate.setFaculty(updateBody.getFaculty());
        return studentRepository.save(studentToUpdate);
    }

    public void deleteStudent(Long id) {
        logger.info("Was invoked method for delete student");
        studentRepository.deleteById(id);
    }

    public Student getStudent(Long id) {
        logger.info("Was invoked method for get student by id");
        return studentRepository.findById(id).orElse(null);
    }

    public Collection<Student> getAllStudents(String letter) {
        logger.info("Was invoked method for get all students");
        if (letter == null) {
            return studentRepository.findAll();
        }
        return studentRepository.findAll().parallelStream()
                .filter(student -> student.getName().startsWith(letter))
                .toList();
    }

    public Collection<Student> getStudentsByAge(Integer id) {
        logger.info("Was invoked method for get student by age");
        return studentRepository.getStudentsByAge(id);
    }

    public Collection<Student> getStudentsByAgeBetween(Integer minAge, Integer maxAge) {
        logger.info("Was invoked method for get student by age between");
        return studentRepository.getStudentsByAgeBetween(minAge, maxAge);
    }

    public Collection<Student> getStudentsByFacultyId(Long id) {
        logger.info("Was invoked method for get student by faculty id");
        return studentRepository.getStudentsByFacultyId(id);
    }

    public Long getStudentsCount() {
        logger.info("Was invoked method for get student count");
        return studentRepository.count();
    }

    public Integer getStudentsAvgAge() {
        logger.info("Was invoked method for get student avg age");
        return studentRepository.getStudentsAvgAge();
    }

    public Collection<Student> getLastFiveStudents() {
        logger.info("Was invoked method for get last five students");
        return studentRepository.getLastFiveStudents();
    }

    public Integer getStudentsAvgAgeParallel() {
        logger.info("Was invoked method for get student avg age parallel");
        return (int) studentRepository.findAll().parallelStream()
                .mapToInt(Student::getAge)
                .average()
                .orElse(0);
    }

    public Integer quickMath() {
        long startTime = System.nanoTime();
        Integer sum = Stream
                .iterate(1, a -> a + 1)
                .limit(100_000_000)
                .parallel()
                .reduce(0, Integer::sum);

        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        System.out.println(duration);
        return sum;

    }

    public void getStudentsNameInThread() {
        logger.info("Was invoked method for get students name in thread");
        System.out.println(studentRepository.getStudentsNames());

        System.out.println(getStudentNameByIndex(0));
        System.out.println(getStudentNameByIndex(1));

        Thread thread = new Thread(() -> {
            System.out.println(getStudentNameByIndex(2));
            System.out.println(getStudentNameByIndex(3));
        });

        thread.start();

        Thread thread2 = new Thread(() -> {
            System.out.println(getStudentNameByIndex(4));
            System.out.println(getStudentNameByIndex(5));
        });

        thread2.start();
    }

    public void getStudentsNameInThreadsSynchronized () {
        logger.info("Was invoked method for get students name in threads synchronized");
        System.out.println(studentRepository.getStudentsNames());

        System.out.println(getStudentNameSynchronized());
        System.out.println(getStudentNameSynchronized());

        Thread thread = new Thread(() -> {
            System.out.println(getStudentNameSynchronized());
            System.out.println(getStudentNameSynchronized());
        });

        thread.start();

        Thread thread2 = new Thread(() -> {
            System.out.println(getStudentNameSynchronized());
            System.out.println(getStudentNameSynchronized());
        });

        thread2.start();
    }

    private String getStudentNameSynchronized() {
        synchronized (this) {
            String name =studentRepository.getStudentsNames().toArray()[indx].toString();
            indx++;
            return name;

        }
    }

    private String getStudentNameByIndex(Integer index) {
        return studentRepository.getStudentsNames().toArray()[index].toString();
    }
}