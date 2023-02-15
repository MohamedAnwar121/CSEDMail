package com.example.mailbe.Service.Filter.FolderCriterias;

import com.example.mailbe.Model.Attachment;
import com.example.mailbe.Model.Mail;
import com.example.mailbe.Repository.MailRepo;
import com.example.mailbe.Response.MailSchema;
import com.example.mailbe.Service.Builder.MailSchemaBuilder;
import com.example.mailbe.Util.Constants;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;

public class SentOrDraftCriteria implements FolderCriteria {

    private final MailRepo mailRepo;
    private final MailSchemaBuilder mailSchemaBuilder;

    public SentOrDraftCriteria(MailRepo mailRepo, MailSchemaBuilder mailSchemaBuilder) {
        this.mailRepo = mailRepo;
        this.mailSchemaBuilder = mailSchemaBuilder;
    }

    @Override
    public List<MailSchema> meetCriteria(String userEmail, String folder, int index, String sortCol) {
        List<Mail> mails = null;

        if (sortCol == null) {
            mails = mailRepo.findAllBySenderAndFolderOrderByMailDetails_SendedAtInSecondsDesc(userEmail, folder,
                    PageRequest.of(index, Constants.PAGE_SIZE));
        } else {
            switch (sortCol) {
                case "message" -> mails = mailRepo.findAllBySenderAndFolderOrderByMailDetails_MessageAsc(userEmail,
                        folder, PageRequest.of(index, Constants.PAGE_SIZE));
                case "subject" -> mails = mailRepo.findAllBySenderAndFolderOrderByMailDetails_SubjectAsc(userEmail,
                        folder, PageRequest.of(index, Constants.PAGE_SIZE));
                case "priority" -> mails = mailRepo.findAllBySenderAndFolderOrderByMailDetails_PriorityDesc(userEmail,
                        folder, PageRequest.of(index, Constants.PAGE_SIZE));
                case "sender" -> mails = mailRepo.findAllBySenderAndFolderOrderBySenderDesc(userEmail,
                        folder, PageRequest.of(index, Constants.PAGE_SIZE));
                case "recievers" -> mails = mailRepo.findAllBySenderAndFolderOrderByMailDetails_RecieversAsStringAsc(
                        userEmail, folder, PageRequest.of(index, Constants.PAGE_SIZE));
                case "sended_at" -> mails = mailRepo.findAllBySenderAndFolderOrderByMailDetails_SendedAtInSecondsDesc
                        (userEmail, folder, PageRequest.of(index, Constants.PAGE_SIZE));
            }
        }

        return mailSchemaBuilder.buildListOfMails(mails);
    }
}
