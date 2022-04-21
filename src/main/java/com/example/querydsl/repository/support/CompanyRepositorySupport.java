package com.example.querydsl.repository.support;

import com.example.querydsl.entity.Company;
import com.example.querydsl.entity.Member;
import com.example.querydsl.vo.MemberVO;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.querydsl.entity.QCompany.company;
import static com.example.querydsl.entity.QMember.member;

@Repository
public class CompanyRepositorySupport extends QuerydslRepositorySupport {

    private final JPAQueryFactory jpaQueryFactory;

    public CompanyRepositorySupport(JPAQueryFactory jpaQueryFactory) {
        super(Company.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public Company findOneByName(String name) {
        return jpaQueryFactory
                .selectFrom(company)
                .where(company.name.eq(name))
                .fetchOne();
    }

    public List<MemberVO> findMembersByName(String name) {
        return jpaQueryFactory
                .select(Projections.fields(MemberVO.class,
                        member.id,
                        member.age,
                        member.name
                        ))
//                .select(member.id, member.age, member.name)
                .from(company)
                .join(company.member, member)
                .where(company.name.eq(name))
                .fetch();
    }

}
