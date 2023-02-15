package com.example.mailbe.Service.Command.UserCommand;

import com.example.mailbe.Repository.ContactRepo;
import com.example.mailbe.Service.Command.AbstractCommand;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;

@Transactional
public class DeleteEmailsFromContactCommand extends AbstractCommand<Void> {


    private final ContactRepo contactRepo;
    private final List<String> emails;
    private final String contactId;

    public DeleteEmailsFromContactCommand(ContactRepo contactRepo, List<String> emails, String contactId) {
        this.contactRepo = contactRepo;
        this.emails = emails;
        this.contactId = contactId;
    }

    @Override
    public Void execute(){
        HashSet<String> emailsList = contactRepo.findContactByContactId(contactId).getEmails();
        emailsList.remove("");
        for (var email : emails){
            emailsList.remove(email);
        }
        return null;
    }
}
