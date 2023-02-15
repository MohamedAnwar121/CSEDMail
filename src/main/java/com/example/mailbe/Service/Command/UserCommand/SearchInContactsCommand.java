package com.example.mailbe.Service.Command.UserCommand;

import com.example.mailbe.Model.Contact;
import com.example.mailbe.Repository.ContactRepo;
import com.example.mailbe.Service.Command.AbstractCommand;
import com.example.mailbe.Service.Factory.ContactCriteriaFactory;
import com.example.mailbe.Service.Filter.ContactCriterias.SearchInContactCriteria;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Transactional
public class SearchInContactsCommand extends AbstractCommand<List<Contact>> {

    private final String userId;
    private final String col;
    private final String key;
    private final ContactRepo contactRepo;

    public SearchInContactsCommand(String userId, String col, String key, ContactRepo contactRepo) {
        this.userId = userId;
        this.col = col;
        this.key = key;
        this.contactRepo = contactRepo;
    }

    @Override
    public  List<Contact> execute(){

        SearchInContactCriteria searchInContactCriteria =
                (SearchInContactCriteria) new ContactCriteriaFactory(contactRepo).getCriteria("searchInContactCriteria");

        return searchInContactCriteria.meetCriteria(userId,col,key);

        /*return switch (col) {
            case "name" -> contactRepo.findAllByUser_UserIdAndNameContainingIgnoreCase(userId, key);
            //case "emails" -> contactRepo.findAllByUser_UserIdAndEmailsContaining(userId, key);
            default -> null;
        };*/
    }
}
