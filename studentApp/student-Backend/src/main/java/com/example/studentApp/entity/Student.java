package com.example.studentApp.entity;

import jakarta.persistence.*;

@Entity
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String course;

    public Student() {}

    public Student(String name, String course) {
        this.name = name;
        this.course = course;
    }

    public Long getId() { return id; }

    public String getName() { return name; }

    public String getCourse() { return course; }

    public void setId(Long id) { this.id = id; }

    public void setName(String name) { this.name = name; }

    public void setCourse(String course) { this.course = course; }
}