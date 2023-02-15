package com.example.mailbe.Service.Command.MailCommand;

import com.example.mailbe.Repository.MailRepo;
import com.example.mailbe.Service.Command.AbstractCommand;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Transactional
public class MoveMailsToCommand extends AbstractCommand<Void> {

    private final List<String> mailIds;
    private final String newFolderName;
    private final MailRepo mailRepo;

    public MoveMailsToCommand(List<String> mailIds, String newFolderName, MailRepo mailRepo) {
        this.mailIds = mailIds;
        this.newFolderName = newFolderName;
        this.mailRepo = mailRepo;
    }


    @Override
    public Void execute() {
        for (var mailId : mailIds) {
            mailRepo.findMailByMailId(mailId).setFolder(newFolderName);
        }
        return null;
    }
}
