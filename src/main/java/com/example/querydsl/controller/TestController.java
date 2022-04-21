package com.example.querydsl.controller;

import com.example.querydsl.entity.Store;
import com.example.querydsl.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/jpa")
@Slf4j
public class TestController {

    private final TestService testService;

    public TestController(TestService testService) {
        this.testService = testService;
    }

    @GetMapping("/allStore")
    public ResponseEntity getAllStore() {
        log.info("getAllStore");
        return ResponseEntity.ok(testService.getStore());
    }

    @GetMapping("/store/{storeId}")
    public ResponseEntity getStoreById(@PathVariable String storeId) {
        log.info("getStoreById");
        return ResponseEntity.ok(testService.getStore(Long.parseLong(storeId)));
    }

    @GetMapping("/store")
    public ResponseEntity getStoreByNameAndAddress(@RequestParam String name, @RequestParam String address) {
        log.info("getStoreByNameAndAddress");
        return ResponseEntity.ok(testService.getStore(name, address));
    }

    @GetMapping("/search-store")
    public ResponseEntity searchStore(@RequestParam String name) {
        log.info("searchStore");
        return ResponseEntity.ok(testService.searchStore(name));
    }

    @PostMapping("/store")
    public ResponseEntity postStore(@RequestBody Store store) {
        log.info("postStore");
        return ResponseEntity.ok(testService.insertStore(store));
    }

    @PutMapping("/store")
    public ResponseEntity putStore(@RequestBody Store store) {
        log.info("putStore");
        return ResponseEntity.ok(testService.updateStore(store));
    }

    @DeleteMapping("/store/{storeId}")
    public ResponseEntity deleteStore(@PathVariable String storeId) {
        log.info("deleteStore");
        testService.deleteStore(Long.parseLong(storeId));
        return ResponseEntity.ok("OK");
    }

}
