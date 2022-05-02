package com.example.querydsl.repository;

import com.example.querydsl.entity.Student;
import com.example.querydsl.vo.StudentVo;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public interface StudentCustomizedRepository {

    PageImpl<Student> findStudentsByNamePaging(String name, Pageable pageable);

    Long updateAddressByName(String oldName, String newName);

}
