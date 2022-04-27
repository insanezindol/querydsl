package com.example.querydsl.repository;

import com.example.querydsl.entity.Student;
import com.example.querydsl.vo.StudentVo;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.PathBuilderFactory;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.Querydsl;

import javax.persistence.EntityManager;
import java.util.List;

import static com.example.querydsl.entity.QStudent.student;

@RequiredArgsConstructor
public class StudentCustomizedRepositoryImpl implements StudentCustomizedRepository {

    private final JPAQueryFactory jpaQueryFactory;

    private final EntityManager entityManager;

    @Override
    public PageImpl<Student> findStudentsByNamePaging(String name, Pageable pageable) {
        JPQLQuery<Student> query = jpaQueryFactory
                .select(Projections.fields(Student.class,
                        student.id, student.age, student.name
                ))
                .from(student)
                .where(student.name.containsIgnoreCase(name));

        long totalCount = query.fetchCount();
        List<Student> results = new Querydsl(entityManager, new PathBuilderFactory().create(Student.class)).applyPagination(pageable, query).fetch();
        return new PageImpl<>(results, pageable, totalCount);
    }

}
