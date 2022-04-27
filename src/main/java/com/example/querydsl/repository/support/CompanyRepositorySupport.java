package com.example.querydsl.repository.support;

import com.example.querydsl.entity.Company;
import com.example.querydsl.vo.CompanyVO;
import com.example.querydsl.vo.MemberVO;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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

    /**
     * Entity 관계 매핑 되어 있는 경우 -> 관계 매핑일 경우 : join(store.staff, staff)
     *
     * @param name
     * @return
     */
    public List<MemberVO> findMembersByName(String name) {
        return jpaQueryFactory
                .select(Projections.fields(MemberVO.class,
                        member.id, member.age, member.name
                ))
                .from(company)
                .join(company.members, member)
                .where(company.name.eq(name))
                .fetch();
    }

    public PageImpl<CompanyVO> findCompanysByNamePaging(String name, Pageable pageable) {
        // JPQLQuery를 생성합니다.
        JPQLQuery<CompanyVO> query = jpaQueryFactory
                .select(Projections.fields(CompanyVO.class,
                        company.id, company.name, company.address
                ))
                .from(company)
                .where(company.name.containsIgnoreCase(name));
        // JPQLQuery로 TotalCount를 조회합니다.
        long totalCount = query.fetchCount();

        // QuerydslRepositorySupport의 getQuerydsl() 메소드를 이용합니다.
        // Pageable 객체의 page, limit, sort값을 적용합니다.
        // 적용된 쿼리를 이용하여 데이터를 조회합니다.
        List<CompanyVO> results = getQuerydsl().applyPagination(pageable, query).fetch();

        // PageImpl 객체로 리턴합니다.
        return new PageImpl<>(results, pageable, totalCount);
    }

}
