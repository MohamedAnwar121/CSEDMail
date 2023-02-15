package com.example.mailbe.Service.Command.MailCommand;

import com.example.mailbe.Model.Mail;
import com.example.mailbe.Repository.MailRepo;
import com.example.mailbe.Service.Command.AbstractCommand;
import com.example.mailbe.Util.DefaultMailFolder;

import java.util.ArrayList;
import java.util.List;

public class RestoreMailCommand extends AbstractCommand<Void> {

    private final List<String> mailIds;
    private final MailRepo mailRepo;

    public RestoreMailCommand(List<String> mailIds, MailRepo mailRepo) {
        this.mailIds = mailIds;
        this.mailRepo = mailRepo;
    }

    @Override
    public Void execute() {
        List<Mail> mails = new ArrayList<>();
        for (var mailId : mailIds) mails.add(mailRepo.findMailByMailId(mailId));

        for (var mail : mails){
            if (mail.getReciever() == null) mail.setFolder(DefaultMailFolder.Sent.name());
            else mail.setFolder(DefaultMailFolder.Inbox.name());
        }
        return null;
    }
}
