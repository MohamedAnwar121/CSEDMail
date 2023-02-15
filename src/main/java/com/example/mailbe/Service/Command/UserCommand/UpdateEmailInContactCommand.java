package com.example.mailbe.Service.Command.UserCommand;

import com.example.mailbe.Repository.ContactRepo;
import com.example.mailbe.Service.Command.AbstractCommand;
import org.springframework.transaction.annotation.Transactional;


@Transactional
public class UpdateEmailInContactCommand extends AbstractCommand<Void> {

    private final String contactId;
    private final String oldEmail;
    private final String newEmail;
    private final ContactRepo contactRepo;

    public UpdateEmailInContactCommand(String contactId, String oldEmail, String newEmail, ContactRepo contactRepo) {
        this.contactId = contactId;
        this.oldEmail = oldEmail;
        this.newEmail = newEmail;
        this.contactRepo = contactRepo;
    }

    @Override
    public Void execute(){

        contactRepo.findContactByContactId(contactId).getEmails().remove(oldEmail);
        contactRepo.findContactByContactId(contactId).getEmails().add(newEmail);

        return null;
    }
}
