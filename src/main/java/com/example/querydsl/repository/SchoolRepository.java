package com.example.querydsl.repository;

import com.example.querydsl.entity.School;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SchoolRepository extends JpaRepository<School, Long>, SchoolCustomizedRepository {

}
