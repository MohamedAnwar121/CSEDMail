package com.example.mailbe.Service.Command.UserCommand;

import com.example.mailbe.Repository.ContactRepo;
import com.example.mailbe.Service.Command.AbstractCommand;
import org.springframework.transaction.annotation.Transactional;


@Transactional
public class UpdateNameInContactCommand extends AbstractCommand<Void> {

    private final String contactId;
    private final String newName;
    private final ContactRepo contactRepo;

    public UpdateNameInContactCommand(String contactId, String newName, ContactRepo contactRepo) {
        this.contactId = contactId;
        this.newName = newName;
        this.contactRepo = contactRepo;
    }

    @Override
    public Void execute(){
        contactRepo.findContactByContactId(contactId).setName(newName);
        return null;
    }
}
