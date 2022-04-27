package com.example.querydsl.repository;

import com.example.querydsl.entity.School;
import com.example.querydsl.vo.StudentVo;

import java.util.List;

public interface SchoolCustomizedRepository {

    List<School> findByName(String name);
    School findOneByName(String name);
    List<StudentVo> findStudentsByName(String name);

}
