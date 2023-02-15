package com.example.mailbe.Service.Command.UserCommand;

import com.example.mailbe.Repository.ContactRepo;
import com.example.mailbe.Service.Command.AbstractCommand;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.Email;
import java.util.HashSet;

@Transactional
public class AddEmailToContactCommand extends AbstractCommand<Void> {



    @Email
    private final String email;
    private final String contactId;
    private final ContactRepo contactRepo;

    public AddEmailToContactCommand(ContactRepo contactRepo, String email, String contactId) {
        this.contactRepo = contactRepo;
        this.email = email;
        this.contactId = contactId;
    }

    @Override
    public Void execute(){
        HashSet<String> set = contactRepo.findContactByContactId(contactId)
                .getEmails();

        set.remove("");
        contactRepo.findContactByContactId(contactId)
                .getEmails().add(email);
        return null;
    }
}
