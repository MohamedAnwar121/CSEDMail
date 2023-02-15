package com.example.mailbe.Service.Filter.FolderCriterias;

import com.example.mailbe.Model.Mail;
import com.example.mailbe.Repository.MailRepo;
import com.example.mailbe.Response.MailSchema;
import com.example.mailbe.Service.Builder.MailSchemaBuilder;
import com.example.mailbe.Util.Constants;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public class InboxCriteria implements FolderCriteria {


    private final MailRepo mailRepo;
    private final MailSchemaBuilder mailSchemaBuilder;

    public InboxCriteria(MailRepo mailRepo,MailSchemaBuilder mailSchemaBuilder) {
        this.mailRepo = mailRepo;
        this.mailSchemaBuilder = mailSchemaBuilder;
    }


    // folder is sent as Inbox
    @Override
    public List<MailSchema> meetCriteria(String userEmail, String folder, int index,String sortCol) {
        List<Mail> mails = null;

        if (sortCol == null) {
            mails = mailRepo.findAllByRecieverAndFolderOrderByMailDetails_SendedAtInSecondsDesc(userEmail, folder,
                    PageRequest.of(index, Constants.PAGE_SIZE));
        } else {
            switch (sortCol) {
                case "message" -> mails = mailRepo.findAllByRecieverAndFolderOrderByMailDetails_MessageAsc(userEmail,
                        folder, PageRequest.of(index, Constants.PAGE_SIZE));
                case "subject" -> mails = mailRepo.findAllByRecieverAndFolderOrderByMailDetails_SubjectAsc(userEmail,
                        folder, PageRequest.of(index, Constants.PAGE_SIZE));
                case "priority" -> mails = mailRepo.findAllByRecieverAndFolderOrderByMailDetails_PriorityDesc(userEmail,
                        folder, PageRequest.of(index, Constants.PAGE_SIZE));
                case "sender" -> mails = mailRepo.findAllByRecieverAndFolderOrderBySenderDesc(userEmail,
                        folder, PageRequest.of(index, Constants.PAGE_SIZE));
                case "recievers" -> mails = mailRepo.findAllByRecieverAndFolderOrderByMailDetails_RecieversAsStringAsc(
                        userEmail, folder, PageRequest.of(index, Constants.PAGE_SIZE));
                case "sended_at" -> mails = mailRepo.findAllByRecieverAndFolderOrderByMailDetails_SendedAtInSecondsDesc
                        (userEmail, folder, PageRequest.of(index, Constants.PAGE_SIZE));
                default -> mails = null;
            }
        }
        return mailSchemaBuilder.buildListOfMails(mails);
    }


}
