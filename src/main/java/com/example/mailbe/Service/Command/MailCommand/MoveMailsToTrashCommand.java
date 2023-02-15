package com.example.mailbe.Service.Command.MailCommand;

import com.example.mailbe.Model.Mail;
import com.example.mailbe.Repository.MailRepo;
import com.example.mailbe.Service.Command.AbstractCommand;
import com.example.mailbe.Util.DefaultMailFolder;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;


@Transactional
public class MoveMailsToTrashCommand extends AbstractCommand<Void> {

    private final List<String> mailIds;
    private final MailRepo mailRepo;

    public MoveMailsToTrashCommand(List<String> mailIds, MailRepo mailRepo) {
        this.mailIds = mailIds;
        this.mailRepo = mailRepo;
    }

    @Override
    public Void execute() {
        for (var mailId : mailIds) {
            Mail mail = mailRepo.findMailByMailId(mailId);
            mail.setFolder(DefaultMailFolder.Trash.name());
            mail.setDeletedAt(Instant.now());
        }
        return null;
    }
}
