package com.example.querydsl.repository.support;

import com.example.querydsl.entity.Member;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import static com.example.querydsl.entity.QCompany.company;
import static com.example.querydsl.entity.QMember.member;

@Repository
public class MemberRepositorySupport extends QuerydslRepositorySupport {

    private final JPAQueryFactory jpaQueryFactory;

    public MemberRepositorySupport(JPAQueryFactory jpaQueryFactory){
        super(Member.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

}
