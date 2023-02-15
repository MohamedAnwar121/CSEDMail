package com.example.mailbe.Service.Builder;

import com.example.mailbe.Model.Mail;
import com.example.mailbe.Model.MailDetails;
import com.example.mailbe.Response.MailSchema;
import com.example.mailbe.Service.InstantStringAdapter;
import com.example.mailbe.Util.DefaultMailFolder;

import java.time.Instant;
import java.util.List;

public class ComposeBuilder extends AbstractMailBuilder {

    public ComposeBuilder(MailSchema mailResponseSchema) {
        super(mailResponseSchema);
    }

    @Override
    public MailDetails buildMailDetails(){
        MailDetails mailDetails = super.buildMailDetails();

        mailDetails.setSendedAt(InstantStringAdapter.instantToFormattedString(Instant.now()));
        mailDetails.setSendedAtInSeconds(InstantStringAdapter.instantToEpochSeconds(Instant.now()));

        mailDetails.setMails(buildMails());
        return mailDetails;
    }

    @Override
    protected List<Mail> buildMails(){
        List<Mail> mails = super.buildMails();
        for (var mail : mails) mail.setFolder(DefaultMailFolder.Inbox.name());
        mails.add(createSentMail());
        return mails;
    }

    private Mail createSentMail(){
        Mail mail = new Mail();
        mail.setSender(mailResponseSchema.getSender());
        mail.setMailDetails(mailDetails);
        mail.setFolder(DefaultMailFolder.Sent.name());
        return mail;
    }
}
