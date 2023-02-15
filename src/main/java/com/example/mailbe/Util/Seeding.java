package com.example.mailbe.Util;

import com.example.mailbe.Model.Mail;
import com.example.mailbe.Model.User;
import com.example.mailbe.Repository.MailRepo;
import com.example.mailbe.Service.UserService;
import org.springframework.boot.CommandLineRunner;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

//@Component
public class Seeding implements CommandLineRunner {
    private final MailRepo mailRepo;
    private final UserService userService;

    public Seeding(MailRepo mailRepo, UserService userService) {
        this.mailRepo = mailRepo;
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        /*User user1 = new User(
                UUID.randomUUID().toString(),
                "emad1",
                "emad1@gmail.com",
                "123456",
                "Admin"
        );

        User user2 = new User(
                UUID.randomUUID().toString(),
                "emad2",
                "emad2@gmail.com",
                "123456",
                "Admin"
        );

        *//*Mail email1 = new Mail(
                UUID.randomUUID().toString(),
                user1.getId(),
                List.of(user2.getEmail()),
                "ss",
                "ss",
                "Inbox",
                Instant.now(),
                null

        );*//*
        userService.save(user1);
        userService.save(user2);
        mailRepo.saveAndFlush(email1);*/
    }
}
