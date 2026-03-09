package com.example.studentApp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.studentApp.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {

}