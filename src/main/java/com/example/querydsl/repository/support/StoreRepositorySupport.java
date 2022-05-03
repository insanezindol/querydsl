package com.example.querydsl.repository.support;

import com.example.querydsl.entity.Staff;
import com.example.querydsl.entity.Store;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.querydsl.entity.QStaff.staff;
import static com.example.querydsl.entity.QStore.store;

@Repository
public class StoreRepositorySupport extends QuerydslRepositorySupport {

    private final JPAQueryFactory jpaQueryFactory;

    public StoreRepositorySupport(JPAQueryFactory jpaQueryFactory) {
        super(Store.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public PageImpl<Store> findStoreJoinOrderPaging(Pageable pageable) {
        // JPQLQuery를 생성합니다.
        JPAQuery<Store> query = jpaQueryFactory
                .selectFrom(store)
                .join(store.staffs, staff);

        // JPQLQuery로 TotalCount를 조회합니다.
        long totalCount = query.fetchCount();

        // QuerydslRepositorySupport의 getQuerydsl() 메소드를 이용합니다.
        // Pageable 객체의 page, limit, sort값을 적용합니다.
        // 적용된 쿼리를 이용하여 데이터를 조회합니다.
        List<Store> results = getQuerydsl().applyPagination(pageable, query).fetch();  // pageable 적용하여 query 실행
        // PageImpl 객체로 리턴합니다.
        return new PageImpl<>(results, pageable, totalCount);
    }

}
