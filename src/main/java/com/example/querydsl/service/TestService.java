package com.example.querydsl.service;

import com.example.querydsl.entity.Store;
import com.example.querydsl.repository.StoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestService {

    @Autowired
    StoreRepository storeRepository;

    public List<Store> getStore() {
        return storeRepository.findAll();
    }

    public Store getStore(long storeId) {
        return storeRepository.findById(storeId).orElse(null);
    }

    public List<Store> getStore(String name, String address) {
        return storeRepository.findByNameAndAddress(name, address);
    }

    public List<Store> searchStore(String name) {
        storeRepository.findByNameContaining(name);
        storeRepository.findByNameContains(name);
        storeRepository.findByNameIsContaining(name);
        storeRepository.findByNameLike('%'+name+'%');
        storeRepository.findByNameStartsWith(name);
        storeRepository.findByNameEndsWith(name);
        storeRepository.findByNameContainingIgnoreCase(name);
        storeRepository.findByNameNotContaining(name);
        return null;
    }

    public Store insertStore(Store store) {
        return storeRepository.save(store);
    }

    public Store updateStore(Store store) {
        return storeRepository.save(store);
    }

    public void deleteStore(long storeId) {
        Store store = Store.builder()
                .id(storeId)
                .build();
        storeRepository.delete(store);
    }

}
