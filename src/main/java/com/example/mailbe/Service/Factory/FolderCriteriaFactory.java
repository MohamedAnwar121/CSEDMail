package com.example.mailbe.Service.Factory;

import com.example.mailbe.Repository.MailRepo;
import com.example.mailbe.Service.Builder.MailSchemaBuilder;
import com.example.mailbe.Service.Filter.FolderCriterias.FolderCriteria;
import com.example.mailbe.Service.Filter.FolderCriterias.InboxCriteria;
import com.example.mailbe.Service.Filter.FolderCriterias.SentOrDraftCriteria;
import com.example.mailbe.Service.Filter.FolderCriterias.TrashOrCustomFolderCriteria;

public class FolderCriteriaFactory {

    private final MailRepo mailRepo;
    private final MailSchemaBuilder mailSchemaBuilder;

    public FolderCriteriaFactory(MailRepo mailRepo, MailSchemaBuilder mailSchemaBuilder){
        this.mailRepo = mailRepo;
        this.mailSchemaBuilder = mailSchemaBuilder;
    }

    public FolderCriteria getCriteria(String criteria){
        return switch (criteria){
            case "Inbox" -> new InboxCriteria(mailRepo,mailSchemaBuilder);
            case "Draft", "Sent" -> new SentOrDraftCriteria(mailRepo, mailSchemaBuilder);
            default -> new TrashOrCustomFolderCriteria(mailRepo, mailSchemaBuilder);
        };
    }

}
