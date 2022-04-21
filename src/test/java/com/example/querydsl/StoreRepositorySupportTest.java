package com.example.querydsl;

import com.example.querydsl.entity.Member;
import com.example.querydsl.entity.Company;
import com.example.querydsl.repository.CompanyRepository;
import com.example.querydsl.repository.support.CompanyRepositorySupport;
import com.example.querydsl.vo.MemberVO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("local")
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class StoreRepositorySupportTest {

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    CompanyRepositorySupport companyRepositorySupport;

    @Test
    void findOneByNameTest() {
        //given
        final String address = "주소3";
        final String name = "스토어3";

        Company company = Company.builder()
                .id(1L)
                .address(address)
                .name(name)
                .build();

        companyRepository.save(company);

        //when
        Company resultByStore = companyRepositorySupport.findOneByName(name);

        //then
        Assertions.assertEquals(name, resultByStore.getName());
    }

    @Test
    @Transactional
    void findMembersByNameTest() {
        //given
        final String memberName1 = "memberName1";
        final Integer age1 = 31;

        final String memberName2 = "memberName2";
        final Integer age2 = 41;

        final String address = "주소4";
        final String name = "스토어4";

        Member member1 = Member.builder()
                .id(1L)
                .name(memberName1)
                .age(age1)
                .build();

        Member member2 = Member.builder()
                .id(2L)
                .name(memberName2)
                .age(age2)
                .build();

        Company company = Company.builder()
                .id(1L)
                .address(address)
                .name(name)
                .member(Arrays.asList(member1, member2))
                .build();

        companyRepository.save(company);

        //when
        List<MemberVO> members = companyRepositorySupport.findMembersByName(name);

        //then
        assertThat(members.size()).isGreaterThan(0);
        assertThat(members.get(0).getName()).isEqualTo(memberName1);
        assertThat(members.get(1).getName()).isEqualTo(memberName2);
    }

}
