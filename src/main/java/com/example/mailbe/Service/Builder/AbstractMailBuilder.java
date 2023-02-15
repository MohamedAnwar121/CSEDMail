package com.example.mailbe.Service.Builder;

import com.example.mailbe.Model.Mail;
import com.example.mailbe.Model.MailDetails;
import com.example.mailbe.Response.MailSchema;
import org.springframework.context.annotation.Scope;
import org.springframework.data.util.Pair;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Scope("singleton")
public class AbstractMailBuilder {


    protected final MailSchema mailResponseSchema;
    protected MailDetails mailDetails;

    public AbstractMailBuilder(MailSchema mailResponseSchema) {
        this.mailResponseSchema = mailResponseSchema;
    }

    public MailDetails buildMailDetails(){
        mailDetails = new MailDetails();
        mailDetails.setMessage(mailResponseSchema.getMessage());
        mailDetails.setSubject(mailResponseSchema.getSubject());
        mailDetails.setPriority(mailResponseSchema.getPriority());
        mailDetails.setRecievers(mailResponseSchema.getRecievers());
        mailDetails.setRecieversAsString(mailResponseSchema.getRecievers());
        return mailDetails;
    }

    protected List<Mail> buildMails(){

        List<Mail> mails = new ArrayList<>();
        HashSet<String> recievers = mailResponseSchema.getRecievers();

        for (String reciever : recievers) {
            Mail mail = new Mail();
            mail.setSender(mailResponseSchema.getSender());
            mail.setReciever(reciever);
            mail.setMailDetails(mailDetails);
            mails.add(mail);
        }

        return mails;
    }

}
