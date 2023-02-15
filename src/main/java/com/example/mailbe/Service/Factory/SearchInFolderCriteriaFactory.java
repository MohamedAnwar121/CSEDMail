package com.example.mailbe.Service.Factory;

import com.example.mailbe.Repository.MailRepo;
import com.example.mailbe.Service.Builder.MailSchemaBuilder;
import com.example.mailbe.Service.Filter.SearchInFolderCriterias.SearchInFolderCriteria;
import com.example.mailbe.Service.Filter.SearchInFolderCriterias.SearchInInboxCriteria;
import com.example.mailbe.Service.Filter.SearchInFolderCriterias.SearchInSentOrDraftCriteria;
import com.example.mailbe.Service.Filter.SearchInFolderCriterias.SearchInTrashOrCustomFolderCriteria;

public class SearchInFolderCriteriaFactory {

    private final MailRepo mailRepo;
    private final MailSchemaBuilder mailSchemaBuilder;

    public SearchInFolderCriteriaFactory(MailRepo mailRepo, MailSchemaBuilder mailSchemaBuilder){
        this.mailRepo = mailRepo;
        this.mailSchemaBuilder = mailSchemaBuilder;
    }

    public SearchInFolderCriteria getCriteria(String criteria){
        return switch (criteria){
            case "Inbox" -> new SearchInInboxCriteria(mailRepo, mailSchemaBuilder);
            case "Draft", "Sent" -> new SearchInSentOrDraftCriteria(mailRepo, mailSchemaBuilder);
            case "Trash", "CustomFolder" -> new SearchInTrashOrCustomFolderCriteria(mailRepo, mailSchemaBuilder);
            default -> null;
        };
    }


}
