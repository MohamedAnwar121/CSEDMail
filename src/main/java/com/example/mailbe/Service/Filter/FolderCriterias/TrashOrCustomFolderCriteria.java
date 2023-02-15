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

public class TrashOrCustomFolderCriteria implements FolderCriteria {

    private final MailRepo mailRepo;
    private final MailSchemaBuilder mailSchemaBuilder;

    public TrashOrCustomFolderCriteria(MailRepo mailRepo, MailSchemaBuilder mailSchemaBuilder) {
        this.mailRepo = mailRepo;
        this.mailSchemaBuilder = mailSchemaBuilder;
    }

    @Override
    public List<MailSchema> meetCriteria(String userEmail, String folder, int index, String sortCol) {
        List<Mail> mails = null;

        if (sortCol == null) {
            mails = mailRepo.findAllByFolderAndSenderOrFolderAndRecieverOrderByMailDetails_SendedAtInSecondsDesc(folder
                    , userEmail, folder, userEmail,
                    PageRequest.of(index, Constants.PAGE_SIZE));
        } else {
            switch (sortCol) {
                case "message" ->
                        mails = mailRepo.findAllByFolderAndSenderOrFolderAndRecieverOrderByMailDetails_MessageAsc
                                (folder, userEmail, folder, userEmail,
                                PageRequest.of(index, Constants.PAGE_SIZE));
                case "subject" ->
                        mails = mailRepo.findAllByFolderAndSenderOrFolderAndRecieverOrderByMailDetails_SubjectAsc
                                (folder, userEmail, folder, userEmail,
                                PageRequest.of(index, Constants.PAGE_SIZE));
                case "priority" ->
                        mails = mailRepo.findAllByFolderAndSenderOrFolderAndRecieverOrderByMailDetails_PriorityDesc
                                (folder, userEmail, folder, userEmail,
                                PageRequest.of(index, Constants.PAGE_SIZE));
                case "sender" ->
                        mails = mailRepo.findAllByFolderAndSenderOrFolderAndRecieverOrderBySenderDesc
                                (folder, userEmail, folder, userEmail,
                                PageRequest.of(index, Constants.PAGE_SIZE));
                case "recievers" ->
                        mails = mailRepo.findAllByFolderAndSenderOrFolderAndRecieverOrderByMailDetails_RecieversAsStringAsc
                                (folder, userEmail, folder, userEmail,
                                PageRequest.of(index, Constants.PAGE_SIZE));
                case "sended_at" ->
                        mails = mailRepo.findAllByFolderAndSenderOrFolderAndRecieverOrderByMailDetails_SendedAtInSecondsDesc
                                (folder, userEmail, folder, userEmail,
                                PageRequest.of(index, Constants.PAGE_SIZE));
            }
        }

        return mailSchemaBuilder.buildListOfMails(mails);
    }
}
