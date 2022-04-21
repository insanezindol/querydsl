package com.example.querydsl.vo;

import com.example.querydsl.entity.Company;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberVO {
    private Long id;
    private String name;
    private Integer age;
    private Company company;
}
