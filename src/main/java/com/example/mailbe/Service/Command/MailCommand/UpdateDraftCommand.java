package com.example.mailbe.Service.Command.MailCommand;

import com.example.mailbe.Model.Mail;
import com.example.mailbe.Repository.MailDetailsRepo;
import com.example.mailbe.Repository.MailRepo;
import com.example.mailbe.Response.MailSchema;
import com.example.mailbe.Service.Command.AbstractCommand;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public class UpdateDraftCommand extends AbstractCommand<String> {

    private final MailRepo mailRepo;
    private final MailDetailsRepo mailDetailsRepo;
    private final MailSchema mailSchema;

    public UpdateDraftCommand(MailRepo mailRepo, MailDetailsRepo mailDetailsRepo, MailSchema mailSchema) {
        this.mailRepo = mailRepo;
        this.mailDetailsRepo = mailDetailsRepo;
        this.mailSchema = mailSchema;
    }

    @Override
    public String execute() {


        mailDetailsRepo.delete(mailRepo.findMailByMailId(mailSchema.getMail_id()).getMailDetails());
        AbstractCommand<String> saveToDraftCommand = new SaveToDraftCommand(mailSchema,mailDetailsRepo, mailRepo);
        return saveToDraftCommand.execute();
    }
}
