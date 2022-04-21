package com.example.querydsl.vo;

import com.example.querydsl.entity.Member;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class CompanyVO {
    private Long id;
    private String name;
    private String address;
    private List<Member> member;
}
