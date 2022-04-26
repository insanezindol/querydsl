package com.example.querydsl.repository.support;

import com.example.querydsl.entity.School;
import com.example.querydsl.vo.StudentVo;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.querydsl.entity.QSchool.school;
import static com.example.querydsl.entity.QStudent.student;

@Repository
public class SchoolRepositorySupport extends QuerydslRepositorySupport {

    private final JPAQueryFactory jpaQueryFactory;

    /**
     * Creates a new {@link QuerydslRepositorySupport} instance for the given domain type.
     *
     * @param domainClass must not be {@literal null}.
     */
    public SchoolRepositorySupport(JPAQueryFactory jpaQueryFactory) {
        super(School.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public List<School> findByName(String name) {
        return jpaQueryFactory
                .selectFrom(school)
                .where(school.name.eq(name))
                .fetch();
    }

    public School findOneByName(String name) {
        return jpaQueryFactory
                .selectFrom(school)
                .where(school.name.eq(name))
                .fetchOne();
    }

    /**
     * Entity 관계 매핑 되어 있지 않을 경우 -> 관계 없을 경우 : join(staff).on(store.id.eq(staff.storeId)
     *
     * @param name
     * @return
     */
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
