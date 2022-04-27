package com.example.querydsl.service;

import com.example.querydsl.entity.Store;
import com.example.querydsl.repository.SchoolRepository;
import com.example.querydsl.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TestService {

    private final StoreRepository storeRepository;

    private final SchoolRepository schoolRepository;

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
        List<Store> searchA = storeRepository.findByNameContaining(name);
        log.info("searchA : {}", searchA.size());
        List<Store> searchB = storeRepository.findByNameContains(name);
        log.info("searchB : {}", searchB.size());
        List<Store> searchC = storeRepository.findByNameIsContaining(name);
        log.info("searchC : {}", searchC.size());
        List<Store> searchD = storeRepository.findByNameLike('%' + name + '%');
        log.info("searchD : {}", searchD.size());
        List<Store> searchE = storeRepository.findByNameStartsWith(name);
        log.info("searchE : {}", searchE.size());
        List<Store> searchF = storeRepository.findByNameEndsWith(name);
        log.info("searchF : {}", searchF.size());
        List<Store> searchG = storeRepository.findByNameContainingIgnoreCase(name);
        log.info("searchG : {}", searchG.size());
        List<Store> searchH = storeRepository.findByNameNotContaining(name);
        log.info("searchH : {}", searchH.size());
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
