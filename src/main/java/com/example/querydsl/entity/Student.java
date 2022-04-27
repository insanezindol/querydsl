package com.example.querydsl.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer age;

    @Column(name = "school_id")
    private Long schoolId;

    @Builder
    public Student(Long id, String name, Integer age, Long schoolId) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.schoolId = schoolId;
    }

}
