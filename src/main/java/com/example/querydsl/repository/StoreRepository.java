package com.example.querydsl.repository;

import com.example.querydsl.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoreRepository extends JpaRepository<Store, Long> {

    Store findByName(String name);

    List<Store> findByNameAndAddress(String name, String address);

    List<Store> findByNameContaining(String name);

    List<Store> findByNameContains(String name);

    List<Store> findByNameIsContaining(String name);

    List<Store> findByNameLike(String s);

    List<Store> findByNameStartsWith(String name);

    List<Store> findByNameEndsWith(String name);

    List<Store> findByNameContainingIgnoreCase(String name);

    List<Store> findByNameNotContaining(String name);

}
