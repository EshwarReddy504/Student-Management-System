package com.example.studentApp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.studentApp.entity.Student;
import com.example.studentApp.repo.StudentRepository;

@RestController
@RequestMapping("/students")
@CrossOrigin
public class StudentController {

    @Autowired
    private StudentRepository repository;

    @GetMapping
    public List<Student> getStudents() {
        return repository.findAll();
    }

    @PostMapping
    public Student addStudent(@RequestBody Student student) {
        return repository.save(student);
    }
    @PutMapping("/{id}")
    public Student updateStudent(@PathVariable Long id, @RequestBody Student student) {

        Student existingStudent = repository.findById(id).orElse(null);

        if(existingStudent != null){
            existingStudent.setName(student.getName());
            existingStudent.setCourse(student.getCourse());
            return repository.save(existingStudent);
        }

        return null;
    }
}