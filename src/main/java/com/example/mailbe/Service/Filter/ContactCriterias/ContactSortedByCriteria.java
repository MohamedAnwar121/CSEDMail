package com.example.mailbe.Service.Filter.ContactCriterias;

import com.example.mailbe.Model.Contact;
import com.example.mailbe.Repository.ContactRepo;

import java.util.List;

public class ContactSortedByCriteria{


    private final ContactRepo contactRepo;

    public ContactSortedByCriteria(ContactRepo contactRepo) {
        this.contactRepo = contactRepo;
    }

    public List<Contact> meetCriteria(String userId, String sortCol) {
        return switch (sortCol){
            case "name" -> contactRepo.findAllByUser_UserIdOrderByName(userId);
            case "emails" -> contactRepo.findAllByUser_UserIdOrderByEmails(userId);
            default -> contactRepo.findAllByUser_UserId(userId);
        };
    }
}
