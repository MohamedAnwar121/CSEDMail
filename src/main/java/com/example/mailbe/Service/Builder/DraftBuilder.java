package com.example.mailbe.Service.Builder;

import com.example.mailbe.Model.Mail;
import com.example.mailbe.Model.MailDetails;
import com.example.mailbe.Response.MailSchema;
import com.example.mailbe.Util.DefaultMailFolder;

import java.util.ArrayList;
import java.util.List;

public class DraftBuilder extends AbstractMailBuilder {

    public DraftBuilder(MailSchema mailResponseSchema) {
        super(mailResponseSchema);
    }

    @Override
    public MailDetails buildMailDetails(){
        MailDetails mailDetails = super.buildMailDetails();
        mailDetails.setMails(buildMails());
        return mailDetails;
    }

    @Override
    protected List<Mail> buildMails(){
        List<Mail> mails = new ArrayList<>();
        Mail mail = new Mail();
        mail.setSender(mailResponseSchema.getSender());
        mail.setMailDetails(mailDetails);
        mail.setFolder(DefaultMailFolder.Draft.name());
        mails.add(mail);
        return mails;
    }
}
