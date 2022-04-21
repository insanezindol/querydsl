package com.example.querydsl.repository;

import com.example.querydsl.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StaffRepository extends JpaRepository<Staff, Long> {

}
