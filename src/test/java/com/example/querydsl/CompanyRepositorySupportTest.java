package com.example.querydsl;

import com.example.querydsl.entity.Company;
import com.example.querydsl.entity.Member;
import com.example.querydsl.repository.CompanyRepository;
import com.example.querydsl.repository.MemberRepository;
import com.example.querydsl.repository.support.CompanyRepositorySupport;
import com.example.querydsl.vo.CompanyVO;
import com.example.querydsl.vo.MemberVO;
import org.junit.jupiter.api.Assertions;
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

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("local")
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CompanyRepositorySupportTest {

    @Autowired
    CompanyRepository companyRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    CompanyRepositorySupport companyRepositorySupport;

    @BeforeEach
    void beforeEach() {
        System.out.println("[BEG] @BeforeEach");

        final String name = "진형의회사";
        final String address = "서울시 강남구";

        List<Company> companies = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Company company = Company.builder()
                    .name(name + "_" + i)
                    .address(address + "_" + i)
                    .build();
            companies.add(company);
        }
        companyRepository.saveAll(companies);

        System.out.println("[END] @BeforeEach");
    }

    @Test
    @DisplayName("회사 등록 후 회사 이름으로 조회 테스트")
    void findOneByNameTest() {
        //given
        final String name = "다른회사";
        final String address = "서울시 중구";

        Company company = Company.builder()
                .name(name)
                .address(address)
                .build();
        companyRepository.save(company);

        //when
        Company resultByStore = companyRepositorySupport.findOneByName(name);

        //then
        Assertions.assertEquals(name, resultByStore.getName());
    }

    @Test
    @Transactional
    @DisplayName("등록된 회사 1개 조회 후 직원 2명 등록 테스트")
    void findMembersByNameTest() {
        //given
        final String name = "진형의회사_10";

        final String memberName1 = "30대직원";
        final Integer age1 = 31;

        final String memberName2 = "40대직원";
        final Integer age2 = 41;

        Company company = companyRepositorySupport.findOneByName(name);

        Member member1 = Member.builder()
                .name(memberName1)
                .age(age1)
                .company(company)
                .build();
        Member member2 = Member.builder()
                .name(memberName2)
                .age(age2)
                .company(company)
                .build();
        memberRepository.saveAll(Arrays.asList(member1, member2));

        //when
        List<MemberVO> members = companyRepositorySupport.findMembersByName(name);

        //then
        assertThat(members.size()).isGreaterThan(0);
        assertThat(members.get(0).getName()).isEqualTo(memberName1);
        assertThat(members.get(1).getName()).isEqualTo(memberName2);
    }


    @Test
    @DisplayName("등록된 회사 페이징 조회 테스트")
    void findPagingCompanysByNameTest() {
        //given
        final String name = "진형의회사";
        final int page = 0; // 페이지 번호 : 0부터 시작
        final int size = 5; // 페이지당 컨텐츠 수

        Sort.Order order = Sort.Order.desc("id");
        Sort sort = Sort.by(order);

        Pageable pageable = PageRequest.of(page, size, sort);

        //when
        PageImpl<CompanyVO> result = companyRepositorySupport.findCompanysByNamePaging(name, pageable);

        System.out.println("[logging] result.getTotalElements() : " + result.getTotalElements()); // 조회된 전체 건수
        System.out.println("[logging] result.getNumber() : " + result.getNumber()); // 현재 페이지 번호
        System.out.println("[logging] result.getTotalPages() : " + result.getTotalPages()); // 전체 페이지 숫자
        System.out.println("[logging] result.getContent().size() : " + result.getContent().size()); // 현재 페이지 컨텐츠 숫자

        List<CompanyVO> companyVOS = result.getContent();
        for (CompanyVO companyVO : companyVOS) {
            System.out.println("[logging] company info : [" + companyVO.getId() + "] " + companyVO.getName());
        }

        //then
        assertThat(result.getNumber()).isEqualTo(page);
        assertThat(result.getTotalPages()).isEqualTo(20);
        assertThat(result.getContent().size()).isEqualTo(5);
    }

}
