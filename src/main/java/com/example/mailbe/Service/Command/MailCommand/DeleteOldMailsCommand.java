package com.example.mailbe.Service.Command.MailCommand;

import com.example.mailbe.Model.Mail;
import com.example.mailbe.Repository.MailDetailsRepo;
import com.example.mailbe.Repository.MailRepo;
import com.example.mailbe.Service.Command.AbstractCommand;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Transactional
public class DeleteOldMailsCommand extends AbstractCommand<Void> {

    private final MailRepo mailRepo;
    private final MailDetailsRepo mailDetailsRepo;

    public DeleteOldMailsCommand(MailRepo mailRepo, MailDetailsRepo mailDetailsRepo) {
        this.mailRepo = mailRepo;
        this.mailDetailsRepo = mailDetailsRepo;
    }

    @Override
    public Void execute() {
        Instant midNight = Instant.now().minus(30, ChronoUnit.DAYS).truncatedTo(ChronoUnit.DAYS);
        List<Mail> mails = mailRepo.findAllOldMails(midNight);

        List<String> mailIds = mails.stream().map(Mail::getMailId).collect(Collectors.toList());
        
        AbstractCommand<Void> eraseMailsCommand = new EraseMailsCommand(mailIds,mailRepo,mailDetailsRepo);
        return eraseMailsCommand.execute();
    }

    /*List<String> mailIds = new ArrayList<>();
        for (var mail : mails){
            mailIds.add(mail.getMailId());
        }*/

}
