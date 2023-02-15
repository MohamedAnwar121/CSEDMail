package com.example.mailbe.Service.Factory;

import com.example.mailbe.Repository.ContactRepo;
import com.example.mailbe.Service.Filter.ContactCriterias.ContactSortedByCriteria;
import com.example.mailbe.Service.Filter.ContactCriterias.SearchInContactCriteria;

public class ContactCriteriaFactory {


    private final ContactRepo contactRepo;

    public ContactCriteriaFactory(ContactRepo contactRepo) {
        this.contactRepo = contactRepo;
    }

    public Object getCriteria(String criteria){
        return switch (criteria){
            case "searchInContactCriteria" -> new SearchInContactCriteria(contactRepo);
            case "contactSortedByCriteria" -> new ContactSortedByCriteria(contactRepo);
            default -> null;
        };
    }

}
