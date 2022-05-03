package com.example.querydsl;

import com.example.querydsl.entity.Staff;
import com.example.querydsl.entity.Store;
import com.example.querydsl.repository.StoreRepository;
import com.example.querydsl.repository.support.StoreRepositorySupport;
import com.example.querydsl.service.TestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("local")
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class StoreRepositoryTest {

    @Autowired
    TestService testService;

    @Autowired
    StoreRepository storeRepository;

    @Autowired
    StoreRepositorySupport storeRepositorySupport;

    @BeforeEach
    void setup() {
        final String storeName = "가게_";
        final String staffName1 = "직원_1";
        final String staffName2 = "직원_2";
        final String address = "강남구";

        List<Store> stores = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Staff staff1 = Staff.builder()
                    .name(storeName + i + "_" + staffName1)
                    .age(30)
                    .build();
            Staff staff2 = Staff.builder()
                    .name(storeName + i + "_" + staffName2)
                    .age(40)
                    .build();
            Store store = Store.builder()
                    .name(storeName + i)
                    .address(address)
                    .staffs(Arrays.asList(staff1, staff2))
                    .build();
            stores.add(store);
        }
        storeRepository.saveAll(stores);
    }

    @Test
    @DisplayName("staff 명 조회 N + 1 테스트")
    void findStaffNamesTest() {
        //given -> @BeforeEach

        //when
        List<String> staffNames = testService.findAllStaffNames();
        // 1SQL: select store0_.id as id1_4_, store0_.address as address2_4_, store0_.name as name3_4_ from store store0_;
        // 2SQL:
        // select
        // staffs0_.store_id as store_id4_3_0_, staffs0_.id as id1_3_0_, staffs0_.id as id1_3_1_, staffs0_.age as age2_3_1_, staffs0_.name as name3_3_1_, staffs0_.store_id as store_id4_3_1_
        // from staff staffs0_
        // where staffs0_.store_id=1 -> 10 까지 반복 실행;

        //then
        assertThat(staffNames).isNotNull();
        assertThat(staffNames).hasSize(20);
    }

    @Test
    @DisplayName("distinct join fetch 테스트")
    void findStaffNamesJoinFetchTest() {
        //given -> @BeforeEach

        //when
        List<String> staffNames = testService.findAllStaffNamesJoinFetch();
        /*
        select
            distinct store0_.id as id1_4_0_, staffs1_.id as id1_3_1_, store0_.address as address2_4_0_, store0_.name as name3_4_0_, staffs1_.age as age2_3_1_, staffs1_.name as name3_3_1_, staffs1_.store_id as store_id4_3_1_, staffs1_.store_id as store_id4_3_0__, staffs1_.id as id1_3_0__
        from store store0_
            inner join staff staffs1_
            on store0_.id=staffs1_.store_id;
         */

        //then
        assertThat(staffNames).isNotNull();
        assertThat(staffNames).hasSize(20);
    }

    @Test
    @DisplayName("entity graph 테스트")
    void findStaffNamesEntityGraphTest() {
        //given -> @BeforeEach

        //when
        List<String> staffNames = testService.findAllStaffNamesEntityGraph();
        /*
         select
            store0_.id as id1_4_0_, staffs1_.id as id1_3_1_, store0_.address as address2_4_0_, store0_.name as name3_4_0_, staffs1_.age as age2_3_1_, staffs1_.name as name3_3_1_, staffs1_.store_id as store_id4_3_1_, staffs1_.store_id as store_id4_3_0__, staffs1_.id as id1_3_0__
         from store store0_
            left outer join staff staffs1_
            on store0_.id=staffs1_.store_id;
         */

        //then
        assertThat(staffNames).isNotNull();
        assertThat(staffNames).hasSize(20);
    }

    @Test
    @DisplayName("querydsl join 시 paging 테스트 - pageable 사용 경우 ")
    void findStaffJoinOrderPagingTest() {
        //given -> @BeforeEach

        // when
        Sort.Order storeNameOrder = Sort.Order.asc("name");          // store
        Sort.Order staffNameOrder = Sort.Order.asc("staffs.name");   // staff

        Sort sort = Sort.by(storeNameOrder, staffNameOrder);
        Pageable pageable = PageRequest.of(0, 10, sort);

        // then
        PageImpl<Store> result = storeRepositorySupport.findStoreJoinOrderPaging(pageable);  // test
        /*
        select
            count(store0_.id) as col_0_0_
        from store store0_
            inner join staff staffs1_
            on store0_.id=staffs1_.store_id;
        select
            store0_.id as id1_4_, store0_.address as address2_4_, store0_.name as name3_4_
        from store store0_
            inner join staff staffs1_
            on store0_.id=staffs1_.store_id
        order by
            store0_.name asc, staffs1_.name asc
        limit 10;
         */

        System.out.println("[logging] result.getTotalElements() : " + result.getTotalElements()); // 조회된 전체 건수
        System.out.println("[logging] result.getNumber() : " + result.getNumber()); // 현재 페이지 번호
        System.out.println("[logging] result.getTotalPages() : " + result.getTotalPages()); // 전체 페이지 숫자
        System.out.println("[logging] result.getContent().size() : " + result.getContent().size()); // 현재 페이지 컨텐츠 숫자

        List<Store> stores = result.getContent();
        for (Store store : stores) {
            System.out.println("[logging] Store : [" + store.getId() + "] " + store.getName());
        }

        //then
        assertThat(result.getNumber()).isEqualTo(0);
        assertThat(result.getTotalPages()).isEqualTo(2);
        assertThat(result.getContent().size()).isEqualTo(10);
    }

}
