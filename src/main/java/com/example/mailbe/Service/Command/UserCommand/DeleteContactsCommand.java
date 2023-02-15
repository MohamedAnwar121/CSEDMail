package com.example.mailbe.Service.Command.UserCommand;

import com.example.mailbe.Repository.ContactRepo;
import com.example.mailbe.Service.Command.AbstractCommand;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Transactional
public class DeleteContactsCommand extends AbstractCommand<Void> {

    private final String userId;
    private final List<String> contactIds;
    private final ContactRepo contactRepo;

    public DeleteContactsCommand(String userId, List<String> contactIds, ContactRepo contactRepo) {
        this.userId = userId;
        this.contactIds = contactIds;
        this.contactRepo = contactRepo;
    }

    @Override
    public Void execute(){
        for (var contactId : contactIds){
            contactRepo.deleteContactByContactId(contactId);
        }
        return null;
    }
}
