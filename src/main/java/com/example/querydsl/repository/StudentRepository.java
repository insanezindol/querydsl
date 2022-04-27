package com.example.querydsl.repository;

import com.example.querydsl.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long>, StudentCustomizedRepository {

}
