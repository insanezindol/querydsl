package com.example.querydsl.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String address;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "store_id")
    private List<Member> member = new ArrayList<>();

    @Builder
    public Company(Long id, String name, String address, List<Member> member) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.member = member;
    }

}
