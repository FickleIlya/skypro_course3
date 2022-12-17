package com.example.skyprocourse3.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Objects;


@Entity
@Table(name = "student")
public class Student {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private Integer age;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "faculty_id")
    private Faculty faculty;

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student student)) return false;
        return Objects.equals(getId(), student.getId()) && Objects.equals(getName(), student.getName()) && Objects.equals(getAge(), student.getAge());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getAge());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty faculty) {
        this.faculty = faculty;
    }
}
