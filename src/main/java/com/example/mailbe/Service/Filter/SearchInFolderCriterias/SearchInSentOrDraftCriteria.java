package com.example.mailbe.Service.Filter.SearchInFolderCriterias;

import com.example.mailbe.Model.Attachment;
import com.example.mailbe.Model.Mail;
import com.example.mailbe.Repository.MailRepo;
import com.example.mailbe.Response.MailSchema;
import com.example.mailbe.Service.Builder.MailSchemaBuilder;
import com.example.mailbe.Util.Constants;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;

public class SearchInSentOrDraftCriteria implements SearchInFolderCriteria {

    private final MailRepo mailRepo;
    private final MailSchemaBuilder mailSchemaBuilder;

    public SearchInSentOrDraftCriteria(MailRepo mailRepo, MailSchemaBuilder mailSchemaBuilder) {
        this.mailRepo = mailRepo;
        this.mailSchemaBuilder = mailSchemaBuilder;
    }

    @Override
    public List<MailSchema> meetCriteria(String userEmail, String folder, int index, String col, String key) {
        List<Mail> mails = switch (col) {
            case "subject" ->
                    mailRepo.findAllBySenderAndFolderAndMailDetails_SubjectContaining(userEmail, folder, key, PageRequest.of(index, Constants.PAGE_SIZE));
            case "message" ->
                    mailRepo.findAllBySenderAndFolderAndMailDetails_MessageContaining(userEmail, folder, key, PageRequest.of(index, Constants.PAGE_SIZE));
            case "sended_at" ->
                    mailRepo.findAllBySenderAndFolderAndMailDetails_SendedAtContaining(userEmail, folder, key, PageRequest.of(index, Constants.PAGE_SIZE));
            case "priority" ->
                    mailRepo.findAllBySenderAndFolderAndMailDetails_PriorityContaining(userEmail, folder, key, PageRequest.of(index, Constants.PAGE_SIZE));
            case "recievers" ->
                    mailRepo.findAllBySenderAndFolderAndMailDetails_RecieversAsStringContaining(userEmail, folder, key, PageRequest.of(index, Constants.PAGE_SIZE));
            case "sender" ->
                    mailRepo.findAllBySenderAndFolderAndSenderContaining
                            (userEmail, folder, key, PageRequest.of(index, Constants.PAGE_SIZE));
            case "attachments" -> searchByAttachmentName(userEmail,folder,index,col,key);
            default -> null;
        };

        return mailSchemaBuilder.buildListOfMails(mails);
    }

    private List<Mail> searchByAttachmentName(String userEmail, String folder
            , int index, String col, String key){


        List<Mail> results = new ArrayList<>();


        List<Mail> mails = mailRepo.findAllBySenderAndFolder
                (userEmail,folder,PageRequest.of(index, Constants.PAGE_SIZE));

        for (var mail : mails){
            List<Attachment> attachments = mail.getMailDetails().getAttachments();
            List<String> atrachmentsNames = new ArrayList<>();

            for (var attachment : attachments){
                atrachmentsNames.add(attachment.getName());
            }

            String namesAsString = String.join("", atrachmentsNames).toLowerCase();
            if (namesAsString.contains(key.toLowerCase())) {
                results.add(mail);
            }

        }

        return results;
    }
}
