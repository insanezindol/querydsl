package com.example.querydsl.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String address;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "store_id")
    private List<Staff> staffs = new ArrayList<>();

    @Builder
    public Store(Long id, String name, String address, List<Staff> staffs) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.staffs = staffs;
    }

    public void changeName(String name) {
        this.name = name;
    }

    public void addStaff(Staff staff) {
        if (this.staffs == null) {
            this.staffs = new ArrayList<>();
        }
        this.staffs.add(staff);
        staff.changeStore(this);
    }

}
