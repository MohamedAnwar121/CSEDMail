package com.example.mailbe.Service.Command.MailCommand;

import com.example.mailbe.Model.Mail;
import com.example.mailbe.Model.MailDetails;
import com.example.mailbe.Repository.MailDetailsRepo;
import com.example.mailbe.Repository.MailRepo;
import com.example.mailbe.Response.MailSchema;
import com.example.mailbe.Service.Builder.AbstractMailBuilder;
import com.example.mailbe.Service.Command.AbstractCommand;
import com.example.mailbe.Service.Factory.MailBuilderFactory;
import org.springframework.transaction.annotation.Transactional;


@Transactional
public class SaveToDraftCommand extends AbstractCommand<String> {

    private final MailSchema mailSchema;
    private final MailDetailsRepo mailDetailsRepo;
    private final MailRepo mailRepo;

    public SaveToDraftCommand(MailSchema mailSchema, MailDetailsRepo mailDetailsRepo, MailRepo mailRepo) {
        this.mailSchema = mailSchema;
        this.mailDetailsRepo = mailDetailsRepo;
        this.mailRepo = mailRepo;
    }

    @Override
    public String execute() {

        /*if (mailSchema.getMail_id() != null){

            //MailDetails mailDetails = mailDetailsRepo.findMailDetailsByMailDetailsId(mailSchema.getMail_id());

            AbstractCommand<String> updateDraftCommand =
                    new UpdateDraftCommand(mailRepo,mailDetailsRepo,mailSchema);
            return updateDraftCommand.execute();
        }*/

        MailBuilderFactory builderFactory = new MailBuilderFactory(mailSchema);
        AbstractMailBuilder builder = builderFactory.getBuilder("Draft");
        MailDetails mailDetails = builder.buildMailDetails();

        MailDetails newMailDetails = mailDetailsRepo.saveAndFlush(mailDetails);
        return newMailDetails.getMailDetailsId();
    }
}
