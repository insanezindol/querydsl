package com.example.querydsl.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Staff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer age;

    @Builder
    public Staff(Long id, String name, Integer age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

}
