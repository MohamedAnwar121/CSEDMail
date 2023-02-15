package com.example.mailbe.Service;


import com.example.mailbe.Model.Contact;
import com.example.mailbe.Repository.ContactRepo;
import com.example.mailbe.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;

@Component
public class ValidationService {

    private final UserRepo userRepo;
    private final ContactRepo contactRepo;


    @Autowired
    public ValidationService(UserRepo userRepo, ContactRepo contactRepo) {
        this.userRepo = userRepo;
        this.contactRepo = contactRepo;
    }


    public void chechEmailTaken(String email){
        if (userRepo.existsUserByEmail(email)) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "email already taken"
            );
        }
    }


    public void checkEmailsInDB(HashSet<String> emails) {

        for (var email : emails) {
            if (!userRepo.existsUserByEmail(email)) {
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "email not found"
                );
            }
        }

    }

    public void checkContactNameUnique(String contactId, String newName) {
        List<Contact> test = contactRepo.findContactByContactId(contactId).getUser().getContacts();

        List<Contact> contacts = contactRepo.findContactByContactId(contactId).getUser().getContacts();

        for (var contact : contacts){
            if (contact.getName().equals(newName)){
                throw new ResponseStatusException(
                        HttpStatus.CONFLICT, "name is repeated"
                );
            }
        }

    }
}
