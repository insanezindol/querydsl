package com.example.querydsl;

import com.example.querydsl.entity.Staff;
import com.example.querydsl.entity.Store;
import com.example.querydsl.repository.StoreRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@ActiveProfiles("local")
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class JpaTest {

    @Autowired
    private StoreRepository storeRepository;

    /**
     * Repository interface에 메소드를 생성할 수 있습니다.
     * 1. findBy{Column}({value})
     * - select * from {entity} where {column} =  {value}
     * <p>
     * 2. findBy{Column1}And{Column2}({value1}, {value2})
     * - select * from {entity} where {column1} = {value1} and {column2} = {value2}
     * <p>
     * 3. findBy{Column1}And{Column2}({value1}, {value2})
     * - select * from {entity} where {column1} = {value1} and {column2} = {value2}
     */

    @Test
    @DisplayName("entity 저장 후 조회")
    void insertTest() {
        // given
        final Long id = 1L;
        final String storeName = "스토어1";
        final String storeAddress = "주소1";

        // Entity Store객체를 Builder를 통해 생성한다.
        Store store = Store.builder()
                .id(id)
                .name(storeName)
                .address(storeAddress)
                .build();
        // storeRepository를 통해 저장한다.
        storeRepository.save(store);

        // when
        // storeRepository에서 name으로 조회
        Store resultStore = storeRepository.findByName(storeName);

        // then
        // 저장한 데이터와 조회된 데이터 비교한다.
        Assertions.assertEquals(resultStore.getName(), storeName);
    }

    @Test
    @DisplayName("수정 테스트")
    void updateTest() {
        // given
        final Long id = 1L;
        final String storeName = "스토어2";
        final String storeAddress2 = "주소2";
        final String storeAddress3 = "주소3";

        // Entity Store객체를 Builder를 통해 생성한다.
        Store store = Store.builder()
                .id(id)
                .name(storeName)
                .address(storeAddress2)
                .build();

        // when
        // storeRepository를 통해 조회 -> ID 없음 -> 등록한다.
        Store insertStore = storeRepository.save(store);

        // Entity Store객체를 Builder를 통해 생성한다.
        Store store2 = Store.builder()
                .id(id)
                .name(storeName)
                .address(storeAddress3)
                .build();

        // storeRepository를 통해 조회 -> ID 있음 -> 수정한다.
        Store updateStore = storeRepository.save(store2);

        // then
        // 저장한 데이터와 조회된 데이터 비교한다.
        Assertions.assertEquals(updateStore.getName(), insertStore.getName());
        Assertions.assertNotEquals(insertStore.getAddress(), updateStore.getAddress());
    }

    @Test
    @DisplayName("Store, Staff entity 저장")
    void saveRelationEntity() {
        // given
        final Long storeId = 2L;
        final String storeName = "store1234";
        final String storeAddress = "storeAddress";

        final String staffName = "staff1234";
        final Integer staffAge = 30;

        Staff staff1 = Staff.builder()
                .id(1L)
                .name(staffName)
                .age(staffAge)
                .build();
        Staff staff2 = Staff.builder()
                .id(2L)
                .name(staffName)
                .age(staffAge)
                .build();

        Store store = Store.builder()
                .id(storeId)
                .name(storeName)
                .address(storeAddress)
                .staffs(Arrays.asList(staff1, staff2))
                .build();

        // when
        // 일 대 다 관계인 Store(일) : Staff(다) 의 데이터 저장 방법이다.
        // Staff 객체를 생성 후 Store 객체에 넣어 생성 후 Repository를 이용하여 저장하면 Store, Staff 테이블에 데이터가 적재된다.
        Store saveStore = storeRepository.save(store);

        // then
        Assertions.assertEquals(saveStore.getName(), storeName);

        List<Staff> result = saveStore.getStaffs();
        Iterator<Staff> iterator = result.iterator();
        Staff next = iterator.next();
        Assertions.assertEquals(next.getName(), staffName);
    }

    @Test
    @DisplayName("Store, Staff entity 저장 후 조회")
    @Transactional
    void saveRelationEntityAndSearch() {
        // given
        final Long storeId = 2L;
        final String storeName = "store1234";
        final String storeAddress = "storeAddress";

        final String staffName = "staff1234";
        final Integer staffAge = 30;

        Staff staff1 = Staff.builder()
                .id(1L)
                .name(staffName)
                .age(staffAge)
                .build();
        Staff staff2 = Staff.builder()
                .id(2L)
                .name(staffName)
                .age(staffAge)
                .build();

        Store store = Store.builder()
                .id(storeId)
                .name(storeName)
                .address(storeAddress)
                .staffs(Arrays.asList(staff1, staff2))
                .build();

        // when
        // 일 대 다 관계인 Store(일) : Staff(다) 의 데이터 저장 방법이다.
        // Staff 객체를 생성 후 Store 객체에 넣어 생성 후 Repository를 이용하여 저장하면 Store, Staff 테이블에 데이터가 적재된다.
        Store saveStore = storeRepository.save(store);

        // then
        Assertions.assertEquals(saveStore.getName(), storeName);
    }

}
