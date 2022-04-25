package com.example.querydsl;

import com.example.querydsl.entity.Member;
import com.example.querydsl.entity.Company;
import com.example.querydsl.repository.CompanyRepository;
import com.example.querydsl.repository.MemberRepository;
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
    MemberRepository memberRepository;

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

        Company company = Company.builder()
                .address(address)
                .name(name)
                .build();
        System.out.println("test1");
        companyRepository.save(company);
        System.out.println("test2");

        Member member1 = Member.builder()
                .name(memberName1)
                .age(age1)
                .company(company)
                .build();
        System.out.println("test3");
        memberRepository.save(member1);
        System.out.println("test4");

        Member member2 = Member.builder()
                .name(memberName2)
                .age(age2)
                .company(company)
                .build();
        System.out.println("test5");
        memberRepository.save(member2);
        System.out.println("test6");

        company.setMembers(Arrays.asList(member1, member2));
        System.out.println("test7");

        //when
        List<MemberVO> members = companyRepositorySupport.findMembersByName(name);

        //then
        assertThat(members.size()).isGreaterThan(0);
        assertThat(members.get(0).getName()).isEqualTo(memberName1);
        assertThat(members.get(1).getName()).isEqualTo(memberName2);
    }

}
