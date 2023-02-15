package com.example.mailbe.Service.Command.MailCommand;

import com.example.mailbe.Model.MailDetails;
import com.example.mailbe.Repository.MailDetailsRepo;
import com.example.mailbe.Repository.MailRepo;
import com.example.mailbe.Response.MailSchema;
import com.example.mailbe.Service.Builder.AbstractMailBuilder;
import com.example.mailbe.Service.Command.AbstractCommand;
import com.example.mailbe.Service.Factory.MailBuilderFactory;
import com.example.mailbe.Service.ValidationService;
import org.springframework.transaction.annotation.Transactional;


@Transactional
public class ComposeCommand extends AbstractCommand<String> {

    private final ValidationService validationService;
    private final MailSchema mailSchema;
    private final MailDetailsRepo mailDetailsRepo;
    private final MailRepo mailRepo;

    public ComposeCommand(ValidationService validationService, MailSchema mailSchema
            , MailDetailsRepo mailDetailsRepo, MailRepo mailRepo) {
        this.validationService = validationService;
        this.mailSchema = mailSchema;
        this.mailDetailsRepo = mailDetailsRepo;
        this.mailRepo = mailRepo;
    }

    @Override
    public String execute() {
        validationService.checkEmailsInDB(mailSchema.getRecievers());

        if (mailSchema.getMail_id() != null) {
            mailDetailsRepo.delete(mailRepo.findMailByMailId(mailSchema.getMail_id()).getMailDetails());
        }

        MailBuilderFactory builderFactory = new MailBuilderFactory(mailSchema);
        AbstractMailBuilder builder = builderFactory.getBuilder("Compose");

        MailDetails mailDetails = builder.buildMailDetails();

        MailDetails newMailDetails = mailDetailsRepo.saveAndFlush(mailDetails);
        return newMailDetails.getMailDetailsId();
    }
}
