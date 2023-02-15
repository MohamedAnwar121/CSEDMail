package com.example.mailbe.Service.Factory;


import com.example.mailbe.Repository.ContactRepo;
import com.example.mailbe.Repository.MailRepo;
import com.example.mailbe.Service.Builder.MailSchemaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class CriteriaFactoryProducer {

    private final MailRepo mailRepo;
    private final MailSchemaBuilder mailSchemaBuilder;

    @Autowired
    public CriteriaFactoryProducer(MailRepo mailRepo, MailSchemaBuilder mailSchemaBuilder) {
        this.mailRepo = mailRepo;
        this.mailSchemaBuilder = mailSchemaBuilder;
    }

    public Object getCriteriaFactory(String factory){
        return switch (factory){
            case "FolderCriteriaFactory" -> new FolderCriteriaFactory(mailRepo, mailSchemaBuilder);
            case "SearchInFolderCriteriaFactory" -> new SearchInFolderCriteriaFactory(mailRepo, mailSchemaBuilder);
            //case "ContactCriteriaFactory" -> new ContactCriteriaFactory(contactRepo);
            default -> null;
        };
    }

}
