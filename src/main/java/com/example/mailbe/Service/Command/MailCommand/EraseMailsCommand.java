package com.example.mailbe.Service.Command.MailCommand;

import com.example.mailbe.Model.Mail;
import com.example.mailbe.Model.MailDetails;
import com.example.mailbe.Repository.MailDetailsRepo;
import com.example.mailbe.Repository.MailRepo;
import com.example.mailbe.Service.Command.AbstractCommand;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Transactional
public class EraseMailsCommand extends AbstractCommand<Void> {

    private final List<String> mailIds;
    private final MailRepo mailRepo;
    private final MailDetailsRepo mailDetailsRepo;

    public EraseMailsCommand(List<String> mailIds, MailRepo mailRepo, MailDetailsRepo mailDetailsRepo) {
        this.mailIds = mailIds;
        this.mailRepo = mailRepo;
        this.mailDetailsRepo = mailDetailsRepo;
    }

    @Override
    public Void execute() {
        for (var mailId : mailIds) {
            Mail mail = mailRepo.findMailByMailId(mailId);
            MailDetails mailDetails = mail.getMailDetails();
            int count = mailDetails.getMails().size();

            if (count == 1) {
                mailDetailsRepo.delete(mailDetails);
                return null;
            }

            mailDetails.getMails().remove(mail);
            mailRepo.deleteMailByMailId(mailId);

            if (mail.getReciever() != null) mailDetails.getRecievers().remove(mail.getReciever());
        }
        return null;
    }
}
