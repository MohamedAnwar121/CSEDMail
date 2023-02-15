package com.example.mailbe.Service.Command.UserCommand;

import com.example.mailbe.Model.Contact;
import com.example.mailbe.Repository.ContactRepo;
import com.example.mailbe.Repository.UserRepo;
import com.example.mailbe.Service.Command.AbstractCommand;
import com.example.mailbe.Service.Factory.ContactCriteriaFactory;
import com.example.mailbe.Service.Factory.CriteriaFactoryProducer;
import com.example.mailbe.Service.Filter.ContactCriterias.ContactSortedByCriteria;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Transactional
public class GetAllContactsCommand extends AbstractCommand<List<Contact>> {

    private final String userId;
    private final String column;
    private final ContactRepo contactRepo;
    private final UserRepo userRepo;

    public GetAllContactsCommand(String userId, String column, ContactRepo contactRepo, UserRepo userRepo) {
        this.userId = userId;
        this.column = column;
        this.contactRepo = contactRepo;
        this.userRepo = userRepo;
    }

    @Override
    public List<Contact> execute(){

        ContactSortedByCriteria contactSortedByCriteria = (ContactSortedByCriteria)
                new ContactCriteriaFactory(contactRepo).getCriteria("contactSortedByCriteria");

        return contactSortedByCriteria.meetCriteria(userId,column);

        /*return switch (column){
            case "name" -> contactRepo.findAllByUser_UserIdOrderByName(userId);
            case "emails" -> contactRepo.findAllByUser_UserIdOrderByEmails(userId);
            default -> contactRepo.findAllByUser_UserId(userId);
        };*/
    }
}
