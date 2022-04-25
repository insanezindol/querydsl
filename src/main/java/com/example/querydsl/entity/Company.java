package com.example.querydsl.entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String address;

    @OneToMany(mappedBy = "company")
    private List<Member> members = new ArrayList<>();

    @Builder
    public Company(Long id, String name, String address, List<Member> members) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.members = members;
    }

}
