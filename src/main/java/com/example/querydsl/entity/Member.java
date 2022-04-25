package com.example.querydsl.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer age;

    @ManyToOne(targetEntity = Company.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", foreignKey = @ForeignKey(name = "fk_member_company_id"))
    private Company company;

    @Builder
    public Member(Long id, String name, Integer age, Company company) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.company = company;
    }

}
