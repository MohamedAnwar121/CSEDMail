package com.example.mailbe.Service.Builder;

import com.example.mailbe.Model.Mail;
import com.example.mailbe.Model.MailDetails;
import com.example.mailbe.Response.MailSchema;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Scope("singleton")
@Component
public class MailSchemaBuilder {

    public MailSchemaBuilder(){}

    public MailSchema buildMailSchema(Mail mail){
        
        MailDetails mailDetails = mail.getMailDetails();
        MailSchema mailSchema = new MailSchema();

        mailSchema.setMail_id(mail.getMailId());
        mailSchema.setFolder(mail.getFolder());
        mailSchema.setSender(mail.getSender());
        mailSchema.setDeleted_at(mail.getDeletedAt() == null ? null : mail.getDeletedAt().toString());


        mailSchema.setRecievers(mailDetails.getRecievers());
        mailSchema.setMessage(mailDetails.getMessage());
        mailSchema.setSubject(mailDetails.getSubject());
        mailSchema.setPriority(mailDetails.getPriority());
        mailSchema.setSended_at(mailDetails.getSendedAtInSeconds());

        //mailSchema.setSended_at(mailDetails.getSendedAt() == null ? null : mailDetails.getSendedAt().toString());

        return mailSchema;
    }

    public List<MailSchema> buildListOfMails(List<Mail> mails){
        List<MailSchema> mailSchemas = new ArrayList<>();

        for (var mail : mails){
            mailSchemas.add(buildMailSchema(mail));
        }

        return mailSchemas;

    }

}
