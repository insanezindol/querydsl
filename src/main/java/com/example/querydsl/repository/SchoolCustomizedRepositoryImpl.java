package com.example.querydsl.repository;

import com.example.querydsl.entity.School;
import com.example.querydsl.vo.StudentVo;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.example.querydsl.entity.QSchool.school;
import static com.example.querydsl.entity.QStudent.student;

@RequiredArgsConstructor
public class SchoolCustomizedRepositoryImpl implements SchoolCustomizedRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<School> findByName(String name) {
        return jpaQueryFactory
                .selectFrom(school)
                .where(school.name.eq(name))
                .fetch();
    }

    @Override
    public School findOneByName(String name) {
        return jpaQueryFactory
                .selectFrom(school)
                .where(school.name.eq(name))
                .fetchOne();
    }

    @Override
    public List<StudentVo> findStudentsByName(String name) {
        return jpaQueryFactory
                .select(Projections.fields(StudentVo.class,
                        student.id, student.age, student.name
                ))
                .from(school)
                .join(student)
                .on(school.id.eq(student.schoolId))
                .where(school.name.eq(name))
                .fetch();
    }

}
