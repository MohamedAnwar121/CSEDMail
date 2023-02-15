package com.example.mailbe.Service.Filter.ContactCriterias;

import com.example.mailbe.Model.Contact;
import com.example.mailbe.Repository.ContactRepo;

import java.util.ArrayList;
import java.util.List;

public class SearchInContactCriteria {

    private final ContactRepo contactRepo;

    public SearchInContactCriteria(ContactRepo contactRepo) {
        this.contactRepo = contactRepo;
    }

    public List<Contact> meetCriteria(String userId,String searchCol, String key) {
        return switch (searchCol){
            case "name" -> contactRepo.findAllByUser_UserIdAndNameContainingIgnoreCase(userId,key);
            case "emails" -> searchInEmails(userId,key);
            default -> null;
        };
    }

    private List<Contact> searchInEmails(String userId, String key){

        List<Contact> allContacts = contactRepo.findAllByUser_UserId(userId);
        List<Contact> results = new ArrayList<>();

        for (var contact : allContacts){
            String emailsAsString = String.join("", contact.getEmails()).toLowerCase();
            if (emailsAsString.contains(key.toLowerCase())){
                results.add(contact);
            }
        }
        return results;
    }
}
