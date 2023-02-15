package com.example.mailbe.Service;

import javax.annotation.Resource;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
public class InitiateFileSystemService implements CommandLineRunner {

    @Resource
    CacheManagmentService cacheManagmentService;

    @Override
    public void run(String... arg) {
        cacheManagmentService.deleteAll();
        cacheManagmentService.init();
    }
}
