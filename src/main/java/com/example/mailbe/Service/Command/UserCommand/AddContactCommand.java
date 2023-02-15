package com.example.mailbe.Service.Command.UserCommand;

import com.example.mailbe.Model.Contact;
import com.example.mailbe.Repository.ContactRepo;
import com.example.mailbe.Service.Command.AbstractCommand;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class AddContactCommand extends AbstractCommand<Void> {

    private final Contact contact;
    private final ContactRepo contactRepo;

    public AddContactCommand(Contact contact, ContactRepo contactRepo) {
        this.contact = contact;
        this.contactRepo = contactRepo;
    }

    @Override
    public Void execute() {
        contactRepo.saveAndFlush(contact);
        return null;
    }
}
